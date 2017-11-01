package org.sam.swing.table;

/**
 * 表格生成器对象
 * @author sam
 *
 */
public interface JSTableBuilder<E extends Object> {
	
	/**
	 * 构建columnModel
	 * @return
	 * @throws Exception 抛出一切之异常
	 */
	public JSTableColumnModel buildTableColumnModel() throws Exception;
	
	/**
	 * 创建tablemodel的方法
	 * @return
	 * @throws Exception
	 */
	public JSTableModel<E> buildTableModel() throws Exception;
	
	/**
	 * 获取刚刚创建好的tablemodel
	 * @return
	 */
	public JSTableModel<E> getTableModel() throws Exception;
	
	/**
	 * 获取刚刚创建好的colmodel
	 * @return
	 * @throws Exception
	 */
	public JSTableColumnModel getTableColumnModel() throws Exception;
	
	/**
	 * 创建表格的方法
	 * @param tableModel 
	 * @param colModel
	 * @return
	 * @throws Exception
	 */
	public default JSTable buildTable(JSTableModel<E> tableModel , JSTableColumnModel colModel) throws Exception{
		return new JSTable(tableModel , colModel);
	}
	
	/**
	 * 用之前2步创建好的对象，直接生成表格
	 * @return
	 * @throws Exception
	 */
	public default JSTable buildTable() throws Exception{
		return this.buildTable(this.getTableModel(), this.getTableColumnModel());
	}
	
	/**
	 * 创建对应的数据操作对象
	 * @param tableModel
	 * @return
	 * @throws Exception
	 */
	public JSTableModelLinster<E> buildModelLinster(JSTableModel<E> tableModel) throws Exception;
	
	/**
	 * 用之前生成好的对象，直接生成表格
	 * @return
	 * @throws Exception
	 */
	public default JSTableModelLinster<E> buildModelLinster() throws Exception{
		return this.buildModelLinster(this.getTableModel());
	}

}
