package org.sam.swing.table.defaultImpl;

import java.util.List;

import org.sam.swing.table.JSTableModel;
import org.sam.swing.table.JSTableModelEvent;
import org.sam.swing.table.JSTableModelLinster;

/**
 * 默认的系统实现
 * @author sam
 *
 * @param <E>
 */
public class JSTableModelDefaultAdapter<E> implements JSTableModelLinster<List<E>> {
	
	/**
	 * 当前的tablemodel对象
	 */
	private JSTableModel<List<E>> tableModel;
	
	/**
	 * 带有构造函数的tableModel
	 * @param tableModel
	 */
	public JSTableModelDefaultAdapter()
	{
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforRetrieve(JSTableModelEvent event) throws Exception {
		tableModel.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<E> onRetrieve() throws Exception {
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterRetrieve(JSTableModelEvent event) throws Exception {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeUpdate(JSTableModelEvent event) throws Exception {
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean update(JSTableModelEvent event) throws Exception {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void atfterUpdate(JSTableModelEvent event) throws Exception {
		tableModel.resetUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforDelete(JSTableModelEvent event) throws Exception {
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterDelete(JSTableModelEvent event) throws Exception {
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeAppend(JSTableModelEvent event) throws Exception {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void aftterAppend(JSTableModelEvent event) throws Exception {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeInsert(JSTableModelEvent event) throws Exception {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void aftterInsert(JSTableModelEvent event) throws Exception {
	}

}
