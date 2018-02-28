package org.sam.swing.table.editor;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;

import org.sam.swing.JSDateSpinner;

/**
 * 日期滚动框
 * 
 * @author sam
 *
 */
public class JSTableDateSpinnerEditor extends AbstractCellEditor implements TreeCellEditor, TableCellEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2254897074135560584L;

	/**
	 * 当前的掩码格式控件
	 */
	private JSDateSpinner editor;

	/**
	 * 当前的掩码格式控件
	 * 
	 * @return
	 */
	public JSDateSpinner getEditor() {
		return editor;
	}

	/**
	 * 当前的掩码格式控件
	 * 
	 * @param editor
	 */
	public void setEditor(JSDateSpinner editor) {
		this.editor = editor;
	}
	
	/**
	 * 几次鼠标操作开始编辑
	 */
	private int clickCountToStart = 2;
	
	/**
	 * 几次鼠标操作开始编辑
	 * @return
	 */
	public int getClickCountToStart() {
		return clickCountToStart;
	}

	/**
	 * 几次鼠标操作开始编辑
	 * @param clickCountToStart
	 */
	public void setClickCountToStart(int clickCountToStart) {
		this.clickCountToStart = clickCountToStart;
	}

	/**
	 * 不带掩码格式的构造函数
	 */
	public JSTableDateSpinnerEditor() {
		super();
		editor = new JSDateSpinner();
	}

	/**
	 * 带有掩码格式的构造函数
	 * 
	 * @param format
	 */
	public JSTableDateSpinnerEditor(String format) {
		super();
		editor = new JSDateSpinner(format);
	}
	
	/**
	 * create with SimpleDateFormat
	 * @param format
	 */
	public JSTableDateSpinnerEditor(SimpleDateFormat format) {
		super();
		editor = new JSDateSpinner(format);
	}

	/**
	 * 带有控件的构造函数
	 * 
	 * @param editor
	 */
	public JSTableDateSpinnerEditor(JSDateSpinner editor) {
		super();
		this.setEditor(editor);
	}

	@Override
	public Object getCellEditorValue() {
		try {

			if (JSDateSpinner.isNull((Date) editor.getValue()))
				return null;
			else
				return editor.getValue();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		this.editor.setValue(value);
		return this.editor;
	}

	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
			boolean leaf, int row) {

		this.editor.setValue(value);
		return this.editor;
	}

	/**
	 * 什么情况下开始允许编辑
	 */
	@Override
	public boolean isCellEditable(EventObject anEvent) {
		if (anEvent instanceof MouseEvent) {
            return ((MouseEvent)anEvent).getClickCount() >= clickCountToStart;
        }
        return true;
    }
}
