package org.sam.swing.table.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * 空操作action对象
 * @author sam
 *
 */
public class JSTableDefaultAction extends AbstractAction implements JSTableChooseAction {

	private static final long serialVersionUID = 2328363418795722955L;
	
	/**
	 * 空操作action对象
	 */
	public JSTableDefaultAction(){
		super();
		this.putValue(NAME, ".");
	}
	
	private Object value;

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public Result getResult() {
		return Result.Cancel;
	}

	@Override
	public void setResult(Result result) {
		
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
	}

}
