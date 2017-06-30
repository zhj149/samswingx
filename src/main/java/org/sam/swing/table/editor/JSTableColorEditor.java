package org.sam.swing.table.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;

import org.sam.swing.JSButtonIcon;
import org.sam.swing.JSPanelColorChooser;
import org.sam.swing.JSPanelColorChooser.ColorChooserLisenter;

/**
 * 颜色编辑器
 * 
 * @author sam
 *
 */
public class JSTableColorEditor extends AbstractCellEditor implements TableCellEditor, TreeCellEditor {

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
	 * colorchoose对象
	 */
	protected JSPanelColorChooser colorChooser;

	/**
	 * 弹出对象
	 */
	protected Popup popup;

	/**
	 * 带有初始化控件对象的操作
	 * 
	 * @param editor
	 */
	public JSTableColorEditor(JButton editor) {
		this.editorComponent = editor;
	}

	/**
	 * 不带参数的默认构造函数
	 */
	public JSTableColorEditor() {
		this(new JButton(new JSButtonIcon()));

		delegate = new EditorDelegate();
		delegate.setClickCountToStart(1);

		editorComponent.addActionListener(delegate);

		if (colorChooser == null)
			colorChooser = new JSPanelColorChooser();

		this.colorChooser.addColorChooserLisenter(new ColorChooserLisenter() {
			@Override
			public void afterChoose(Color color) {
				popup.hide();
				delegate.setValue(color);
			}

			@Override
			public void afterCancle() {
				if (popup != null)
					popup.hide();
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return delegate.getCellEditorValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
			boolean leaf, int row) {

		String stringValue = tree.convertValueToText(value, isSelected, expanded, leaf, row, false);

		delegate.setValue(stringValue);
		return editorComponent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		delegate.setValue(value);

		if (value != null) {
			Point location = java.awt.MouseInfo.getPointerInfo().getLocation();
			popup = PopupFactory.getSharedInstance().getPopup(table, this.colorChooser, (int)location.getX(), (int)location.getY());
			popup.show();
		}

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

			JSButtonIcon icon = (JSButtonIcon) editorComponent.getIcon();
			try {

				if (value == null) {
					icon.setIconColor(Color.BLACK);
					return;
				}

				if (value instanceof Integer) {
					icon.setIconColor(new Color((Integer) value));
				} else if (value instanceof Long) {
					icon.setIconColor(new Color(((Long) value).intValue()));
				} else if (value instanceof String) {
					icon.setIconColor(Color.getColor(value.toString()));
				} else if (value instanceof Color) {
					icon.setIconColor((Color) value);
				}
			} catch (Exception ex) {
				icon.setIconColor(Color.BLACK);
			}

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
			JSTableColorEditor.this.stopCellEditing();
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
			JSTableColorEditor.this.stopCellEditing();
		}
	}
}
