package org.sam.swing;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JPanel;

/**
 * 顶部工具条
 * 
 * @author sam
 *
 */
public class JSPanelToolBar extends JPanel implements JSToolBarBuilder {

	private static final long serialVersionUID = -4564113028292744141L;

	/**
	 * 顶部工具条
	 */
	public JSPanelToolBar(){
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
	}
	
	/**
	 * 直接在工具条上增加
	 * @param image
	 * @param text
	 * @param toolTip
	 * @param listener
	 */
	@Override
	public void addActionListener(String image, String text, String toolTip, ActionListener listener){
		this.add(this.buildButton(image, text, toolTip, listener));
	}
	
	/**
	 * 直接在工具条上增加按钮对象
	 * @param action
	 */
	@Override
	public void addAction(Action action){
		this.add(this.buildButton(action));
	}

}