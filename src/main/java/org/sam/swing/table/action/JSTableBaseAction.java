package org.sam.swing.table.action;

import javax.swing.AbstractAction;

import org.jdesktop.swingx.JXTable;

/**
 * 所有action对象的抽象基类
 * @author sam
 *
 */
public abstract class JSTableBaseAction extends AbstractAction {

	private static final long serialVersionUID = -2137655328830377886L;
	
	/**
	 * 当前操作的table对象
	 */
	protected JXTable table;
	
	/**
	 * 当前操作的table对象
	 */
	public JXTable getTable() {
		return table;
	}

	/**
	 * 当前操作的table对象
	 */
	public void setTable(JXTable table) {
		this.table = table;
	}

	/**
	 * 所有action对象的抽象基类
	 * @param table
	 */
	public JSTableBaseAction(JXTable table){
		super();
		this.setTable(table);
	}
}
