/**
 * 
 */
package com.zf.mysql;

/**
 * 
 * 结果集列信息实体bean
 * 
 * Create on 2013-6-28 上午11:20:50
 * 
 * @author <a href="mailto:xiaxiayoyo@gmail.com">ZhouYan</a.
 * 
 */
public class Column {

	/**
	 * 列别名
	 */
	private String lable;
	/**
	 * 类型名称
	 */
	private String typeName;
	/**
	 * 类型值java.sql.Types
	 */
	private int type;

	/**
	 * @return the lable
	 */
	public String getLable() {
		return lable;
	}

	/**
	 * @param lable
	 *            the lable to set
	 */
	public void setLable(String lable) {
		this.lable = lable;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName
	 *            the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

}
