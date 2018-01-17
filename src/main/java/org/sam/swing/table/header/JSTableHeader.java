package org.sam.swing.table.header;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.JComponent;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXTableHeader;
import org.jdesktop.swingx.sort.SortController;

/**
 * 扩展的tableheader
 * 
 * @author sam
 *
 */
public class JSTableHeader extends JXTableHeader {

	private static final long serialVersionUID = 4792009483524146357L;

	/**
	 * {@inheritDoc} 使用默认设置构造的header对象
	 */
	public JSTableHeader() {
		super();
	}

	/**
	 * {@inheritDoc} 使用创建的tablecolumnmodel
	 * 
	 * @param columnModel
	 */
	public JSTableHeader(TableColumnModel columnModel) {
		super(columnModel);
	}

	private MouseInputListener headerListener;

	/**
	 * 初始化表头对象
	 */
	@Override
	protected void installHeaderListener() {
		if (headerListener == null) {
			headerListener = new HeaderListener();
			addMouseListener(headerListener);
			addMouseMotionListener(headerListener);
		}
	}

	/**
	 * Returns the (visible) view index for the table column or -1 if not
	 * visible or not contained in this header's columnModel.
	 * 
	 * 
	 * @param aColumn
	 *            the TableColumn to find the view index for
	 * @return the view index of the given table column or -1 if not visible or
	 *         not contained in the column model.
	 */
	private int getViewIndexForColumn(TableColumn aColumn) {
		if (aColumn == null)
			return -1;
		TableColumnModel cm = getColumnModel();
		for (int column = 0; column < cm.getColumnCount(); column++) {
			if (cm.getColumn(column) == aColumn) {
				return column;
			}
		}
		return -1;
	}
	
	/**
	 * 重写的header鼠标事件适配对象，主要是为了加入checkbox扩展
	 * @author sam
	 *
	 */
	protected class HeaderListener implements MouseInputListener, Serializable {
		
		private static final long serialVersionUID = -9004150932514765105L;
		private TableColumn cachedResizingColumn;
		private SortOrder[] cachedSortOrderCycle;
		private int sortColumn = -1;

		/**
		 * Packs column on double click in resize region. Resorts column on
		 * double click if enabled and not in resize region.
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if (shouldIgnore(e)) {
				return;
			}
			int colIndex = columnAtPoint(e.getPoint());
			if (colIndex < 0)
				return;
			
			TableColumn column = columnModel.getColumn(colIndex);
			TableCellRenderer renderer = column.getHeaderRenderer();
			if (renderer == null || renderer instanceof DefaultTableCellRenderer){
				doResize(e);
				doDoubleSort(e);
				uncacheResizingColumn();
			}
			else {
				//自定义的cellrender，然后我们弄一下如何做操作
				JComponent component = (JComponent)renderer.getTableCellRendererComponent(
                        getTable(), column.getHeaderValue(), false, false,
                        -1, colIndex);
				
				MouseListener[] mouseListeners = component.getMouseListeners();
				if (mouseListeners != null && mouseListeners.length > 0){
					for(MouseListener l : mouseListeners){
						l.mouseClicked(e);
					}
				}
			}
			
		}

		private void doDoubleSort(MouseEvent e) {
			if (!hasCachedSortColumn() || e.getClickCount() % 2 == 1)
				return;
			getXTable().toggleSortOrder(sortColumn);
			uncacheSortColumn();
		}

		private boolean hasCachedSortColumn() {
			return sortColumn >= 0;
		}

		/**
		 * Resets sort enablement always, set resizing marker if available.
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			resetToggleSortOrder(e);
			if (shouldIgnore(e)) {
				return;
			}
			cacheResizingColumn(e);
		}

		/**
		 * Sets resizing marker if available, disables table sorting if in
		 * resize region and sort gesture (aka: single click).
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
			if (shouldIgnore(e)) {
				return;
			}
			cacheResizingColumn(e);
			cacheSortColumn(e);
			if (isInResizeRegion(e) && e.getClickCount() % 2 == 1) {
				disableToggleSortOrder(e);
			}
		}

		private void cacheSortColumn(MouseEvent e) {
			if (!canCacheSortColumn(e))
				uncacheSortColumn();
			if (e.getClickCount() % 2 == 1) {
				int column = columnAtPoint(e.getPoint());
				if (column >= 0) {
					int primarySortIndex = getXTable().getSortedColumnIndex();
					if (primarySortIndex == column) {
						column = -1;
					}
				}
				sortColumn = column;
			}

		}

		private void uncacheSortColumn() {
			sortColumn = -1;
		}

		private boolean canCacheSortColumn(MouseEvent e) {
			if (hasSortController() && !isInResizeRegion(e) && getResortsOnDoubleClick()) {
				return true;
			}
			return false;
		}

		/**
		 * Returns a boolean indication if the mouse event should be ignored.
		 * Here: returns true if table not enabled or not an event from the left
		 * mouse button.
		 * 
		 * @param e
		 * @return
		 */
		private boolean shouldIgnore(MouseEvent e) {
			return !SwingUtilities.isLeftMouseButton(e) || !table.isEnabled();
		}

		/**
		 * Packs caches resizing column on double click, if available. Does
		 * nothing otherwise.
		 * 
		 * @param e
		 */
		private void doResize(MouseEvent e) {
			if (e.getClickCount() != 2)
				return;
			int column = getViewIndexForColumn(cachedResizingColumn);
			if (column >= 0) {
				(getXTable()).packColumn(column, 5);
			}
		}

		/**
		 * 
		 * @param e
		 */
		private void disableToggleSortOrder(MouseEvent e) {
			if (!hasSortController())
				return;
			SortController<?> controller = (SortController<?>) getXTable().getRowSorter();
			cachedSortOrderCycle = controller.getSortOrderCycle();
			controller.setSortOrderCycle();
		}

		/**
		 * @return
		 */
		private boolean hasSortController() {
			return (getXTable().getRowSorter() instanceof SortController<?>);
		}

		/**
		 * 
		 */
		private void resetToggleSortOrder(MouseEvent e) {
			if (cachedSortOrderCycle == null)
				return;
			((SortController<?>) getXTable().getRowSorter()).setSortOrderCycle(cachedSortOrderCycle);
			cachedSortOrderCycle = null;
		}

		/**
		 * Caches the resizing column if set. Does nothing if null.
		 * 
		 * @param e
		 */
		private void cacheResizingColumn(MouseEvent e) {
			TableColumn column = getResizingColumn();
			if (column != null) {
				cachedResizingColumn = column;
			}
		}

		/**
		 * Sets the cached resizing column to null.
		 */
		private void uncacheResizingColumn() {
			cachedResizingColumn = null;
		}

		/**
		 * Returns true if the mouseEvent happened in the resizing region.
		 * 
		 * @param e
		 * @return
		 */
		private boolean isInResizeRegion(MouseEvent e) {
			return cachedResizingColumn != null; // inResize;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		/**
		 * Resets all cached state.
		 */
		@Override
		public void mouseExited(MouseEvent e) {
			uncacheSortColumn();
			uncacheResizingColumn();
			resetToggleSortOrder(e);
		}

		/**
		 * Resets all cached state.
		 */
		@Override
		public void mouseDragged(MouseEvent e) {
			uncacheSortColumn();
			uncacheResizingColumn();
			resetToggleSortOrder(e);
		}

		/**
		 * Resets all cached state.
		 */
		@Override
		public void mouseMoved(MouseEvent e) {
			uncacheSortColumn();
			resetToggleSortOrder(e);
		}
	}
}
