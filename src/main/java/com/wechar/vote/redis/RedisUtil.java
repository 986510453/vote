package com.wechar.vote.redis;

import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * TODO redis数据操作工具类
 *
 * @Name: RedisUtil
 * @Auther: zzf
 * @Date: 2018/8/4 8:54
 */
public class RedisUtil {

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle = 100;

    @Value("${spring.redis.pool.min-idle}")
    private int minIdle = 1;

    @Value("${spring.redis.pool.max-active}")
    private int maxActive = 1000;

    @Value("${spring.redis.pool.max-wait}")
    private int maxWait = -1;

    private static JedisPool jedisPool;

    //获得饿汉模式单例
    public final static RedisUtil I = new RedisUtil();

    //根据sessionId获取用户名
    public static final String SESSION_USER_PREFIX = "session_user:";

    //根据用户名保存对应session
    public static final String USER_SESSION_PREFIX = "user_session:";

    //存储所有的权限
    public static final String Power_PREFIX = "power:";


    //用于保存用户的信息，主要是权限
    public static final String USER_PREFIX = "user:";


    public void init(String host,int port) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        jedisPool = new JedisPool(jedisPoolConfig,host,port);
    }

    private Jedis getJedis(){
        return jedisPool.getResource();
    }



    public RedisUtil getInstance(){
        return I;
    }

    /**
     * TODO 保存数据 可设置过期时间
     * @Author zzf
     * @Date 2018/8/4 9:05
     * @param key       键
     * @param val       值
     * @param expire    过期时间
     * @return          值
     */
    public String set(String key,String val,int expire){
        if(key == null || val == null){
            return null;
        }
        Jedis jedis = getJedis();
        val = jedis.set(key,val);
        if(expire > 0){
            jedis.expire(key,expire);
        }
        closeJedis(jedis);
        return val;
    }


    /**
     * TODO 获取数据
     * @Author zzf
     * @Date 2018/8/4 9:05
     * @param key 键
     * @return    值
     */
    public String get(String key){
        if(key == null){
            return null;
        }
        Jedis jedis = getJedis();
        String val = jedis.get(key);
        closeJedis(jedis);
        return val;
    }

    /**
     * TODO 删除指定数据
     * @Author zzf
     * @Date 2018/8/4 9:13
     * @param key   键
     * @return      值
     */
    public String del(String key){
        if(key == null){
            return null;
        }
        Jedis jedis = getJedis();
        String val = jedis.get(key);
        jedis.del(key);
        closeJedis(jedis);
        return val;
    }

    /**
     * TODO 根据参数获取所有的key
     * @Author zzf
     * @Date 2018/8/4 9:13
     * @param param 参数
     * @return      key的Set集合
     */
    public Set<String> getKeys(String param){
        Jedis jedis = getJedis();
        if(param == null){
            return jedis.keys("*");
        }
        return jedis.keys(param + "*");

    }


    /**
     * TODO 获取所有的值
     * @Author zzf
     * @Date 2018/8/4 9:18
     * @return 所有值的LinkedList集合
     */
    public Collection<String> getValues(){
        Set<String> keys = this.getKeys(null);
        List<String> values = new LinkedList<>();
        if(keys == null){
            return values;
        }
        Jedis jedis = getJedis();
        keys.forEach(s -> values.add(jedis.get(s)));
        return values;
    }


    /**
     * TODO 获取redis数据数量
     * @Author zzf
     * @Date 2018/8/4 9:14
     * @return
     */
    public int size(){
        Jedis jedis = getJedis();
        Long size = jedis.dbSize();
        return size.intValue();
    }

    public void clear(){
        Jedis jedis = getJedis();
        jedis.flushDB();
    }




    private void closeJedis(Jedis jedis){
        jedis.close();
    }

}
