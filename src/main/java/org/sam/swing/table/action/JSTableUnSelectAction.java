package org.sam.swing.table.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;
import org.sam.swing.resource.ResourceLoader;

/**
 * 全不选操作
 * 
 * @author sam
 *
 */
public class JSTableUnSelectAction extends JSTableBaseAction {

	private static final long serialVersionUID = -1903852879063133862L;

	/**
	 * 全不选操作
	 * 
	 * @param table
	 */
	public JSTableUnSelectAction(JXTable table) {
		super(table);
		this.putValue(AbstractAction.NAME, "");
		this.putValue(AbstractAction.SMALL_ICON,
				new ImageIcon(ResourceLoader.getResource(ResourceLoader.IMAGE_UNSELECT)));
		this.putValue(SHORT_DESCRIPTION, "全不选");
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
	 * 当前选中字段的非选中值 默认是数字0
	 */
	private Object unSelectedValue = Integer.valueOf(0);

	/**
	 * 当前选中字段的非选中值 默认是数字0
	 * 
	 * @return
	 */
	public Object getUnSelectedValue() {
		return unSelectedValue;
	}

	/**
	 * 当前选中字段的非选中值 默认是数字0
	 * 
	 * @param unSelectedValue
	 */
	public void setUnSelectedValue(Object unSelectedValue) {
		this.unSelectedValue = unSelectedValue;
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
					if (!tableModel.getValueAt(i, editColIndex).equals(unSelectedValue))
						tableModel.setValueAt(unSelectedValue, i, editColIndex);
				}
			} else {
				// 有选中行操作的情况下
				for (int i = 0; i < selectedRows.length; i++) {
					int iRow = table.convertRowIndexToModel(selectedRows[i]);
					if (!tableModel.getValueAt(iRow, editColIndex).equals(unSelectedValue))
						tableModel.setValueAt(unSelectedValue, iRow, editColIndex);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
