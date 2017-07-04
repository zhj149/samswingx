package org.sam.swing.table.editor;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;

import org.sam.swing.JSFontChooserDialog;
import org.sam.swing.JSFontChooserDialog.FontChooserLisenter;

/**
 * 字体选择编辑器
 * 
 * @author sam
 *
 */
public class JSTableFontEditor extends AbstractCellEditor implements TreeCellEditor, TableCellEditor {

	private static final long serialVersionUID = 757140289750579347L;

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
	protected JSFontChooserDialog fontChooser;

	/**
	 * 带有初始化控件对象的操作
	 * 
	 * @param editor
	 */
	public JSTableFontEditor(JButton editor) {
		this.editorComponent = editor;
	}

	/**
	 * 不带参数的默认构造函数
	 */
	public JSTableFontEditor() {
		this(new JButton());

		delegate = new EditorDelegate();
		delegate.setClickCountToStart(2);

		editorComponent.addActionListener(delegate);

		if (fontChooser == null)
			fontChooser = new JSFontChooserDialog();

		this.fontChooser.addFontChooserLisenter(new FontChooserLisenter() {

			/**
			 * 点击确定后执行的操作
			 */
			@Override
			public void afterChoose(Font font, String fontDecode) {
				if (delegate.getCellEditorValue() instanceof Font)
					delegate.setValue(font);
				else if (delegate.getCellEditorValue() instanceof String)
					delegate.setValue(fontDecode);
				else
					delegate.setValue(fontDecode);
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

		if (isSelected) {
			if (value == null) {
				fontChooser.setVisible(true);
			} else {
				try {
					
					if (value instanceof String) {
						fontChooser.setSelectFont(Font.decode(value.toString()));
					} else if (value instanceof Font) {
						fontChooser.setSelectFont((Font) value);
					}
					
					fontChooser.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
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

			try {
				editorComponent.setText(value == null ? "" : value.toString());
			} catch (Exception ex) {
				editorComponent.setText("");
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
			JSTableFontEditor.this.stopCellEditing();
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
			JSTableFontEditor.this.stopCellEditing();
		}
	}

}
