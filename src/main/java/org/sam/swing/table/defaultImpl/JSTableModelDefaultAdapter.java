package org.sam.swing.table.defaultImpl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.sam.swing.table.JSTableColumn;
import org.sam.swing.table.JSTableModel;
import org.sam.swing.table.JSTableModelEvent;
import org.sam.swing.table.JSTableModelLinster;

/**
 * 默认的系统实现
 * @author sam
 *
 * @param <E>
 */
public class JSTableModelDefaultAdapter<E> implements JSTableModelLinster<List<E>> {
	
	/**
	 * 当前的tablemodel对象
	 */
	private JSTableModel<List<E>> tableModel;
	
	/**
	 * 当前的tablemodel
	 * @return
	 */
	public JSTableModel<List<E>> getTableModel() {
		return tableModel;
	}

	/**
	 * 当前的操作tabelmodel对象
	 * @param tableModel
	 */
	public void setTableModel(JSTableModel<List<E>> tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * 带有构造函数的tableModel
	 * @param tableModel
	 */
	public JSTableModelDefaultAdapter(JSTableModel<List<E>> tableModel)
	{
		super();
		this.setTableModel(tableModel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforRetrieve(JSTableModelEvent event) throws Exception {
		tableModel.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<E> onRetrieve() throws Exception {
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterRetrieve(JSTableModelEvent event) throws Exception {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeUpdate(JSTableModelEvent event) throws Exception {
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean update(JSTableModelEvent event) throws Exception {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void atfterUpdate(JSTableModelEvent event) throws Exception {
		tableModel.resetUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforDelete(JSTableModelEvent event) throws Exception {
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterDelete(JSTableModelEvent event) throws Exception {
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeAppend(JSTableModelEvent event) throws Exception {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void aftterAppend(JSTableModelEvent event) throws Exception {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeInsert(JSTableModelEvent event) throws Exception {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void aftterInsert(JSTableModelEvent event) throws Exception {
	}
	
	/**
	 * {@inheritDoc}
	 * @throws ParseException 
	 */
	public Object getDataTranstor(JSTableColumn col, Object value, Class<?> targetCls) throws ParseException {
		if (value == null)
			return null;

		if (targetCls == null)
			return value;

		if (targetCls.equals(Integer.class)) {
			if (value.toString().trim().length() <= 0) {
				return col.getCanBeNull() ? null : 0;
			} else if (value.equals("true") || value.equals("false")) {
				return value.equals("true") ? 1 : 0;
			} else if (value instanceof Boolean) {
				return value.equals(true) ? 1 : 0;
			} else {
				return Integer.parseInt(value.toString());
			}
		} else if (targetCls.equals(String.class)) {
			return value.toString();
		} else if (targetCls.equals(Double.class)) {
			if (value.toString().trim().length() <= 0) {
				return col.getCanBeNull() ? null : 0d;
			} else {
				return Double.parseDouble(value.toString());
			}
		} else if (targetCls.equals(Float.class)) {
			if (value.toString().trim().length() <= 0) {
				return col.getCanBeNull() ? null : 0f;
			} else {
				return Float.parseFloat(value.toString());
			}
		} else if (targetCls.equals(Long.class)) {
			if (value.toString().trim().length() <= 0) {
				return col.getCanBeNull() ? null : 0l;
			} else {
				if (value.toString().equals("true"))
					return 1L;
				else if (value.toString().equals("false"))
					return 0L;
				else 
					return Long.parseLong(value.toString());
			}
		} else if (targetCls.equals(Character.class)) {
			if (value.toString().trim().length() <= 0) {
				return col.getCanBeNull() ? null : ' ';
			} else {
				return value.toString().toCharArray()[0];
			}			
		} else if (targetCls.equals(Boolean.class)) {
			if (value.toString().trim().length() <= 0) {
				return false;
			} else {
				return Boolean.parseBoolean(value.toString());
			}	
		} else if (targetCls.equals(Short.class)) {
			if (value.toString().trim().length() <= 0) {
				return col.getCanBeNull() ? null : (short)0;
			} else {
				return Short.parseShort(value.toString());
			}	
		} else if (targetCls.equals(Date.class)) {
			if (value instanceof Date) {
				return value;
			} else if (value.toString().trim().equals("") || value.equals("0000-00-00") || value.equals("0000/00/00")
					|| value.equals("0000-00-00 00:00:00") || value.equals("0000-00-00-00-00-00")
					|| value.equals("0000/00/00 00:00:00")) {
				return null;
			} else {
				return col.getFormator().parseObject(value.toString());
			}
		} else if (targetCls.equals(BigDecimal.class)) {
			return new BigDecimal(value.toString());
		} else {
			return value;
		}
	}

}
