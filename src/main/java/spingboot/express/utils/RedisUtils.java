package spingboot.express.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
public class RedisUtils {
    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);
    public static String RUN_MESSAGE = "请开启Redis服务,还有Redis密码配置是否有误，而且密码不能为空（为空时Redis服务会连接不上）。";
    private static RedisTemplate redisTemplate = (RedisTemplate)SpringUtils.getBean("redisTemplate");
    private static StringRedisTemplate stringRedisTemplate = (StringRedisTemplate)SpringUtils.getBean("stringRedisTemplate");
    private static Environment env = (Environment)SpringUtils.getBean(Environment.class);
    public static String SHIRO_REDIS = "shiro_redis_cache";
    public static String SHIRO_REDIS_OBJECT = "org.apache.shiro.subject.SimplePrincipalCollection";
    public static String KEY_PREFIX;
    public static Long expireTime;

    public RedisUtils() {
    }

    public static String getExpire() {
        return expireTime != null ? expireTime / 60L + "分钟" : "0分钟";
    }

    public static long getExpireTime(String key) {
        if (!run()) {
            return 0L;
        } else {
            long time = redisTemplate.getExpire(key, TimeUnit.SECONDS);
            return time;
        }
    }

    public static void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }

    }

    public static void batchDel(String... pattern) {
        String[] var1 = pattern;
        int var2 = pattern.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String kp = var1[var3];
            redisTemplate.delete(redisTemplate.keys(kp + "*"));
        }

    }

    public static Integer getInt(String key) {
        String value = (String)stringRedisTemplate.boundValueOps(key).get();
        return StringUtilsExtend.isNotBlank(value) ? Integer.valueOf(value) : null;
    }

    public static String getStr(String key) {
        return (String)stringRedisTemplate.boundValueOps(key).get();
    }

    public static String getStr(String key, boolean retain) {
        String value = (String)stringRedisTemplate.boundValueOps(key).get();
        if (!retain) {
            redisTemplate.delete(key);
        }

        return value;
    }

    public static Object getObj(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    public static Object getObj(String key, boolean retain) {
        Object obj = redisTemplate.boundValueOps(key).get();
        if (!retain) {
            redisTemplate.delete(key);
        }

        return obj;
    }

    public static <T> T get(String key, Class<T> clazz) {
        return (T) redisTemplate.boundValueOps(key).get();
    }

    public static <T> T getJson(String key, Class<T> clazz) {
        return JSON.parseObject((String)stringRedisTemplate.boundValueOps(key).get(), clazz);
    }

    public static void set(String key, Object value, long time) {
        if (value.getClass().equals(String.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Integer.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Double.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Float.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Short.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Long.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else if (value.getClass().equals(Boolean.class)) {
            stringRedisTemplate.opsForValue().set(key, value.toString());
        } else {
            redisTemplate.opsForValue().set(key, value);
        }

        if (time > 0L) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }

    }

    public static void setJson(String key, Object value, long time) {
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(value));
        if (time > 0L) {
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }

    }

    public static void setJsonField(String key, String field, String value) {
        JSONObject obj = JSON.parseObject((String)stringRedisTemplate.boundValueOps(key).get());
        obj.put(field, value);
        stringRedisTemplate.opsForValue().set(key, obj.toJSONString());
    }

    public static double decr(String key, double by) {
        return redisTemplate.opsForValue().increment(key, -by);
    }

    public static double incr(String key, double by) {
        return redisTemplate.opsForValue().increment(key, by);
    }

    public static double getDouble(String key) {
        String value = (String)stringRedisTemplate.boundValueOps(key).get();
        return StringUtilsExtend.isNotBlank(value) ? Double.valueOf(value) : 0.0D;
    }

    public static void setDouble(String key, double value, long time) {
        stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
        if (time > 0L) {
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }

    }

    public static void setInt(String key, int value, long time) {
        stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
        if (time > 0L) {
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }

    }

    public static <T> void setMap(String key, Map<String, T> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public static <T> void setMap(String key, T obj, long time) {
        Map<String, String> map = (Map)JSON.parseObject(JSON.toJSONString(obj), Map.class);
        redisTemplate.opsForHash().putAll(key, map);
    }

    public static <T> void addMap(String key, Map<String, T> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public static void addMap(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    public static <T> void addMap(String key, String field, T obj) {
        redisTemplate.opsForHash().put(key, field, obj);
    }

    public static <T> Map<String, T> mget(String key, Class<T> clazz) {
        BoundHashOperations<String, String, T> boundHashOperations = redisTemplate.boundHashOps(key);
        return boundHashOperations.entries();
    }

    public static <T> T getMap(String key, Class<T> clazz) {
        BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(key);
        Map<String, String> map = boundHashOperations.entries();
        return JSON.parseObject(JSON.toJSONString(map), clazz);
    }

    public static <T> T getMapField(String key, String field, Class<T> clazz) {
        return (T) redisTemplate.boundHashOps(key).get(field);
    }

    public void delMapField(String key, String... field) {
        BoundHashOperations<String, String, ?> boundHashOperations = redisTemplate.boundHashOps(key);
        boundHashOperations.delete(field);
    }

    public static void expire(String key, long time) {
        if (time > 0L) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }

    }

    public static void sadd(String key, String... value) {
        redisTemplate.boundSetOps(key).add(value);
    }

    public static void srem(String key, String... value) {
        redisTemplate.boundSetOps(key).remove(value);
    }

    public static void srename(String oldkey, String newkey) {
        redisTemplate.boundSetOps(oldkey).rename(newkey);
    }

    public static void setIntForPhone(String key, Object value, int time) {
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(value));
        if (time > 0) {
            stringRedisTemplate.expire(key, (long)time, TimeUnit.SECONDS);
        }

    }

    public static Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public void remove(String... keys) {
        if (run()) {
            try {
                String[] var2 = keys;
                int var3 = keys.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    String key = var2[var4];
                    remove(key);
                }
            } catch (Exception var6) {
                logger.error("RedisUtils remove:" + RUN_MESSAGE + var6.getMessage(), RUN_MESSAGE + var6.getMessage());
            }

        }
    }

    public static void removePattern(String pattern) {
        if (run()) {
            if (listFlush()) {
                try {
                    if (pattern == null) {
                        pattern = "";
                    }

                    Set<String> keys = getKyes(pattern);
                    if (keys.size() > 0) {
                        redisTemplate.delete(keys);
                    }
                } catch (Exception var2) {
                    logger.error("RedisUtils removePattern:" + RUN_MESSAGE + var2.getMessage(), RUN_MESSAGE + var2.getMessage());
                }

            }
        }
    }

    public static void removePatternShiroReids(String pattern) {
        if (run()) {
            if (listFlush()) {
                try {
                    if (pattern == null) {
                        pattern = "";
                    }

                    Set<String> keys = getKyesShiroReids(pattern);
                    if (keys.size() > 0) {
                        stringRedisTemplate.delete(keys);
                    }
                } catch (Exception var2) {
                    logger.error("RedisUtils removePattern:" + RUN_MESSAGE + var2.getMessage(), RUN_MESSAGE + var2.getMessage());
                }

            }
        }
    }

    public static Set<String> getKyes(String pattern) {
        if (!run()) {
            return null;
        } else {
            try {
                if (pattern == null) {
                    pattern = "";
                }

                Set<String> keys = stringRedisTemplate.keys("*" + pattern);
                Set<String> keysnew = new HashSet();
                Iterator it = keys.iterator();

                while(it.hasNext()) {
                    keysnew.add(((String)it.next()).substring(7));
                }

                return keysnew;
            } catch (Exception var4) {
                logger.error("RedisUtils getKyes:" + RUN_MESSAGE + var4.getMessage(), var4.getMessage());
                return null;
            }
        }
    }

    public static Set<String> getKyesShiroReids(String pattern) {
        if (!run()) {
            return null;
        } else {
            try {
                if (pattern == null) {
                    pattern = "";
                }

                Set<String> keys = stringRedisTemplate.keys("*" + pattern);
                Set<String> keysnew = new HashSet();
                Iterator it = keys.iterator();

                while(it.hasNext()) {
                    String tr = (String)it.next();
                    if (tr.contains(SHIRO_REDIS)) {
                        keysnew.add(tr);
                    } else if (tr.contains(SHIRO_REDIS_OBJECT)) {
                        keysnew.add(tr.substring(8));
                    }
                }

                return keysnew;
            } catch (Exception var5) {
                logger.error("RedisUtils getKyes:" + RUN_MESSAGE + var5.getMessage(), var5.getMessage());
                return null;
            }
        }
    }

    public static Set<String> getKyesAll() {
        if (!run()) {
            return null;
        } else {
            try {
                Set<String> keys = stringRedisTemplate.keys("*");
                Set<String> keysnew = new HashSet();
                Iterator it = keys.iterator();

                while(it.hasNext()) {
                    keysnew.add((String) it.next());
                }

                return keysnew;
            } catch (Exception var3) {
                logger.error("RedisUtils getKyes:" + RUN_MESSAGE + var3.getMessage(), var3.getMessage());
                return null;
            }
        }
    }

    public static int getCount() {
        if (!run()) {
            return 0;
        } else {
            try {
                Set<String> keys = stringRedisTemplate.keys("*");
                return keys.size();
            } catch (Exception var1) {
                logger.error("RedisUtils getCount:" + RUN_MESSAGE + var1.getMessage(), var1.getMessage());
                return 0;
            }
        }
    }

    public static int getCountShiro() {
        if (run() && isShiroRedis()) {
            try {
                Set<String> keys = stringRedisTemplate.keys(SHIRO_REDIS + "*");
                return keys.size();
            } catch (Exception var1) {
                logger.error("RedisUtils getCount:" + RUN_MESSAGE + var1.getMessage(), var1.getMessage());
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static void remove(String key) {
        if (run()) {
            try {
                if (key.contains(SHIRO_REDIS)) {
                    stringRedisTemplate.delete(key);
                } else {
                    redisTemplate.delete(key);
                }
            } catch (Exception var2) {
                logger.error("RedisUtils exists:" + RUN_MESSAGE + var2.getMessage(), RUN_MESSAGE + var2.getMessage());
            }

        }
    }

    public static boolean exists(String key) {
        if (!run()) {
            return false;
        } else {
            boolean retuslt = false;

            try {
                if (key.contains(SHIRO_REDIS)) {
                    retuslt = stringRedisTemplate.hasKey(key);
                } else {
                    retuslt = redisTemplate.hasKey(key);
                }
            } catch (Exception var3) {
                logger.error("RedisUtils exists:" + RUN_MESSAGE + var3.getMessage(), RUN_MESSAGE + var3.getMessage());
            }

            return retuslt;
        }
    }

    public static Object get(String key) {
        if (!run()) {
            return null;
        } else {
            Object result = null;

            try {
                ValueOperations operationsString;
                if (key.contains(SHIRO_REDIS)) {
                    operationsString = stringRedisTemplate.opsForValue();
                    result = operationsString.get(key);
                } else {
                    operationsString = redisTemplate.opsForValue();
                    result = operationsString.get(key);
                }

                return result;
            } catch (Exception var3) {
                logger.error("RedisUtils get:" + RUN_MESSAGE + var3.getMessage(), RUN_MESSAGE + var3.getMessage());
                return result;
            }
        }
    }

    public static boolean set(String key, Object value) {
        if (!run()) {
            return false;
        } else {
            boolean result = false;

            try {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                operations.set(key, value);
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
                result = true;
            } catch (Exception var4) {
                logger.error("RedisUtils set:" + RUN_MESSAGE + var4.getMessage(), RUN_MESSAGE + var4.getMessage());
            }

            return result;
        }
    }

    public static boolean set(String key, Object value, Long expireTime) {
        if (!run()) {
            return false;
        } else {
            boolean result = false;

            try {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                operations.set(key, value);
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
                result = true;
            } catch (Exception var5) {
                logger.error("RedisUtils set:" + RUN_MESSAGE + var5.getMessage(), RUN_MESSAGE + var5.getMessage());
            }

            return result;
        }
    }

    public boolean set(String key, Object value, Long expireTime, TimeUnit unit) {
        if (!run()) {
            return false;
        } else {
            boolean result = false;

            try {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                operations.set(key, value);
                redisTemplate.expire(key, expireTime, unit);
                result = true;
            } catch (Exception var7) {
                logger.error("RedisUtils set:" + RUN_MESSAGE + var7.getMessage(), RUN_MESSAGE + var7.getMessage());
            }

            return result;
        }
    }

    private static boolean run() {
        if (env.getProperty("spring.redis.run") == "true") {
            return true;
        } else {
            logger.info("未启用spring.redis.run");
            return false;
        }
    }

    public static boolean isRun() {
        if (env.getProperty("spring.redis.run") == "true") {
            return true;
        } else {
            logger.info("未启用spring.redis.run");
            return false;
        }
    }

    public static boolean isShiroRedis() {
        if (!isRun()) {
            return false;
        } else if (env.getProperty("shiro.redis.run") != "true") {
            logger.info("未启用shiro.redis");
            return false;
        } else {
            return true;
        }
    }

    private static boolean listFlush() {
        if (env.getProperty("spring.redis.listFlush") == "true") {
            return true;
        } else {
            logger.info("未启用spring.redis.listFlush");
            return false;
        }
    }

    static {
        KEY_PREFIX = env.getProperty("spring.redis.keyPrefix");
        expireTime = Long.parseLong(env.getProperty("spring.redis.expireTime"));
    }
}
