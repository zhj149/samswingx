package org.sam.swing.table.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;

import org.sam.swing.table.view.JSTableColumnMappingAbstract;

public class JSTableDialogEditor extends AbstractCellEditor
	implements TableCellEditor, TreeCellEditor  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7718894438504689717L;
	
	//
	//  Instance Variables
	//

    /** The Swing component being edited. */
    protected JComponent editorComponent;
    /**
     * The delegate class which handles all methods sent from the
     * <code>CellEditor</code>.
     */
    protected EditorDelegate delegate;
    /**
     * An integer specifying the number of clicks needed to start editing.
     * Even if <code>clickCountToStart</code> is defined as zero, it
     * will not initiate until a click occurs.
     */
    protected int clickCountToStart = 1;

    /**
     * 绑定显示的字段
     */
    protected String fieldName;

    /**
     * 绑定显示的字段
     * @return
     */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * 绑定显示的字段
	 * @param fieldName
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	/**
	 * 表的映射关系
	 */
	private JSTableColumnMappingAbstract mapping;
	
	/**
	 * 表的映射关系
	 * @return
	 */
	public JSTableColumnMappingAbstract getMapping() {
		return mapping;
	}

	/**
	 * 实体的映射关系
	 * @param mapping
	 */
	public void setMapping(JSTableColumnMappingAbstract mapping) {
		this.mapping = mapping;
	}

	private JLabel label;
	
	/**
	 * 主体的框架
	 */
	private JPanel panel;
	
	/**
	 * 按钮
	 */
	private JButton btn;
	
	/**
	 * 保留的数据
	 */
	private Object data;
	
	/**
	 * true 返回的对象值 false 返回对应的字段值
	 */
	private boolean objectData = true;
	
	/**
	 * true 返回的对象值 false 返回对应的字段值
	 * @return
	 */
	public boolean isObjectData() {
		return objectData;
	}

	/**
	 * true 返回的对象值 false 返回对应的字段值
	 * @param objectData
	 */
	public void setObjectData(boolean objectData) {
		this.objectData = objectData;
	}

	/**
	 * 双击编辑
	 */
	public JSTableDialogEditor(JSTableColumnMappingAbstract colMapping , String fieldName)
	{
		super();
		label = new JLabel();
		btn = new JButton(".");
		panel = new JPanel(new BorderLayout());
		panel.add(label , BorderLayout.CENTER);
		panel.add(btn , BorderLayout.EAST);
		this.editorComponent = panel;
		btn.setVisible(false);
		
        this.clickCountToStart = 2;
        delegate = new EditorDelegate() {
        	
            /**
			 * 
			 */
			private static final long serialVersionUID = 8157752346835662096L;

			public void setValue(Object value) {
            	
            	//此处实现有点low
            	//原因是我不太明白swing这个table的工作原理，
            	//我猜测，在table第一次加载的时候，本editor就被实例化，然后隐藏起来备用
            	//所有我不在这里设置一下数据，就会窗口加载的时候，一直弹出对话框
            	if (!init)
            	{
            		label.setText("");
            		init = true;
            		return;
            	}
            	
            	data = value;
				
				JSTableSingleSelectionDialog dialog = new JSTableSingleSelectionDialog(colMapping);
				dialog.setData(value);
				dialog.setVisible(true);
				
				if (JSTableSingleSelectionDialog.OK.equals(dialog.getAction()))
				{
					data = dialog.getData();
				}
            	
				if (data instanceof String)
				{
					label.setText((data == null) ? "" : data.toString());
				}
				else if (data != null && fieldName != null && fieldName.length() > 0) {
        			try
        			{
        				if (data != null)
        				{
        					//带有嵌套效果的对象数据
        					if (fieldName.indexOf('.') >= 0) {
        						
        						String[] objs = fieldName.split("\\.");
        						Object object = data;
        						
        						for(int i = 0 ; i < objs.length ; i++)
        						{
        							Field field = object.getClass().getDeclaredField(objs[i]);
        							field.setAccessible(true);
        							object = field.get(object);
        						}
        						
        						label.setText((object == null) ? "" : object.toString());
        						
        					} else {
        						Field field = data.getClass().getDeclaredField(fieldName);
        						field.setAccessible(true);
        						Object object = field.get(data);
        						label.setText((object == null) ? "" : object.toString());
        					}
	        				
	        				if (!isObjectData())
	        				{
	        					data = label.getText();
	        				}
        				}
        			}
        			catch(Exception ex)
        			{
        				ex.printStackTrace();
        				label.setText("");
        			}
        			
        		} else {
        			label.setText("");
        		}
            }

            public Object getCellEditorValue() {
                return data;
            }
            
            
        };
        btn.addActionListener(delegate);
	}
	
	 /**
     * Specifies the number of clicks needed to start editing.
     *
     * @param count  an int specifying the number of clicks needed to start editing
     * @see #getClickCountToStart
     */
    public void setClickCountToStart(int count) {
        clickCountToStart = count;
    }

    /**
     * Returns the number of clicks needed to start editing.
     * @return the number of clicks needed to start editing
     */
    public int getClickCountToStart() {
        return clickCountToStart;
    }

//
//  Override the implementations of the superclass, forwarding all methods
//  from the CellEditor interface to our delegate.
//

    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * @see EditorDelegate#getCellEditorValue
     */
    public Object getCellEditorValue() {
        return delegate.getCellEditorValue();
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * @see EditorDelegate#isCellEditable(EventObject)
     */
    public boolean isCellEditable(EventObject anEvent) {
        return delegate.isCellEditable(anEvent);
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * @see EditorDelegate#shouldSelectCell(EventObject)
     */
    public boolean shouldSelectCell(EventObject anEvent) {
        return delegate.shouldSelectCell(anEvent);
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * @see EditorDelegate#stopCellEditing
     */
    public boolean stopCellEditing() {
        return delegate.stopCellEditing();
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * @see EditorDelegate#cancelCellEditing
     */
    public void cancelCellEditing() {
        delegate.cancelCellEditing();
    }

//
//  Implementing the TreeCellEditor Interface
//

    /** Implements the <code>TreeCellEditor</code> interface. */
    public Component getTreeCellEditorComponent(JTree tree, Object value,
                                                boolean isSelected,
                                                boolean expanded,
                                                boolean leaf, int row) {
        String         stringValue = tree.convertValueToText(value, isSelected,
                                            expanded, leaf, row, false);

        delegate.setValue(stringValue);
        return editorComponent;
    }

//
//  Implementing the CellEditor Interface
//
    /** Implements the <code>TableCellEditor</code> interface. */
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 int row, int column) {
        delegate.setValue(value);
        return editorComponent;
    }

    /**
     * The protected <code>EditorDelegate</code> class.
     */
    public class EditorDelegate implements ActionListener, ItemListener, Serializable {

        /**
		 * 
		 */
		private static final long serialVersionUID = -4152162985273984109L;
		

		/**  The value of this cell. */
        protected Object value;
        
        /**
         * 是否初始化
         */
        protected boolean init = false;

       /**
        * Returns the value of this cell.
        * @return the value of this cell
        */
        public Object getCellEditorValue() {
            return value;
        }

       /**
        * Sets the value of this cell.
        * @param value the new value of this cell
        */
        public void setValue(Object value) {
            this.value = value;
        }

       /**
        * Returns true if <code>anEvent</code> is <b>not</b> a
        * <code>MouseEvent</code>.  Otherwise, it returns true
        * if the necessary number of clicks have occurred, and
        * returns false otherwise.
        *
        * @param   anEvent         the event
        * @return  true  if cell is ready for editing, false otherwise
        * @see #setClickCountToStart
        * @see #shouldSelectCell
        */
        public boolean isCellEditable(EventObject anEvent) {
            if (anEvent instanceof MouseEvent) {
                return ((MouseEvent)anEvent).getClickCount() >= clickCountToStart;
            }
            return true;
        }

       /**
        * Returns true to indicate that the editing cell may
        * be selected.
        *
        * @param   anEvent         the event
        * @return  true
        * @see #isCellEditable
        */
        public boolean shouldSelectCell(EventObject anEvent) {
            return true;
        }

       /**
        * Returns true to indicate that editing has begun.
        *
        * @param anEvent          the event
        */
        public boolean startCellEditing(EventObject anEvent) {
            return true;
        }

       /**
        * Stops editing and
        * returns true to indicate that editing has stopped.
        * This method calls <code>fireEditingStopped</code>.
        *
        * @return  true
        */
        public boolean stopCellEditing() {
            fireEditingStopped();
            return true;
        }

       /**
        * Cancels editing.  This method calls <code>fireEditingCanceled</code>.
        */
       public void cancelCellEditing() {
           fireEditingCanceled();
       }

       /**
        * When an action is performed, editing is ended.
        * @param e the action event
        * @see #stopCellEditing
        */
        public void actionPerformed(ActionEvent e) {
        	JSTableDialogEditor.this.stopCellEditing();
        }

       /**
        * When an item's state changes, editing is ended.
        * @param e the action event
        * @see #stopCellEditing
        */
        public void itemStateChanged(ItemEvent e) {
        	JSTableDialogEditor.this.stopCellEditing();
        }
    }

    
}
