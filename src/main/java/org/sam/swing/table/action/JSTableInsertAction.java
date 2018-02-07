package org.sam.swing.table.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.jdesktop.swingx.JXTable;
import org.sam.swing.resource.ResourceLoader;
import org.sam.swing.table.JSTableColumnModel;
import org.sam.swing.table.JSTableModel;

/**
 * 追加操作对象
 * 
 * @author sam
 *
 */
public class JSTableInsertAction extends JSTableBaseAction {

	public JSTableInsertAction(JXTable table) {
		super(table);
		this.putValue(AbstractAction.NAME, "");
		this.putValue(AbstractAction.SMALL_ICON,
				new ImageIcon(ResourceLoader.getResource(ResourceLoader.IMAGE_ADD_SMALL)));
		this.putValue(SHORT_DESCRIPTION, "当前行之前插入数据");
	}

	private static final long serialVersionUID = -3068593608658058519L;

	/**
	 * 追加数据
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			JSTableModel<?> module = (JSTableModel<?>) this.getTable().getModel();
			int iRow = this.getTable().getSelectedRow();
			if (iRow < 0 || iRow >= this.getTable().getRowCount())
				return;
			
			boolean result = module.insert(this.getTable().convertRowIndexToModel(iRow));
			if (!result) {
				JOptionPane.showMessageDialog(null, "插入数据失败");
				return;
			}
			
			JSTableColumnModel colModel = (JSTableColumnModel) this.getTable().getColumnModel();
			
			this.getTable().scrollRowToVisible(iRow);
			if (!module.getEditable()) {
				module.setEditable(true);
			}
			this.getTable().changeSelection(iRow, 0, false, false);
			this.getTable().setEditingRow(iRow);
			this.getTable().editCellAt(iRow, getTable().convertColumnIndexToView(colModel.getFirstEditColumnModelIndex()));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
