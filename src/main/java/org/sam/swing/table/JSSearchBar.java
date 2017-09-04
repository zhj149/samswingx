package org.sam.swing.table;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.sam.swing.resource.ResourceLoader;

/**
 * 重用的table表格搜索框bar
 * 
 * @author 李超
 * @date 2017年6月27日下午4:47:28
 */
public class JSSearchBar extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * 搜索文本框
	 */
	protected JTextField txtFind;

	/**
	 * 查找按钮
	 */
	protected JButton btnFind;

	/**
	 * 最后找到的行
	 */
	protected int lastRow = -1;

	/**
	 * 最后操作列
	 */
	protected int lastColumn = -1;

	/**
	 * 当前操作的表格
	 */
	protected JSTable tableMain;

	/**
	 * 数据操作对象
	 */
	protected JSTableModel<?> searchModel;

	/**
	 * 构造方法
	 */
	public JSSearchBar(JSTable table, JSTableModel<?> tableModel) {
		this.tableMain = table;
		this.searchModel = tableModel;
		this.initComponents();
	}

	/**
	 * 初始化搜索框
	 */
	private void initComponents() {
		this.setLayout(new BorderLayout());

		// 查找工具条
		txtFind = new JTextField(20);
		txtFind.addKeyListener(new KeyAdapter() {

			/**
			 * 点击回车的时候执行检索操作
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnFind.doClick();
				}
			}
		});

		btnFind = new JButton("", new ImageIcon(ResourceLoader.getResource(ResourceLoader.IMAGE_FIND)));
		// 增加检索的代码
		btnFind.addActionListener(this);

		JPanel panel = new JPanel(new FlowLayout());
		panel.add(txtFind, FlowLayout.LEFT);
		panel.add(btnFind, FlowLayout.CENTER);

		add(panel, BorderLayout.WEST);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		int iBeingRow = (lastRow < 0 || lastRow >= tableMain.getRowCount()) ? 0 : lastRow;
		int iBeingCol = (lastColumn < 0 || lastColumn >= tableMain.getColumnCount()) ? 0 : lastColumn;

		if (lastRow >= 0) {
			if (lastColumn >= 0)
				iBeingCol++;
			else
				lastRow++;
		}

		for (int i = iBeingRow; i < tableMain.getRowCount(); i++) {
			if (i > iBeingRow)
				iBeingCol = 0;

			for (int j = iBeingCol; j < tableMain.getColumnCount(); j++) {
				String text = txtFind.getText();
				Object valueAt = searchModel.getValueAt(i, j);
				if (valueAt != null) {
					if (valueAt.toString().contains(text)) {
						lastRow = i;
						lastColumn = j;
						tableMain.changeSelection(i, j, false, false);
						return;
					}
					if(i == tableMain.getRowCount()-1 && j == tableMain.getColumnCount()-1){
						i = 0;
						j = 0;
						return;
					}
				}
			}
		}

		// 没找到归零
		lastRow = -1;
		lastColumn = -1;
	}
}
