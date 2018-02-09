package org.sam.swing.table.renderer;

import java.text.Format;

import javax.swing.table.DefaultTableCellRenderer;

/**
 * 带有掩码格式的renderer对象
 * 
 * @author sam
 *
 */
public class JSTableFormatRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -3981456436820431417L;

	/**
	 * 当前的掩码格式对象
	 */
	private Format format;

	/**
	 * 当前的掩码格式对象
	 * 
	 * @return
	 */
	public Format getFormat() {
		return format;
	}

	/**
	 * 当前的掩码格式对象
	 * 
	 * @param format
	 */
	public void setFormat(Format format) {
		this.format = format;
	}

	/**
	 * 空值显示文本
	 */
	private String nullText;

	/**
	 * 空值显示文本
	 * 
	 * @return
	 */
	public String getNullText() {
		return nullText;
	}

	/**
	 * 空值显示文本
	 * 
	 * @param nullText
	 */
	public void setNullText(String nullText) {
		this.nullText = nullText;
	}

	/**
	 * 带参数的构造函数
	 * 
	 * @param formator
	 *            掩码格式对象
	 * @param nullText
	 *            空值显示文本
	 */
	public JSTableFormatRenderer(Format formator, String nullText) {
		super();
		this.setFormat(formator);
		this.setNullText(nullText);
	}

	/**
	 * 带有掩码格式的构造函数
	 * 
	 * @param formator
	 */
	public JSTableFormatRenderer(Format formator) {
		this(formator, "");
	}

	/**
	 * 重写绘制方法
	 */
	@Override
	protected void setValue(Object value) {
		try {
			setText((value == null) ? (nullText == null ? "" : nullText) : format.format(value));
		} catch (Exception ex) {
			ex.printStackTrace();
			setText("");
		}
	}
}
