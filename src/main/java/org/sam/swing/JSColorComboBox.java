package org.sam.swing;

import javax.swing.JComboBox;

/**
 * 下拉颜色框
 * @author sam
 *
 */
public class JSColorComboBox extends JComboBox<JSColorChooserPanel> {
	
	/**
	 * 颜色选择对象 
	 */
	protected JSColorChooserPanel colorChooser;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -931021026684581413L;

	/**
	 * 下拉颜色框
	 */
	public JSColorComboBox()
	{
		super();
		this.initCompents();
	}
	
	protected void initCompents()
	{
		colorChooser = new JSColorChooserPanel();
		this.addItem(colorChooser);
	}
	
}
