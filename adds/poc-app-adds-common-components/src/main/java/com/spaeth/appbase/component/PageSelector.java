package com.spaeth.appbase.component;

import java.util.LinkedHashSet;
import java.util.Set;

import com.spaeth.appbase.adds.datasource.bean.builder.BeanDataSourceBuilder;
import com.spaeth.appbase.adds.datasource.bean.builder.BeanSetDataSourceBuilder;
import com.spaeth.appbase.component.api.IPageSelector;
import com.spaeth.appbase.core.datasource.CollectionDataSource;
import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.datasource.decorators.PagedDataSourceDecorator;
import com.spaeth.appbase.core.datasource.event.ValueChangeListener;

public class PageSelector extends VisualCompositeComponent<ComboBox> implements IPageSelector {

	private static final long serialVersionUID = 1L;

	private long pagesCount = 0;
	private long currentPage = 0;

	private final CollectionDataSource pagesDataSource = createPagesDataSource();
	private final DataSource valueDataSource = createValueDataSource();
	private PagedDataSourceDecorator pagedDataSource;

	private final ValueChangeListener pagedDataSourceValueChangeListener = new ValueChangeListener() {
		@Override
		public void onChange(final DataSourceValueChangedEvent event) {
			update();
		}
	};

	private final ComboBox pageSelection = new ComboBox();

	public PageSelector() {
		this(null);
	}

	public PageSelector(final String caption) {
		pageSelection.setCollectionDataSource(pagesDataSource);
		pageSelection.setDataSource(valueDataSource);
		pageSelection.setCaption(caption);
		initialize(pageSelection);
	}

	private DataSource createValueDataSource() {
		return new BeanDataSourceBuilder() //
				.setType(Page.class) //
				.addValueChangeListener(new ValueChangeListener() {
					@Override
					public void onChange(final DataSourceValueChangedEvent event) {
						Object selected = event.getNewValue();

						if (selected == null) {
							return;
						}

						Page selectedPage = (Page) selected;

						if (pagedDataSource.getPage() != selectedPage.page) {
							pagedDataSource.goToPage(selectedPage.getPage());
						}
					}
				}) //
				.build();
	}

	private CollectionDataSource createPagesDataSource() {
		return (CollectionDataSource) new BeanSetDataSourceBuilder() //
				.setType(Page.class) //
				.addDataSource(new BeanDataSourceBuilder("caption")) //
				.build();
	}

	public long getPages() {
		return this.pagesCount;
	}

	public long getCurrentPage() {
		return this.currentPage;
	}

	public long getSelectedPage() {
		try {
			return ((Page) (this.pageSelection.getValue())).getPage();
		} catch (final Exception e) {
			return 0;
		}
	}

	@Override
	public void setPagedDataSource(final PagedDataSourceDecorator pagedDataSource) {
		if (this.pagedDataSource != null) {
			this.pagedDataSource.removeDataSourceValueChangeListener(pagedDataSourceValueChangeListener);
		}
		this.pagedDataSource = pagedDataSource;
		if (this.pagedDataSource != null) {
			this.pagedDataSource.addDataSourceValueChangeListener(pagedDataSourceValueChangeListener);
		}
		update();
	}

	private void update() {

		Page selectedPage = (Page) (valueDataSource.get());

		int selectedPageInt = 0;
		if (selectedPage != null) {
			selectedPageInt = selectedPage.getPage();
		}

		boolean pageChanged = !String.valueOf(this.currentPage).equals(String.valueOf(selectedPageInt));
		boolean pageCountChanged = pagedDataSource.getPageCount() != this.pagesCount;

		if (!pageChanged && !pageCountChanged) {
			return;
		}

		this.currentPage = pagedDataSource.getPage();
		this.pagesCount = pagedDataSource.getPageCount();

		Page selected = null;
		Set<Page> pages = new LinkedHashSet<Page>();
		for (int i = 0; i < this.pagesCount; i++) {
			Page page = new Page(i, String.valueOf(i + 1));
			if (this.currentPage == i || selected == null) {
				selected = page;
			}
			pages.add(page);
		}

		if (pageCountChanged) {
			pagesDataSource.reset(pages);
		}

		if (pageCountChanged || pageChanged || valueDataSource.getModel() == null) {
			valueDataSource.removeDataSourceValueChangeListener(pagedDataSourceValueChangeListener);
			valueDataSource.reset(selected);
			valueDataSource.addDataSourceValueChangeListener(pagedDataSourceValueChangeListener);
		}
	}

	public void setCaption(final String caption) {
		pageSelection.setCaption(caption);
	}

	public String getCaption() {
		return pageSelection.getCaption();
	}

	public static class Page {
		private final int page;
		private final String caption;

		public Page(final int page, final String caption) {
			super();
			this.page = page;
			this.caption = caption;
		}

		public int getPage() {
			return page;
		}

		public String getCaption() {
			return caption;
		}

		@Override
		public String toString() {
			return caption;
		}
	}

}