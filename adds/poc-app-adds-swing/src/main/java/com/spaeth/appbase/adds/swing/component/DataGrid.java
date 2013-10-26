package com.spaeth.appbase.adds.swing.component;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.spaeth.appbase.adds.swing.component.customized.JFieldFrame;
import com.spaeth.appbase.component.AutoColumnModel;
import com.spaeth.appbase.component.ColumnModel;
import com.spaeth.appbase.component.api.IDataGrid;
import com.spaeth.appbase.component.api.IDataGridColumn;
import com.spaeth.appbase.core.datasource.CollectionDataSource;
import com.spaeth.appbase.core.datasource.DataSource;

public class DataGrid extends VisualCollectionFieldComponent<JFieldFrame<JPanel>> implements IDataGrid, TableModel, ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private ColumnModel columnModel;

	private final List<IDataGridColumn> columns = new ArrayList<IDataGridColumn>();

	private final List<Object> showingObjects = new ArrayList<Object>();

	private final List<TableModelListener> tableModelListeners = new ArrayList<TableModelListener>();

	private TableColumnModel tableColumnModel;

	private JTable table;

	public DataGrid() {
		table.setModel(this);
	}

	@Override
	protected JFieldFrame<JPanel> createDelegated() {
		columnModel = new AutoColumnModel();
		table = new JTable();
		table.setColumnModel(tableColumnModel = new DefaultTableColumnModel());

		table.getSelectionModel().addListSelectionListener(this);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(table.getTableHeader(), BorderLayout.NORTH);
		panel.add(new JScrollPane(table), BorderLayout.CENTER);

		return new JFieldFrame<JPanel>(panel);
	}

	@Override
	public void setColumnModel(final ColumnModel columnModel) {
		this.columnModel = columnModel;
		if (getCollectionDataSource() != null) {
			updateTableModel(getCollectionDataSource(), null);
		}
	}

	@Override
	public ColumnModel getColumnModel() {
		return columnModel;
	}

	@Override
	public void setCaption(final String caption) {
		getDelegated().setCaption(caption);
	}

	@Override
	public String getCaption() {
		return getDelegated().getCaption();
	}

	@Override
	public int getRowCount() {
		return showingObjects.size();
	}

	@Override
	public int getColumnCount() {
		return columns.size();
	}

	@Override
	public String getColumnName(final int columnIndex) {
		return columns.get(columnIndex).getCaption();
	}

	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		return getCollectionDataSource().getDataSource(columns.get(columnIndex).getPropertyName()).getClass();
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		DataSource element = getCollectionDataSource().getElement(showingObjects.get(rowIndex));
		if (element == null) {
			return null;
		}
		return element.getDataSource(columns.get(columnIndex).getPropertyName()).get();
	}

	@Override
	public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
	}

	@Override
	public void addTableModelListener(final TableModelListener l) {
		tableModelListeners.add(l);
	}

	@Override
	public void removeTableModelListener(final TableModelListener l) {
		tableModelListeners.remove(l);
	}

	@Override
	public void setCollectionDataSource(final CollectionDataSource collectionDataSource) {
		super.setCollectionDataSource(collectionDataSource);
		if (collectionDataSource != null) {
			updateTableModel(collectionDataSource, null);
		}
	}

	protected void fireTableModelChanged(final TableModelEvent e) {
		for (TableModelListener tml : tableModelListeners) {
			tml.tableChanged(e);
		}
	}

	@Override
	protected void internalOnChange(final DataSourceValueChangedEvent event) {
		DataSource dataSource = event.getSource();
		if (dataSource == getDataSource().getCoreDataSource()) {
			super.internalOnChange(event);
			fireTableModelChanged(new TableModelEvent(this, table.getSelectedRow()));
		} else if (dataSource == getCollectionDataSource().getCoreDataSource()) {
			if (event.getCause() != DataSourceEventCause.COMMIT) {
				updateTableModel(getCollectionDataSource(), null);
			}
		} else {
			fireTableModelChanged(new TableModelEvent(this, table.getSelectedRow()));

		}
	}

	protected void updateTableModel(final DataSource dataSource, final TableModelEvent e) {
		this.columns.clear();
		this.columns.addAll(columnModel.getColumns((CollectionDataSource) dataSource));
		this.showingObjects.clear();
		this.showingObjects.addAll((Collection<?>) getCollectionDataSource().get());

		Enumeration<TableColumn> columns = tableColumnModel.getColumns();
		while (columns.hasMoreElements()) {
			tableColumnModel.removeColumn(columns.nextElement());
		}

		for (IDataGridColumn dgc : this.columns) {
			TableColumn tc = new TableColumn();
			tc.setHeaderValue(dgc.getCaption());
			tableColumnModel.addColumn(tc);
		}

		fireTableModelChanged(e);
	}

	@Override
	public void onAccessPolicyChanged(final AccessPolicyChangeEvent event) {
		System.out.println("access policy changed");
	}

	@Override
	protected void updateView(final Object value) {
		table.getSelectionModel().setLeadSelectionIndex(showingObjects.indexOf(value));
	}

	@Override
	public void valueChanged(final ListSelectionEvent e) {
		int selectedRow = table.getSelectedRow();
		Object selectedObject = null;
		if (selectedRow != -1) {
			selectedObject = showingObjects.get(selectedRow);
		}
		setValue(selectedObject);
	}

}
