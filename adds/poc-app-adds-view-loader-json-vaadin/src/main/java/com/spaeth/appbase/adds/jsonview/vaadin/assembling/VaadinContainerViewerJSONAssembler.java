package com.spaeth.appbase.adds.jsonview.vaadin.assembling;

import org.json.JSONObject;

import com.spaeth.appbase.adds.jsonview.JSONContext;
import com.spaeth.appbase.adds.jsonview.assembling.JSONViewException;
import com.spaeth.appbase.adds.vaadin.component.mediator.VaadinDataSourceContainerAdapter;
import com.spaeth.appbase.core.datasource.CollectionDataSource;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.model.DataSourceHolderViewModel.DataSourceHolderViewModelHelper;
import com.spaeth.appbase.core.view.assembling.AbstractSpecificClassAsssembler;
import com.spaeth.appbase.core.view.assembling.Assembler;
import com.spaeth.appbase.core.view.assembling.Context;
import com.vaadin.data.Container.Viewer;

public class VaadinContainerViewerJSONAssembler extends
		AbstractSpecificClassAsssembler<Viewer, JSONObject, JSONContext> {

	public static final String COLLECTION_DATASOURCE = "collectionDataSource";

	public VaadinContainerViewerJSONAssembler(final Assembler<JSONObject, JSONContext> nextAssembler) {
		super(Viewer.class, nextAssembler);
	}

	@Override
	protected void internalAssemble(final Viewer result, final JSONObject representation,
			final Context<JSONObject> context) {
		if (representation.has(COLLECTION_DATASOURCE)) {
			try {
				DataSource dataSource = DataSourceHolderViewModelHelper.getDataSource(context.getViewModel());
				if (dataSource != null) {
					String dsn = representation.getString(COLLECTION_DATASOURCE);
					DataSource ds = dataSource.getDataSource(dsn.split("\\."));

					if (!(ds instanceof CollectionDataSource)) {
						System.err.println("datasource '" + dsn
								+ "' for container MUST be a collection datasource but is "
								+ ds.getClass().getSimpleName());
						return;
					}

					@SuppressWarnings("unchecked")
					final VaadinDataSourceContainerAdapter container = new VaadinDataSourceContainerAdapter(
							(CollectionDataSource<DataSource>) ds);
					result.setContainerDataSource(container);
				}
			} catch (final Exception e) {
				throw new JSONViewException(e);
			}
		}
	}

}
