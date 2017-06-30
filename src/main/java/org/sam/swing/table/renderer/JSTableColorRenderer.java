package org.sam.swing.table.renderer;

import java.awt.Color;
import java.awt.Component;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.sam.swing.JSButtonIcon;

/**
 * 颜色显示列
 * 
 * @author sam
 *
 */
public class JSTableColorRenderer extends JButton implements TableCellRenderer, Serializable {

	private static final long serialVersionUID = -2271791531155280948L;
		
	/**
	 * 构造函数
	 */
	public JSTableColorRenderer()
	{
		super(new JSButtonIcon());
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

		JSButtonIcon icon = (JSButtonIcon)this.getIcon();
		
		try
		{
			if (value instanceof Integer) {
				icon.setIconColor(new Color((Integer)value));
			} else if (value instanceof Long) {
				icon.setIconColor(new Color(((Long) value).intValue()));
			} else if (value instanceof String) {
				icon.setIconColor(Color.getColor(value.toString()));
			} else if (value instanceof Color) {
				icon.setIconColor((Color)value);
			}
		
		}
		catch(Exception ex)
		{
			icon.setIconColor(Color.BLACK);
		}

		return this;
	}

	
}
