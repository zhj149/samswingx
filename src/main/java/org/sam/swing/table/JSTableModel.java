package org.sam.swing.table;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 * 扩展的TableModule对象 最终生成类的父类 采用模版模式
 * 
 * @author sam
 *
 */
public abstract class JSTableModel<T> extends DefaultTableModel implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5129544725258528221L;

	/**
	 * 无数据行的构造函数
	 */
	public JSTableModel() {
		super();
	}

	/**
	 * 带参数的构造函数
	 * 
	 * @param cols
	 *            当前的列集合
	 */
	public JSTableModel(JSTableColumn[] cols) {
		super(0, cols.length);
		this.setColumnIdentifiers(cols);
	}

	/**
	 * 原始的数据集合
	 * 
	 * @return
	 */
	public abstract T getOrginal();

	/**
	 * 原始的数据集合
	 * 
	 * @param orginal
	 */
	public abstract void setOrginal(T orginal);

	/**
	 * 已删除的数据集合
	 * 
	 * @return
	 */
	public abstract T getDeletes();

	/**
	 * 已删除的数据集合
	 * 
	 * @param deletes
	 */
	public abstract void setDeletes(T deletes);

	/**
	 * 新增数据的集合
	 * 
	 * @return
	 */
	public abstract T getCreates();

	/**
	 * 新增数据的集合
	 * 
	 * @param creates
	 */
	public abstract void setCreates(T creates);

	/**
	 * 更新的数据集合
	 * 
	 * @return
	 */
	public abstract T getModified();

	/**
	 * 更新的数据集合
	 * 
	 * @param modified
	 */
	public abstract void setModified(T modified);

	/**
	 * 清空所有缓冲区
	 */
	public abstract void resetUpdate();

	/**
	 * 初始化数据的操作
	 * 
	 * @return 0无数据 -1出错 成功返回行数
	 * @exception Exception
	 */
	public abstract int onRetrieve() throws Exception;

	/**
	 * 删除时候执行的操作
	 * 
	 * @return
	 * @exception Exception
	 */
	public abstract boolean onDelete(int moduleRow) throws Exception;

	/**
	 * 插入的时候执行的操作
	 * 
	 * @param moduleRow
	 * @return
	 * @exception Exception
	 */
	public abstract boolean onInsert(int moduleRow) throws Exception;

	/**
	 * 当执行追加的时候执行的操作
	 * 
	 * @return
	 * @exception Exception
	 */
	public abstract boolean onAppend() throws Exception;

	/**
	 * 获取列列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSTableColumn[] getTableColumns() {
		return (JSTableColumn[]) this.columnIdentifiers.toArray(new JSTableColumn[this.getColumnCount()]);
	}

	/**
	 * 快速检索模式，默认不开启 主要是retrieve的时候，不切换数据库
	 */
	private boolean quickRetrieveModel = false;

	/**
	 * 快速检索模式，默认不开启 主要是retrieve的时候，不切换数据库
	 * 
	 * @return
	 */
	public boolean isQuickRetrieveModel() {
		return quickRetrieveModel;
	}

	/**
	 * 快速检索模式，默认不开启 主要是retrieve的时候，不切换数据库
	 * 
	 * @param quickRetrieveModel
	 */
	public void setQuickRetrieveModel(boolean quickRetrieveModel) {
		this.quickRetrieveModel = quickRetrieveModel;
	}

	/**
	 * 是否编辑状态
	 */
	private Boolean editable = true;

	/**
	 * 是否编辑状态
	 * 
	 * @return
	 */
	public Boolean getEditable() {
		return editable;
	}

	/**
	 * 是否编辑状态
	 * 
	 * @param editable
	 */
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	/**
	 * 是否在retrieve的时候加入空白行
	 */
	private boolean retrieveWithEmptyRow = false;

	/**
	 * 是否在retrieve的时候加入空白行
	 * 
	 * @return
	 */
	public boolean isRetrieveWithEmptyRow() {
		return retrieveWithEmptyRow;
	}

	/**
	 * 是否在retrieve的时候加入空白行
	 * 
	 * @param retrieveWithEmptyRow
	 */
	public void setRetrieveWithEmptyRow(boolean retrieveWithEmptyRow) {
		this.retrieveWithEmptyRow = retrieveWithEmptyRow;
	}

	/**
	 * 保护起来的单元格 存储格式为，行，列
	 */
	private Map<Integer, Set<Integer>> protectCell = new LinkedHashMap<Integer, Set<Integer>>();

	/**
	 * 保护起来的单元格
	 * 
	 * @return
	 */
	public Map<Integer, Set<Integer>> getProtectCell() {
		return protectCell;
	}

	/**
	 * 保护起来的单元格
	 * 
	 * @param protectCell
	 */
	public void setProtectCell(Map<Integer, Set<Integer>> protectCell) {
		this.protectCell = protectCell;
	}

	/**
	 * 注入的tablemodel对象
	 */
	private JSTableModelLinster<T> tableModelLinster;

	/**
	 * 注入的tablemodel对象
	 * 
	 * @return
	 */
	public JSTableModelLinster<T> getTableModelLinster() {
		return tableModelLinster;
	}

	/**
	 * 注入的tablemodel对象
	 * 
	 * @param tableModelLinster
	 */
	public void setTableModelLinster(JSTableModelLinster<T> tableModelLinster) {
		this.tableModelLinster = tableModelLinster;
	}

	/**
	 * 重写的返回字段是否可编辑的功能
	 */
	@Override
	public boolean isCellEditable(int row, int column) {

		if (!this.getEditable())
			return false;

		if (protectCell != null && protectCell.containsKey(row)) {
			if (protectCell.get(row).contains(column))
				return false;
		}

		if (this.columnIdentifiers != null && this.columnIdentifiers.size() > column) {
			if (this.columnIdentifiers.get(column) instanceof JSTableColumn)
				return ((JSTableColumn) this.columnIdentifiers.get(column)).isEditable();
		}

		return true;
	}

	/**
	 * 查询是否有需要更新的操作
	 * 
	 * @return
	 */
	public abstract boolean hasChange();

	/**
	 * 交换2行数据，无保存状态交互数据
	 * 
	 * @param srcRow
	 *            原始数据行
	 * @param tarRow
	 *            目标数据行
	 * @throws Exception
	 */
	public abstract void moveRow(int srcRow, int tarRow) throws Exception;

	/**
	 * 收集当前变更后的数据集合
	 * 
	 * @throws Exception
	 */
	public abstract T getDatas() throws Exception;

	/**
	 * 重建索引列
	 * 
	 * @param colIndex
	 *            列索引
	 * @param begin
	 *            开始值，比如1，0作为索引开始值
	 * @param seed
	 *            增速
	 * @throws Exception
	 */
	public abstract void reBuildIndex(int colIndex, int begin, int seed) throws Exception;

	/**
	 * 返回当前选中行的数据
	 * 
	 * @param modelRow
	 *            模式行
	 * @return 返回当前行的数据
	 * @throws Exception
	 */
	public abstract Object getData(int modelRow) throws Exception;

	/**
	 * 拿到一个一个单元格对应的数据
	 * 
	 * @param modelRow
	 *            模式行
	 * @return
	 * @throws Exception
	 */
	public Object[] getCellData(int modelRow) throws Exception {

		int iOriginal = this.findColumn(JSTableColumn.COLUMN_ORIGINAL);
		if (iOriginal < 0)
			throw new Exception("Not include original data");

		if (modelRow < 0 || modelRow >= this.getRowCount())
			throw new Exception("modelRow over index");

		Object[] datas = new Object[this.getColumnCount()];

		for (int i = 0; i < this.getColumnCount(); i++) {
			datas[i] = this.getValueAt(modelRow, i);
		}

		return datas;
	}

	/**
	 * 新生成一个数据,但不插入集合
	 * 
	 * @return 新生成一个数据集合
	 * @throws Exception
	 */
	public abstract Object[] createNew() throws Exception;

	/**
	 * 根据实体对象
	 * 
	 * @param data
	 *            查找数据所在的索引位置
	 * @return
	 * @throws Exception
	 */
	public int findIndexOf(Object data) throws Exception {
		int rowCount = this.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			if (this.getData(i) == data)
				return i;
		}

		return -1;
	}

	/**
	 * 清空所有当前显示数据 并且清空所有缓冲区
	 * 
	 * @throws Exception
	 */
	public abstract void clear() throws Exception;

	/**
	 * 使用泛型类型插入一行数据
	 * 
	 * @param row
	 *            要插入行的位置
	 * @param t
	 *            数据
	 * @throws Exception
	 */
	public abstract void insert(int row, Object t) throws Exception;

	/**
	 * 替换掉行的数据操作
	 * 但是不记录到系统里
	 * 
	 * @param row
	 *            行
	 * @param t
	 *            数据
	 * @throws Exception
	 */
	public abstract void replace(int row, Object t) throws Exception;
	
	/**
	 * 清空数据显示
	 * @throws Exception
	 */
	public void removeAll() throws Exception{
		for (int i = this.getRowCount() - 1; i >= 0; i--) {
			this.removeRow(i);
		}
	}

	/**
	 * 更新一个单元格数据，但是不记录到系统里
	 * 
	 * @param aValue
	 *            新值
	 * @param row
	 *            行号
	 * @param column
	 *            列
	 * @throws Exception
	 */
	public void updateValueAt(Object aValue, int row, int column) throws Exception {
		this.removeTableModelListener(this);
		try {
			this.setValueAt(aValue, row, column);
		} finally {
			this.addTableModelListener(this);
		}
	}

	/**
	 * 获取一列的数据
	 * 
	 * @param colIndex
	 *            列所在的model索引位置
	 * @param beginRow
	 *            开始行
	 * @param endRow
	 *            结束行
	 * @throws Exception
	 */
	public Object[] getColData(int colIndex, int beginRow, int endRow) throws Exception {

		if (colIndex < 0 || colIndex >= this.getColumnCount())
			throw new Exception("column over index");

		if (beginRow < 0 || beginRow >= this.getRowCount())
			throw new Exception("beginRow over index");

		if (endRow < 0 || endRow >= this.getRowCount())
			throw new Exception("endRow over index");

		if (beginRow > endRow)
			throw new Exception("beginRow more than endRow");

		Object[] result = new Object[endRow - beginRow + 1];
		for (int i = beginRow; i <= endRow; i++) {
			result[i] = this.getValueAt(i, colIndex);
		}

		return result;
	}

	/**
	 * 更新操作
	 * 
	 * @return 更新操作
	 * @throws Exception
	 *             出错抛出异常
	 */
	public boolean update() throws Exception {

		JSTableModelEvent event = new JSTableModelEvent(this);

		this.getTableModelLinster().beforeUpdate(event);
		if (event.isCancel() || !event.getResult())
			return false;

		if (!this.getTableModelLinster().update(event))
			return false;

		event.setCancel(false);
		event.setResult(true);
		this.getTableModelLinster().atfterUpdate(event);

		return true;
	}

	/**
	 * 加载数据的操作
	 * 
	 * @return 负数出错 其它为返回的加载数据行数
	 * @throws Exception
	 *             抛出一切之错误
	 */
	public int retrieve() throws Exception {

		this.removeTableModelListener(this);
		try {
			JSTableModelEvent event = new JSTableModelEvent(this);

			this.getTableModelLinster().beforRetrieve(event);
			if (event.isCancel() || !event.getResult())
				return -1;

			int iResult = onRetrieve();

			event.setCancel(false);
			event.setRow(iResult);
			event.setResult(iResult >= 0);
			this.getTableModelLinster().afterRetrieve(event);

			return iResult;
		} finally {
			this.addTableModelListener(this);
		}
	}

	/**
	 * 删除操作
	 * 
	 * @return true 成功 false失败
	 * @param modelRow
	 *            要删除的table model数据行
	 * @throws Exception
	 *             抛出异常
	 */
	public boolean delete(int modelRow) throws Exception {

		JSTableModelEvent event = new JSTableModelEvent(this);
		event.setRow(modelRow);

		this.getTableModelLinster().beforDelete(event);
		if (event.isCancel() || !event.getResult())
			return false;

		if (!this.onDelete(modelRow))
			return false;

		event.setCancel(false);
		event.setResult(true);
		this.getTableModelLinster().afterDelete(event);

		return true;
	}

	/**
	 * 插入一行数据的操作
	 * 
	 * @return true插入成功 false插入失败
	 * @throws Exception
	 *             抛出一切可以抛出之异常
	 */
	public boolean append() throws Exception {

		JSTableModelEvent event = new JSTableModelEvent(this);

		this.getTableModelLinster().beforeAppend(event);
		if (event.isCancel() || !event.getResult())
			return false;

		if (!this.onAppend())
			return false;

		event.setCancel(false);
		event.setResult(true);
		event.setRow(this.getRowCount() - 1);
		this.getTableModelLinster().aftterAppend(event);

		return true;

	}

	/**
	 * 插入一行数据
	 * 
	 * @param index插入行的索引位置
	 * @return
	 * @throws Exception
	 */
	public boolean insert(int modelRow) throws Exception {

		JSTableModelEvent event = new JSTableModelEvent(this);
		event.setRow(modelRow);

		this.getTableModelLinster().beforeInsert(event);
		if (event.isCancel() || !event.getResult())
			return false;

		if (!this.onInsert(modelRow))
			return false;

		event.setCancel(false);
		event.setResult(true);
		this.getTableModelLinster().aftterInsert(event);

		return true;
	}
}
