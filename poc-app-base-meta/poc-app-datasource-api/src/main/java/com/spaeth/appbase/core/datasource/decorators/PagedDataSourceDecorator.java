package com.spaeth.appbase.core.datasource.decorators;

import com.spaeth.appbase.core.datasource.CollectionDataSource;
import com.spaeth.appbase.core.datasource.builder.DataSourceBuilder;
import com.spaeth.appbase.core.datasource.decorators.builder.PagedDataSourceDecoratorBuilder;

public class PagedDataSourceDecorator extends AbstractCollectionDataSourceDecorator {

	private final PagedDataSourceController<?> pageController;
	private int page = -1;
	private int pageCount = 0;
	private int total = 0;
	private final int pageSize;

	public PagedDataSourceDecorator(final PagedDataSourceController<?> pageController, final CollectionDataSource decorated,
			final int pageSize) {
		super(decorated);
		this.pageSize = pageSize;
		this.pageController = pageController;
	}

	public void refresh() {
		loadExactPage(page);
	}

	public void nextPage() {
		if (page < pageCount) {
			loadExactPage(page++);
		}
	}

	public void priorPage() {
		if (page > 0) {
			loadExactPage(page++);
		}
	}

	public void goToPage(final int page) {
		loadExactPage(page);
	}

	public void goToPage(final Object element) {
		if (element == null) {
			throw new IllegalArgumentException("element provided is null");
		}
		loadReferencedPage(element);
	}

	public void goToPage(final int page, final Object element) {
		if (element != null) {
			goToPage(element);
		} else {
			goToPage(page);
		}
	}

	public int getPage() {
		return page;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getTotal() {
		return total;
	}

	public int getPageSize() {
		return pageSize;
	}

	protected void loadExactPage(final int page) {
		loadPage(pageController.createPageRequest(page, pageSize));
	}

	@SuppressWarnings("unchecked")
	protected void loadReferencedPage(final Object element) {
		loadPage(((PagedDataSourceController<Object>) pageController).createPageRequest(element, pageSize));
	}

	protected void loadPage(final PageRequest pageRequest) {
		PageResponse<?> pageResponse = pageController.load(pageRequest);
		this.page = pageResponse.getPage();
		this.pageCount = Double.valueOf(Math.ceil(pageResponse.getTotal() * 1.0 / pageResponse.getPageSize())).intValue();
		this.total = pageResponse.getTotal();
		reset(pageResponse.getResult());
	}

	@Override
	protected DataSourceBuilder<?> decorate(final DataSourceBuilder<?> builder) {
		return builder.addDecorator(new PagedDataSourceDecoratorBuilder());
	}

}