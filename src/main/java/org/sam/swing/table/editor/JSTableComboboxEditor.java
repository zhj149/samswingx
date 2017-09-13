package org.sam.swing.table.editor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.EventObject;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.TreeCellEditor;

import org.sam.swing.JSComboBox;


public class JSTableComboboxEditor<T, V> extends AbstractCellEditor implements TableCellEditor, TreeCellEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2824493139376461549L;
	
	protected JComponent editorComponent;

	protected EditorDelegate delegate;

	public JSTableComboboxEditor (T[] items, V[] datas) {
		if (items == null || datas == null || items.length != datas.length)
			throw new IllegalArgumentException();
		this.editorComponent = new JSComboBox<>(items, datas);
		delegate = new EditorDelegate() {
			private static final long serialVersionUID = 4156243708866105666L;

			@Override
			public void setValue(Object value) {
				super.setValue(value);
			}

			@SuppressWarnings("unchecked")
			@Override
			public Object getCellEditorValue() {
				return ((JSComboBox<T,V>) editorComponent).getSelectedValue();
			}
		};
		delegate.setClickCountToStart(1);
	}
	
	public JSTableComboboxEditor (Map<T,V> map) {
		
		if (map == null || map.size() <= 0)
			throw new IllegalArgumentException();
		
		Vector<T> items = new Vector<>();
		Vector<V> datas = new Vector<>();
		
		for(Entry<T,V> entry : map.entrySet()){
			items.add(entry.getKey());
			datas.add(entry.getValue());
		}
		
		this.editorComponent = new JSComboBox<>(datas,items);
		delegate = new EditorDelegate() {
			private static final long serialVersionUID = 4156243708866105666L;

			@Override
			public void setValue(Object value) {
				super.setValue(value);
			}

			@SuppressWarnings("unchecked")
			@Override
			public Object getCellEditorValue() {
				return ((JSComboBox<T,V>) editorComponent).getSelectedValue();
			}
		};
		delegate.setClickCountToStart(1);
	}
	
	public Component getComponent() {
		return editorComponent;
	}

	public Object getCellEditorValue() {
		return delegate.getCellEditorValue();
	}

	public boolean isCellEditable(EventObject anEvent) {
		return delegate.isCellEditable(anEvent);
	}

	public boolean shouldSelectCell(EventObject anEvent) {
		return delegate.shouldSelectCell(anEvent);
	}

	public boolean stopCellEditing() {
		// TODO 后期添加是否允许为空
//		if (((JSComboBox) editorComponent).getSelectedValue() == null) {
//			JOptionPane.showMessageDialog(null, "请选择内容");
//			return false;
//		}
		return delegate.stopCellEditing();
	}

	public void cancelCellEditing() {
		delegate.cancelCellEditing();
	}

	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
			boolean leaf, int row) {
		String stringValue = tree.convertValueToText(value, isSelected, expanded, leaf, row, false);

		delegate.setValue(stringValue);
		return editorComponent;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		delegate.setValue(value);
		if (editorComponent instanceof JCheckBox) {
			// in order to avoid a "flashing" effect when clicking a checkbox
			// in a table, it is important for the editor to have as a border
			// the same border that the renderer has, and have as the background
			// the same color as the renderer has. This is primarily only
			// needed for JCheckBox since this editor doesn't fill all the
			// visual space of the table cell, unlike a text field.
			TableCellRenderer renderer = table.getCellRenderer(row, column);
			Component c = renderer.getTableCellRendererComponent(table, value, isSelected, true, row, column);
			if (c != null) {
				editorComponent.setOpaque(true);
				editorComponent.setBackground(c.getBackground());
				if (c instanceof JComponent) {
					editorComponent.setBorder(((JComponent) c).getBorder());
				}
			} else {
				editorComponent.setOpaque(false);
			}
		} else if (editorComponent instanceof JSComboBox) {
			((JSComboBox) editorComponent).setSelectedValue(value);
		}
		return editorComponent;
	}

	//
	// Protected EditorDelegate class
	//

	/**
	 * The protected <code>EditorDelegate</code> class.
	 */
	protected class EditorDelegate extends JSEditorDelegateAdapter  {

		private static final long serialVersionUID = -3542950973231402020L;

		/**
		 * Stops editing and returns true to indicate that editing has stopped.
		 * This method calls <code>fireEditingStopped</code>.
		 *
		 * @return true
		 */
		public boolean stopCellEditing() {
			fireEditingStopped();
			return true;
		}

		/**
		 * Cancels editing. This method calls <code>fireEditingCanceled</code>.
		 */
		public void cancelCellEditing() {
			fireEditingCanceled();
		}

		/**
		 * When an action is performed, editing is ended.
		 * 
		 * @param e
		 *            the action event
		 * @see #stopCellEditing
		 */
		public void actionPerformed(ActionEvent e) {
			this.stopCellEditing();
		}

		/**
		 * When an item's state changes, editing is ended.
		 * 
		 * @param e
		 *            the action event
		 * @see #stopCellEditing
		 */
		public void itemStateChanged(ItemEvent e) {
			this.stopCellEditing();
		}
	}
	

}
