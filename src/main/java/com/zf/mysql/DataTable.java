/**
 * 
 */
package com.zf.mysql;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * SQL执行结果集，所有结果都在内存中保存包括元数据与内容
 * 
 * Create on 2013-6-27 下午5:23:04
 * 
 * @author <a href="mailto:xiaxiayoyo@gmail.com">ZhouYan</a.
 * 
 */
public class DataTable {

	/**
	 * 列描述
	 */
	private List<Column> cols;
	/**
	 * 行数据
	 */
	private List<DataRow> rows;

	/**
	 * @param cols
	 * @param rows
	 */
	public DataTable(List<Column> cols, List<DataRow> rows) {
		super();
		this.cols = cols;
		this.rows = rows;
	}

	public int getRowCount() {
		return rows.size();
	}

	public int getColCount() {
		return cols.size();
	}

	/**
	 * @return the cols
	 */
	public Column[] getCols() {
		return cols.toArray(new Column[cols.size()]);
	}

	/**
	 * @return the rows
	 */
	public DataRow[] getRows() {
		return rows.toArray(new DataRow[rows.size()]);
	}

	public DataRow getRow(int index) {
		return rows.get(index - 1);
	}

	/**
	 * 添加结果集列
	 * 
	 * @param col
	 * @return
	 */
	protected void addColumn(String lable, int type, String typeName) {
		Column newCol = new Column();
		newCol.setLable(lable);
		newCol.setType(type);
		newCol.setTypeName(typeName);
		cols.add(newCol);
	}

	/**
	 * 添加行数据
	 * 
	 * @param dr
	 */
	protected void addRow(DataRow dr) {
		rows.add(dr);
	}

	/**
	 * 创建结果集编辑器
	 * 
	 * @return
	 */
	public DataTableEditor getEditor() {
		return new DataTableEditor(this);
	}

	/**
	 * 填充指定数据模型
	 * 
	 * @param targetType
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public <T> List<T> padding(Class<T> targetType) throws Exception {
		ArrayList<T> lst = new ArrayList<>();
		for (int j = 0; j < rows.size(); j++) {
			DataRow dr = rows.get(j);
			T target = targetType.newInstance();
			lst.add(target);
			for (int i = 0; i < cols.size(); i++) {
				Column col = cols.get(i);
				String name = col.getLable();
				name = name.substring(0, 1).toLowerCase() + name.substring(1);
				PropertyDescriptor proDes = new PropertyDescriptor(name, target.getClass());

				Class<?> clz = proDes.getWriteMethod().getParameterTypes()[0];
				if (clz.getSimpleName().endsWith("int"))
					proDes.getWriteMethod().invoke(target, dr.getInt(col.getLable()));
				else if (clz.getSimpleName().endsWith("Integer"))
					proDes.getWriteMethod().invoke(target, dr.getInt(col.getLable()));
				else if (clz.getSimpleName().endsWith("byte"))
					proDes.getWriteMethod().invoke(target, dr.getByte(col.getLable()));
				else if (clz.getSimpleName().endsWith("Byte"))
					proDes.getWriteMethod().invoke(target, dr.getByte(col.getLable()));
				else if (clz.getSimpleName().endsWith("short"))
					proDes.getWriteMethod().invoke(target, dr.getShort(col.getLable()));
				else if (clz.getSimpleName().endsWith("Short"))
					proDes.getWriteMethod().invoke(target, dr.getShort(col.getLable()));
				else if (clz.getSimpleName().endsWith("float"))
					proDes.getWriteMethod().invoke(target, dr.getFloat(col.getLable()));
				else if (clz.getSimpleName().endsWith("Float"))
					proDes.getWriteMethod().invoke(target, dr.getFloat(col.getLable()));
				else if (clz.getSimpleName().endsWith("double"))
					proDes.getWriteMethod().invoke(target, dr.getDouble(col.getLable()));
				else if (clz.getSimpleName().endsWith("Double"))
					proDes.getWriteMethod().invoke(target, dr.getDouble(col.getLable()));
				else if (clz.getSimpleName().endsWith("long"))
					proDes.getWriteMethod().invoke(target, dr.getLong(col.getLable()));
				else if (clz.getSimpleName().endsWith("Long"))
					proDes.getWriteMethod().invoke(target, dr.getLong(col.getLable()));
				else if (clz.getSimpleName().endsWith("Date"))
					proDes.getWriteMethod().invoke(target, dr.getDate(col.getLable()));
				else if (clz.getSimpleName().endsWith("String"))
					proDes.getWriteMethod().invoke(target, dr.getString(col.getLable()));
				else
					throw new RuntimeException("unknow type.");

			}
		}
		return lst;
	}

}
