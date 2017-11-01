package org.sam.swing.table.renderer;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * 图片按钮渲染器
 * 
 * @author sam
 *
 */
public class JSTableImageButtonRenderer extends JLabel implements TableCellRenderer, Serializable {

	private static final long serialVersionUID = -3684299559581737726L;

	/**
	 * 图片按钮渲染器
	 */
	public JSTableImageButtonRenderer(Icon icon) {
		super(icon);
		this.setOpaque(true);
	}

	/**
	 * 重写的对象绘制方法
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		if (isSelected) {
			this.setBackground(table.getSelectionBackground());
		} else {
			this.setBackground(table.getBackground());
		}

		return this;
	}

}
