package org.sam.swing.table.editor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import org.sam.swing.JSDialog;
import org.sam.swing.resource.ResourceLoader;
import org.sam.swing.table.JSTable;
import org.sam.swing.table.JSTableBuilder;
import org.sam.swing.table.JSTableColumn;
import org.sam.swing.table.JSTableColumnModel;
import org.sam.swing.table.JSTableModel;
import org.sam.swing.table.defaultImpl.JSTableDefaultBuilderImpl;
import org.sam.swing.table.view.JSTableColumnMapping;
import org.sam.swing.table.view.JSTableColumnMappingAbstract;

/**
 * 单行数据选择对象
 * @author sam
 *
 */
public class JSTableSingleSelectionDialog extends JSDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3370944197046301383L;
	
	/**
	 * 主框体
	 */
	private final JPanel contentPanel = new JPanel();
	
	/**
	 * 当前操作的数据
	 */
	private Object data;
	
	/**
	 * 搜索文本框
	 */
	private JTextField txtFind;
	
	/**
	 * 查找按钮
	 */
	private JButton btnFind;
	
	/**
	 * 主数据窗口
	 */
	private JSTable tableMain;
	
	/**
	 * 当前的列定义列表
	 */
	private JSTableColumnMappingAbstract colMapping;
	
	/**
	 * 当前的表格模型对象
	 */
	private JSTableModel<?> tableModel;
	
	/**
	 * 表格的列模型
	 */
	private JSTableColumnModel colModel;
	
	/**
	 * 确定按钮
	 */
	private JButton okButton;
	
	/**
	 * 加载进度条
	 */
	private JProgressBar progressBar;
	
	/**
	 * 设置数据对象
	 * @return
	 */
	public Object getData() {
		return data;
	}

	/**
	 * 设置数据对象
	 * @param data
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
	/**
	 * 最后找到的行
	 */
	private int lastRow = -1;

	/**
	 * 最后找到的行
	 * @return
	 */
	public int getLastRow() {
		return lastRow;
	}

	/**
	 * 最后找到的行
	 * @param lastRow
	 */
	public void setLastRow(int lastRow) {
		this.lastRow = lastRow;
	}
	
	/**
	 * 最后操作列
	 */
	private int lastColumn = -1;
	
	/**
	 * 最后操作列
	 * @return
	 */
	public int getLastColumn() {
		return lastColumn;
	}

	/**
	 * 最后操作列
	 * @param lastColumn
	 */
	public void setLastColumn(int lastColumn) {
		this.lastColumn = lastColumn;
	}

	/**
	 * 当前的列定义列表
	 * @return
	 */
	public JSTableColumnMapping getColMapping() {
		return colMapping;
	}

	/**
	 * 当前的列定义列表
	 * @param colMapping
	 */
	public void setColMapping(JSTableColumnMappingAbstract colMapping) {
		if (this.colMapping != colMapping)
		{
			this.colMapping = colMapping;
			this.initTable();
		}
	}
	
	/**
	 * 不带参数的构造函数
	 * 默认不生成任何数据表格
	 */
	public JSTableSingleSelectionDialog()
	{
		this(null);
	}

	/**
	 * 单行数据选择对象
	 */
	public JSTableSingleSelectionDialog(JSTableColumnMappingAbstract colMapping) {
		/**
		 * 创建无父容器的模态窗口
		 */
		super((JFrame)null,true);
		this.colMapping = colMapping;
		
		setTitle("请选择数据");
		setBounds(100, 100, 572, 402);
		getContentPane().setLayout(new BorderLayout());
		
		//查找工具条
		JPanel findBar = new JPanel(new BorderLayout());
		findBar.add(new JLabel("请输入需要检索的数据关键字:") , BorderLayout.WEST);
		findBar.add(txtFind = new JTextField() , BorderLayout.CENTER);
		txtFind.addKeyListener(new KeyAdapter() {
			
			/**
			 * 点击回车的时候执行检索操作
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					btnFind.doClick();
				}
			}
		});
	
		findBar.add(btnFind = new JButton("" , new ImageIcon(ResourceLoader.getResource(ResourceLoader.IMAGE_FIND))), 
				BorderLayout.EAST);
		//增加检索的代码
		btnFind.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int iBeingRow = (lastRow < 0 || lastRow >= tableMain.getRowCount()) ? 0 : lastRow;
				int iBeingCol = ( lastColumn < 0 || lastColumn >= tableMain.getColumnCount()) ? 0 : lastColumn;
				
				if (lastRow >= 0)
				{
					if (lastColumn >= 0)
						iBeingCol++;
					else 
						lastRow++;
				}
				
				for (int i = iBeingRow ; i < tableMain.getRowCount(); i++)
				{
					if (i > iBeingRow)
						iBeingCol = 0;
					
					for(int j = iBeingCol ; j < tableMain.getColumnCount() ; j++)
					{
						String text = txtFind.getText();
						Object valueAt = tableMain.getValueAt(i, j);
						if (valueAt != null)
						{
							if ( valueAt.toString().contains(text) )
							{
								lastRow = i;
								lastColumn = j;
								tableMain.changeSelection(i, j, false, false);
								return;
							}
						}
					}
				}
				
				//没找到归零
				lastRow = -1;
				lastColumn = -1;
			}
		});
		
		getContentPane().add(findBar , BorderLayout.NORTH);
		
		//初始化表格
		this.initTable();
		
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.SOUTH);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			progressBar = new JProgressBar();
			progressBar.setMaximum(100);
			progressBar.setMinimum(0);
			
			buttonPane.add(progressBar);
			{
				okButton = new JButton("确定");
				okButton.setActionCommand(OK);
				buttonPane.add(okButton);
				okButton.addActionListener(this);
//				getRootPane().setDefaultButton(btnFind);
			}
			{
				JButton cancelButton = new JButton("取消");
				cancelButton.setActionCommand(CANCEL);
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
		
		this.setLocationRelativeTo(null); //屏幕居中
		
		//制作进度条效果
		SwingWorker<Void,Void> progressWorker = new SwingWorker<Void, Void>()
				{

					@Override
					protected Void doInBackground() throws Exception {
						
						for (int i = 0; i < progressBar.getMaximum() ;i++)
						{
							progressBar.setValue(i);
							Thread.currentThread();
							Thread.sleep(100);
						}
						return null;
					}
			
				};
				
		
				progressWorker.execute();
		
		SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>()
				{
				
					@Override
					protected Boolean doInBackground() throws Exception {
						tableModel.retrieve();
						return true;
					}
			
					@Override
					protected void done()
					{
						try {
							progressWorker.cancel(false);
							progressBar.setVisible(false);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				
				worker.execute();
	}

	/**
	 * 生成数据表格的操作
	 */
	public void initTable()
	{
		if (this.colMapping == null)
			return;
		
		JSTableColumn[] columns = this.colMapping.getColumns();
		if (columns == null || columns.length <= 0)
			return;
		
		JSTableBuilder<?> builder = new JSTableDefaultBuilderImpl<>(colMapping.getEntityClss(),columns);
		
		try
		{
			tableModel = builder.buildTableModel();
			tableModel.setRetrieveWithEmptyRow(true);
			tableModel.setEditable(false);
			colModel = builder.buildTableColumnModel();
			tableMain = new JSTable(tableModel, colModel);
			JScrollPane scrollPane = new JScrollPane(tableMain);
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			//设置为自动填充宽度
			tableMain.setAutoResizeMode(JSTable.AUTO_RESIZE_ALL_COLUMNS);
			//添加双击事件
			tableMain.addMouseListener(new MouseAdapter() {
				
				/**
				 * 鼠标双击返回数据
				 */
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() >= 2)
					{						
						okButton.doClick();
					}
				}
			});
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * 按钮点击事件
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (OK.equals(e.getActionCommand())) {
			
			this.setAction(OK);
			int selectedRow = this.tableMain.getSelectedRow();
			if (selectedRow >= 0 && selectedRow < this.tableMain.getRowCount())
			{
				int iRow = tableMain.convertRowIndexToModel(selectedRow);
				try {
					setData( tableModel.getData(iRow) );
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			this.setVisible(false);
			
		} else if (CANCEL.equals(e.getActionCommand())) {
			this.setAction(CANCEL);
			this.setData(null);
			this.setVisible(false);
		}
	}
}
