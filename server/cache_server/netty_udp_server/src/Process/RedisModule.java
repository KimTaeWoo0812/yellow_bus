package Process;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisModule {
	static JedisPool jedisPool;
	static Jedis jedis;
	
	public RedisModule() {
	}
	public static void StartRedis(){
		jedisPool = new JedisPool(new JedisPoolConfig(), "localhost");
		jedis = jedisPool.getResource();
	}
	
	public synchronized static void SaveToRedis(String key, String val){
		try{ 
			jedis.set(key, val); 
		}catch(JedisConnectionException e){ 
			e.printStackTrace();
			if(null != jedis){ 
				jedisPool.returnBrokenResource(jedis); 
				jedis = null; 
			} 
		}
	}
	public synchronized static String GetFromRedis(String key){
		try{ 
			String val = jedis.get(key);
			return val;
		}catch(JedisConnectionException e){ 
			e.printStackTrace();
			if(null != jedis){ 
				jedisPool.returnBrokenResource(jedis); 
				jedis = null; 
			} 
			return null;
		}
	}
	public synchronized static void Del_key(String key){
		try{ 
			jedis.del(key);
		}catch(JedisConnectionException e){ 
			e.printStackTrace();
			if(null != jedis){ 
				jedisPool.returnBrokenResource(jedis); 
				jedis = null; 
			} 
		}
	}
	//존재하면 1 리턴, 않으면 0 리턴
	public synchronized static boolean HasExists(String key){
		Boolean val = false;
		try{ 
			val = jedis.exists(key);
			return val;
		}catch(JedisConnectionException e){ 
			e.printStackTrace();
			if(null != jedis){ 
				jedisPool.returnBrokenResource(jedis); 
				jedis = null; 
			} 
		}
		return val;
	}
	
}
