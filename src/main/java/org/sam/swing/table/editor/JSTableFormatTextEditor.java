package org.sam.swing.table.editor;

import java.text.Format;

import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;

/**
 * 带有多种值类型的checkbox工具
 * 
 * @author sam
 *
 */
public class JSTableFormatTextEditor extends DefaultCellEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 545401568684102849L;

	/**
	 * 不带参数的构造函数
	 */
	public JSTableFormatTextEditor() {
		this(new JFormattedTextField());
	}
	
	/**
	 * 带有掩码工具的text文本
	 * @param format
	 */
	public JSTableFormatTextEditor(Format format){
		this(new JFormattedTextField(format));
	}

	/**
	 * 带参数的构造函数
	 * 
	 * @param checkBox
	 */
	public JSTableFormatTextEditor(JFormattedTextField formattedTextField) {
		super(formattedTextField);
		formattedTextField.removeActionListener(this.delegate);
		delegate = new EditorDelegate() {
			private static final long serialVersionUID = 1L;

			@Override
			public void setValue(Object value) {
				
				super.setValue(value);
				formattedTextField.setValue(value);
			}

			@Override
			public Object getCellEditorValue() {
				return formattedTextField.getValue();
			}
		};

	}

}
