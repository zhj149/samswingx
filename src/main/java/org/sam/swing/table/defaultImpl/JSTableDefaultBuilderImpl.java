package org.sam.swing.table.defaultImpl;

import java.util.Collection;

import org.sam.swing.table.JSTableBuilder;
import org.sam.swing.table.JSTableColumn;
import org.sam.swing.table.JSTableColumnModel;
import org.sam.swing.table.JSTableModel;
import org.sam.swing.table.JSTableModelLinster;

/**
 * 默认的tablemodel和columnmodel实现
 * 
 * @author sam
 *
 * @param <E>
 */
public class JSTableDefaultBuilderImpl<E> implements JSTableBuilder<Collection<E>> {

	/**
	 * 当前的column列表
	 */
	private JSTableColumn[] columns;

	/**
	 * 当前的类型
	 */
	private Class<E> cls;

	/**
	 * 当前操作的column列表
	 * 
	 * @return
	 */
	public JSTableColumn[] getColumns() {
		return columns;
	}

	/**
	 * 当前操作的column列表
	 * 
	 * @param columns
	 */
	public void setColumns(JSTableColumn[] columns) {
		this.columns = columns;
	}

	/**
	 * 缓存的colmodel对象
	 */
	private JSTableColumnModel colModel;

	/**
	 * 缓存的tablemodel对象
	 */
	private JSTableModel<Collection<E>> tableModel;

	/**
	 * 带有列信息的构造函数
	 * 
	 * @param cols
	 */
	public JSTableDefaultBuilderImpl(Class<E> cls, JSTableColumn... cols) {
		this.columns = cols;
		this.cls = cls;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JSTableColumnModel buildTableColumnModel() throws Exception {
		JSTableColumnModel colModel = new JSTableColumnModel();

		if (this.columns == null || this.columns.length <= 0)
			throw new Exception("no columns");

		int i = 0;
		for (JSTableColumn col : this.columns) {
			if (col.getModelIndex() < 0) {
				col.setModelIndex(i);
			}
			colModel.addColumn(col);
			i++;
		}

		this.colModel = colModel;

		return colModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JSTableModel<Collection<E>> buildTableModel() throws Exception {
		JSTableDefaultModel<E> tabModel = new JSTableDefaultModel<>(cls);

		if (this.columns == null || this.columns.length <= 0)
			throw new Exception("no columns");

		for (JSTableColumn col : this.columns) {
			tabModel.addColumn(col);
		}

		// 生成原始数据的列
		if (tabModel.findColumn(JSTableColumn.COLUMN_ORIGINAL) < 0) {
			JSTableColumn column = new JSTableColumn();
			column.setTitle(JSTableColumn.COLUMN_ORIGINAL);
			column.setHeaderValue(JSTableColumn.COLUMN_ORIGINAL);
			tabModel.addColumn(column);
		}

		this.tableModel = tabModel;

		return tabModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JSTableModelLinster<Collection<E>> buildModelLinster(JSTableModel<Collection<E>> tableModel)
			throws Exception {
		JSTableModelLinster<Collection<E>> adapter = new JSTableModelDefaultAdapter<>();
		tableModel.setTableModelLinster(adapter);
		return adapter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JSTableModel<Collection<E>> getTableModel() throws Exception {
		if (this.tableModel == null)
			this.tableModel = buildTableModel();

		return this.tableModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JSTableColumnModel getTableColumnModel() throws Exception {
		if (this.colModel == null)
			this.colModel = buildTableColumnModel();

		return this.colModel;
	}
}
