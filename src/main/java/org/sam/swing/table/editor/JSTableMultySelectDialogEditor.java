package org.sam.swing.table.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.lang.reflect.Field;
import java.util.EventObject;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;

import org.sam.swing.table.view.JSTableColumnMappingAbstract;

public class JSTableMultySelectDialogEditor extends AbstractCellEditor implements TableCellEditor, TreeCellEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3789071656674137030L;

	/** The Swing component being edited. */
	protected JComponent editorComponent;
	/**
	 * The delegate class which handles all methods sent from the
	 * <code>CellEditor</code>.
	 */
	protected JSEditorDelegateAdapter delegate;

	/**
	 * 设置操作代理对象
	 * 
	 * @return
	 */
	public JSEditorDelegateAdapter getDelegate() {
		return delegate;
	}

	public void setDelegate(JSEditorDelegateAdapter delegate) {
		
		if (this.delegate != null)
			btn.removeActionListener(this.delegate);
		
		this.delegate = delegate;
		
		if (this.delegate != null)
			btn.addActionListener(this.delegate);
	}

	/**
	 * An integer specifying the number of clicks needed to start editing. Even
	 * if <code>clickCountToStart</code> is defined as zero, it will not
	 * initiate until a click occurs.
	 */
	protected int clickCountToStart = 1;

	/**
	 * 绑定显示的字段
	 */
	protected String fieldName;

	/**
	 * 绑定显示的字段
	 * 
	 * @return
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * 绑定显示的字段
	 * 
	 * @param fieldName
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * 表的映射关系
	 */
	private JSTableColumnMappingAbstract mapping;

	/**
	 * 表的映射关系
	 * 
	 * @return
	 */
	public JSTableColumnMappingAbstract getMapping() {
		return mapping;
	}

	/**
	 * 实体的映射关系
	 * 
	 * @param mapping
	 */
	public void setMapping(JSTableColumnMappingAbstract mapping) {
		this.mapping = mapping;
	}

	private JLabel label;

	/**
	 * 主体的框架
	 */
	private JPanel panel;

	/**
	 * 按钮
	 */
	private JButton btn;

	/**
	 * true 返回的对象值 false 返回对应的toString
	 */
	private boolean objectData = true;

	/**
	 * true 返回的对象值 false 返回对应的字段值
	 * 
	 * @return
	 */
	public boolean isObjectData() {
		return objectData;
	}

	/**
	 * true 返回的对象值 false 返回对应的字段值
	 * 
	 * @param objectData
	 */
	public void setObjectData(boolean objectData) {
		this.objectData = objectData;
	}

	/**
	 * 双击编辑弹出多选对话框
	 * 
	 * @param colMapping
	 * @param fieldName
	 */
	public JSTableMultySelectDialogEditor(JSTableColumnMappingAbstract colMapping, String fieldName) {
		this(colMapping, null, fieldName);

	}

	/**
	 * 双击编辑弹出多选对话框
	 * 
	 * @param colMapping
	 * @param delegate
	 * @param fieldName
	 */
	public JSTableMultySelectDialogEditor(JSTableColumnMappingAbstract colMapping,
			JSEditorDelegateAdapter delegate, String fieldName) {
		super();
		label = new JLabel();
		btn = new JButton(".");
		panel = new JPanel(new BorderLayout());
		panel.add(label, BorderLayout.CENTER);
		panel.add(btn, BorderLayout.EAST);
		this.editorComponent = panel;
		btn.setVisible(false);
		
		this.mapping = colMapping;
		
		if (delegate != null) {
			this.setDelegate(delegate);
		} else {
			delegate = new EditorDelegate();
			this.setDelegate(delegate);
		}
		btn.addActionListener(delegate);
		this.clickCountToStart = 2;
	}

	/**
	 * Specifies the number of clicks needed to start editing.
	 *
	 * @param count
	 *            an int specifying the number of clicks needed to start editing
	 * @see #getClickCountToStart
	 */
	public void setClickCountToStart(int count) {
		clickCountToStart = count;
	}

	/**
	 * Returns the number of clicks needed to start editing.
	 * 
	 * @return the number of clicks needed to start editing
	 */
	public int getClickCountToStart() {
		return clickCountToStart;
	}

	//
	// Override the implementations of the superclass, forwarding all methods
	// from the CellEditor interface to our delegate.
	//

	/**
	 * Forwards the message from the <code>CellEditor</code> to the
	 * <code>delegate</code>.
	 * 
	 * @see EditorDelegate#getCellEditorValue
	 */
	public Object getCellEditorValue() {
		return delegate.getCellEditorValue();
	}

	/**
	 * Forwards the message from the <code>CellEditor</code> to the
	 * <code>delegate</code>.
	 * 
	 * @see EditorDelegate#isCellEditable(EventObject)
	 */
	public boolean isCellEditable(EventObject anEvent) {
		return delegate.isCellEditable(anEvent);
	}

	/**
	 * Forwards the message from the <code>CellEditor</code> to the
	 * <code>delegate</code>.
	 * 
	 * @see EditorDelegate#shouldSelectCell(EventObject)
	 */
	public boolean shouldSelectCell(EventObject anEvent) {
		return delegate.shouldSelectCell(anEvent);
	}

	/**
	 * Forwards the message from the <code>CellEditor</code> to the
	 * <code>delegate</code>.
	 * 
	 * @see EditorDelegate#stopCellEditing
	 */
	public boolean stopCellEditing() {
		return delegate.stopCellEditing();
	}

	/**
	 * Forwards the message from the <code>CellEditor</code> to the
	 * <code>delegate</code>.
	 * 
	 * @see EditorDelegate#cancelCellEditing
	 */
	public void cancelCellEditing() {
		delegate.cancelCellEditing();
	}

	//
	// Implementing the TreeCellEditor Interface
	//

	/** Implements the <code>TreeCellEditor</code> interface. */
	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
			boolean leaf, int row) {
		String stringValue = tree.convertValueToText(value, isSelected, expanded, leaf, row, false);

		delegate.setValue(stringValue);
		return editorComponent;
	}

	//
	// Implementing the CellEditor Interface
	//
	/** Implements the <code>TableCellEditor</code> interface. */
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		delegate.setValue(value);
		return editorComponent;
	}

	/**
	 * The protected <code>EditorDelegate</code> class.
	 */
	public class EditorDelegate extends JSEditorDelegateAdapter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1949419889509892785L;

		/**
		 * 设置数据的操作
		 */
		@Override
		public void setValue(Object value) {
			// 此处实现有点low
			// 原因是我不太明白swing这个table的工作原理，
			// 我猜测，在table第一次加载的时候，本editor就被实例化，然后隐藏起来备用
			// 所有我不在这里设置一下数据，就会窗口加载的时候，一直弹出对话框
			if (!init) {
				label.setText("");
				init = true;
				return;
			}

			this.value = value;

			JSTableMultySelectionDialog dialog = new JSTableMultySelectionDialog(getMapping(), value);
			dialog.setData(value);
			dialog.setVisible(true);

			if (JSTableMultySelectionDialog.OK.equals(dialog.getAction())) {
				try {
					if (this.value instanceof Set<?>) {
						this.value = dialog.getSet();
					} else if (this.value instanceof List<?>) {
						this.value = dialog.getList();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			if (this.value instanceof String) {
				label.setText((this.value == null) ? "" : this.value.toString());
			}

			else if (this.value != null && fieldName != null && fieldName.length() > 0) {
				try {
					if (this.value != null) {
						Field field = this.value.getClass().getDeclaredField(fieldName);
						field.setAccessible(true);
						Object object = field.get(this.value);
						label.setText((object == null) ? "" : object.toString());

						if (!isObjectData()) {
							this.value = label.getText();
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					label.setText("");
				}

			} else if (this.value != null) {
				label.setText(this.value.toString());
			} else {
				label.setText("");
			}
		}

		/**
		 * 获取单元格数据
		 */
		public Object getCellEditorValue() {
			return this.value;
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
			JSTableMultySelectDialogEditor.this.stopCellEditing();
		}

		/**
		 * When an item's state changes, editing is ended.
		 * 
		 * @param e
		 *            the action event
		 * @see #stopCellEditing
		 */
		public void itemStateChanged(ItemEvent e) {
			JSTableMultySelectDialogEditor.this.stopCellEditing();
		}
	}

}
