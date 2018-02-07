package org.sam.swing.table.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

import org.jdesktop.swingx.JXTable;
import org.sam.swing.JSFileDataStoreChooser;
import org.sam.swing.resource.ResourceLoader;

/**
 * 删除操作对象
 * 
 * @author sam
 *
 */
public class JSTableImportAction extends JSTableBaseAction {

	public JSTableImportAction(JXTable table) {
		super(table);
		this.putValue(AbstractAction.NAME, "导入");
		this.putValue(AbstractAction.SMALL_ICON,
				new ImageIcon(ResourceLoader.getResource(ResourceLoader.IMAGE_IMPORT)));
		this.putValue(SHORT_DESCRIPTION, "导入数据数据");
	}

	private static final long serialVersionUID = -3068593608658058519L;

	/**
	 * 导入操作事件
	 */
	private List<JSFileImportActionListener> importListener = new LinkedList<>();

	/**
	 * 新增加一个事件操作对象
	 * 
	 * @param l
	 */
	public synchronized void addFileImportActionListener(JSFileImportActionListener l) {
		if (!importListener.contains(l))
			importListener.add(l);
	}

	/**
	 * 移除一个事件操作对象
	 * 
	 * @param l
	 */
	public synchronized void removeFileImportActionListener(JSFileImportActionListener l) {
		if (importListener.contains(l))
			importListener.remove(l);
	}

	/**
	 * 导入数据操作
	 */
	public void actionPerformed(ActionEvent e) {

		try {
			File file = JSFileDataStoreChooser.showOpenFile("csv", null);
			if (file != null && file.exists()) {

				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

					@Override
					protected synchronized Void doInBackground() throws Exception {
						for (JSFileImportActionListener l : importListener) {
							l.afterOpen(file);
						}
						return null;
					}

					@Override
					protected synchronized void done() {
						try {
							for (JSFileImportActionListener l : importListener) {
								l.afterDone();
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						} finally {
						}
					}
				};

				worker.execute();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
