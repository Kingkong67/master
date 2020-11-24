package spingboot.express.service;


import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存service层
 */
public interface RedisCacheService {
    /**
     * 初始化 redis 序列化器
     * @param clazz 传入类变量
     * @return 返回序列化对象
     */
    RedisSerializer getSerializer(Class clazz);

    /**
     * 通过键值获取对应的值
     * @param key 传入的键值
     * @return 返回该键对应的值
     */
    String getValue(String key);

    /**
     * 保存具有过期时间的键值对
     * @param key 键值
     * @param value 键值对应的值
     * @param expireTime 过期时间
     */
    void saveValueWithExpireTime(String key, String value, long expireTime);

    /**
     * 保存键值对
     * @param key 键值
     * @param value 键值对应的值
     */
    void saveValue(String key, Object value);


    /**
     * 保存键值对（有过期时间）
     * @param key key
     * @param obj object
     * @param expireTime expireTime
     * @param timeUnit timeUnit 时间单位
     * @param <T> 具体参数类型
     */
    <T> void saveObjectWithExpireTime(final String key, final T obj, final long expireTime, TimeUnit timeUnit);

    /**
     * 保存键值对
     * @param key key
     * @param obj object
     * @param <T> 具体参数类型
     */
    <T> void saveObject(String key, T obj);

    /**
     * 获取键对应的值
     * @param key key
     * @param valueClass 值对应的class对象
     * @param <T> 具体的参数类型
     * @return 返回具体值
     */
    <T> T getObject(String key, Class<T> valueClass);

    /**
     * 获取列表数据
     * @param key key
     * @param clazz 值对应的class对象
     * @param <T> 具体的参数类型
     * @return 返回具体列表值
     */
    <T> List<T> getListData(final String key, final Class<T> clazz);

    /**
     * 保存列表值
     * @param key key
     * @param list 对应列表数据
     * @param clazz 对应值的class对象
     * @param <T> 具体参数类型
     */
    <T> void saveListData(String key, List<T> list, Class<T> clazz);

    /**
     * 保存列表值（有过期时间）
     * @param key key
     * @param list 对应列表数据
     * @param expireTime 过期时间
     * @param clazz 对应值的class对象
     * @param <T> 具体参数类型
     */
    <T> void setListDataWithExpireTime(final String key, final List<T> list, final long expireTime,
                                       final Class<T> clazz);

    /**
     * 保存序列化之后的二级制数据（hash）
     * @param key key
     * @param field 对应hash中的域
     * @param bytesValue 字节数据
     * @param expireTime 过期时间
     * @param <T> 具体参数类型
     */
    <T> void hashSaveWithExpireTime(final String key, final String field, final byte[] bytesValue, final long expireTime);

    /**
     * hash保存数据
     * @param key key
     * @param field 对应hash中的域
     * @param bytesValue 字节数据
     * @param <T> 具体参数类型
     */
    <T> void hashSave(final String key, final String field, final byte[] bytesValue);

    /**
     * hash保存序列化之后的二级制数据（有过期时间）
     * @param key key
     * @param field 对应hash中的域
     * @param value 对应的值
     * @param expireTime 过期时间
     * @param clazz 对应值的class对象
     * @param <T> 具体参数类型
     */
    <T> void hashSaveWithExpireTime(final String key, String field, T value, long expireTime, final Class<T> clazz);

    /**
     * hash保存
     * @param key key
     * @param field 对应hash中的域
     * @param value 值
     * @param clazz 值对应的class对象
     * @param <T> 具体参数类型
     */
    <T> void hashSave(final String key, String field, T value, final Class<T> clazz);

    /**
     * 获取hash对象类型的值
     * @param key key
     * @param field 对应hash中的域
     * @param clazz 值对应的class对象
     * @param <T> 具体的参数类型
     * @return 返回具体的值
     */
    <T> T hashGet(final String key, final String field, final Class<T> clazz);

    /**
     * 获取所有的Hash对象类型的键值对
     * @param key key
     * @param fieldClazz 对应的域的class对象
     * @param valueClazz 对应的值的class对象
     * @param <K> 返回键的类型
     * @param <V> 返回值的类型
     * @return 返回参数类型
     */
    <K, V> Map<K, V> hashGetAll(final String key, final Class<K> fieldClazz, final Class<V> valueClazz);

    /**
     * 保存多个hash对象类型的值
     * @param key key
     * @param map 对应值的map集合
     * @param fieldClazz 对应的域的class对象
     * @param valueClazz 对应的值的class对象
     * @param <K> 返回键的类型
     * @param <V> 返回值的类型
     */
    <K, V> void hashMultiSave(final String key, Map<K, V> map, final Class<K> fieldClazz, final Class<V> valueClazz);

    /**
     * 删除键
     * @param key key
     */
    void delete(String key);

    /**
     * hash删除对应的域，可以理解删除一个hash桶
     * @param key key
     * @param field 对应的域
     */
    void hashDelete(String key, String field);

    /**
     * 判断是否存在该键值
     * @param key key
     * @return 返回查询结果
     */
    boolean hasKey(String key);

    /**
     * 保存Integer数据类型的数据
     * @param key key
     * @param value 对应的值
     */
    void saveInteger(final String key, final int value);

    /**
     * 获取对应的值
     * @param key key
     * @return 返回查询的值
     */
    Integer getInteger(final String key);

    /**
     * 自增，并返回自增后的值
     * @param key 对应的键值
     * @return 返回自增后的值
     */
    Integer incrementAndGet(final String key);



}
