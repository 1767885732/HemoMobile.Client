package com.mdsd.library.manage.single;

import android.content.Context;

import com.mdsd.library.manage.cache.ACache;

/**
 * 缓存管理单例
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年6月12日
 */
public class CacheManage {
	private static class CacheManageHolder {
		static CacheManage instance = new CacheManage();
	}

	public static CacheManage getInstance() {
		return CacheManageHolder.instance;
	}

	private ACache simpleCache;

	/**
	 * 缓存初始化
	 * 
	 * @param context
	 * @param cacheName
	 *            缓存名
	 */
	public void init(Context context, String cacheName) {
		simpleCache = ACache.get(context.getApplicationContext(),
				cacheName);
	}

	/**
	 * 获取缓存对象
	 * 
	 * @return
	 */
	public ACache getCache() {
		return simpleCache;
	}

}
