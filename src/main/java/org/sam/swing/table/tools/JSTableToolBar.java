package org.sam.swing.table.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import org.apache.commons.lang3.ArrayUtils;
import org.sam.swing.FileImportActionListener;
import org.sam.swing.JSFindBar;
import org.sam.swing.resource.ResourceLoader;
import org.sam.swing.table.JSTable;
import org.sam.swing.table.JSTableModel;

/**
 * 给JSTable增加的工具条
 * 
 * @author sam
 *
 */
public class JSTableToolBar extends JToolBar {

	private static final long serialVersionUID = -5793491647918673440L;

	/**
	 * 当前操作的table
	 */
	private JSTable table;

	/**
	 * 当前的model对象
	 */
	private JSTableModel<?> tableModel;

	/**
	 * 当前的model对象
	 * 
	 * @return
	 */
	public JSTableModel<?> getModel() {
		return tableModel;
	}

	/**
	 * 当前的model对象
	 * 
	 * @param model
	 */
	public void setModel(JSTableModel<?> model) {
		this.tableModel = model;
	}

	/**
	 * 当前操作的table
	 * 
	 * @return
	 */
	public JSTable getTable() {
		return table;
	}

	/**
	 * 当前操作的table
	 * 
	 * @param table
	 */
	public void setTable(JSTable table) {
		this.table = table;
	}

	/**
	 * 构造函数
	 * 
	 * @param table
	 * @param tableModel
	 */
	public JSTableToolBar(JSTable table, JSTableModel<?> tableModel) {
		this.setTable(table);
		this.setModel(tableModel);
	}

	/**
	 * 不带参数的toolbar
	 */
	public JSTableToolBar() {
		this(null, null);
	}

	/**
	 * 刷新按钮
	 */
	private boolean retrieve = false;

	/**
	 * 全选按钮
	 */
	private boolean select = false;

	/**
	 * 导入按钮
	 */
	private boolean imp = false;

	/**
	 * 编辑按钮
	 */
	private boolean edit = false;

	/**
	 * 新增按钮
	 */
	private boolean add = false;

	/**
	 * 删除按钮
	 */
	private boolean del = false;

	/**
	 * 删除按钮
	 */
	private boolean save = false;

	/**
	 * 搜索工具条
	 */
	private boolean search = false;

	/**
	 * 刷新
	 * 
	 * @return
	 */
	public boolean isRetrieve() {
		return retrieve;
	}

	/**
	 * 刷新
	 * 
	 * @param retrieve
	 */
	public void setRetrieve(boolean retrieve) {
		this.retrieve = retrieve;
	}

	/**
	 * 全选，不全选
	 * 
	 * @return
	 */
	public boolean isSelect() {
		return select;
	}

	/**
	 * 全选，不全选
	 * 
	 * @param select
	 */
	public void setSelect(boolean select) {
		this.select = select;
	}

	/**
	 * 导入
	 * 
	 * @return
	 */
	public boolean isImp() {
		return imp;
	}

	/**
	 * 导入
	 * 
	 * @param imp
	 */
	public void setImp(boolean imp) {
		this.imp = imp;
	}

	/**
	 * 编辑
	 * 
	 * @return
	 */
	public boolean isEdit() {
		return edit;
	}

	/**
	 * 编辑
	 * 
	 * @param edit
	 */
	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	/**
	 * 新增
	 * 
	 * @return
	 */
	public boolean isAdd() {
		return add;
	}

	/**
	 * 新增
	 * 
	 * @param add
	 */
	public void setAdd(boolean add) {
		this.add = add;
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public boolean isDel() {
		return del;
	}

	/**
	 * 删除
	 * 
	 * @param del
	 */
	public void setDel(boolean del) {
		this.del = del;
	}

	/**
	 * 保存
	 * 
	 * @return
	 */
	public boolean isSave() {
		return save;
	}

	/**
	 * 保存
	 * 
	 * @param save
	 */
	public void setSave(boolean save) {
		this.save = save;
	}

	/**
	 * 搜索
	 * 
	 * @return
	 */
	public boolean isSearch() {
		return search;
	}

	/**
	 * 搜索
	 * 
	 * @param search
	 */
	public void setSearch(boolean search) {
		this.search = search;
	}

	/**
	 * 刷新的动作
	 */
	private ActionListener retrieveAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				tableModel.retrieve();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	};

	/**
	 * 全选的代码
	 */
	private ActionListener selectAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				int[] selectedRows = table.getSelectedRows();

				// 未有行选中的情况下的操作
				if (selectedRows == null || selectedRows.length <= 1) {
					int count = tableModel.getRowCount();
					for (int i = 0; i < count; i++) {
						if (!tableModel.getValueAt(i, 1).equals(1))
							tableModel.setValueAt(1, i, 1);
					}
				} else {
					// 有选中行操作的情况下
					for (int i = 0; i < selectedRows.length; i++) {
						int iRow = table.convertRowIndexToModel(selectedRows[i]);
						if (!tableModel.getValueAt(iRow, 1).equals(1))
							tableModel.setValueAt(1, iRow, 1);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	};

	/**
	 * 取消全选的操作
	 */
	private ActionListener unSeelctAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {

				int[] selectedRows = table.getSelectedRows();

				// 未有行选中的情况下的操作
				if (selectedRows == null || selectedRows.length <= 1) {
					int count = tableModel.getRowCount();
					for (int i = 0; i < count; i++) {
						if (!tableModel.getValueAt(i, 1).equals(0))
							tableModel.setValueAt(0, i, 1);
					}
				} else {
					// 有选中行操作的情况下
					for (int i = 0; i < selectedRows.length; i++) {
						int iRow = table.convertRowIndexToModel(selectedRows[i]);
						if (!tableModel.getValueAt(iRow, 1).equals(0))
							tableModel.setValueAt(0, iRow, 1);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	};

	/**
	 * 反向选择执行的操作
	 */
	private ActionListener invertSelectAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			int[] selectedRows = table.getSelectedRows();
			Integer selected = 0;
			Integer unSelected = 1;
			int count = tableModel.getRowCount();

			// 转换成modelrow index
			for (int i = 0; i < selectedRows.length; i++) {
				selectedRows[i] = table.convertRowIndexToModel(selectedRows[i]);
			}

			// 以选中行的第一行的行状态为准
			if (selectedRows != null && selectedRows.length > 0) {

				Object value = tableModel.getValueAt(selectedRows[0], 1);
				if (value instanceof Boolean) {
					selected = (((Boolean) value).booleanValue() ? Integer.valueOf(1) : Integer.valueOf(0));
				} else if (value instanceof Integer) {
					selected = (Integer) value;
				} else {
					selected = Integer.valueOf(0);
				}

				if (selected == null)
					selected = Integer.valueOf(0);

				unSelected = (selected.equals(0) ? Integer.valueOf(1) : Integer.valueOf(0));
			}

			for (int i = 0; i < count; i++) {
				if (ArrayUtils.indexOf(selectedRows, i) >= 0) {
					if (!tableModel.getValueAt(i, 1).equals(selected))
						tableModel.setValueAt(selected, i, 1);
				} else {
					if (!tableModel.getValueAt(i, 1).equals(unSelected))
						tableModel.setValueAt(unSelected, i, 1);
				}
			}
		}
	};

	/**
	 * 导入操作
	 */
	private ActionListener importAction = new FileImportActionListener() {

		@Override
		public void afterOpen(File file) throws Exception {
			JOptionPane.showMessageDialog(null, "请重写导入文件这个接口");
		}
	};

	/**
	 * 编辑操作
	 */
	private ActionListener editAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				table.readOnly();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	};

	/**
	 * 新增操作
	 */
	private ActionListener addAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				table.update();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	};

	/**
	 * 删除操作
	 */
	private ActionListener delAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				table.delete();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	};

	/**
	 * 保存操作
	 */
	private ActionListener saveAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				table.update();
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "保存失败\r\n出错信息为：\r\n" + ex.getMessage());
			}
		}
	};

	/**
	 * 刷新数据操作
	 * 
	 * @return
	 */
	public ActionListener getRetrieveAction() {
		return retrieveAction;
	}

	/**
	 * 刷新数据操作
	 * 
	 * @param retrieveAction
	 */
	public void setRetrieveAction(ActionListener retrieveAction) {
		this.retrieveAction = retrieveAction;
	}

	/**
	 * 全选操作
	 * 
	 * @return
	 */
	public ActionListener getSelectAction() {
		return selectAction;
	}

	/**
	 * 全选操作
	 * 
	 * @param selectAction
	 */
	public void setSelectAction(ActionListener selectAction) {
		this.selectAction = selectAction;
	}

	/**
	 * 全不选操作
	 * 
	 * @return
	 */
	public ActionListener getUnSeelctAction() {
		return unSeelctAction;
	}

	/**
	 * 反选
	 * 
	 * @return
	 */
	public ActionListener getInvertSelectAction() {
		return invertSelectAction;
	}

	/**
	 * 反选
	 * 
	 * @param invertSelectAction
	 */
	public void setInvertSelectAction(ActionListener invertSelectAction) {
		this.invertSelectAction = invertSelectAction;
	}

	/**
	 * 全不选操作
	 * 
	 * @param unSeelctAction
	 */
	public void setUnSeelctAction(ActionListener unSeelctAction) {
		this.unSeelctAction = unSeelctAction;
	}

	/**
	 * 导入数据操作
	 * 
	 * @return
	 */
	public ActionListener getImportAction() {
		return importAction;
	}

	/**
	 * 导入数据操作
	 * 
	 * @param importAction
	 */
	public void setImportAction(ActionListener importAction) {
		this.importAction = importAction;
	}

	/**
	 * 编辑操作
	 * 
	 * @return
	 */
	public ActionListener getEditAction() {
		return editAction;
	}

	/**
	 * 编辑操作
	 * 
	 * @param editAction
	 */
	public void setEditAction(ActionListener editAction) {
		this.editAction = editAction;
	}

	/**
	 * 新增操作
	 * 
	 * @return
	 */
	public ActionListener getAddAction() {
		return addAction;
	}

	/**
	 * 新增操作
	 * 
	 * @param addAction
	 */
	public void setAddAction(ActionListener addAction) {
		this.addAction = addAction;
	}

	/**
	 * 删除操作
	 * 
	 * @return
	 */
	public ActionListener getDelAction() {
		return delAction;
	}

	/**
	 * 删除操作
	 * 
	 * @param delAction
	 */
	public void setDelAction(ActionListener delAction) {
		this.delAction = delAction;
	}

	/**
	 * 保存操作
	 * 
	 * @return
	 */
	public ActionListener getSaveAction() {
		return saveAction;
	}

	/**
	 * 保存操作
	 * 
	 * @param saveAction
	 */
	public void setSaveAction(ActionListener saveAction) {
		this.saveAction = saveAction;
	}

	/**
	 * 生成工具条 请在所有工作都做完了，再调用本方法
	 * 
	 * @return
	 */
	public JSTableToolBar build() {

		if (this.isRetrieve()) {
			this.add(buildRetrieve());
		}
		if (this.isImp()) {
			this.add(buildImport());
		}
		if (this.isSelect()) {
			this.add(buildSelect());
			this.add(buildUnSelect());
			this.add(buildInvertSelect());
		}
		if (this.isEdit()) {
			this.add(buildEdit());
		}
		if (this.isAdd()) {
			this.add(buildAdd());
		}
		if (this.isDel()) {
			this.add(buildDel());
		}
		if (this.isSave()) {
			this.add(buildSave());
		}
		if (this.isSearch()) {
			this.add(buildSearchBar());
		}

		return this;
	}

	/**
	 * 创建自定义的按钮对象
	 * 
	 * @param image
	 * @param text
	 * @param listener
	 * @return
	 */
	public JButton buildButton(String image, String text, ActionListener listener) {
		return buildButton(image, text, "", listener);
	}

	/**
	 * 创建自定义的按钮对象
	 * 
	 * @param image
	 * @param text
	 * @param toolTip
	 * @param listener
	 * @return
	 */
	public JButton buildButton(String image, String text, String toolTip, ActionListener listener) {

		final JButton btn = new JButton(text, new ImageIcon(ResourceLoader.getResource(image)));
		btn.addActionListener(listener);
		btn.setToolTipText(toolTip);
		return btn;
	}

	/**
	 * 构建检索按钮
	 * 
	 * @return
	 */
	public JButton buildRetrieve() {
		return buildButton(ResourceLoader.IMAGE_RETRIEVE, "刷新", retrieveAction);
	}

	/**
	 * 生成全选按钮
	 * 
	 * @return
	 */
	public JButton buildSelect() {
		return buildButton(ResourceLoader.IMAGE_SELECT, "", "全选", selectAction);
	}

	/**
	 * 生成全不选按钮
	 * 
	 * @return
	 */
	public JButton buildUnSelect() {
		return buildButton(ResourceLoader.IMAGE_UNSELECT, "", "全不选", unSeelctAction);
	}

	/**
	 * 生成反选
	 * 
	 * @return
	 */
	public JButton buildInvertSelect() {
		return buildButton(ResourceLoader.IMAGE_INVERT_SELECT, "", "反选", invertSelectAction);
	}

	/**
	 * 生成导入按钮
	 * 
	 * @return
	 */
	public JButton buildImport() {
		return buildButton(ResourceLoader.IMAGE_IMPORT, "导入", importAction);
	}

	/**
	 * 生成编辑按钮
	 * 
	 * @return
	 */
	public JButton buildEdit() {
		return buildButton(ResourceLoader.IMAGE_MODIFY, "", "编辑状态", editAction);
	}

	/**
	 * 生成新增按钮
	 * 
	 * @return
	 */
	public JButton buildAdd() {
		return buildButton(ResourceLoader.IMAGE_ADD_SMALL, "", "新增一行", addAction);
	}

	/**
	 * 生成删除按钮
	 * 
	 * @return
	 */
	public JButton buildDel() {
		return buildButton(ResourceLoader.IMAGE_DELETE, "", "删除一行", delAction);
	}

	/**
	 * 生成保存按钮
	 * 
	 * @return
	 */
	public JButton buildSave() {
		return buildButton(ResourceLoader.IMAGE_SAVE, "", "保存数据", saveAction);
	}

	/**
	 * 使用jxtable自带的searchbar
	 * 
	 * @return
	 */
	public JSFindBar buildSearchBar() {
		JSFindBar searchBar = new JSFindBar(table.getSearchable());
		return searchBar;
	}
}
