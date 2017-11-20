package org.sam.swing;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXDialog;
import org.jdesktop.swingx.search.PatternModel;
import org.jdesktop.swingx.search.Searchable;
import org.sam.swing.search.MuiltyPatternModel;
import org.sam.swing.table.JSTableColumn;

/**
 * 扩展的增强搜索栏 主要增加字段选择列表：全部 + 可见字段列表 +号按钮，多条件窗口 apply按钮，接受条件
 * 
 * @author sam
 *
 */
public class JSFindBar extends JSFindPanel {

	private static final long serialVersionUID = 1594690163014862566L;

	protected Color previousBackgroundColor;

	protected Color previousForegroundColor;

	// PENDING: need to read from UIManager
	protected Color notFoundBackgroundColor = Color.decode("#FF6666");

	protected Color notFoundForegroundColor = Color.white;

	protected JButton findNext;

	protected JButton findPrevious;

	/**
	 * 当前的tablemodel
	 */
	private TableModel tableModel;

	/**
	 * 当前的列model
	 */
	private TableColumnModel colModel;

	/**
	 * 当前的tablemodel
	 * 
	 * @return
	 */
	public TableModel getTableModel() {
		return tableModel;
	}

	/**
	 * 当前的tablemodel
	 * 
	 * @param tableModel
	 */
	public void setTableModel(TableModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * 当前的列模型
	 * 
	 * @return
	 */
	public TableColumnModel getColModel() {
		return colModel;
	}

	/**
	 * 当前的列模型
	 * 
	 * @param colModel
	 */
	public void setColModel(TableColumnModel colModel) {
		this.colModel = colModel;
	}

	/**
	 * 字段列表
	 */
	protected JSComboBox<String, String> columsList;

	public JSFindBar() {
		this(null);
	}

	public JSFindBar(Searchable searchable) {
		this(searchable, null, null);
	}

	public JSFindBar(Searchable searchable, TableModel tableModel, TableColumnModel colModel) {
		super(searchable);
		getPatternModel().setIncremental(true);
		getPatternModel().setWrapping(true);
		this.setTableModel(tableModel);
		this.setColModel(colModel);
	}

	@Override
	public void setSearchable(Searchable searchable) {
		super.setSearchable(searchable);
		match();
	}

	/**
	 * here: set textfield colors to not-found colors.
	 */
	@Override
	protected void showNotFoundMessage() {
		// JW: quick hack around #487-swingx - NPE in setSearchable
		if (searchField == null)
			return;
		searchField.setForeground(notFoundForegroundColor);
		searchField.setBackground(notFoundBackgroundColor);
	}

	/**
	 * here: set textfield colors to normal.
	 */
	@Override
	protected void showFoundMessage() {
		// JW: quick hack around #487-swingx - NPE in setSearchable
		if (searchField == null)
			return;
		searchField.setBackground(previousBackgroundColor);
		searchField.setForeground(previousForegroundColor);
	}

	@Override
	public void addNotify() {
		super.addNotify();
		if (previousBackgroundColor == null) {
			previousBackgroundColor = searchField.getBackground();
			previousForegroundColor = searchField.getForeground();
		} else {
			searchField.setBackground(previousBackgroundColor);
			searchField.setForeground(previousForegroundColor);
		}
	}

	// --------------------------- action call back
	/**
	 * Action callback method for bound action JXDialog.CLOSE_ACTION_COMMAND.
	 * 
	 * Here: does nothing. Subclasses can override to define custom "closing"
	 * behaviour. Alternatively, any client can register a custom action with
	 * the actionMap.
	 * 
	 * 
	 */
	public void cancel() {
	}

	// -------------------- init

	@Override
	protected void initExecutables() {
		getActionMap().put(JXDialog.CLOSE_ACTION_COMMAND, createBoundAction(JXDialog.CLOSE_ACTION_COMMAND, "cancel"));
		super.initExecutables();
	}

	@Override
	protected void bind() {
		super.bind();
		searchField.addActionListener(getAction(JXDialog.EXECUTE_ACTION_COMMAND));
		findNext.setAction(getAction(FIND_NEXT_ACTION_COMMAND));
		findPrevious.setAction(getAction(FIND_PREVIOUS_ACTION_COMMAND));
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
		getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(stroke, JXDialog.CLOSE_ACTION_COMMAND);
	}

	@Override
	protected void build() {
		setLayout(new FlowLayout(SwingConstants.LEADING));
		add(searchLabel);
		add(new JLabel(":"));
		add(new JLabel("  "));
		// 字段列表
		add(columsList);
		add(searchField);
		add(findNext);
		add(findPrevious);
	}
	
	/**
	 * 可以自己重写检索model对象
	 */
	@Override
	protected PatternModel createPatternModel() {
        return new MuiltyPatternModel();
    }
 
	@Override
	protected void initComponents() {
		super.initComponents();
		findNext = new JButton();
		findPrevious = new JButton();
		/**
		 * 初始化类列表
		 */
		Map<String,String> colNames = new LinkedHashMap<>();
		colNames.put("", "全部");
		
		if (this.getColModel() != null){
			Enumeration<TableColumn> columns = this.getColModel().getColumns();
			while(columns.hasMoreElements()){
				TableColumn column = columns.nextElement();
				if (column == null)
					continue;
				
				if (column instanceof JSTableColumn){
					
					JSTableColumn col = (JSTableColumn)column;
					if (col.getIdentifier() == null || colNames.toString().length() <= 0)
						continue;
					
					colNames.put(col.toString(), col.getTitle());
				}
			}
		}
		
		columsList = new JSComboBox<>(colNames.values().toArray(new String[colNames.size()]) , 
				colNames.keySet().toArray(new String[colNames.size()]));
	}

}
