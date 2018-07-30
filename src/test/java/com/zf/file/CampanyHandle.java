package com.zf.file;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class CampanyHandle {

	public static Map<String, String> camIdMap = new HashMap<String, String>();
	public static Map<String, String> deparmentIdMap = new HashMap<String, String>();
	public static Map<String, String> nameUniqcodeMap = new HashMap<String, String>();
	public static Set<String> sourceSet = new HashSet<String>();
	public static Set<String> existNameZkSet = new HashSet<String>();

	/**
	 * 联盟
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		initCamIdMap();
		initDeparmentIdMap();
		initSourceSet();
		initNameUniqcodeMap();
		Map<String, Cam> camMap = new HashMap<String, CampanyHandle.Cam>();
		Map<String, Relation> relMap = new HashMap<String, Relation>();
		String path = "E:/logs/tmp/cam_source_depart_rela.txt";
		List<String> list = FileUtils.readLines(new File(path));
		for (String s : list) {
			//			System.out.println(s);
			String[] sArray = s.split("\t");
			if (sArray != null && sArray.length > 7) {
				String sysCode = sArray[0];
				String name = sArray[3];
				String fzr = sArray[7];
				String jc = sArray[2];
				String qc = sArray[4];
				String zt = sArray[5];
				String camId = camIdMap.get(jc);
				String depId = deparmentIdMap.get(fzr);
				//处理公司信息
				if (StringUtils.isBlank(camId)) {
					Cam cam = new Cam(jc, qc, zt);
					camMap.put(jc, cam);
				}

				//处理relation
				Relation re = new Relation(camId, depId, sysCode, name);
				int source = Integer.valueOf(sysCode);
				if (source % 2 == 1) {
					source = source - 1;
					if (!relMap.containsKey(source + "")) {
						if (!sourceSet.contains(source + "")) {
							relMap.put(source + "", re);
						}
					}
				} else {
					if (!sourceSet.contains(source + "")) {
						relMap.put(sysCode, re);
					}
				}
			}
		}
		//		System.out.println(JSON.toJSONString(camMap));

		//		String sql = "insert into campany_info(demand_company_abbreviation,demand_company_full_name,our_subject) values('%s','%s','%s');";
		//		for (String s : camMap.keySet()) {
		//			System.out.println(String.format(sql, camMap.get(s).getJc(), camMap.get(s).getQc(), camMap.get(s).getZt()));
		//		}

		String relationSql = "insert into demand_campany_department_relation(type,campany_info_id,department_id,system_code,name) values('1','%s','%s','%s','%s');";
		for (String s : relMap.keySet()) {
			System.out.println(String.format(relationSql, relMap.get(s).getCamId(), relMap.get(s).getDepId(), s, relMap.get(s).getName()));
		}

	}

	/**
	 * 直客
	 * @throws IOException
	 */
	@Test
	public void testZk() throws IOException {
		initCamIdMap();
		initDeparmentIdMap();
		initNameUniqcodeMap();
		initExistNameZkSet();
		Map<String, Cam> camMap = new HashMap<String, CampanyHandle.Cam>();
		Map<String, Relation> relMap = new HashMap<String, Relation>();
		Map<String, Set<String>> nameOfferidMap = new HashMap<String, Set<String>>();
		String path = "E:/logs/tmp/cam_source_depart_rela_zk.txt";
		List<String> list = FileUtils.readLines(new File(path));
		for (String s : list) {
			//			System.out.println(s);
			String[] sArray = s.split("\t");
			if (sArray != null && sArray.length > 7) {
				String sysCode = sArray[0];
				String jc = sArray[2];
				String offerid = sArray[3];
				String name = sArray[5];
				String qc = sArray[6];
				String zt = sArray[7];
				String fzr = sArray[9];
				String camId = camIdMap.get(jc);
				String depId = deparmentIdMap.get(fzr);
				//处理公司信息
				Cam cam = new Cam(jc, qc, zt);
				if (!camMap.containsKey(jc)) {
					if (!camIdMap.containsKey(jc)) {
						camMap.put(jc, cam);
					}
				}

				//处理relation
				Relation re = new Relation(camId, depId, sysCode, name);
				if (!existNameZkSet.contains(name)) {
					relMap.put(name, re);
				}
				if(!nameOfferidMap.containsKey(name)){
					nameOfferidMap.put(name, new HashSet<String>());
				}
				nameOfferidMap.get(name).add(offerid);
				//				int source = Integer.valueOf(sysCode);
				//				if (source % 2 == 1) {
				//					source = source - 1;
				//					if (!relMap.containsKey(source + "")) {
				//						relMap.put(source + "", re);
				//					}
				//				} else {
				//					relMap.put(sysCode, re);
				//				}
			}
		}
		//		System.out.println(JSON.toJSONString(camMap));

		//		String sql = "insert into campany_info(demand_company_abbreviation,demand_company_full_name,our_subject) values('%s','%s','%s');";
		//		for (String s : camMap.keySet()) {
		//			System.out.println(String.format(sql, camMap.get(s).getJc(), camMap.get(s).getQc(), camMap.get(s).getZt()));
		//		}

		String relationSql = "insert into demand_campany_department_relation(type,campany_info_id,department_id,system_code,name) values('2','%s','%s','%s','%s');";
		for (String s : relMap.keySet()) {
			System.out.println(String.format(relationSql, relMap.get(s).getCamId(), relMap.get(s).getDepId(), relMap.get(s).getSysCode(), relMap.get(s).getName()));
		}
		System.out.println(JSON.toJSONString(relMap.keySet()));

		//		更新直客 offer 单子对应的账号code

		//		String relationSql = "update feed_offers set demand_company_uniq_code='%s' where id in(%s);";
		//		for (String s : nameOfferidMap.keySet()) {
		//			String uniqCode = nameUniqcodeMap.get(s);
		//			String offeridStr = "";
		//			for (String offerid : nameOfferidMap.get(s)) {
		//				offeridStr += offerid + ",";
		//			}
		//			if (offeridStr.length() > 1) {
		//				offeridStr = offeridStr.substring(0, offeridStr.length() - 1);
		//			}
		//			System.out.println(String.format(relationSql, uniqCode, offeridStr));
		//		}
	}

	public void initCamIdMap() throws IOException {
		String path = "E:/logs/tmp/cam_id.txt";
		List<String> list = FileUtils.readLines(new File(path));
		for (String s : list) {
			String[] sArray = s.split("\t");
			if (sArray != null && sArray.length == 2) {
				String id = sArray[0];
				String jc = sArray[1];
				camIdMap.put(jc, id);
			}
		}
	}

	public void initSourceSet() throws IOException {
		String path = "E:/logs/tmp/existsource.txt";
		List<String> list = FileUtils.readLines(new File(path));
		for (String s : list) {
			if (StringUtils.isNotBlank(s)) {
				sourceSet.add(s);
			}
		}
	}

	public void initDeparmentIdMap() throws IOException {
		String path = "E:/logs/tmp/deparment_id.txt";
		List<String> list = FileUtils.readLines(new File(path));
		for (String s : list) {
			String[] sArray = s.split("\t");
			if (sArray != null && sArray.length == 2) {
				String id = sArray[0];
				String username = sArray[1];
				deparmentIdMap.put(username, id);
			}
		}
	}

	public void initNameUniqcodeMap() throws IOException {
		String path = "E:/logs/tmp/name_uniqcode.txt";
		List<String> list = FileUtils.readLines(new File(path));
		for (String s : list) {
			String[] sArray = s.split("\t");
			if (sArray != null && sArray.length == 2) {
				String name = sArray[0];
				String uniqcode = sArray[1];
				nameUniqcodeMap.put(name, uniqcode);
			}
		}
	}

	public void initExistNameZkSet() throws IOException {
		String path = "E:/logs/tmp/exist_rel_zk.txt";
		List<String> list = FileUtils.readLines(new File(path));
		for (String s : list) {
			String[] sArray = s.split("\t");
			if (sArray != null && sArray.length == 1) {
				String name = sArray[0];
				existNameZkSet.add(name);
			}
		}
	}

	public class Cam {
		private String jc;
		private String qc;
		private String zt;

		public Cam() {

		}

		public Cam(String jc, String qc, String zt) {
			super();
			this.jc = jc;
			this.qc = qc;
			this.zt = zt;
		}

		public String getJc() {
			return jc;
		}

		public void setJc(String jc) {
			this.jc = jc;
		}

		public String getQc() {
			return qc;
		}

		public void setQc(String qc) {
			this.qc = qc;
		}

		public String getZt() {
			return zt;
		}

		public void setZt(String zt) {
			this.zt = zt;
		}

	}

	public class Relation {
		private String camId;
		private String depId;
		private String sysCode;
		private String name;

		public Relation() {

		}

		public Relation(String camId, String depId, String sysCode,String name) {
			super();
			this.camId = camId;
			this.depId = depId;
			this.sysCode = sysCode;
			this.name = name;
		}

		public String getCamId() {
			return camId;
		}

		public void setCamId(String camId) {
			this.camId = camId;
		}

		public String getDepId() {
			return depId;
		}

		public void setDepId(String depId) {
			this.depId = depId;
		}

		public String getSysCode() {
			return sysCode;
		}

		public void setSysCode(String sysCode) {
			this.sysCode = sysCode;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	@Test
	public void departinf(){
		String str = "李梦,Fifi,Lyx,Archer,张乘逢,李琛";
		List<String> list = Arrays.asList(str.split(","));
		System.out.println(JSON.toJSON(list));
		String sql = "insert into department_info(deparment_name,member_name) values('%s','%s');";
		for (String s : list) {
			System.out.println(String.format(sql, "运营部门", s));
		}
	}

	@Test
	public void updateSwFzr() throws IOException {
		initDeparmentIdMap();
		String path = "E:/logs/tmp/sw_fzr.txt";
		List<String> list = FileUtils.readLines(new File(path));
		Map<String, String> swfzrMap = new HashMap<String, String>();
		for (String s : list) {
			String[] sArray = s.split("\t");
			String source = sArray[0];
			String fzr = sArray[1];
			String fzrid = deparmentIdMap.get(fzr);
			swfzrMap.put(source, fzrid);
		}
		
		String sql = "update demand_campany_department_relation set department_id=%s where system_code='%s';";
		for (String s : swfzrMap.keySet()) {
			System.out.println(String.format(sql, swfzrMap.get(s), s));
		}
	}

	@Test
	public void updateSwFzrZk() throws IOException {
		initDeparmentIdMap();
		String path = "E:/logs/tmp/sw_fzr_zk.txt";
		List<String> list = FileUtils.readLines(new File(path));
		Map<String, String> swfzrMap = new HashMap<String, String>();
		for (String s : list) {
			String[] sArray = s.split("\t");
			String source = sArray[0];
			if (sArray.length < 2) {
				continue;
			}
			String fzr = sArray[1];
			String fzrid = deparmentIdMap.get(fzr);
			swfzrMap.put(source, fzrid);
		}

		String sql = "update demand_campany_department_relation set department_id=%s where name='%s';";
		for (String s : swfzrMap.keySet()) {
			System.out.println(String.format(sql, swfzrMap.get(s), s));
		}
	}

	@Test
	public void updateYyFzr() throws IOException {
		initDeparmentIdMap();
		String path = "E:/logs/tmp/yy_fzr.txt";
		List<String> list = FileUtils.readLines(new File(path));
		Map<String, String> swfzrMap = new HashMap<String, String>();
		for (String s : list) {
			String[] sArray = s.split("\t");
			String source = sArray[0];
			String fzr = sArray[1];
			String fzrid = deparmentIdMap.get(fzr);
			swfzrMap.put(source, fzrid);
		}

		String sql = "update demand_campany_department_relation set operatiion_dm_id=%s where system_code='%s';";
		for (String s : swfzrMap.keySet()) {
			System.out.println(String.format(sql, swfzrMap.get(s), s));
		}
	}

	@Test
	public void updateRemoteAccount() throws IOException {
		//		initDeparmentIdMap();
		String path = "E:/logs/tmp/remote_account.txt";
		List<String> list = FileUtils.readLines(new File(path));
		Map<String, String> remoteAccountMap = new HashMap<String, String>();
		for (String s : list) {
			String[] sArray = s.split("\t");
			if (sArray.length > 1) {
				String source = sArray[0];
				String remoteaccount = sArray[1];
				remoteAccountMap.put(source, remoteaccount);
			}
		}

		String sql = "update demand_campany_department_relation set demand_system_code='%s' where system_code='%s';";
		for (String s : remoteAccountMap.keySet()) {
			System.out.println(String.format(sql, remoteAccountMap.get(s), s));
		}
	}

	@Test
	public void updatePostback() throws IOException {
		//		initDeparmentIdMap();
		String path = "E:/logs/tmp/source_postback.txt";
		List<String> list = FileUtils.readLines(new File(path));
		Map<String, String> remoteAccountMap = new HashMap<String, String>();
		for (String s : list) {
			String[] sArray = s.split("\t");
			if (sArray.length > 1) {
				String source = sArray[0];
				String remoteaccount = sArray[1];
				remoteAccountMap.put(source, remoteaccount);
			}
		}

		String sql = "update demand_campany_department_relation set demand_postback_url='%s' where system_code='%s';";
		for (String s : remoteAccountMap.keySet()) {
			System.out.println(String.format(sql, remoteAccountMap.get(s), s));
		}
	}

	/**
	 * 更新直客账号信息
	 * @throws IOException
	 */
	@Test
	public void updateRemoteAccountZk() throws IOException {
		//		initDeparmentIdMap();
		String path = "E:/logs/tmp/name_remote_account.txt";
		List<String> list = FileUtils.readLines(new File(path));
		Map<String, String> remoteAccountMap = new HashMap<String, String>();
		for (String s : list) {
			String[] sArray = s.split("\t");
			if (sArray.length > 1) {
				String name = sArray[0];
				String remoteaccount = sArray[1];
				remoteAccountMap.put(name, remoteaccount);
			}
		}

		String sql = "update demand_campany_department_relation set demand_system_code='%s' where name='%s' and type=2;";
		for (String s : remoteAccountMap.keySet()) {
			System.out.println(String.format(sql, remoteAccountMap.get(s), s));
		}
	}
}
