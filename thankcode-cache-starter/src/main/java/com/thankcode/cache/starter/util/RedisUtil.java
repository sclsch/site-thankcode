/**
 * All rights Reserved, Designed By ysusolt.
 *
 * @author: jiangqian
 * @date: 2019/11/12 0012
 * @Copyright ?2019 ysusolt. All rights reserved.
 * 注意：本内容仅限于燕大燕软内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.thankcode.cache.starter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis util
 *
 * @author: jiangqian
 * @date: 2019/11/12 0012 10:02
 * @version: V1.0
 * @review: jiangqian/2019/11/12 0012 10:02
 */
@Component
public class RedisUtil {
    final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpireByKey(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */

    public boolean isExistKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */

    public void delKey(String... key) {

        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */

    public Object getValByKey(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }


    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     *              time 失效时间
     * @return true成功 false失败
     */


    public void setVal(String key, Object value, long time) {

        redisTemplate.opsForValue().set(key, value);
        if (time > 0) {
            expire(key, time);
        }
    }


    /**
     * 值是hash 取value
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object getHashVal(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 值是hash 取 value的 键值
     *
     * @param key 键
     * @return 对应的多个键值
     */

    public Map<Object, Object> getHashKeys(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */


    public void setHash(String key, String item, Object value, long time) {
        redisTemplate.opsForHash().put(key, item, value);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */

    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }


    /**
     * 把set集合放进 缓存
     *
     * @param key
     * @param set
     * @param time
     */

    public void sSet(String key, Set<Object> set, long time) {

        Long count = redisTemplate.opsForSet().add(key, set);

        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 把set集合放进 缓存
     *
     * @param key
     * @param o
     * @param time
     */

    public void zSet(String key, Object o, long time) {
        redisTemplate.opsForZSet().add(key, o,time);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return 490
     */


    public void setListVal(String key, List<Object> value, long time) {


        redisTemplate.opsForList().rightPushAll(key, value);

        if (time > 0) {
            expire(key, time);
        }

    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     **/

    public void expire(String key, long time) {

        redisTemplate.expire(key, time, TimeUnit.SECONDS);

    }

    /**
     * 取两个键的集合交集存置目标键的集合中
     *
     * @param key      键
     * @param otherKey 其他键
     * @param destKey  目的键
     * @return
     * @author suncl
     * 2019年11月23日14:45:35
     */
    public Long intersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * 从键的集合中取出起始元素到结束元素，并且按照从高到低排序
     *
     * @param key   键
     * @param start 起始
     * @param end   结束
     * @return 排序后的集合
     * @author suncl
     * 2019年11月23日14:45:35
     */
    public Set<ZSetOperations.TypedTuple<Object>> reverseRangeWithScores(String key, long start, long end) {
        Set<ZSetOperations.TypedTuple<Object>> reverseRangeWithScores = redisTemplate.opsForZSet()
                .reverseRangeWithScores(key, start, end);
        return reverseRangeWithScores;
    }

    /**
     * 返回给定键的集合的个数
     *
     * @param key 键
     * @return
     * @author suncl
     * 2019年11月23日14:45:35
     */
    public Long zSetSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 向集合左边添加元素
     * @param key 键
     * @param value 元素
     * @return 操作完后集合的长度
     * @author suncl 2019年11月23日14:52:31
     */
    public Long listLeftPush(String key,String value){
        final Long aLong = redisTemplate.opsForList().leftPush(key, value);
        return aLong;
    }

    /**
     * 向集合右边添加元素
     * @param key 键
     * @param value 元素
     * @return 操作完后集合的长度
     * @author suncl 2019年11月23日14:52:31
     */
    public Long listRightPush(String key,String value){
        final Long aLong = redisTemplate.opsForList().rightPush(key, value);
        return aLong;
    }

    /**
     * 返回键key的集合中某个元素o的分数
     * @param key 键
     * @param o 元素
     * @return
     */
    public Double zSetScore(String key,Object o){
        return redisTemplate.opsForZSet().score(key,o);
    }
}
