package org.sam.swing.table.renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.Serializable;

import javax.swing.CellRendererPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.sam.swing.resource.ResourceLoader;

/**
 * 使用实体字段方式显示的渲染器
 * 
 * @author sam
 *
 */
public class JSTableButtonRenderer extends CellRendererPane implements TableCellRenderer, Serializable {

	private static final long serialVersionUID = -959451058213640129L;
    
    /**
	 * 未选中的时候背景颜色
	 */
	private Color unselectedForeground;

	/**
	 * 选中的时候背景颜色
	 */
	private Color unselectedBackground;
	
	/**
	 * 控件容器
	 */
	private JPanel panel;

	/**
	 * 按钮
	 */
	private JButton btn;
	
	/**
	 * 
	 */
	private JLabel label;

	/**
	 * 使用实体字段方式显示的渲染器
	 * 
	 * @param fieldName
	 */
	public JSTableButtonRenderer() {
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
        label = new JLabel("");
        panel.add(label , BorderLayout.CENTER);
		btn = new JButton(new ImageIcon(ResourceLoader.getResource( ResourceLoader.IMAGE_DOWN_ARROW )));
		panel.add(btn , BorderLayout.EAST);
	}
	
	/**
	 * Overrides <code>JComponent.setForeground</code> to assign the
	 * unselected-foreground color to the specified color.
	 *
	 * @param c
	 *            set the foreground color to this value
	 */
	@Override
	public void setForeground(Color c) {
		super.setForeground(c);
		unselectedForeground = c;
	}

	/**
	 * Overrides <code>JComponent.setBackground</code> to assign the
	 * unselected-background color to the specified color.
	 *
	 * @param c
	 *            set the background color to this value
	 */
	@Override
	public void setBackground(Color c) {
		super.setBackground(c);
		unselectedBackground = c;
	}
	
	/**
	 * {@inheritDoc}
	 */
	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		label.setText(value == null ? "" : value.toString());

		JTable.DropLocation dropLocation = table.getDropLocation();
		if (dropLocation != null && !dropLocation.isInsertRow() && !dropLocation.isInsertColumn()
				&& dropLocation.getRow() == row && dropLocation.getColumn() == column) {

			isSelected = true;
		}

		if (isSelected) {
			super.setForeground(table.getSelectionForeground());
			super.setBackground(table.getSelectionBackground());
			panel.setForeground(table.getSelectionForeground());
			panel.setBackground(table.getSelectionBackground());
			label.setForeground(table.getSelectionForeground());
			label.setBackground(table.getSelectionBackground());
		} else {
			Color background = unselectedBackground != null ? unselectedBackground : table.getBackground();
			super.setForeground(unselectedForeground != null ? unselectedForeground : table.getForeground());
			super.setBackground(background);
			panel.setForeground(this.getForeground());
			panel.setBackground(this.getBackground());
			label.setForeground(this.getForeground());
			label.setBackground(this.getBackground());
		}
		
		return panel;
	}
}
