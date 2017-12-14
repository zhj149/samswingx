package org.sam.swing.table.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.EventObject;

/**
 * table.editor对应的事件代理对象
 * 
 * @author sam
 *
 */
public abstract class JSEditorDelegateAdapter implements ActionListener, ItemListener, Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = -4152162985273984109L;

	/** The value of this cell. */
	protected Object value;

	/**
	 * 是否初始化
	 */
	protected boolean init = false;

	/**
	 * 启动本操作的次数
	 */
	protected int clickCountToStart = 2;

	/**
	 * 当前操作行
	 */
	protected int row = -1;

	/**
	 * 当前操作列
	 */
	protected int column = -1;

	/**
	 * 启动本操作的次数
	 * 
	 * @return
	 */
	public int getClickCountToStart() {
		return clickCountToStart;
	}

	/**
	 * 启动本操作的次数
	 * 
	 * @param clickCountToStart
	 */
	public void setClickCountToStart(int clickCountToStart) {
		this.clickCountToStart = clickCountToStart;
	}

	/**
	 * Returns the value of this cell.
	 * 
	 * @return the value of this cell
	 */
	public Object getCellEditorValue() {
		return value;
	}

	/**
	 * Sets the value of this cell.
	 * 
	 * @param value
	 *            the new value of this cell
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * 当前操作行
	 * @return
	 */
	public int getRow() {
		return row;
	}

	/**
	 * 当前操作行
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * 当前操作列
	 * @return
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * 当前操作列
	 * @param column
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * Returns true if <code>anEvent</code> is <b>not</b> a
	 * <code>MouseEvent</code>. Otherwise, it returns true if the necessary
	 * number of clicks have occurred, and returns false otherwise.
	 *
	 * @param anEvent
	 *            the event
	 * @return true if cell is ready for editing, false otherwise
	 * @see #setClickCountToStart
	 * @see #shouldSelectCell
	 */
	public boolean isCellEditable(EventObject anEvent) {
		if (anEvent instanceof MouseEvent) {
			return ((MouseEvent) anEvent).getClickCount() >= clickCountToStart;
		}
		return true;
	}

	/**
	 * Returns true to indicate that the editing cell may be selected.
	 *
	 * @param anEvent
	 *            the event
	 * @return true
	 * @see #isCellEditable
	 */
	public boolean shouldSelectCell(EventObject anEvent) {
		return true;
	}

	/**
	 * Returns true to indicate that editing has begun.
	 *
	 * @param anEvent
	 *            the event
	 */
	public boolean startCellEditing(EventObject anEvent) {
		return true;
	}

	/**
	 * Stops editing and returns true to indicate that editing has stopped. This
	 * method calls <code>fireEditingStopped</code>.
	 *
	 * @return true
	 */
	public abstract boolean stopCellEditing();

	/**
	 * Cancels editing. This method calls <code>fireEditingCanceled</code>.
	 */
	public abstract void cancelCellEditing();

	/**
	 * When an action is performed, editing is ended.
	 * 
	 * @param e
	 *            the action event
	 * @see #stopCellEditing
	 */
	public abstract void actionPerformed(ActionEvent e);

	/**
	 * When an item's state changes, editing is ended.
	 * 
	 * @param e
	 *            the action event
	 * @see #stopCellEditing
	 */
	public abstract void itemStateChanged(ItemEvent e);
}
