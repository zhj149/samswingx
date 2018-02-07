package org.sam.swing.table.action;

import java.io.File;
import java.util.EventListener;

/**
 * 文件调用的action
 * 
 * @author sam
 *
 */
public interface JSFileImportActionListener extends EventListener {

	/**
	 * 打开文件完成后传递出文件的回调方法
	 * 
	 * @param file
	 * @throws Exception
	 */
	public void afterOpen(File file) throws Exception;
	
	/**
	 * 导入成功后的操作
	 * @throws Exception
	 */
	public void afterDone() throws Exception;

}
