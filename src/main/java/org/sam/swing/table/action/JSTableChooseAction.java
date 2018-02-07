package org.sam.swing.table.action;

import javax.swing.Action;

/**
 * 选择按钮等editor控件用的到action对象
 * 
 * @see JSTableButtonEditor
 * @author sam
 *
 */
public interface JSTableChooseAction extends Action {

	/**
	 * 执行结果
	 * 
	 * @author sam
	 *
	 */
	public enum Result {
		OK, Cancel;
	}

	/**
	 * 获取当前执行的执行结果
	 * 
	 * @return
	 */
	public Result getResult();

	/**
	 * 设置当前的执行结果
	 * 
	 * @param result
	 */
	public void setResult(Result result);

	/**
	 * 获取设置后的值
	 * 
	 * @return
	 */
	public Object getValue();

	/**
	 * 设置值,用于初始化信息等
	 * 
	 * @param value
	 */
	public void setValue(Object value);

}
