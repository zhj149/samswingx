package org.sam.swing;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 演示代码
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	try {
    		//获取当前操作系统的主题
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()	);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
    	
    	JFrameDefaultTableDemo frm = new JFrameDefaultTableDemo();
    	frm.setSize(1024 , 768);
    	frm.setLocationRelativeTo(null); //在屏幕上居中
        frm.setVisible(true);
       
//    	JFrameDemo frame = new JFrameDemo();
//        frame.setVisible(true);
    }
}
