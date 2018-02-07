package org.sam.swing.table.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.jdesktop.swingx.JXTable;
import org.sam.swing.resource.ResourceLoader;
import org.sam.swing.table.JSTableModel;

/**
 * 删除操作对象
 * 
 * @author sam
 *
 */
public class JSTableDeleteAction extends JSTableBaseAction {

	public JSTableDeleteAction(JXTable table) {
		super(table);
		this.putValue(AbstractAction.NAME, "");
		this.putValue(AbstractAction.SMALL_ICON,
				new ImageIcon(ResourceLoader.getResource(ResourceLoader.IMAGE_DELETE)));
		this.putValue(SHORT_DESCRIPTION, "删除选中数据");
	}

	private static final long serialVersionUID = -3068593608658058519L;

	/**
	 * 删除操作
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			int selectedRowCount = this.getTable().getSelectedRowCount();
			if (selectedRowCount <= 0) {
				JOptionPane.showMessageDialog(null, "请选择您要删除的数据行");
				return;
			} else if (selectedRowCount == 1) {
				int iRow = this.getTable().getSelectedRow();
				((JSTableModel<?>) this.getTable().getModel()).delete(this.getTable().convertRowIndexToModel(iRow));
			} else {
				// > 1的情况
				int confim = JOptionPane.showConfirmDialog(null, "您将删除【" + selectedRowCount + "】行数据，是否删除?");
				if (confim == JOptionPane.OK_OPTION) {
					int[] selectedRows = this.getTable().getSelectedRows();
					for (int i = selectedRowCount - 1; i >= 0; i--) {
						JSTableModel<?> model = (JSTableModel<?>) this.getTable().getModel();
						model.delete(this.getTable().convertRowIndexToModel(selectedRows[i]));
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
