package org.sam.swing.table.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;

import org.apache.commons.lang3.StringUtils;
import org.sam.swing.table.action.JSTableChooseAction;
import org.sam.swing.table.action.JSTableDefaultAction;
import org.sam.swing.utils.ReflectUtil;

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
	 * 显示actionbutton按钮操作
	 * @param visible 是否显示
	 */
	public void setEnableActionButton(boolean visible) {
		if (this.btnAction != null){
			this.btnAction.setVisible(visible);
		}
	}

	/**
	 * 显示清空按钮
	 * @param visible
	 */
	public void setEnableCanelButton(boolean visible) {
		if (this.btnCancel != null){
			this.btnCancel.setVisible(visible);
		}
	}
	
	/**
	 * 按钮
	 */
	private JButton btnAction;

	/**
	 * 取消操作
	 */
	private JButton btnCancel;

	/**
	 * 操作代理对象
	 */
	private EditorDelegate delegate;

	/**
	 * 绑定的操作对象
	 */
	private JSTableChooseAction action;

	/**
	 * 绑定的操作对象
	 * 
	 * @return
	 */
	public JSTableChooseAction getAction() {
		return action;
	}

	/**
	 * 绑定的操作对象
	 * 
	 * @param action
	 */
	public void setAction(JSTableChooseAction action) {
		this.action = action;
	}
	
	/**
	 * 用于显示的字段
	 */
	private String fieldName;

	/**
	 * 用于显示的字段
	 * @return
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * 用于显示的字段
	 * @param fieldName
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * 无参数构造函数
	 */
	public JSTableButtonEditor() {
		this("");
	}

	/**
	 * 设置显示字段的构造函数
	 * 
	 * @param fieldName
	 */
	public JSTableButtonEditor(String fieldName) {
		this(fieldName, null);
	}

	/**
	 * 有参数构造函数
	 * 
	 * @param fieldName
	 *            字段名称
	 */
	public JSTableButtonEditor(String fieldName, JSTableChooseAction action) {
		super();
		this.setFieldName(fieldName);
		
		if (action == null) {
			action = new JSTableDefaultAction();
		}

		panel = new JPanel(new BorderLayout());
		label = new JLabel("");
		panel.add(label, BorderLayout.CENTER);

		JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));

		// 执行操作的按钮
		btnAction = new JButton(new DecoratorAction(action));
		btnAction.setFont(new Font("宋体", Font.PLAIN, 8));
		panelButtons.add(btnAction);

		btnCancel = new JButton("X");
		btnCancel.setFont(new Font("宋体", Font.BOLD, 8));
		btnCancel.addActionListener((e) -> {
			this.setValue(null);
		});
		panelButtons.add(btnCancel);

		panel.add(panelButtons, BorderLayout.EAST);

		delegate = new EditorDelegate();

		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1)
					btnAction.doClick();
			}
		});
	}

	/**
	 * 设置值
	 * 
	 * @param value
	 */
	public void setValue(Object value) {
		this.delegate.setValue(value);
		this.setText(value);
	}
	
	/**
	 * 
	 * @param value
	 */
	private void setText(Object value){
		if (StringUtils.isEmpty(this.fieldName)){
			this.label.setText(value == null ? "" : value.toString());
		}else{
			this.label.setText(ReflectUtil.getDisplay(value, fieldName));
		}
	}

	/**
	 * 获取值
	 */
	@Override
	public Object getCellEditorValue() {
		return delegate.getCellEditorValue();
	}

	/**
	 * 树节点的调用
	 */
	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
			boolean leaf, int row) {

		delegate.setValue(value);
		String stringValue = tree.convertValueToText(value, isSelected, expanded, leaf, row, false);
		this.label.setText(stringValue);
		return this.panel;
	}

	/**
	 * 表格节点的调用
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		delegate.setValue(value);
		this.setText(value);
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

	/**
	 * 执行按钮点击操作的装饰类
	 * @author sam
	 *
	 */
	private class DecoratorAction extends AbstractAction {

		private static final long serialVersionUID = -7295640104332726324L;
		
		/**
		 * 原始的action的对象
		 */
		private JSTableChooseAction orignalAction;

		/**
		 * JStableButtonEditor action的装饰器
		 * 
		 * @param action
		 */
		public DecoratorAction(JSTableChooseAction action) {
			this.orignalAction = action;
			this.putValue(Action.NAME, ".");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			orignalAction.setValue(delegate.getCellEditorValue());
			orignalAction.actionPerformed(e);
			if (JSTableChooseAction.Result.OK.equals(orignalAction.getResult())){
				JSTableButtonEditor.this.setValue(orignalAction.getValue());
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object getValue(String key) {
			return orignalAction.getValue(key);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void putValue(String key, Object value) {
			orignalAction.putValue(key, value);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setEnabled(boolean b) {
			orignalAction.setEnabled(b);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isEnabled() {
			return orignalAction.isEnabled();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void addPropertyChangeListener(PropertyChangeListener listener) {
			orignalAction.addPropertyChangeListener(listener);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void removePropertyChangeListener(PropertyChangeListener listener) {
			orignalAction.removePropertyChangeListener(listener);
		}

	}
}
