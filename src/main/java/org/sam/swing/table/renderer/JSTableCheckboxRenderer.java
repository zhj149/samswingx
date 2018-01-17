package org.sam.swing.table.renderer;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * 选择框风格的渲染器
 * 
 * @author sam
 *
 */
public class JSTableCheckboxRenderer extends JCheckBox implements TableCellRenderer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5916141000327482269L;
	
	/**
	 * 设置默认为居中
	 */
	public JSTableCheckboxRenderer(){
		this.setHorizontalAlignment(JCheckBox.CENTER);
	}

	/**
	 * 
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		if (isSelected) {
			this.setForeground(table.getSelectionForeground());
			this.setBackground(table.getSelectionBackground());
		} else {
			this.setForeground(table.getForeground());
			this.setBackground(table.getBackground());
		}

		boolean isSelect = false;
		
		if (value instanceof Integer) {
			isSelect = Integer.valueOf(1).equals(value);
		} else if (value instanceof Long) {
			isSelect = Long.valueOf(1).equals(value);
		} else if (value instanceof Boolean) {
			isSelect = Boolean.TRUE.equals(value); 
		} else if (value instanceof String) {
			isSelect = "true".equals(value);
		} else if (value instanceof Character) {
			isSelect = Character.valueOf('t').equals(value);
		}
		
		this.setSelected(isSelect);

		return this;
	}

}
