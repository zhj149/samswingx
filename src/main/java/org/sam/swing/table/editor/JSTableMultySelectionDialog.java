package org.sam.swing.table.editor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import org.sam.swing.JSDialog;
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
public class JSTableMultySelectionDialog extends JSDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3370944197046301383L;
	
	/**
	 * 全选操作
	 */
	public static final String SELECT_ALL = "selectall";
	
	/**
	 * 取消全选操作
	 */
	public static final String UN_SELECT_ALL = "unselectall";
	
	/**
	 * 主框体
	 */
	private final JPanel contentPanel = new JPanel();
	
	/**
	 * 当前操作的数据
	 */
	private Object data;
	
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
	 * 全选
	 */
	private JButton btnSelectAll;
	
	/**
	 * 取消全选
	 */
	private JButton btnUnSelectAll;
	
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
	public JSTableMultySelectionDialog()
	{
		this(null,null);
	}

	/**
	 * 单行数据选择对象
	 */
	public JSTableMultySelectionDialog(JSTableColumnMappingAbstract colMapping , Object data) {
		/**
		 * 创建无父容器的模态窗口
		 */
		super((JFrame)null,true);
		this.colMapping = colMapping;
		this.setData(data);
		
		setTitle("请选择数据");
		setBounds(100, 100, 572, 402);
		getContentPane().setLayout(new BorderLayout());
		
		//初始化表格
		this.initTable();
		
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.SOUTH);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			btnSelectAll = new JButton("全选");
			btnSelectAll.setActionCommand(SELECT_ALL);
			btnSelectAll.addActionListener(this);
			buttonPane.add(btnSelectAll);
			
			btnUnSelectAll = new JButton("全不选");
			btnUnSelectAll.setActionCommand(UN_SELECT_ALL);
			btnUnSelectAll.addActionListener(this);
			buttonPane.add(btnUnSelectAll);
			
			progressBar = new JProgressBar();
			progressBar.setMaximum(100);
			progressBar.setMinimum(0);
			
			buttonPane.add(progressBar);
			{
				okButton = new JButton("确定");
				okButton.setActionCommand(OK);
				buttonPane.add(okButton);
				okButton.addActionListener(this);
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
							tableMain.readOnly();
							progressWorker.cancel(false);
							progressBar.setVisible(false);
							
							for(int i = 0; i < tableModel.getRowCount();i++)
							{
								tableModel.setValueAt(false, i, 0);
							}
							
							//初始化列check，规定默认的第一列
							if (data != null)
							{
								if (data instanceof Set<?>)
								{
									Set<?> newData = (Set<?>)data;
									for(Object entity : newData)
									{
										for(int i = 0;i < tableModel.getRowCount();i++)
										{
											if (tableModel.getData(i).equals(entity))
											{
												tableModel.setValueAt(true, i, 0);
											}
										}
									}
								}
								else if (data instanceof List<?>)
								{
									List<?> newData = (List<?>)data;
									for(Object entity : newData)
									{
										for(int i = 0;i < tableModel.getRowCount();i++)
										{
											if (tableModel.getData(i).equals(entity))
											{
												tableModel.setValueAt(true, i, 0);
											}
										}
									}
								}
								else if (data instanceof Map<?,?>)
								{
									Map<?,?> newData = (Map<?,?>)data;
									for(Object entity : newData.values())
									{
										for(int i = 0;i < tableModel.getRowCount();i++)
										{
											if (tableModel.getData(i).equals(entity))
											{
												tableModel.setValueAt(true, i, 0);
											}
										}
									}
								}
							}
							
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
		
		JSTableBuilder<?> builder = new JSTableDefaultBuilderImpl<>(colMapping.getEntityClss(), columns);
		
		try
		{
			tableModel = builder.buildTableModel();
			tableModel.setEditable(false);
			colModel = builder.buildTableColumnModel();
			tableMain = new JSTable(tableModel, colModel);
			JScrollPane scrollPane = new JScrollPane(tableMain);
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			//设置为自动填充宽度
			tableMain.setAutoResizeMode(JSTable.AUTO_RESIZE_ALL_COLUMNS);
			//添加retrieve事件			
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
			this.setVisible(false);
			
		} else if (CANCEL.equals(e.getActionCommand())) {
			this.setAction(CANCEL);
			this.setData(null);
			this.setVisible(false);
		}
		else if (SELECT_ALL.equals(e.getActionCommand())) //全选
		{
			for ( int i = 0 ; i < tableModel.getRowCount() ; i++)
			{
				tableModel.setValueAt(true, i, 0);
			}
		}
		else if (UN_SELECT_ALL.equals(e.getActionCommand())) //全不选
		{
			for ( int i = 0 ; i < tableModel.getRowCount() ; i++)
			{
				tableModel.setValueAt(false, i, 0);
			}
			
		}
	}
	
	/**
	 * 返回set的新结果集
	 * @return
	 */
	public Set<? extends Object> getSet() throws Exception
	{
		Set<? super Object> result = new LinkedHashSet<Object>();
		
		for(int i = 0 ; i < tableModel.getRowCount();i++)
		{
			if (tableModel.getValueAt(i, 0).equals(true))
			{
				Set<?> set = (Set<?>)data;
				Object newDate = tableModel.getData(i);
				if (set.contains(newDate))
				{
					Iterator<?> iterator = set.iterator();
					while(iterator.hasNext())
					{
						Object next = iterator.next();
						if (next.equals(newDate))
						{
							result.add(next);
						}
					}
				}
				result.add(newDate);
			}
		}
		
		return result;
	}
	
	/**
	 * 返回set的新结果集
	 * @return
	 */
	public List<? extends Object> getList() throws Exception
	{
		List<? super Object> result = new LinkedList<>();
		
		for(int i = 0 ; i < tableModel.getRowCount();i++)
		{
			if (tableModel.getValueAt(i, 0).equals(true))
			{
				result.add(tableModel.getData(i));
			}
		}
		
		return result;
	}
}
