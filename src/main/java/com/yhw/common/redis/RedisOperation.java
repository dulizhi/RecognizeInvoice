package com.yhw.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.params.geo.GeoRadiusParam;

import java.util.*;

/**
 * Created by xw594011 on 16-5-25.
 */
public class RedisOperation {
    private static Logger Log = LoggerFactory.getLogger(RedisOperation.class);
    public JedisPool jedisPool;
    private int database = 0;
    private static RedisOperation cachePool[] = new RedisOperation[16];

    public static RedisOperation getInstance(int database) {
        if (cachePool[database] == null) {
            cachePool[database] = new RedisOperation(database);
        }
        return cachePool[database];
    }

    public Jedis getJedis() {
        Jedis jedis = null;
        int count = 0;
        while (true) {
            try {
                if (jedisPool == null || jedisPool.isClosed()) {
                    getInstance(this.database);
                }
                if (jedisPool != null) {
                    jedis = jedisPool.getResource();
                    if (jedis != null && jedis.isConnected()) {
                        if (jedis.getDB() != this.database) {
                            jedis.select(this.database);
                        }
                    } else {
                        if (jedis != null && !jedis.isConnected()) {
                            jedisPool.returnBrokenResource(jedis);
                        }
                        jedis = null;
                    }
                }
            } catch (JedisConnectionException e) {
                if (jedisPool != null && jedis != null) {
                    jedisPool.returnBrokenResource(jedis);
                }
                jedis = null;
            } finally {
                if (jedis != null) {
                    return jedis;
                }
                if (count > 3) {
                    break;
                }
                count++;
            }
        }
        return null;
    }

    public String flushDB() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String value = jedis.flushDB();
            return value;
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return null;
        }
    }

    public String getValue(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                value = jedis.get(key);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return value;
        }
    }

    public void setValue(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                jedis.set(key, value);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return;
        }
    }

    public void del(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                boolean isExist = jedis.exists(key);
                Log.debug("delValue key=" + key + ",isExist=" + isExist);
                if (isExist) {
                    jedis.del(key);
                }
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return;
        }
    }

    public long delKey(String key) {
        Jedis jedis = null;
        long res = -1;
        try {
            jedis = getJedis();
            if (jedis != null) {
                boolean isExist = jedis.exists(key);
                Log.debug("delValue key=" + key + ",isExist=" + isExist);
                if (isExist) {
                    res = jedis.del(key);
                } else {
                    res = 0;
                }
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return res;
        }
    }

    public long delKey(String... keys) {
        Jedis jedis = null;
        long res = -1;
        try {
            jedis = getJedis();
            if (jedis != null) {
                res = jedis.del(keys);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return res;
        }
    }

    public void expire(String key, int expire) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                boolean isExist = jedis.exists(key);
                if (isExist) {
                    jedis.expire(key, expire);
                }
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return;
        }
    }

    public boolean hmset(String key, HashMap<String, String> value) {
        boolean result = false;
        Jedis jedis = null;
        if (value == null || value.isEmpty()) return result;
        try {
            jedis = getJedis();
/*            if (jedis != null) {
                Log.debug("hmset key=" + key);
                jedis.hmset(key, value);
                result = true;
            }*/
            if (jedis != null) {
                Pipeline pipe = jedis.pipelined();
                if (value != null && !value.isEmpty()) {
                    pipe.hmset(key, value);
                }
                pipe.sync();
                result = true;
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    public boolean hmset(String key, Map<String, String> value) {
        boolean result = false;
        Jedis jedis = null;
        if (value == null || value.isEmpty()) return result;
        try {
            jedis = getJedis();
            if (jedis != null) {
                Pipeline pipe = jedis.pipelined();
                if (value != null && !value.isEmpty()) {
                    pipe.hmset(key, value);
                }
                pipe.sync();
                result = true;
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    public void hsetValue(String vin, String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                Log.debug("hsetValue key=" + key);
                jedis.hset(vin, key, value);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return;
        }
    }

    public String hgetValue(String vin, String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                Log.debug("hgetValue key=" + key);
                value = jedis.hget(vin, key);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return value;
        }
    }

    public void hsetByteValue(String vin, String key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                Log.debug("hsetValue key=" + key);
                jedis.hset(vin.getBytes(), key.getBytes(), value);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return;
        }
    }

    public byte[] hgetByteValue(String vin, String key) {
        Jedis jedis = null;
        byte[] value = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                Log.debug("hgetValue key=" + key);
                value = jedis.hget(vin.getBytes(), key.getBytes());
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return value;
        }
    }

    public void batchPush(String key, Queue<String> values) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                Log.debug("batchPush key=" + key + ",values=" + values);
                Pipeline pipe = jedis.pipelined();
                while (!values.isEmpty()) {
                    pipe.lpush(key, values.poll());
                }
                pipe.sync();
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return;
        }
    }

    public void batchPushEx(String key, Queue<String> values, int delay) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                Log.debug("batchPush key=" + key + ",values=" + values);
                Pipeline pipe = jedis.pipelined();
                if (pipe.get(key) != null) {
                    pipe.del(key);
                }
                while (!values.isEmpty()) {
                    pipe.lpush(key, values.poll());
                    pipe.expire(key, delay);
                }
                pipe.sync();
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return;
        }
    }

    public String rpop(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                Pipeline pipe = jedis.pipelined();
                pipe.rpop(key);
                List<Object> list = pipe.syncAndReturnAll();
                if (list != null) {
                    if (list.get(0) != null) {
                        value = list.get(0).toString();
                    }
                }
                Log.debug("rpop key=" + key + ",value=" + value);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return value;
        }
    }

    public Long hdetValue(String vin, String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.hexists(vin, key)) {
                Log.debug("hdetValue key=" + key);
                Long value = jedis.hdel(vin, key);
                return value;
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return null;
        }
    }

    public Map<String, String> hgetAllValues(String vin) {
        Jedis jedis = null;
        Map<String, String> values = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                Log.debug("hgetAllValues vin=" + vin);
                values = jedis.hgetAll(vin);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return values;
        }
    }

    public long incr(String key) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.incr(key);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    public String get(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.get(key);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    public Set<String> getLikeKey(String pre_key) {
        Jedis jedis = null;
        Set<String> result = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.keys(pre_key + "*");
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    public Set<String> getLikeKeys(String pre_key, String aft_key) {
        Jedis jedis = null;
        Set<String> result = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.keys(pre_key + "*" + aft_key);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    public String setEx(String key, int seconds, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                Log.debug("setEx key=" + key + ",seconds=" + seconds + ",value=" + value);
                result = jedis.setex(key, seconds, value);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    public long getEx(String key) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.ttl(key);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //有序集合系列
    //插入集合
    public long zadd(String key, double score, String member) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = getJedis();

            if (jedis != null && jedis.isConnected()) {
                Long res = jedis.zadd(key, score, member);
                if (res != null) {
                    result = res.longValue();
                }
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //批量插入
    public long zadd(String key, Map<String, Double> scoreMembers) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                Long res = jedis.zadd(key, scoreMembers);
                if (res != null) {
                    result = res.longValue();
                }
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //从集合中删除
    public long zrem(String key, String member) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                Long res = jedis.zrem(key, member);
                if (res != null) {
                    result = res.longValue();
                }
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    public long zremrangeByRank(String key, long start, long end) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                Long res = jedis.zremrangeByRank(key, start, end);
                if (res != null) {
                    result = res.longValue();
                }
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    public long zremrangeByScore(String key, double start, double end) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                Long res = jedis.zremrangeByScore(key, start, end);
                if (res != null) {
                    result = res.longValue();
                }
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //增加至score值
    public double zincrby(String key, double score, String member) {
        Jedis jedis = null;
        double result = 0;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                Double res = jedis.zincrby(key, score, member);
                if (res != null) {
                    result = res.doubleValue();
                }
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //获取元素member的score
    public double zscore(String key, String member) {
        Jedis jedis = null;
        double result = 0;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                Double res = jedis.zscore(key, member);
                if (res != null) {
                    result = res.doubleValue();
                }
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //升序，获取排名在start~end范围的元素列表
    public Set<String> zrange(String key, long start, long end) {
        Jedis jedis = null;
        Set<String> result = null;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                result = jedis.zrange(key, start, end);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //降序，获取排名在start~end范围的元素列表
    public Set<String> zrevrange(String key, long start, long end) {
        Jedis jedis = null;
        Set<String> result = null;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                result = jedis.zrevrange(key, start, end);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //升序，获取排名在start~end范围的元素列表,带score
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        Jedis jedis = null;
        Set<Tuple> result = null;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                result = jedis.zrangeWithScores(key, start, end);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //降序，获取排名在start~end范围的元素列表，带score
    public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        Jedis jedis = null;
        Set<Tuple> result = null;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                result = jedis.zrevrangeWithScores(key, start, end);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //升序，获取score在min~max范围的元素列表
    public Set<String> zrangeByScore(String key, double min, double max) {
        Jedis jedis = null;
        Set<String> result = null;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                result = jedis.zrangeByScore(key, min, max);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //降序，获取score在min~max范围的元素列表
    public Set<String> zrevrangeByScore(String key, double min, double max) {
        Jedis jedis = null;
        Set<String> result = null;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                result = jedis.zrevrangeByScore(key, min, max);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //降序，获取score在min~max范围的元素列表,带score
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double min, double max) {
        Jedis jedis = null;
        Set<Tuple> result = null;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                result = jedis.zrevrangeByScoreWithScores(key, min, max);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //降序，获取score在min~max范围的元素列表，同时获取offset之后的count个元素列表
    public Set<String> zrevrangeByScore(String key, double min, double max, int offset, int count) {
        Jedis jedis = null;
        Set<String> result = null;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                result = jedis.zrevrangeByScore(key, min, max, offset, count);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //升序，获取score在min~max范围的元素列表,同时获取offset之后的count个元素列表
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        Jedis jedis = null;
        Set<String> result = null;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                result = jedis.zrangeByScore(key, min, max, offset, count);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //升序，获取元素member的排名
    public long zrank(String key, String member) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                Long res = jedis.zrank(key, member);
                if (res != null) {
                    result = res.longValue();
                }
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //降序，获取元素member的排名
    public long zrevrank(String key, String member) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                Long res = jedis.zrevrank(key, member);
                if (res != null) {
                    result = res.longValue();
                }
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //获取集合中元素的数量
    public long zcard(String key) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                Long res = jedis.zcard(key);
                if (res != null) {
                    result = res.longValue();
                }
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //获取score在min~max内的元素个数
    public long zcount(String key, double min, double max) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                Long res = jedis.zcount(key, min, max);
                if (res != null) {
                    result = res.longValue();
                }
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //获取score在min~max内的元素个数
    public long zcount(String key, String min, String max) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                Long res = jedis.zcount(key, min, max);
                if (res != null) {
                    result = res.longValue();
                }
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //地理位置
    //添加经纬度
    public long geoadd(String key, double longitude, double latitude, String member) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                Long res = jedis.geoadd(key, longitude, latitude, member);
                if (res != null) {
                    result = res.longValue();
                }
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //批量添加
    public long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                Long res = jedis.geoadd(key, memberCoordinateMap);
                if (res != null) {
                    result = res.longValue();
                }
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //获取坐标
    public List<GeoCoordinate> geopos(String key, String member) {
        Jedis jedis = null;
        List<GeoCoordinate> result = new ArrayList<>();
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                result = jedis.geopos(key, member);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //计算两点距离,单位默认为米
    public double geodist(String key, String member1, String member2) {
        Jedis jedis = null;
        double result = (double) 0;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                Double res = jedis.geodist(key, member1, member2);
                if (res != null) {
                    result = res.doubleValue();
                }
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //计算两点距离,单位默认为米
    public double geodist(String key, String member1, String member2, GeoUnit geoUnit) {
        Jedis jedis = null;
        double result = (double) 0;
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                Double res = jedis.geodist(key, member1, member2, geoUnit);
                if (res != null) {
                    result = res.doubleValue();
                }
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //获取radius范围内的点坐标
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit geoUnit, GeoRadiusParam geoRadiusParam) {
        Jedis jedis = null;
        List<GeoRadiusResponse> result = new ArrayList<>();
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                result = jedis.georadius(key, longitude, latitude, radius, geoUnit, geoRadiusParam);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    //获取radius范围内的点坐标
    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit geoUnit, GeoRadiusParam geoRadiusParam) {
        Jedis jedis = null;
        List<GeoRadiusResponse> result = new ArrayList<>();
        try {
            jedis = getJedis();
            if (jedis != null && jedis.isConnected()) {
                result = jedis.georadiusByMember(key, member, radius, geoUnit, geoRadiusParam);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    public boolean setbit(String key, long offset, String value) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.setbit(key, offset, value);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    public boolean setbit(String key, long offset, boolean value) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.setbit(key, offset, value);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    public boolean getbit(String key, long offset) {
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.getbit(key, offset);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    public long bitcount(String key) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = getJedis();
            if (jedis != null) {
                result = jedis.bitcount(key);
            }
        } catch (JedisConnectionException e) {
            if (jedisPool != null && jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            jedis = null;
        } catch (Exception e) {
        } finally {
            if (jedis != null && jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return result;
        }
    }

    public RedisOperation() {
    }

    public RedisOperation(int database) {
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxIdle(200);
        config.setMaxWaitMillis(3000);
        config.setMaxTotal(300);
        config.setMinIdle(10);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);
        this.database = database;
        jedisPool = new JedisPool(config, "10.199.1.217",
                6379,
                3000,
                "Q1w2e3r4",
                database);
    }

    public void destroy() {
        if (jedisPool != null) {
            jedisPool.destroy();
            jedisPool = null;
        }
    }
}
