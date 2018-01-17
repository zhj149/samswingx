package org.sam.swing.table.header;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * 表头带有选择框的控件
 * 
 * @author sam
 *
 */
public class JSTableHeaderCheckboxRenderer extends JCheckBox implements TableCellRenderer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5916141000327482269L;

	/**
	 * 表头带有选择框的控件
	 */
	public JSTableHeaderCheckboxRenderer() {
		super();
		this.setHorizontalAlignment(JSTableHeaderCheckboxRenderer.CENTER);
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				JSTableHeaderCheckboxRenderer.this.setSelected(!JSTableHeaderCheckboxRenderer.this.isSelected());
			}
		});
	}

	/**
	 * 
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		return this;
	}

}
