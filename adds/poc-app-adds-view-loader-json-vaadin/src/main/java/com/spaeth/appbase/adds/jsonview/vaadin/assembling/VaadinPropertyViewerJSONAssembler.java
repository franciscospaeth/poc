package com.spaeth.appbase.adds.jsonview.vaadin.assembling;

import static com.spaeth.appbase.core.model.DataSourceHolderViewModel.DataSourceHolderViewModelHelper.getDataSource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spaeth.appbase.adds.jsonview.JSONContext;
import com.spaeth.appbase.adds.jsonview.assembling.JSONViewException;
import com.spaeth.appbase.adds.vaadin.component.mediator.VaadinDataSourcePropertyAdapter;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.view.assembling.AbstractSpecificClassAsssembler;
import com.spaeth.appbase.core.view.assembling.Assembler;
import com.spaeth.appbase.core.view.assembling.Context;
import com.vaadin.data.Property.Viewer;

public class VaadinPropertyViewerJSONAssembler extends AbstractSpecificClassAsssembler<Viewer, JSONObject, JSONContext> {

	public static final String PROPERTY_DATASOURCE = "propertyDataSource";
	public Logger logger = LoggerFactory.getLogger(this.getClass());

	public VaadinPropertyViewerJSONAssembler(final Assembler<JSONObject, JSONContext> nextAssembler) {
		super(Viewer.class, nextAssembler);
	}

	@Override
	protected void internalAssemble(final Viewer result, final JSONObject representation,
			final Context<JSONObject> context) {

		if (representation.has(PROPERTY_DATASOURCE)) {
			try {
				final String propertyName = representation.getString(PROPERTY_DATASOURCE);
				final DataSource dataSource;
				if ((context.getViewModel() == null) || ((dataSource = getDataSource(context.getViewModel())) == null)) {
					this.logger.warn("No datasource set within " + context.getViewModel() + ", not binding property "
							+ propertyName);
					return;
				}
				DataSource dataSourceProperty = null;
				try {
					dataSourceProperty = dataSource.getDataSource(propertyName.split("\\."));
				} catch (final Exception e) {
					this.logger.warn("exception trying to get property named " + propertyName + " from datasource "
							+ dataSource);
				}

				if (dataSourceProperty == null) {
					return;
				}

				result.setPropertyDataSource(new VaadinDataSourcePropertyAdapter(dataSourceProperty));
			} catch (final Exception e) {
				throw new JSONViewException(e);
			}
		}
	}

}