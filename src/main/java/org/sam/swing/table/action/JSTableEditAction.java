package org.sam.swing.table.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import org.jdesktop.swingx.JXTable;
import org.sam.swing.resource.ResourceLoader;
import org.sam.swing.table.JSTableModel;

/**
 * 保存操作
 * 
 * @author sam
 *
 */
public class JSTableEditAction extends JSTableBaseAction {

	private static final long serialVersionUID = 2834589546104965124L;

	/**
	 * 保存操作
	 * 
	 * @param table
	 */
	public JSTableEditAction(JXTable table) {
		super(table);
		this.putValue(AbstractAction.NAME, "");
		this.putValue(AbstractAction.SMALL_ICON,
				new ImageIcon(ResourceLoader.getResource(ResourceLoader.IMAGE_MODIFY)));
		this.putValue(SHORT_DESCRIPTION, "编辑");
	}

	/**
	 * 保存操作
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			JSTableModel<?> module = (JSTableModel<?>) this.getTable().getModel();
			module.setEditable(!module.getEditable());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
