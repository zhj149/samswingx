package org.sam.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

/**
 * 单选按钮组控件
 * 
 * @author sam
 *
 * @param <V>
 *            绑定值
 * @param <T>
 *            显示值
 */
public class JSRadioButtonGroup<V, T> extends JPanel {

	private static final long serialVersionUID = 3257285842266567986L;

	/**
	 * 当前绑定组的对象
	 */
	private ButtonGroup buttonGroup;

	/**
	 * 当前绑定的数据对象
	 */
	private final Map<V, T> values = new LinkedHashMap<>();

	/**
	 * 选中操作
	 */
	private ActionSelectionListener actionHandler;

	/**
	 * Create a default JXRadioGroup with a default layout axis of
	 * {@link BoxLayout#X_AXIS}.
	 */
	public JSRadioButtonGroup() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		buttonGroup = new ButtonGroup();
	}

	/**
	 * Create a default JXRadioGroup with a default layout axis of
	 * {@link BoxLayout#X_AXIS}.
	 * 
	 * @param radioValues
	 *            the list of values used to create the group.
	 */
	public JSRadioButtonGroup(Map<V, T> radioValues) {
		this();
		for (Entry<V, T> entry : radioValues.entrySet()) {
			add(entry);
		}
	}

	/**
	 * 默认的选中对象
	 * 
	 * @param radioValues
	 *            数据列表
	 * @param selectValue
	 *            选中的数据
	 */
	public JSRadioButtonGroup(Map<V, T> radioValues, V selectValue) {
		this();
		for (Entry<V, T> entry : radioValues.entrySet()) {
			add(entry);
		}
		this.setSelectedValue(selectValue);
	}

	/**
	 * Convenience factory method. Reduces code clutter when dealing with
	 * generics.
	 * 
	 * @param radioValues
	 *            the map of values used to create the group.
	 */
	public static <V, T> JSRadioButtonGroup<V, T> create(Map<V, T> radioValues) {
		return new JSRadioButtonGroup<V, T>(radioValues);
	}

	/**
	 * Set the layout axis of the radio group.
	 * 
	 * @param axis
	 *            values from {@link BoxLayout}.
	 */
	public void setLayoutAxis(int axis) {
		setLayout(new BoxLayout(this, axis));
	}

	/**
	 * Sets the values backing this group. This replaces the current set of
	 * values with the new set.
	 * 
	 * @param radioValues
	 *            the new backing values for this group
	 */
	public void setValues(Map<V, T> radioValues) {
		clearAll();
		for (Entry<V, T> entry : radioValues.entrySet()) {
			add(entry);
		}
	}

	/**
	 * clear all item
	 */
	private void clearAll() {
		values.clear();
		buttonGroup = new ButtonGroup();
		// remove all the child components
		removeAll();
	}

	/**
	 * 增加一个数据项到系统中的方法
	 * 
	 * @param entry
	 */
	public void add(Entry<V, T> entry) {

		if (entry == null)
			throw new IllegalArgumentException("key-value is not can be null");

		if (entry.getKey() instanceof AbstractButton) {
			values.put(entry.getKey(), entry.getValue());
			addButton((AbstractButton) entry.getKey());
		} else {
			values.put(entry.getKey(), entry.getValue());
			addButton(new JSRadioButton("" + entry.getValue(), entry.getKey()));
		}
	}

	/**
	 * 增加一个单选按钮的方法
	 * 
	 * @param button
	 *            按钮
	 */
	private void addButton(AbstractButton button) {
		buttonGroup.add(button);
		super.add(button);
		if (actionHandler == null) {
			actionHandler = new ActionSelectionListener();
		}
		button.setBackground(null); // 设置了这个属性，背景色就会变成透明，不解
		button.addActionListener(actionHandler);
		button.addItemListener(actionHandler);
	}

	/**
	 * 消息通知
	 * 
	 * @author sam
	 *
	 */
	private class ActionSelectionListener implements ActionListener, ItemListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			fireActionEvent(e);
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			fireActionEvent(null);
		}
	}

	/**
	 * Gets the currently selected button.
	 * 
	 * @return the currently selected button
	 * @see #getSelectedValue()
	 */
	public AbstractButton getSelectedButton() {
		final ButtonModel selectedModel = buttonGroup.getSelection();
		final AbstractButton children[] = getButtonComponents();
		for (int i = 0; i < children.length; i++) {
			AbstractButton button = children[i];
			if (button.getModel() == selectedModel) {
				return button;
			}
		}
		return null;
	}

	/**
	 * 获取按钮列表，这个方法好脱裤子放屁
	 * 
	 * @return
	 */
	private AbstractButton[] getButtonComponents() {
		final Component[] children = getComponents();
		final List<AbstractButton> buttons = new ArrayList<AbstractButton>();
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof AbstractButton) {
				buttons.add((AbstractButton) children[i]);
			}
		}
		return buttons.toArray(new AbstractButton[buttons.size()]);
	}

	/**
	 * 获取选中的索引
	 * 
	 * @return
	 */
	public int getSelectedIndex() {
		final ButtonModel selectedModel = buttonGroup.getSelection();
		final Component children[] = getButtonComponents();
		for (int i = 0; i < children.length; i++) {
			AbstractButton button = (AbstractButton) children[i];
			if (button.getModel() == selectedModel) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * The currently selected value.
	 * 
	 * @return the current value
	 */
	@SuppressWarnings("unchecked")
	public V getSelectedValue() {
		final AbstractButton selectedButton = getSelectedButton();
		if (selectedButton instanceof JSRadioButton)
			return (V) ((JSRadioButton) selectedButton).getTag();

		// 这里有个bug 要fix
		return null;
	}

	/**
	 * Selects the supplied value.
	 * 
	 * @param value
	 *            the value to select
	 */
	public void setSelectedValue(V value) {

		AbstractButton[] buttons = getButtonComponents();
		for (AbstractButton button : buttons) {
			if (button instanceof JSRadioButton) {
				JSRadioButton b = (JSRadioButton) button;
				if ((value == null && b.getTag() == null)
						|| (value != null && (value == b.getTag() || value.equals(b.getTag()))))
					button.setSelected(true);
			} else {
				if (button.getText().equals(value))
					button.setSelected(true);
			}
		}

	}

	/**
	 * Retrieve the child button by index.
	 */
	public AbstractButton getChildButton(int index) {
		return getButtonComponents()[index];
	}

	/**
	 * Retrieve the child button that represents this value.
	 */
	public AbstractButton getChildButton(V value) {
		T t = values.get(value);

		AbstractButton[] buttons = getButtonComponents();
		for (AbstractButton button : buttons) {
			if (button.getText().equals(t))
				return button;
		}
		return null;
	}

	/**
	 * Get the number of child buttons.
	 */
	public int getChildButtonCount() {
		return getButtonComponents().length;
	}

	/**
	 * Adds an <code>ActionListener</code>.
	 * <p>
	 * The <code>ActionListener</code> will receive an <code>ActionEvent</code>
	 * when a selection has been made.
	 *
	 * @param l
	 *            the <code>ActionListener</code> that is to be notified
	 * @see #setSelectedValue(Object)
	 */
	public void addActionListener(ActionListener l) {
		listenerList.add(ActionListener.class, l);
	}

	/**
	 * Removes an <code>ActionListener</code>.
	 * 
	 * @param l
	 *            the <code>ActionListener</code> to remove
	 */
	public void removeActionListener(ActionListener l) {
		listenerList.remove(ActionListener.class, l);
	}

	/**
	 * Returns an array of all the <code>ActionListener</code>s added to this
	 * JRadioGroup with addActionListener().
	 *
	 * @return all of the <code>ActionListener</code>s added or an empty array
	 *         if no listeners have been added
	 */
	public ActionListener[] getActionListeners() {
		return listenerList.getListeners(ActionListener.class);
	}

	/**
	 * Notifies all listeners that have registered interest for notification on
	 * this event type.
	 * 
	 * @param e
	 *            the event to pass to the listeners
	 * @see EventListenerList
	 */
	protected void fireActionEvent(ActionEvent e) {
		for (ActionListener l : getActionListeners()) {
			l.actionPerformed(e);
		}
	}

	/**
	 * Enable/disable all of the child buttons
	 * 
	 * @see JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		for (Enumeration<AbstractButton> en = buttonGroup.getElements(); en.hasMoreElements();) {
			final AbstractButton button = en.nextElement();
			/*
			 * We don't want to enable a button where the action does not permit
			 * it.
			 */
			if (enabled && button.getAction() != null && !button.getAction().isEnabled()) {
				// do nothing
			} else {
				button.setEnabled(enabled);
			}
		}
	}

}
