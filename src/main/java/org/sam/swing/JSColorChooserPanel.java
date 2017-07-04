package org.sam.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

public class JSColorChooserPanel extends JPanel {

	private static final long serialVersionUID = 8301312679935345851L;
	
	/**
	 * 颜色选择控件
	 */
	private JColorChooser colorChooser;
	
	/**
	 * 确定按钮
	 */
	private JButton btnOk;
	
	/**
	 * 取消按钮
	 */
	private JButton btnCancle;
	
	/**
	 * 回调事件
	 */
	private ColorChooserLisenter chooseListener;
	
	/**
	 * 设置颜色选择事件
	 * @param l
	 */
	public void addColorChooserLisenter(ColorChooserLisenter l)
	{
		chooseListener = l;
	}
	
	/**
	 * 移除listener
	 */
	public void removeColorChooserLisenter()
	{
		chooseListener = null;
	}
	
	/**
	 * 当亲的颜色
	 * @return
	 */
	public Color getColor() {
		return colorChooser.getColor();
	}

	/**
	 * 当前的颜色
	 * @param color
	 */
	public void setColor(Color color) {
		colorChooser.setColor(color);
	}
	
	/**
	 * 带有颜色的构造函数
	 * @param color
	 */
	public JSColorChooserPanel(Color color)
	{
		this();
		this.setColor(color);
	}

	/**
	 * Create the panel.
	 */
	public JSColorChooserPanel() {
		setLayout(new BorderLayout(0, 0));
		
		colorChooser = new JColorChooser();
		add(colorChooser, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		btnOk = new JButton("确定");
		panel.add(btnOk);
		
		btnCancle = new JButton("取消");
		panel.add(btnCancle);
		
		btnCancle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chooseListener != null){
					chooseListener.afterCancle();
				}
			}
		});
		
		/**
		 * 执行回调
		 */
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chooseListener != null){
					chooseListener.afterChoose(getColor());
				}
					
			}
		});

	}

	/**
	 * 设置颜色的操作
	 * @author sam
	 *
	 */
	public interface ColorChooserLisenter extends EventListener
	{
		/**
		 * 点击确定的按钮执行的操作
		 * @param color
		 */
		public void afterChoose(Color color);
		
		/**
		 * 取消的操作
		 */
		public void afterCancle();
	}
}
