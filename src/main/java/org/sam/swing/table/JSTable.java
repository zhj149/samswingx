package org.sam.swing.table;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.search.Searchable;
import org.sam.swing.table.header.JSTableHeader;

/**
 * 自定义的table对象
 * 
 * @author sam
 *
 */
public class JSTable extends JXTable implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2300711063946251096L;

	/** Instantiates a JXTable with a default table model, no data. */
	public JSTable() {
		super();
		initConfig();
	}

	/**
	 * Instantiates a JXTable with a specific table model.
	 * 
	 * @param dm
	 *            The model to use.
	 */
	public JSTable(TableModel dm) {
		super(dm);
		initConfig();
	}

	/**
	 * Instantiates a JXTable with a specific table model.
	 * 
	 * @param dm
	 *            The model to use.
	 */
	public JSTable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
		initConfig();
	}

	/**
	 * Instantiates a JXTable with a specific table model, column model, and
	 * selection model.
	 * 
	 * @param dm
	 *            The table model to use.
	 * @param cm
	 *            The column model to use.
	 * @param sm
	 *            The list selection model to use.
	 */
	public JSTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm, cm, sm);
		initConfig();
	}

	/**
	 * Instantiates a JXTable for a given number of columns and rows.
	 * 
	 * @param numRows
	 *            Count of rows to accommodate.
	 * @param numColumns
	 *            Count of columns to accommodate.
	 */
	public JSTable(int numRows, int numColumns) {
		super(numRows, numColumns);
		initConfig();
	}

	/**
	 * Instantiates a JXTable with data in a vector or rows and column names.
	 * 
	 * @param rowData
	 *            Row data, as a Vector of Objects.
	 * @param columnNames
	 *            Column names, as a Vector of Strings.
	 */
	public JSTable(Vector<?> rowData, Vector<?> columnNames) {
		super(rowData, columnNames);
		initConfig();
	}

	/**
	 * Instantiates a JXTable with data in a array or rows and column names.
	 * 
	 * @param rowData
	 *            Row data, as a two-dimensional Array of Objects (by row, for
	 *            column).
	 * @param columnNames
	 *            Column names, as a Array of Strings.
	 */
	public JSTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		initConfig();
	}

	/**
	 * 设置窗口可编辑状态
	 * 
	 * @throws Exception
	 */
	public void readOnly() throws Exception {
		JSTableModel<?> module = (JSTableModel<?>) this.getModel();
		module.setEditable(!module.getEditable());
	}

	/**
	 * 初始化设置
	 */
	private void initConfig() {
		this.addKeyListener(this);
		this.setAutoscrolls(true);
		this.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
		this.setSortable(true);
		this.setRowHeight(26);
		this.setColumnControlVisible(true);
		this.setShowGrid(true, true);
		this.addHighlighter(HighlighterFactory.createSimpleStriping(Color.LIGHT_GRAY));

		//设置表头对象
		this.setTableHeader(new JSTableHeader(this.getColumnModel()));
		TableCellRenderer headerRenerder = this.getTableHeader().getDefaultRenderer();
		if (headerRenerder instanceof DefaultTableCellRenderer) {
			DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) headerRenerder;
			renderer.setHorizontalAlignment(JLabel.CENTER);
		}
		
		if (this.getModel() instanceof JSTableModel) {
			JSTableModel<?> module = (JSTableModel<?>) this.getModel();
			if (module != null)
				module.setEditable(false);
		}
	}

	/**
	 * 移动行操作，同时会做出原始行到新行的高亮效果 不带计入缓冲区
	 * 
	 * @param srcRow
	 *            原始行
	 * @param tarRow
	 *            目标行
	 * @throws Exception
	 */
	public void moveRow(int srcRow, int tarRow) throws Exception {
		if (srcRow < 0 || srcRow >= this.getRowCount())
			throw new Exception("sourceRow over index");

		if (tarRow < 0 || tarRow >= this.getRowCount())
			throw new Exception("targetRow over index");

		JSTableModel<?> module = (JSTableModel<?>) this.getModel();
		module.moveRow(convertRowIndexToModel(srcRow), convertRowIndexToModel(tarRow));
		this.changeSelection(tarRow, 0, false, false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Searchable getSearchable() {
		return super.getSearchable();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * 判断鼠标右键操作
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// ascii V 的十进制
		if (e.isControlDown() && e.getKeyCode() == 86) {
			Clipboard sysClb = Toolkit.getDefaultToolkit().getSystemClipboard();
			Transferable t = sysClb.getContents(null);

			if (null != t && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				try {

					String text = (String) t.getTransferData(DataFlavor.stringFlavor);
					if (text == null || text.length() <= 0)
						return;

					String[] rows = text.split("\n");
					if (rows == null || rows.length <= 0)
						return;

					if (this.getSelectedRows().length != rows.length) {
						JOptionPane.showMessageDialog(null, "您所粘贴的行数和您界面上选择的行数不同");
						return;
					}
					// 以下是设置值的操作
					int dataIndex = 0;

					for (int j : this.getSelectedRows()) {
						// 数据是以行为代表的
						String row = rows[dataIndex];
						String[] datas = row.split("\t");
						for (int i = 0; i < datas.length; i++) {
							this.setValue(j, i, datas[i]);
						}

						dataIndex++;
					}

				} catch (UnsupportedFlavorException | IOException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	/**
	 * 根据单元格的位置，设置数据
	 * 
	 * @param row
	 *            行索引
	 * @param column
	 *            列索引
	 * @param value
	 *            文本数据
	 */
	public void setValue(int row, int column, String value) throws Exception {

		Object orginal = this.getValueAt(row, column);
		if (value == null || orginal == null) {
			return;
		}

		Class<?> colType = orginal.getClass();
		JSTableColumn col = (JSTableColumn) this.getColumn(column);
		Object data = ((JSTableModel<?>) this.getModel()).getTableModelLinster().getDataTranstor(col, value, colType);
		this.setValueAt(data, row, column);
	}

}