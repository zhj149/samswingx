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
import java.math.BigDecimal;
import java.util.Date;
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
	 * 删除一行数据的操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public void delete() throws Exception {
		int iRow = this.getSelectedRow();
		if (iRow < 0) {
			JOptionPane.showMessageDialog(null, "请选择您要删除的数据行");
			return;
		}
		((JSTableModel<?>) this.getModel()).delete(this.convertRowIndexToModel(iRow));
	}

	/**
	 * 新增一行的操作
	 * 
	 * @return 返回新增数据行号
	 * @throws Exception
	 */
	public int append() throws Exception {
		JSTableModel<?> module = (JSTableModel<?>) this.getModel();
		module.append();
		this.scrollRowToVisible(module.getRowCount() - 1);
		if (!module.getEditable()) {
			module.setEditable(true);
		}
		int iRow = module.getRowCount() - 1;
		this.changeSelection(iRow, 0, false, false);
		this.setEditingRow(iRow);
		this.editCellAt(iRow, 0);

		return iRow;
	}

	/**
	 * 在第几行的位置插入数据
	 * 
	 * @param index
	 *            行索引
	 * @return
	 * @throws Exception
	 */
	public int insert(int index) throws Exception {
		JSTableModel<?> module = (JSTableModel<?>) this.getModel();
		module.insert(index);
		this.scrollRowToVisible(index);
		if (!module.getEditable()) {
			module.setEditable(true);
		}
		int iRow = index;
		this.changeSelection(iRow, 0, false, false);
		this.setEditingRow(iRow);
		this.editCellAt(iRow, 0);

		return iRow;
	}

	/**
	 * 保存操作
	 * 
	 * @throws Exception
	 */
	public void update() throws Exception {
		JSTableModel<?> module = (JSTableModel<?>) this.getModel();

		if (module.update()) {
			JOptionPane.showMessageDialog(null, "保存成功");
		} else {
			JOptionPane.showMessageDialog(null, "保存失败");
		}

		module.setEditable(false);
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
		this.setSortable(false);
		this.setRowHeight(20);
		this.setColumnControlVisible(true);
		this.setShowGrid(true, true);
		this.addHighlighter(HighlighterFactory.createSimpleStriping(Color.LIGHT_GRAY));

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
		module.moveRow(srcRow, tarRow);
		this.changeSelection(tarRow, 0, false, false);
	}

	// begin keyevent

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
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
		if (colType.equals(Integer.class)) {
			if (value.trim().length() <= 0) {
				this.setValueAt(null, row, column);
			} else if (value.equals("true") || value.equals("false")) {
				this.setValueAt(value.equals("true") ? 1 : 0, row, column);
			} else {
				this.setValueAt(Integer.parseInt(value), row, column);
			}
		} else if (colType.equals(String.class)) {
			this.setValueAt(value, row, column);
		} else if (colType.equals(Double.class)) {
			if (value.trim().length() <= 0) {
				this.setValueAt(null, row, column);
			} else {
				this.setValueAt(Double.parseDouble(value), row, column);
			}
		} else if (colType.equals(Float.class)) {
			if (value.trim().length() <= 0) {
				this.setValueAt(null, row, column);
			} else {
				this.setValueAt(Float.parseFloat(value), row, column);
			}
		} else if (colType.equals(Long.class)) {
			if (value.trim().length() <= 0) {
				this.setValueAt(null, row, column);
			} else {
				this.setValueAt(Long.parseLong(value), row, column);
			}
		} else if (colType.equals(Character.class)) {
			this.setValueAt(value.toCharArray()[0], row, column);
		} else if (colType.equals(Boolean.class)) {
			this.setValueAt(Boolean.parseBoolean(value), row, column);
		} else if (colType.equals(Short.class)) {
			this.setValueAt(Short.parseShort(value), row, column);
		} else if (colType.equals(Date.class)) {
			if (value.trim().equals("") || value.equals("0000-00-00") || value.equals("0000/00/00")
					|| value.equals("0000-00-00 00:00:00") || value.equals("0000-00-00-00-00-00")
					|| value.equals("0000/00/00 00:00:00")) {
				this.setValueAt(null, row, column);
			}
		} else if (colType.equals(BigDecimal.class)) {
			this.setValueAt(new BigDecimal(value), row, column);
		} else {
			this.setValueAt(value, row, column);
		}
	}

	// end
}
