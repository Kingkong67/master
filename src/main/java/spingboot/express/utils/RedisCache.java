package spingboot.express.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: wanghu
 * @Date: Create in 2021 02 2021/2/16
 * @return:
 * @throws:
 */
@Component
public class RedisCache {
    @Autowired
    public RedisTemplate redisTemplate;

    public RedisCache() {
    }

    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取缓存的值
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

        public <T> ValueOperations<String, T> setCacheObject(String key, T value) {
        ValueOperations<String, T> operation = this.redisTemplate.opsForValue();
        operation.set(key, value);
        return operation;
    }

    public <T> ValueOperations<String, T> setCacheObject(String key, T value, Integer timeout, TimeUnit timeUnit) {
        ValueOperations<String, T> operation = this.redisTemplate.opsForValue();
        operation.set(key, value, (long)timeout, timeUnit);
        return operation;
    }

    public <T> T getCacheObject(String key) {
        ValueOperations<String, T> operation = this.redisTemplate.opsForValue();
        return operation.get(key);
    }

    public void deleteObject(String key) {
        this.redisTemplate.delete(key);
    }

    public void deleteObject(Collection collection) {
        this.redisTemplate.delete(collection);
    }

    public <T> ListOperations<String, T> setCacheList(String key, List<T> dataList) {
        ListOperations listOperation = this.redisTemplate.opsForList();
        if (null != dataList) {
            int size = dataList.size();

            for(int i = 0; i < size; ++i) {
                listOperation.leftPush(key, dataList.get(i));
            }
        }

        return listOperation;
    }

    public <T> List<T> getCacheList(String key) {
        List<T> dataList = new ArrayList();
        ListOperations<String, T> listOperation = this.redisTemplate.opsForList();
        Long size = listOperation.size(key);

        for(int i = 0; (long)i < size; ++i) {
            dataList.add(listOperation.index(key, (long)i));
        }

        return dataList;
    }

    public <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = this.redisTemplate.boundSetOps(key);
        Iterator it = dataSet.iterator();

        while(it.hasNext()) {
            setOperation.add((T) new Object[]{it.next()});
        }

        return setOperation;
    }

    public <T> Set<T> getCacheSet(String key) {
        new HashSet();
        BoundSetOperations<String, T> operation = this.redisTemplate.boundSetOps(key);
        Set<T> dataSet = operation.members();
        return dataSet;
    }

    public <T> HashOperations<String, String, T> setCacheMap(String key, Map<String, T> dataMap) {
        HashOperations hashOperations = this.redisTemplate.opsForHash();
        if (null != dataMap) {
            Iterator var4 = dataMap.entrySet().iterator();

            while(var4.hasNext()) {
                Map.Entry<String, T> entry = (Map.Entry)var4.next();
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }

        return hashOperations;
    }

    public <T> Map<String, T> getCacheMap(String key) {
        Map<String, T> map = this.redisTemplate.opsForHash().entries(key);
        return map;
    }

    public Collection<String> keys(String pattern) {
        return this.redisTemplate.keys(pattern);
    }
}
