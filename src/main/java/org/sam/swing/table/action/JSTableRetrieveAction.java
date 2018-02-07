package org.sam.swing.table.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import org.jdesktop.swingx.JXTable;
import org.sam.swing.resource.ResourceLoader;
import org.sam.swing.table.JSTableModel;

/**
 * 检索操作
 * @author sam
 *
 */
public class JSTableRetrieveAction extends JSTableBaseAction {

	/**
	 * 检索操作
	 * @param table
	 */
	public JSTableRetrieveAction(JXTable table) {
		super(table);
		this.putValue(AbstractAction.NAME, "刷新");
		this.putValue(AbstractAction.SMALL_ICON,
				new ImageIcon(ResourceLoader.getResource(ResourceLoader.IMAGE_RETRIEVE)));
		this.putValue(SHORT_DESCRIPTION, "刷新数据");
	}

	private static final long serialVersionUID = -8948801795558420788L;

	/**
	 * 检索操作
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			((JSTableModel<?>)this.getTable().getModel()).retrieve();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
