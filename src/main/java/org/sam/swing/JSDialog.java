package org.sam.swing;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import javax.swing.JDialog;

public class JSDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7236196432243929113L;

	//begin constructor
	
	/**
	 * {@inheritDoc}
	 */
	public JSDialog() {
		this((Frame) null, false);
	}

	/**
	 * {@inheritDoc}
	 */
	public JSDialog(Frame owner) {
		this(owner, false);
	}

	/**
	 * {@inheritDoc}
	 */
	public JSDialog(Frame owner, boolean modal) {
		this(owner, "", modal);
	}

	/**
	 * {@inheritDoc}
	 */
	public JSDialog(Frame owner, String title) {
		this(owner, title, false);
	}

	/**
	 * {@inheritDoc}
	 */
	public JSDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
	}

	/**
	 * {@inheritDoc}
	 */
	public JSDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
	}

	/**
	 * {@inheritDoc}
	 */
	public JSDialog(Dialog owner) {
		this(owner, false);
	}

	/**
	 * {@inheritDoc}
	 */
	public JSDialog(Dialog owner, boolean modal) {
		this(owner, "", modal);
	}

	/**
	 * {@inheritDoc}
	 */
	public JSDialog(Dialog owner, String title) {
		this(owner, title, false);
	}

	/**
	 * {@inheritDoc}
	 */
	public JSDialog(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
	}

	/**
	 * {@inheritDoc}
	 */
	public JSDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
	}

	/**
	 * {@inheritDoc}
	 */
	public JSDialog(Window owner) {
		this(owner, Dialog.ModalityType.MODELESS);
	}

	/**
	 * {@inheritDoc}
	 */
	public JSDialog(Window owner, ModalityType modalityType) {
		this(owner, "", modalityType);
	}

	/**
	 * {@inheritDoc}
	 */
	public JSDialog(Window owner, String title) {
		this(owner, title, Dialog.ModalityType.MODELESS);
	}

	/**
	 * {@inheritDoc}
	 */
	public JSDialog(Window owner, String title, Dialog.ModalityType modalityType) {
		super(owner, title, modalityType);
	}

	/**
	 * {@inheritDoc}
	 */
	public JSDialog(Window owner, String title, Dialog.ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
	}
	
	//end
	
	//begin property
	
	/**
	 * ????????????
	 */
	public static final String OK = "ok";
	
	/**
	 * ????????????
	 */
	public static final String CANCEL = "cancel";
	
	/**
	 * ?????????????????????
	 */
	private String action = CANCEL;

	/**
	 * ?????????????????????
	 * @return
	 */
	public String getAction() {
		return action;
	}

	/**
	 * ?????????????????????
	 * @param action
	 */
	public void setAction(String action) {
		this.action = action;
	}
	
	//end 
}
