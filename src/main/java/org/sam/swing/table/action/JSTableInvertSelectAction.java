package org.sam.swing.table.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.table.TableModel;

import org.apache.commons.lang3.ArrayUtils;
import org.jdesktop.swingx.JXTable;
import org.sam.swing.resource.ResourceLoader;

/**
 * 反选操作
 * 
 * @author sam
 *
 */
public class JSTableInvertSelectAction extends JSTableBaseAction {

	private static final long serialVersionUID = -1903852879063133862L;

	/**
	 * 全不选操作
	 * 
	 * @param table
	 */
	public JSTableInvertSelectAction(JXTable table) {
		super(table);
		this.putValue(AbstractAction.NAME, "");
		this.putValue(AbstractAction.SMALL_ICON,
				new ImageIcon(ResourceLoader.getResource(ResourceLoader.IMAGE_INVERT_SELECT)));
		this.putValue(SHORT_DESCRIPTION, "反选");
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
			Object selected = this.getSelectedValue();
			Object unSelected = this.getUnSelectedValue();
			int count = tableModel.getRowCount();

			// 转换成modelrow index
			for (int i = 0; i < selectedRows.length; i++) {
				selectedRows[i] = this.getTable().convertRowIndexToModel(selectedRows[i]);
			}

			// 以选中行的第一行的行状态为准
			for (int i = 0; i < count; i++) {
				if (ArrayUtils.indexOf(selectedRows, i) >= 0) {
					if (!tableModel.getValueAt(i, editColIndex).equals(selected))
						tableModel.setValueAt(selected, i, editColIndex);
				} else {
					if (!tableModel.getValueAt(i, editColIndex).equals(unSelected))
						tableModel.setValueAt(unSelected, i, editColIndex);
				}
			}
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
