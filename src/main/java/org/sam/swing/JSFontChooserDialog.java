package org.sam.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 颜色选择对话框
 * 
 * @author sam
 *
 */
public class JSFontChooserDialog extends JSDialog {

	private static final long serialVersionUID = -4073734321825991766L;

	/**
	 * 确定按钮
	 */
	private JButton btnOk;

	/**
	 * 取消按钮
	 */
	private JButton btnCancle;

	/**
	 * 字体选择器
	 */
	private JSFontChooserPanel fontChooser;

	/**
	 * 回调事件
	 */
	private FontChooserLisenter fontListener;

	/**
	 * 设置颜色选择事件
	 * 
	 * @param l
	 */
	public void addFontChooserLisenter(FontChooserLisenter l) {
		fontListener = l;
	}

	/**
	 * 移除listener
	 */
	public void removeFontChooserLisenter() {
		fontListener = null;
	}

	/**
	 * 颜色选择对话框
	 */
	public JSFontChooserDialog() {
		super();
		setFont(new Font("宋体", Font.PLAIN, 12));
		initCompents();
	}

	/**
	 * 设置字体
	 */
	public void setSelectFont(Font font) {
		if (fontChooser != null)
			fontChooser.setSelectFont(font);
	}

	/**
	 * 初始化空间的操作
	 */
	protected void initCompents() {
		getContentPane().setLayout(new BorderLayout(0, 0));
		setTitle("字体选择器");

		fontChooser = new JSFontChooserPanel();
		getContentPane().add(fontChooser, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		btnOk = new JButton("确定");
		panel.add(btnOk);

		btnCancle = new JButton("取消");
		panel.add(btnCancle);

		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setSize(390, 297);
		this.setResizable(false);

		btnCancle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (fontListener != null) {
					fontListener.afterCancle();
				}
				setVisible(false);
			}
		});

		/**
		 * 执行回调
		 */
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (fontListener != null) {
					fontListener.afterChoose(fontChooser.getFont(), fontChooser.getFontDecode());
				}
				setVisible(false);
			}
		});
	}

	/**
	 * 设置颜色的操作
	 * 
	 * @author sam
	 *
	 */
	public interface FontChooserLisenter extends EventListener {
		/**
		 * 点击确定的按钮执行的操作
		 * 
		 * @param font
		 *            字体
		 * @param fontDecode
		 *            重新编码过的对象
		 */
		public void afterChoose(Font font, String fontDecode);

		/**
		 * 取消的操作
		 */
		public default void afterCancle() {
		};
	}

}
