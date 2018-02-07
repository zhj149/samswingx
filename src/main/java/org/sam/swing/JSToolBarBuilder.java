package org.sam.swing;

import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.sam.swing.resource.ResourceLoader;

/**
 * 按钮生成工具类
 * @author sam
 *
 */
public interface JSToolBarBuilder {
	/**
	 * 创建自定义的按钮对象
	 * 
	 * @param image
	 * @param text
	 * @param listener
	 * @return
	 */
	public default JButton buildButton(String image, String text, ActionListener listener) {
		return buildButton(image, text, "", listener);
	}

	/**
	 * 创建自定义的按钮对象
	 * 
	 * @param image
	 * @param text
	 * @param toolTip
	 * @param listener
	 * @return
	 */
	public default JButton buildButton(String image, String text, String toolTip, ActionListener listener) {
		final JButton btn = new JButton(text, new ImageIcon(ResourceLoader.getResource(image)));
		btn.addActionListener(listener);
		btn.setToolTipText(toolTip);
		btn.setFocusable(true);
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(false);
		btn.setDefaultCapable(true);
		return btn;
	}
	
	/**
	 * 使用action对象创建button对象
	 * @param action action对象
	 * @return
	 */
	public default JButton buildButton(Action action){
		return new JButton(action);
	}
	
	/**
	 * 直接在工具条上增加
	 * @param image
	 * @param text
	 * @param toolTip
	 * @param listener
	 */
	public void addActionListener(String image, String text, String toolTip, ActionListener listener);
	
	/**
	 * 直接在工具条上增加按钮对象
	 * @param action
	 */
	public void addAction(Action action);
}
