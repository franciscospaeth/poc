package com.spaeth.appbase.component;

import com.spaeth.appbase.component.api.IPageStatus;
import com.spaeth.appbase.core.datasource.decorators.PagedDataSourceDecorator;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener;

public class PageStatus extends VisualCompositeComponent<StaticLabel> implements ValueChangeListener,
		IPageStatus {

	private static final long serialVersionUID = 1L;

	private long total = 0;
	private long pagesCount = 0;
	private long currentPage = 0;
	private long currentCount = 0;

	private PagedDataSourceDecorator pagedDataSource;

	private String textFormat = "showing %d records from %d, (page %d of %d)";

	public PageStatus() {
		initialize(new StaticLabel());
		synchronizeComponents();
	}

	protected String getPageDisplayString() {
		return String.format(textFormat, this.currentCount, this.total, this.pagesCount > 0 ? this.currentPage + 1 : 0,
				this.pagesCount);
	}

	private void synchronizeComponents() {
		getRoot().setText(getPageDisplayString());
	}

	public long getTotal() {
		return total;
	}

	public long getPages() {
		return this.pagesCount;
	}

	public long getCurrentPage() {
		return this.currentPage;
	}

	public long getCurrentCount() {
		return this.currentCount;
	}

	@Override
	public void setPagedDataSource(final PagedDataSourceDecorator pagedDataSource) {
		if (this.pagedDataSource != null) {
			this.pagedDataSource.removeDataSourceValueChangeListener(this);
		}
		this.pagedDataSource = pagedDataSource;
		if (this.pagedDataSource != null) {
			this.pagedDataSource.addDataSourceValueChangeListener(this);
		}
		update();
	}

	public void setTextFormat(final String textFormat) {
		this.textFormat = textFormat;
		synchronizeComponents();
	}

	@Override
	public void onChange(final DataSourceValueChangedEvent event) {
		update();
	}

	private void update() {
		this.currentCount = pagedDataSource.size();
		this.currentPage = pagedDataSource.getPage();
		this.pagesCount = pagedDataSource.getPageCount();
		this.total = pagedDataSource.getTotal();
		synchronizeComponents();
	}

}