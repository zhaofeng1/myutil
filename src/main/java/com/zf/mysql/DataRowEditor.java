/**
 * 
 */
package com.zf.mysql;

import java.util.Map;

/**
 * 
 * DataTable结果集的行数据编辑器
 *
 * Create on 2013-10-22 上午11:36:59
 *
 * @author <a href="mailto:xiaxiayoyo@gmail.com">ZhouYan</a>. 
 * 
 */
public class DataRowEditor {

	/**
	 * 行数据
	 */
	private DataRow dr;

	/**
	 * 数据源
	 */
	private Map<String, Object> mapRow;

	protected DataRowEditor(DataRow dr) {
		this.dr = dr;
		this.mapRow = dr.getDataSource();
	}

	/**
	 * 按索引更改列数据
	 * @param index
	 * @param value
	 */
	public void setData(int index, Object value) {
		mapRow.put(dr.toColumnLable(index), value);
	}

	/**
	 * 按列标签更改数据
	 * @param columnLabel
	 * @param value
	 */
	public void setData(String columnLabel, Object value) {
		mapRow.put(columnLabel, value);
	}

}
