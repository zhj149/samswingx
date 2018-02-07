package org.sam.swing.table.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;
import org.sam.swing.resource.ResourceLoader;

/**
 * 全选操作
 * 
 * @author sam
 *
 */
public class JSTableSelectAction extends JSTableBaseAction {

	private static final long serialVersionUID = -1903852879063133862L;

	/**
	 * 全选操作
	 * 
	 * @param table
	 */
	public JSTableSelectAction(JXTable table) {
		super(table);
		this.putValue(AbstractAction.NAME, "");
		this.putValue(AbstractAction.SMALL_ICON,
				new ImageIcon(ResourceLoader.getResource(ResourceLoader.IMAGE_SELECT)));
		this.putValue(SHORT_DESCRIPTION, "全选");
	}

	/**
	 * 当前操作的列位置 默认下标1
	 */
	private int editColIndex = 1;

	/**
	 * 当前操作的列位置 默认下标1
	 * 
	 * @return
	 */
	public int getEditColIndex() {
		return editColIndex;
	}

	/**
	 * 当前操作的列位置 默认下标1
	 * 
	 * @param editColIndex
	 */
	public void setEditColIndex(int editColIndex) {
		this.editColIndex = editColIndex;
	}

	/**
	 * 当前选中字段的值 默认是数字1
	 */
	private Object selectedValue = Integer.valueOf(1);

	/**
	 * 当前选中字段的值 默认是数字1
	 * 
	 * @return
	 */
	public Object getSelectedValue() {
		return selectedValue;
	}

	/**
	 * 当前选中字段的值 默认是数字1
	 * 
	 * @param selectedValue
	 */
	public void setSelectedValue(Object selectedValue) {
		this.selectedValue = selectedValue;
	}

	/**
	 * 执行的按钮操作
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			int[] selectedRows = this.getTable().getSelectedRows();

			TableModel tableModel = this.getTable().getModel();
			// 未有行选中的情况下的操作
			if (selectedRows == null || selectedRows.length <= 1) {
				int count = tableModel.getRowCount();
				for (int i = 0; i < count; i++) {
					if (!tableModel.getValueAt(i, editColIndex).equals(selectedValue))
						tableModel.setValueAt(selectedValue, i, editColIndex);
				}
			} else {
				// 有选中行操作的情况下
				for (int i = 0; i < selectedRows.length; i++) {
					int iRow = table.convertRowIndexToModel(selectedRows[i]);
					if (!tableModel.getValueAt(iRow, editColIndex).equals(selectedValue))
						tableModel.setValueAt(selectedValue, iRow, editColIndex);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
