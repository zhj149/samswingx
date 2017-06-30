package org.sam.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * 按钮上的ICON对象
 * @author sam
 *
 */
public class JSButtonIcon implements Icon {

	/**
	 * 默认图标大小
	 */
	public static final int ICON_SIZE = 20;
	
	/**
	 * 图标宽度
	 */
	protected int iconWidth = ICON_SIZE;
	
	/**
	 * 图标高度
	 */
	protected int iconHeight = ICON_SIZE / 2;
	
	/**
	 * 图标颜色
	 */
	protected Color iconColor = Color.black;
	
	/**
	 * 绘制图标的操作
	 */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		
		Color oldColor = g.getColor();

		g.setColor(iconColor);
		g.fillRect(4, 4, c.getWidth() - 8, c.getHeight() - 8);
		g.setColor(oldColor);

	}

	/**
	 * 图标宽度
	 */
	@Override
	public int getIconWidth() {
		return iconWidth;
	}

	/**
	 * 图标高度
	 */
	@Override
	public int getIconHeight() {
		return iconHeight;
	}
	
	/**
	 * 设置宽度
	 * @param iconWidth
	 */
	public void setIconWidth(int iconWidth) {
		this.iconWidth = iconWidth;
	}

	/**
	 * 设置高度
	 * @param iconHeight
	 */
	public void setIconHeight(int iconHeight) {
		this.iconHeight = iconHeight;
	}

	/**
	 * 图标颜色
	 * @return
	 */
	public Color getIconColor() {
		return iconColor;
	}

	/**
	 * 图标颜色
	 * @param iconColor
	 */
	public void setIconColor(Color iconColor) {
		this.iconColor = iconColor;
	}
	
}
