package org.sam.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * 文件调用的action
 * 
 * @author sam
 *
 */
public interface FileImportActionListener extends ActionListener {

	/**
	 * Invoked when an action occurs.
	 */
	public default void actionPerformed(ActionEvent e) {

		try {
			File file = JSFileDataStoreChooser.showOpenFile("csv", null);
			if (file != null && file.exists()) {
				afterOpen(file);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 打开文件完成后传递出文件的回调方法
	 * 
	 * @param file
	 * @throws Exception
	 */
	public void afterOpen(File file) throws Exception;

}
