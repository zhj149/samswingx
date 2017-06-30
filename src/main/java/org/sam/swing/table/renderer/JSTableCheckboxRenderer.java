package org.sam.swing.table.renderer;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.sam.swing.resource.ResourceLoader;

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
	
	public JSTableCheckboxRenderer()
	{
		super();
		this.setSelectedIcon(new ImageIcon(ResourceLoader.getResource(ResourceLoader.IMAGE_NEW)));
		this.setDisabledSelectedIcon(new ImageIcon(ResourceLoader.getResource(ResourceLoader.IMAGE_DELETE)));
		
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
		
		if (value instanceof Integer) {
			this.setSelected(Integer.valueOf(1).equals(value));
		} else if (value instanceof Boolean) {
			this.setSelected((Boolean) value);
		} else if (value instanceof String) {
			this.setSelected("true".equals(value));
		} else if (value instanceof Character) {
			this.setSelected(Character.valueOf('t').equals(value));
		}
		
		return this;
	}

}
