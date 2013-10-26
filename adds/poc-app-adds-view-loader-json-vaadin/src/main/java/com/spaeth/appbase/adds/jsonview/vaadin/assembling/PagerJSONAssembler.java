package com.spaeth.appbase.adds.jsonview.vaadin.assembling;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.spaeth.appbase.core.model.DataSourceHolderViewModel.DataSourceHolderViewModelHelper.getDataSource;

import com.spaeth.appbase.adds.jsonview.JSONContext;
import com.spaeth.appbase.adds.jsonview.assembling.JSONViewException;
import com.spaeth.appbase.adds.vaadin.component.Pager;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.decorators.PagedDataSourceDecorator;
import com.spaeth.appbase.core.view.assembling.AbstractSpecificClassAsssembler;
import com.spaeth.appbase.core.view.assembling.Assembler;
import com.spaeth.appbase.core.view.assembling.Context;

public class PagerJSONAssembler extends AbstractSpecificClassAsssembler<Pager, JSONObject, JSONContext> {

	private static final Logger logger = LoggerFactory.getLogger(VaadinButtonJSONAssembler.class);

	public PagerJSONAssembler(Class<Pager> assemblingClass, Assembler<JSONObject, JSONContext> nextAssembler) {
		super(assemblingClass, nextAssembler);
	}

	@Override
	protected void internalAssemble(Pager result, JSONObject representation, Context<JSONObject> context) {
		DataSource ds = getDataSource(context.getViewModel());

		if (ds == null) {
			return;
		}

		String dsName = null;
		try {
			dsName = representation.getString("dataSource");
		} catch (JSONException e) {
			throw new JSONViewException(e);
		}

		if (dsName == null) {
			logger.error("pager component needs to present a dataSource");
		}

		ds = ds.getDataSource(dsName.split("\\."));

		if (!(ds instanceof PagedDataSourceDecorator)) {
			logger.error("not able to use {} as pagedDataSource, did you forget to decorate it?", dsName);
			return;
		}

		result.setPagedDataSource((PagedDataSourceDecorator<?>) ds);
	}

}
