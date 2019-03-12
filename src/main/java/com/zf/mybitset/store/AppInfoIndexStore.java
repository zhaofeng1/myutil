package com.zf.mybitset.store;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;
import com.zf.mybitset.model.AppInfo;
import com.zf.mybitset.model.FindResultBean;

/**
 * app 索引
 * @author zhaofeng
 *
 */
public class AppInfoIndexStore {

	private static final String GEO = "geo";
	private static final String PLATFORM = "platform";
	private static final String STATUS = "status";

	//entity
	private BitSetEntity geoBit;
	private BitSetEntity platformBit;
	private BitSetEntity statusBit;

	/**
	 * appinfoid appinfo
	 */
	private ConcurrentHashMap<Integer, AppInfo> appInfoMap;
	private ConcurrentHashMap<Integer, Integer> appInfoIndexMap;
	public static final AppInfoIndexStore INSTANC = getInstance();
	
	private static List<AppInfo> appList = new ArrayList<>();

	private AppInfoIndexStore() {
		initData();
	}

	private static class AppInfoIndexStoreHolder {
		private static AppInfoIndexStore instance = new AppInfoIndexStore();
	}

	public static AppInfoIndexStore getInstance(){
		return AppInfoIndexStoreHolder.instance;
	}

	public void initData() {
		//初始化索引
		this.geoBit = new BitSetEntity(GEO);
		this.platformBit = new BitSetEntity(PLATFORM);
		this.statusBit = new BitSetEntity(STATUS);

		this.appInfoMap = new ConcurrentHashMap<Integer, AppInfo>();
		this.appInfoIndexMap = new ConcurrentHashMap<Integer, Integer>();
	}
	
	public void initAppIndex(AppInfo info, int i) {
		getGeoBit().updateList(info.getGeos(), i);
		getPlatformBit().update(info.getPlatform(), i);
		getStatusBit().update(info.getStatus(), i);
	}

	/**
	 * 初始化app 数据
	 */
	public void initAppList() {
		AppInfo info1 = new AppInfo(1, "test1", "active", "android", "IN,ID");
		AppInfo info2 = new AppInfo(2, "test2", "publish", "android", "IN,ID");
		AppInfo info3 = new AppInfo(3, "test3", "active", "android", "IN,ID");
		AppInfo info4 = new AppInfo(4, "test4", "active", "ios", "IN,ID");
		appList.add(info1);
		appList.add(info2);
		appList.add(info3);
		appList.add(info4);

		//索引
		for (int i = 0; i < appList.size(); i++) {
			AppInfo appinfo = appList.get(i);
			initAppIndex(appinfo, i);
			this.appInfoMap.put(i, appinfo);
		}

	}

	public void getApplist(FindResultBean bean) {
		String geo = bean.getGeo();
		String status = bean.getStatus();
		String platform = bean.getPlatform();

		BitSet bitset = null;

		bitset = getGeoBit().and(geo, bitset);
		bitset = getPlatformBit().and(platform, bitset);
		bitset = getStatusBit().and(status, bitset);

		List<Integer> list = BitSetEntity.getRealIndexs(bitset);
		System.out.println(JSON.toJSON(list));

	}

	public static void main(String[] args) {
		FindResultBean bean = new FindResultBean();
		bean.setGeo("IN");
		bean.setPlatform("android");
		AppInfoIndexStore.INSTANC.initAppList();
		AppInfoIndexStore.INSTANC.getApplist(bean);
	}

	public BitSetEntity getGeoBit() {
		return geoBit;
	}

	public void setGeoBit(BitSetEntity geoBit) {
		this.geoBit = geoBit;
	}

	public BitSetEntity getPlatformBit() {
		return platformBit;
	}

	public void setPlatformBit(BitSetEntity platformBit) {
		this.platformBit = platformBit;
	}

	public BitSetEntity getStatusBit() {
		return statusBit;
	}

	public void setStatusBit(BitSetEntity statusBit) {
		this.statusBit = statusBit;
	}
}
