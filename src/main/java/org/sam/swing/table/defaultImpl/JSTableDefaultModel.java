package org.sam.swing.table.defaultImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.event.TableModelEvent;

import org.sam.swing.table.JSTableColumn;
import org.sam.swing.table.JSTableModel;
import org.sam.swing.table.JSTableModelLinster;
import org.sam.swing.utils.Pair;
import org.sam.swing.utils.ReflectUtil;

/**
 * 默认的实体类型的数据操作对象
 * 
 * @author sam
 *
 * @param <E>
 *            实体类型
 */
public class JSTableDefaultModel<E> extends JSTableModel<Collection<E>> {

	private static final long serialVersionUID = -7100624972070901341L;

	/**
	 * 原始的值
	 */
	private volatile Collection<E> orginal;

	/**
	 * 删除的对象集合o
	 */
	private Collection<E> deletes = new LinkedList<>();

	/**
	 * 要更新的集合列表
	 */
	private Collection<E> modifies = new LinkedList<>();

	/**
	 * 需要创建的对象列表
	 */
	private Collection<E> creates = new LinkedList<>();

	/**
	 * 当前的泛型的类型
	 */
	private Class<E> cls;

	/**
	 * 必须带有实体的构造类型，好变态
	 * 
	 * @param cls
	 */
	public JSTableDefaultModel(Class<E> cls) {
		super();
		this.cls = cls;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return
	 */
	@Override
	public JSTableModelLinster<Collection<E>> getTableModelLinster() {

		if (super.getTableModelLinster() == null)
			this.setTableModelLinster(new JSTableModelDefaultAdapter<E>());
		return super.getTableModelLinster();
	}

	/**
	 * 获取当前泛型的类型
	 * 
	 * @return
	 */
	public Class<E> getCls() {
		return this.cls;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<E> getOrginal() {
		return this.orginal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setOrginal(Collection<E> orginal) {
		this.orginal = orginal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<E> getDeletes() {
		return this.deletes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDeletes(Collection<E> deletes) {
		this.deletes = deletes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<E> getCreates() {
		return this.creates;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCreates(Collection<E> creates) {
		this.creates = creates;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<E> getModified() {
		return this.modifies;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setModified(Collection<E> modified) {
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
	@SuppressWarnings("unchecked")
	@Override
	public Collection<E> getDatas() throws Exception {
		Collection<E> result = new LinkedList<>();

		int iOriginal = this.findColumn(JSTableColumn.COLUMN_ORIGINAL);
		if (iOriginal < 0)
			throw new Exception("Not include original data");

		for (int i = 0; i < this.getRowCount(); i++) {
			Object obj = this.getValueAt(i, iOriginal);
			result.add((E) obj);
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
	public Object getData(int modelRow) throws Exception {
		int iOriginal = this.findColumn(JSTableColumn.COLUMN_ORIGINAL);
		if (iOriginal < 0)
			throw new Exception("Not include original data");

		if (modelRow < 0 || modelRow >= this.getRowCount())
			throw new Exception("modelRow over index");

		return this.getValueAt(modelRow, iOriginal);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] createNew() throws Exception {
		Object entity = this.getCls().newInstance();
		Object[] datas = new Object[this.getColumnCount()];
		JSTableColumn[] cols = getTableColumns();

		// 未找到绑定的实体列
		int originalCol = this.findColumn(JSTableColumn.COLUMN_ORIGINAL);
		if (originalCol < 0)
			throw new Exception("not init the original column");

		// 初始化数据
		for (int i = 0; i < cols.length; i++) {
			JSTableColumn column = cols[i];
			String colName = column.getIdentifier().toString();
			int findColumn = this.findColumn(colName);
			if (findColumn < 0)
				continue;

			// 无数据绑定列
			if (null == colName || colName.length() <= 0) {

			} else if (originalCol == i) {
				datas[originalCol] = entity;
			} else {
				// 有数据绑定列

				// 先判断get函数，没有get函数，才访问成员
				Method method = ReflectUtil.getSetMethod(entity, colName);

				if (method != null) {
					datas[findColumn] = column.getDefaultValue();
					method.invoke(entity, datas[findColumn]);
				} else {
					Field field = this.getCls().getDeclaredField(colName);
					if (field == null)
						continue;
					field.setAccessible(true);

					datas[findColumn] = column.getDefaultValue();
					field.set(entity, datas[findColumn]);
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
	@SuppressWarnings("unchecked")
	@Override
	public void insert(int row, Object t) throws Exception {
		E e = (E) t;

		// 假设已经生成完成了列的映射，然后根据列绑定的次序我们插入数据
		// 数据集合
		Object[] datas = new Object[getColumnCount()];

		if (e != null) {
			for (int i = 0; i < getColumnCount(); i++) {
				String colName = getColumnName(i);
				if (JSTableColumn.COLUMN_ORIGINAL.equals(colName)) {
					datas[i] = e;
				} else {
					if (null == colName || colName.length() <= 0) {
						datas[i] = null;
					} else {

						Pair<Boolean, Object> result = ReflectUtil.invokeGetMethod(e, colName);

						if (result.getKey()) {
							datas[i] = result.getValue();
						} else {
							Field field = this.getCls().getDeclaredField(colName);
							if (field == null)
								continue;
							field.setAccessible(true);
							datas[i] = field.get(e);
						}

					}
				}
			}
		}

		this.insertRow(row, datas);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void replace(int row, Object t) throws Exception {
		try{
			this.removeTableModelListener(this);
			// 假设已经生成完成了列的映射，然后根据列绑定的次序我们插入数据
			// 数据集合
			for (int i = 0; i < getColumnCount(); i++) {
				String colName = getColumnName(i);
				if (JSTableColumn.COLUMN_ORIGINAL.equals(colName)) {
					this.setValueAt(t, row, i);
				} else {
					if (null == colName || colName.length() <= 0) {
						this.setValueAt(null, row, i);
					} else {
						Pair<Boolean, Object> result = ReflectUtil.invokeGetMethod(t, colName);

						if (result.getKey()) {
							this.setValueAt(result.getValue(), row, i);
						} else {
							Field field = this.getCls().getDeclaredField(colName);
							if (field == null)
								continue;
							field.setAccessible(true);
							this.setValueAt(field.get(t), row, i);
						}
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		} finally{
			this.addTableModelListener(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.
	 * TableModelEvent)
	 */
	@SuppressWarnings("unchecked")
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

				Object entity = this.getValueAt(iRow, findColumn);
				if (entity == null)
					throw new Exception("get orignal data fail");

				String colName = this.getColumnName(e.getColumn());
				if (colName == null || colName.length() <= 0)
					return;
				
				if (JSTableColumn.COLUMN_ORIGINAL.equals(colName)){
					return;
				}

				JSTableColumn[] columns = this.getTableColumns();
				int iFind = this.findColumn(colName);
				JSTableColumn curColumn = columns[iFind];

				Method method = ReflectUtil.getSetMethod(entity,colName);
				Method getMethod = ReflectUtil.getMethod(entity,colName);

				if (method != null) {
					method.invoke(entity, this.getTableModelLinster().getDataTranstor(curColumn,
							this.getValueAt(e.getFirstRow(), e.getColumn()), getMethod.getReturnType()));
				} else {
					Field field = this.getCls().getDeclaredField(colName);
					field.setAccessible(true);
					field.set(entity, this.getTableModelLinster().getDataTranstor(curColumn,
							this.getValueAt(e.getFirstRow(), e.getColumn()), field.getType()));
				}

				if (this.creates.contains(entity)) {
					// 如果新增行包含数据
				} else if (this.modifies.contains(entity)) {
					// 如果是在更新组里的话
				} else {
					this.modifies.add((E) entity);
				}
				// 以下这句会引起连锁反应，比如在窗口上更新了一个数据，然后会重新激发本事件代码，但是列变成了col_orginal
				this.setValueAt(entity, iRow, findColumn);
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
				for (Object entity : orginal) {
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
								Pair<Boolean, Object> result = ReflectUtil.invokeGetMethod(entity, colName);

								if (result.getKey()) {
									datas[i] = result.getValue();
								} else {
									Field field = this.getCls().getDeclaredField(colName);
									if (field == null)
										continue;
									field.setAccessible(true);
									datas[i] = field.get(entity);
								}
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
	@SuppressWarnings("unchecked")
	@Override
	public boolean onDelete(int moduleRow) throws Exception {
		int findColumn = this.findColumn(JSTableColumn.COLUMN_ORIGINAL);
		if (findColumn < 0)
			return false;
		
		if (moduleRow < 0 )
			return true;

		Object entity = this.getValueAt(moduleRow, findColumn);
		if (entity == null)
			return false;

		if (this.modifies.contains(entity)) {
			this.deletes.add((E) entity);
			this.modifies.remove(entity);
		} else if (this.creates.contains(entity)) {
			this.creates.remove(entity);
		} else {
			this.deletes.add((E) entity);
		}

		this.removeRow(moduleRow);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean onInsert(int moduleRow) throws Exception {
		Object[] datas = createNew();

		int findColumn = this.findColumn(JSTableColumn.COLUMN_ORIGINAL);
		this.creates.add((E) datas[findColumn]);
		this.insertRow(moduleRow, datas);

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean onAppend() throws Exception {
		Object[] datas = createNew();

		int findColumn = this.findColumn(JSTableColumn.COLUMN_ORIGINAL);
		this.creates.add((E) datas[findColumn]);
		this.addRow(datas);

		return true;
	}
	
}
