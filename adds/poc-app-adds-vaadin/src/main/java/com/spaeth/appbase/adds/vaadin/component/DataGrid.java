package com.spaeth.appbase.adds.vaadin.component;

import java.util.ArrayList;
import java.util.List;

import com.spaeth.appbase.component.AutoColumnModel;
import com.spaeth.appbase.component.ColumnModel;
import com.spaeth.appbase.component.api.IDataGrid;
import com.spaeth.appbase.component.api.IDataGridColumn;
import com.spaeth.appbase.core.datasource.CollectionDataSource;
import com.spaeth.appbase.core.datasource.DataSource;
import com.vaadin.ui.Table;

public class DataGrid extends VisualCollectionFieldComponent<Table> implements IDataGrid {

	private static final long serialVersionUID = 1L;

	private ColumnModel columnModel;

	@Override
	protected Table createDelegated() {
		columnModel = new AutoColumnModel();
		Table table = new Table();
		table.setSelectable(true);
		table.setImmediate(true);
		return table;
	}

	@Override
	public void setColumnModel(final ColumnModel columnModel) {
		this.columnModel = columnModel;
	}

	@Override
	public ColumnModel getColumnModel() {
		return columnModel;
	}

	@Override
	public void setCollectionDataSource(final CollectionDataSource colDS) {
		super.setCollectionDataSource(colDS);
		onContainerPropertiesUpdated(colDS);
	}

	@Override
	protected void onContainerPropertiesUpdated(final CollectionDataSource dataSource) {
		super.onContainerPropertiesUpdated(dataSource);
		List<IDataGridColumn> columns = columnModel.getColumns(dataSource);

		List<String> visibleColumns = new ArrayList<String>();
		List<String> captions = new ArrayList<String>();

		for (IDataGridColumn c : columns) {
			visibleColumns.add(c.getPropertyName());
		}

		for (IDataGridColumn c : columns) {
			captions.add(c.getCaption());
		}

		getDelegated().setVisibleColumns(visibleColumns.toArray());
		getDelegated().setColumnHeaders(captions.toArray(new String[] {}));
	}

	@Override
	public void setDataSource(final DataSource dataSource) {
		if (this.property.getDataSource() != null) {
			this.property.getDataSource().removeDataSourceValueChangeListener(property);
			this.property.getDataSource().removeDataSourceAccessPolicyChangeListener(property);
		}
		this.property.setDataSource(dataSource);
		if (dataSource != null) {
			this.property.getDataSource().addDataSourceAccessPolicyChangeListener(property);
			this.property.getDataSource().addDataSourceValueChangeListener(property);
		}
	}
	
}
