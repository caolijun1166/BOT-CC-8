package com.cn.bot.util;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * @Discription: todo
 * 
 *
 */
public class MemcacheHelper {
	private static Logger log = LoggerFactory.getLogger(MemcacheHelper.class);

	protected static MemCachedClient mcc;

	private static MemcacheHelper memcacheHelper = null;

	private MemcacheHelper() {
	}

	public static MemcacheHelper getInstance() {
		if (memcacheHelper == null) {
			memcacheHelper = new MemcacheHelper();
		}
		return memcacheHelper;
	}

	public boolean isAvailable() {
		return mcc != null;
	}

	static {
		try {
			String[] servers = { "192.168.80.130:11211" };//CentOS������ϵ�Memcached�����ַ
	        SockIOPool pool = SockIOPool.getInstance();
	        pool.setServers(servers);
	        pool.setFailover(true);
	        pool.setInitConn(10);
	        pool.setMinConn(5);
	        pool.setMaxConn(250);
	        pool.setMaintSleep(30);
	        pool.setNagle(false);
	        pool.setSocketTO(3000);
	        pool.setAliveCheck(true);
	        pool.initialize();
			mcc = new MemCachedClient();
			mcc.setPrimitiveAsString(true);
			log.debug("---------------------------------------Excute in static block !---------------------------------------");
		} catch (Exception e) {
			log.error("memcache init error",e);
			if (log.isInfoEnabled()) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 添加内容
	 * 如果存在相同key值，则覆盖该数据
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(String key, Object value) {
		return mcc.set(key, value);
	}

	/**
	 * 添加内容
	 * 如果存在相同key值，则覆盖该数据
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public boolean set(String key, Object value, Date expire) {
		return mcc.set(key, value, expire);
	}

	/**
	 * todo
	 * @param key
	 * @return
	 */
	public long incr(String key) {
		return mcc.incr(key);
	}

	/**
	 * todo
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return mcc.get(key);
	}

	/**
	 * todo
	 * @param key
	 * @return
	 */
	public boolean delKey(String key) {
		return mcc.delete(key);
	}
	
	/**
	 * 添加内容
	 * 如果存在相同key值，则添加失�?
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public boolean add(String key, Object value, Date expire){
		return mcc.add(key, value, expire);
	}
}
