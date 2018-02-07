package org.sam.swing;

import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JToolBar;

/**
 * 扩展的工具条
 * @author sam
 *
 */
public class JSToolBar extends JToolBar implements JSToolBarBuilder {

	private static final long serialVersionUID = 6158188823161534815L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addActionListener(String image, String text, String toolTip, ActionListener listener) {
		this.add(this.buildButton(image, text, toolTip, listener));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAction(Action action) {
		this.add(action);
	}

}
