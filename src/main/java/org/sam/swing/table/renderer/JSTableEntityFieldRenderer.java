package org.sam.swing.table.renderer;

import javax.swing.table.DefaultTableCellRenderer;

import org.sam.swing.utils.ReflectUtil;

/**
 * 使用实体字段方式显示的渲染器
 * 
 * @author sam
 *
 */
public class JSTableEntityFieldRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -959451058213640129L;

	/**
	 * 实体对象名称
	 */
	private String fieldName = "";

	/**
	 * 实体对象名称
	 * 
	 * @return
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * 实体对象名称
	 * 
	 * @param fieldName
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * 使用实体字段方式显示的渲染器
	 * 
	 * @param fieldName
	 */
	public JSTableEntityFieldRenderer(String fieldName) {
		super();
		this.setFieldName(fieldName);
	}

	/**
	 * 重写的赋值部分
	 */
	@Override
	protected void setValue(Object value) {
		setText(ReflectUtil.getDisplay(value, fieldName));
	}
}
