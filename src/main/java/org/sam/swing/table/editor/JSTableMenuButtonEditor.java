package org.sam.swing.table.editor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.AbstractCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;

import org.sam.swing.resource.ResourceLoader;

/**
 * 图片按钮编辑器
 * 主要作用还是执行一个什么操作
 * 
 * @author sam
 *
 */
public class JSTableMenuButtonEditor extends AbstractCellEditor implements TableCellEditor, TreeCellEditor {

	private static final long serialVersionUID = -1287059888797387629L;

	/**
	 * 操作代理对象
	 */
	private EditorDelegate delegate;
	
	/**
	 * 当前的编辑控件
	 */
	protected JButton editorComponent;
	
	/**
	 * 当前操作的menu对象
	 */
	protected JPopupMenu menu;

	/**
	 * 当前操作的menu对象
	 * @return
	 */
	public JPopupMenu getMenu() {
		return menu;
	}

	/**
	 * 当前操作的menu对象
	 * @param menu
	 */
	public void setMenu(JPopupMenu menu) {
		this.menu = menu;
	}

	/**
	 * 当前的封装对象
	 * @return
	 */
	public EditorDelegate getDelegate() {
		return delegate;
	}

	/**
	 * 带有初始化控件对象的操作
	 * 
	 * @param editor
	 */
	public JSTableMenuButtonEditor(JButton editor) {
		this.editorComponent = editor;
		delegate = new EditorDelegate();
		delegate.setClickCountToStart(1);

		editorComponent.addActionListener(delegate);
		
	}

	/**
	 * 不带参数的默认构造函数
	 */
	public JSTableMenuButtonEditor(Icon icon) {
		this(new JButton(icon));
	}
	
	/**
	 * 使用默认的下拉箭头图标
	 * @param menu
	 */
	public JSTableMenuButtonEditor(JPopupMenu menu) {
		this(new JButton(new ImageIcon(ResourceLoader.getResource(ResourceLoader.IMAGE_DOWN_ARROW))));
		this.setMenu(menu);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getCellEditorValue() {
		return delegate.getCellEditorValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
			boolean leaf, int row) {
		
		String stringValue = tree.convertValueToText(value, isSelected, expanded, leaf, row, false);
		
		delegate.setRow(row);
		delegate.setValue(stringValue);
		return editorComponent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		if (isSelected) {
			editorComponent.setBackground(table.getSelectionBackground());
		} else {
			editorComponent.setBackground(table.getBackground());
		}
		
		delegate.setColumn(column);
		delegate.setRow(row);
		delegate.setValue(value);
		return editorComponent;
	}

	/**
	 * 实现的内部方法
	 * 
	 * @author sam
	 *
	 */
	protected class EditorDelegate extends JSEditorDelegateAdapter {

		private static final long serialVersionUID = -9163652875049976071L;

		/**
		 * 值发生改变的时候执行的操作
		 */
		public void setValue(Object value) {
			super.setValue(value);
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
		@Override
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
		@Override
		public void actionPerformed(ActionEvent e) {
			if (menu != null)
				menu.show(editorComponent, editorComponent.getWidth() / 2 , editorComponent.getHeight());
			JSTableMenuButtonEditor.this.stopCellEditing();
		}

		/**
		 * When an item's state changes, editing is ended.
		 * 
		 * @param e
		 *            the action event
		 * @see #stopCellEditing
		 */
		@Override
		public void itemStateChanged(ItemEvent e) {
			JSTableMenuButtonEditor.this.stopCellEditing();
		}
	}
}
