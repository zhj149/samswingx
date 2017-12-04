package org.sam.swing.table.defaultImpl;

import java.util.Collection;
import java.util.LinkedList;

import javax.swing.event.TableModelEvent;

import org.apache.commons.lang3.NotImplementedException;
import org.sam.swing.table.JSTableColumn;
import org.sam.swing.table.JSTableModel;
import org.sam.swing.table.JSTableModelLinster;

/**
 * 默认的实体类型的数据操作对象
 * 
 * @author sam
 *
 * @param <E>
 *            实体类型
 */
public class JSTableArrayModel extends JSTableModel<Collection<Object[]>> {

	private static final long serialVersionUID = -7100624972070901341L;

	/**
	 * 原始的值
	 */
	private Collection<Object[]> orginal;

	/**
	 * 删除的对象集合
	 */
	private Collection<Object[]> deletes = new LinkedList<>();

	/**
	 * 要更新的集合列表
	 */
	private Collection<Object[]> modifies = new LinkedList<>();

	/**
	 * 需要创建的对象列表
	 */
	private Collection<Object[]> creates = new LinkedList<>();

	/**
	 * 必须带有实体的构造类型，好变态
	 * 
	 * @param cls
	 */
	public JSTableArrayModel() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public JSTableModelLinster<Collection<Object[]>> getTableModelLinster() {

		if (super.getTableModelLinster() == null)
			this.setTableModelLinster(new JSTableModelArrayAdapter());
		return super.getTableModelLinster();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Object[]> getOrginal() {
		return orginal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setOrginal(Collection<Object[]> orginal) {
		this.orginal = orginal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Object[]> getDeletes() {
		return this.deletes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDeletes(Collection<Object[]> deletes) {
		this.deletes = deletes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Object[]> getCreates() {
		return this.creates;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCreates(Collection<Object[]> creates) {
		this.creates = creates;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Object[]> getModified() {
		return this.modifies;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setModified(Collection<Object[]> modified) {
		this.modifies = modified;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetUpdate() {
		this.deletes.clear();
		this.creates.clear();
		this.modifies.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasChange() {
		return this.deletes.size() > 0 | this.creates.size() > 0 | this.modifies.size() > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void moveRow(int srcRow, int tarRow) throws Exception {
		if (srcRow < 0 || srcRow >= this.getRowCount())
			throw new Exception("sourceRow over index");

		if (tarRow < 0 || tarRow >= this.getRowCount())
			throw new Exception("targetRow over index");

		this.removeTableModelListener(this);
		try {
			for (int i = 0; i < this.getColumnCount(); i++) {
				Object obj = this.getValueAt(tarRow, i);
				this.setValueAt(this.getValueAt(srcRow, i), tarRow, i);
				this.setValueAt(obj, srcRow, i);
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			this.addTableModelListener(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Object[]> getDatas() throws Exception {
		Collection<Object[]> result = new LinkedList<>();

		int iOriginal = this.findColumn(JSTableColumn.COLUMN_ORIGINAL);
		if (iOriginal < 0)
			throw new Exception("Not include original data");

		for (int i = 0; i < this.getRowCount(); i++) {
			Object[] obj = (Object[]) this.getValueAt(i, iOriginal);
			result.add(obj);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reBuildIndex(int colIndex, int begin, int seed) throws Exception {
		for (int i = 0; i < this.getRowCount(); i++) {
			Integer oldValue = (Integer) this.getValueAt(i, colIndex);
			if (oldValue == null || !oldValue.equals(begin)) {
				this.setValueAt(begin, i, colIndex);
			}
			begin = begin + seed;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] getData(int modelRow) throws Exception {
		int iOriginal = this.findColumn(JSTableColumn.COLUMN_ORIGINAL);
		if (iOriginal < 0)
			throw new Exception("Not include original data");

		if (modelRow < 0 || modelRow >= this.getRowCount())
			throw new Exception("modelRow over index");

		return (Object[]) this.getValueAt(modelRow, iOriginal);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] createNew() throws Exception {
		Object[] datas = new Object[this.getColumnCount()];
		JSTableColumn[] cols = getTableColumns();

		// 未找到绑定的实体列
		int originalCol = this.findColumn(JSTableColumn.COLUMN_ORIGINAL);
		if (originalCol < 0)
			throw new Exception("not init the original column");

		// 初始化数据
		for (int i = 0; i < cols.length; i++) {
			JSTableColumn column = cols[i];
			Object[] original = new Object[cols.length - 1];

			// 无数据绑定列
			if (originalCol == i) {
				datas[originalCol] = original;
			} else // 有数据绑定列
			{
				if (null == column.getDefaultValue()) {
					datas[i] = null;
					original[i] = null;
				} else {
					datas[i] = column.getDefaultValue();
					original[i] = datas[i];
				}
			}
		}

		return datas;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() throws Exception {

		for (int i = this.getRowCount() - 1; i >= 0; i--) {
			this.removeRow(i);
		}
		
		this.resetUpdate();

		if (this.orginal != null)
			this.orginal.clear();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insert(int row, Object t) throws Exception {
		Object[] e = (Object[]) t;

		// 假设已经生成完成了列的映射，然后根据列绑定的次序我们插入数据
		// 数据集合
		Object[] datas = new Object[getColumnCount()];

		if (e != null) {
			// 假设已经生成完成了列的映射，然后根据列绑定的次序我们插入数据
			// 数据集合
			for (int i = 0; i < getColumnCount(); i++) {
				String colName = getColumnName(i);
				
				if (JSTableColumn.COLUMN_ORIGINAL.equals(colName)) {
					datas[i] = e;
				} else {
					if (null == colName || colName.length() <= 0) {
						datas[i] = null;
					} else {
						datas[i] = e[Integer.valueOf(colName)];
					}
				}
			}
		}

		addRow(datas);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void replace(int row, Object t) throws Exception {
		throw new NotImplementedException("there is nothing");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.
	 * TableModelEvent)
	 */
	@Override
	public void tableChanged(TableModelEvent e) {
		// insert 是直接在 tablemodel插入数据的，所以我觉得不用单独在时间里操作了
		if (e.getType() == TableModelEvent.INSERT) {
		} else if (e.getType() == TableModelEvent.UPDATE) {
			try {
				this.removeTableModelListener(this);
				int iRow = e.getFirstRow();

				int findColumn = this.findColumn(JSTableColumn.COLUMN_ORIGINAL);
				if (findColumn < 0)
					throw new Exception("get orignal data column fail");

				Object[] original = (Object[]) this.getValueAt(iRow, findColumn);
				if (original == null)
					throw new Exception("get orignal data fail");

				original[e.getColumn()] = this.getValueAt(e.getFirstRow(), e.getColumn());

				if (this.creates.contains(original)) {
					// 如果新增行包含数据
				} else if (this.modifies.contains(original)) {
					// 如果是在更新组里的话
				} else {
					this.modifies.add(original);
				}
				// 以下这句会引起连锁反应，比如在窗口上更新了一个数据，然后会重新激发本事件代码，但是列变成了col_orginal
				this.setValueAt(original, iRow, findColumn);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				this.addTableModelListener(this);
			}
		} else if (e.getType() == TableModelEvent.DELETE) {
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int onRetrieve() throws Exception {
		try {

			orginal = this.getTableModelLinster().onRetrieve();

			/**
			 * 如果设置为插入空白行，则新增加的时候，直接插入一行空白
			 */
			if (this.isRetrieveWithEmptyRow()) {
				Object[] createNew = this.createNew();
				int findColumn = this.findColumn(JSTableColumn.COLUMN_ORIGINAL);
				createNew[findColumn] = null;
				addRow(createNew);
			}

			if (orginal != null && !orginal.isEmpty()) {
				for (Object[] entity : orginal) {
					// 假设已经生成完成了列的映射，然后根据列绑定的次序我们插入数据
					// 数据集合
					Object[] datas = new Object[getColumnCount()];
					for (int i = 0; i < getColumnCount(); i++) {
						String colName = getColumnName(i);
						
						if (JSTableColumn.COLUMN_ORIGINAL.equals(colName)) {
							datas[i] = entity;
						} else {
							if (null == colName || colName.length() <= 0) {
								datas[i] = null;
							} else {
								datas[i] = entity[Integer.valueOf(colName)];
							}
						}
					}
					addRow(datas);
				}

				return orginal.size();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} 
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onDelete(int moduleRow) throws Exception {
		int findColumn = this.findColumn(JSTableColumn.COLUMN_ORIGINAL);
		if (findColumn < 0)
			return false;

		Object[] entity = (Object[])this.getValueAt(moduleRow, findColumn);
		if (entity == null)
			return false;

		if (this.modifies.contains(entity)) {
			this.deletes.add(entity);
			this.modifies.remove(entity);
		} else if (this.creates.contains(entity)) {
			this.creates.remove(entity);
		} else {
			this.deletes.add(entity);
		}

		this.removeRow(moduleRow);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onInsert(int moduleRow) throws Exception {
		Object[] datas = createNew();

		int findColumn = this.findColumn(JSTableColumn.COLUMN_ORIGINAL);
		this.creates.add((Object[])datas[findColumn]);
		this.insertRow(moduleRow, datas);

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onAppend() throws Exception {
		Object[] datas = createNew();

		int findColumn = this.findColumn(JSTableColumn.COLUMN_ORIGINAL);
		this.creates.add((Object[]) datas[findColumn]);
		this.addRow(datas);

		return true;
	}

}
