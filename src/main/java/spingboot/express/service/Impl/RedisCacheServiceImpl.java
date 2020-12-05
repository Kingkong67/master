package spingboot.express.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import spingboot.express.service.RedisCacheService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存service实现层
 */
@Service
public class RedisCacheServiceImpl implements RedisCacheService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Integer> integerRedisTemplate;

    @Autowired
    private RedisTemplate<String, Object> objectRedisTemplate;

    private Map<Class, RedisSerializer> serializerMap = new ConcurrentHashMap<>();

    @Override
    public RedisSerializer getSerializer(Class clazz) {
        RedisSerializer serializer = serializerMap.get(clazz);
        if (serializer == null) {
            if (String.class.equals(clazz)) {
                serializer = new StringRedisSerializer();
            } else {
                serializer = new Jackson2JsonRedisSerializer<>(clazz);
            }
            serializerMap.put(clazz, serializer);
        }
        return serializer;
    }

    @Override
    public String getValue(String key) {
       return getObject(key, String.class);
    }

    @Override
    public void saveValueWithExpireTime(String key, String value, long expireTime) {
        saveObjectWithExpireTime(key, value, expireTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public void saveValue(String key, Object value) {
        saveObject(key, value);
    }

    @Override
    public <T> void saveObjectWithExpireTime(String key, T obj, long expireTime, TimeUnit timeUnit) {
        saveObject(key, obj);
        stringRedisTemplate.expire(key, expireTime, timeUnit);

    }

    @Override
    public <T> void saveObject(String key, T obj) {
        if (key == null || obj == null) {
            return;
        }
        stringRedisTemplate.execute((RedisCallback<String>) connection -> {
            byte[] value = getSerializer(obj.getClass()).serialize(obj);
            connection.set(Objects.requireNonNull(stringRedisTemplate.getStringSerializer().serialize(key)), value);
            return null;
        });
    }

    @Override
    public <T> T getObject(String key, Class<T> valueClass) {
        return stringRedisTemplate.execute((RedisCallback<T>) connection -> {
            byte[] k = stringRedisTemplate.getStringSerializer().serialize(key);
            if (connection.exists(k)) {
                byte[] value = connection.get(k);
                return (T) getSerializer(valueClass).deserialize(value);
            }
            return null;
        });
    }

    @Override
    public <T> List<T> getListData(String key, Class<T> clazz) {
        return stringRedisTemplate.execute((RedisCallback<List<T>>) connection -> {
            List<T> list = new ArrayList<>();
            List<byte[]> values = connection.lRange(stringRedisTemplate.getStringSerializer().serialize(key), 0, -1);
            for (byte[] value : values) {
                T obj = (T) getSerializer(clazz).deserialize(value);
                list.add(obj);
            }
            return list;
        });
    }

    @Override
    public <T> void saveListData(String key, List<T> list, Class<T> clazz) {
        setListDataWithExpireTime(key, list, 0, clazz);
    }

    @Override
    public <T> void setListDataWithExpireTime(String key, List<T> list, long expireTime, Class<T> clazz) {
        if (list.isEmpty()) {
            return;
        }
        stringRedisTemplate.execute((RedisCallback<List<T>>) connection -> {
            byte[][] values = new byte[list.size()][];
            int index = 0;
            for (T obj : list) {
                byte[] value = getSerializer(clazz).serialize(obj);
                values[index] = value;
                index++;
            }
            connection.rPush(stringRedisTemplate.getStringSerializer().serialize(key), values);
            return null;
        });

        if (expireTime > 0) {
            stringRedisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        }
    }

    @Override
    public <T> void hashSaveWithExpireTime(String key, String field, byte[] bytesValue, long expireTime) {
        HashOperations<String, String, byte[]> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.put(key, field, bytesValue);
        stringRedisTemplate.expire(key, expireTime, TimeUnit.SECONDS);

    }

    @Override
    public <T> void hashSave(String key, String field, byte[] bytesValue) {
        HashOperations<String, String, byte[]> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.put(key, field, bytesValue);
    }

    @Override
    public <T> void hashSaveWithExpireTime(String key, String field, T value, long expireTime, Class<T> clazz) {
        hashSaveWithExpireTime(key, field, getSerializer(clazz).serialize(value), expireTime);

    }

    @Override
    public <T> void hashSave(String key, String field, T value, Class<T> clazz) {
        hashSave(key, field, getSerializer(clazz).serialize(value));
    }

    @Override
    public <T> T hashGet(String key, String field, Class<T> clazz) {
        return stringRedisTemplate.execute((RedisCallback<T>) connection -> {
            RedisSerializer<String> stringRedisSerializer = stringRedisTemplate.getStringSerializer();
            byte[] keyByte = stringRedisSerializer.serialize(key);
            byte[] fieldByte = stringRedisSerializer.serialize(field);
            if (connection.hExists(keyByte, fieldByte)) {
                byte[] value = connection.hGet(keyByte, fieldByte);
                return (T) getSerializer(clazz).deserialize(value);
            } else {
                return null;
            }
        });
    }

    @Override
    public <K, V> Map<K, V> hashGetAll(String key, Class<K> fieldClazz, Class<V> valueClazz) {
        return stringRedisTemplate.execute((RedisCallback<Map<K, V>>) connection -> {
            Map<K, V> map = new HashMap<>();
            RedisSerializer<String> stringRedisSerializer = stringRedisTemplate.getStringSerializer();
            byte[] keyByte = stringRedisSerializer.serialize(key);
            Map<byte[], byte[]> result = connection.hGetAll(keyByte);
            for (byte[] fieldByte : result.keySet()) {
                map.put((K) getSerializer(fieldClazz).deserialize(fieldByte), (V) getSerializer(valueClazz).deserialize(fieldByte));
            }
            return map;
        });
    }

    @Override
    public <K, V> void hashMultiSave(String key, Map<K, V> map, Class<K> fieldClazz, Class<V> valueClazz) {
        stringRedisTemplate.execute((RedisCallback<Map<K, V>>) connection -> {
            for (K field : map.keySet()) {
                connection.hSet(stringRedisTemplate.getStringSerializer().serialize(key),
                        getSerializer(fieldClazz).serialize(field), getSerializer(valueClazz).serialize(map.get(field)));
            }
            return null;
        });
    }

    @Override
    public void delete(String key) {
        stringRedisTemplate.execute((RedisCallback<Long>) connection -> connection
                .del(stringRedisTemplate.getStringSerializer().serialize(key)));
    }

    @Override
    public void hashDelete(String key, String field) {
        stringRedisTemplate.execute((RedisCallback<Long>) connection -> connection
                .hDel(stringRedisTemplate.getStringSerializer().serialize(key), stringRedisTemplate.getStringSerializer().serialize(key)));
    }

    @Override
    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    @Override
    public void saveInteger(String key, int value) {
        integerRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Integer getInteger(String key) {
        return integerRedisTemplate.opsForValue().get(key);
    }

    @Override
    public Integer incrementAndGet(String key) {
        return integerRedisTemplate.opsForValue().increment(key, 1).intValue();
    }
}
