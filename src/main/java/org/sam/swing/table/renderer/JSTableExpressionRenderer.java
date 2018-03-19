package org.sam.swing.table.renderer;

import javax.swing.JTable;

/**
 * 表达式渲染器
 * 
 * @author sam
 *
 */
public class JSTableExpressionRenderer extends JSTableDefaultCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8993416282846463368L;

	/**
	 * 当前的el的表达式
	 */
	private Expression el;

	/**
	 * 当前的el表达式
	 * 
	 * @return
	 */
	public Expression getEl() {
		return el;
	}

	/**
	 * 当前的el表达式
	 * 
	 * @param el
	 */
	public void setEl(Expression el) {
		this.el = el;
	}

	/**
	 * 使用el表达式的renderer
	 * 
	 * @param el
	 */
	public JSTableExpressionRenderer(Expression el) {
		super();
		this.el = el;
	}

	/**
	 * 设置显示值
	 */
	@Override
	protected void setValue(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (this.el != null) {
			setText(el.getText(table, value, isSelected, hasFocus, row, column));
		} else {
			setText((value == null) ? "" : value.toString());
		}
	}

	/**
	 * 获取值
	 * 
	 * @author sam
	 *
	 */
	public interface Expression {
		public String getText(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column);
	}
}
