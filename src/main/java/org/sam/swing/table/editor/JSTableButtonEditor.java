package org.sam.swing.table.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;

import org.sam.swing.resource.ResourceLoader;

/**
 * 空编辑模式的editor对象
 * 
 * @author sam
 *
 */
public class JSTableButtonEditor extends AbstractCellEditor implements TableCellEditor, TreeCellEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4070221556048242744L;

	/**
	 * 外边的框体
	 */
	private JPanel panel;

	/**
	 * 当前的控件
	 */
	private JLabel label;

	/**
	 * 按钮
	 */
	private JButton btn;

	/**
	 * 操作代理对象
	 */
	private EditorDelegate delegate;

	/**
	 * 无参数构造函数
	 */
	public JSTableButtonEditor() {
		this("");
	}

	/**
	 * 有参数构造函数
	 * 
	 * @param fieldName
	 *            字段名称
	 */
	public JSTableButtonEditor(String fieldName) {
		super();
		
		panel = new JPanel(new BorderLayout());
		label = new JLabel("");
		btn = new JButton(new ImageIcon(ResourceLoader.getResource(ResourceLoader.IMAGE_DOWN_ARROW)));
		panel.add(label , BorderLayout.CENTER);
		panel.add(btn, BorderLayout.EAST);
		
		delegate = new EditorDelegate();
	}

	/**
	 * 设置值
	 * 
	 * @param value
	 */
	public void setValue(Object value) {
		this.delegate.setValue(value);
		this.label.setText(value == null ? "" : value.toString());
	}

	@Override
	public Object getCellEditorValue() {
		return delegate.getCellEditorValue();
	}

	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
			boolean leaf, int row) {

		delegate.setValue(value);

		String stringValue = tree.convertValueToText(value, isSelected, expanded, leaf, row, false);
		this.label.setText(stringValue);
		
		return this.panel;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		delegate.setValue(value);

		this.label.setText(value == null ? "" : value.toString());
		
		if (isSelected) {
			panel.setForeground(table.getSelectionForeground());
			panel.setBackground(table.getSelectionBackground());
			label.setForeground(table.getSelectionForeground());
			label.setBackground(table.getSelectionBackground());
		} else {
			panel.setForeground(table.getForeground());
			panel.setBackground(table.getBackground());
			label.setForeground(table.getForeground());
			label.setBackground(table.getForeground());
		}

		
		return this.panel;
	}

	/**
	 * 
	 * @author sam
	 *
	 */
	protected class EditorDelegate extends JSEditorDelegateAdapter {
		private static final long serialVersionUID = 4366510118323198573L;

		public EditorDelegate() {
			super();
		}

		/**
		 * Stops editing and returns true to indicate that editing has stopped.
		 * This method calls <code>fireEditingStopped</code>.
		 *
		 * @return true
		 */
		public boolean stopCellEditing() {
			fireEditingStopped();
			return true;
		}

		/**
		 * Cancels editing. This method calls <code>fireEditingCanceled</code>.
		 */
		public void cancelCellEditing() {
			fireEditingCanceled();
		}

		/**
		 * When an action is performed, editing is ended.
		 * 
		 * @param e
		 *            the action event
		 * @see #stopCellEditing
		 */
		public void actionPerformed(ActionEvent e) {
			JSTableButtonEditor.this.stopCellEditing();
		}

		/**
		 * When an item's state changes, editing is ended.
		 * 
		 * @param e
		 *            the action event
		 * @see #stopCellEditing
		 */
		public void itemStateChanged(ItemEvent e) {
			JSTableButtonEditor.this.stopCellEditing();
		}
	}

}
