package com.spaeth.appbase.core.datasource.decorators;

public interface DataSourceContentProvider<ProvidedType, KeyType> {

	@SuppressWarnings("rawtypes")
	public static final DataSourceContentProvider DUMMY = new DataSourceContentProvider() {

		@Override
		public Object load(final Object key) {
			return null;
		}

		@Override
		public Object store(final Object key, final Object content) {
			return null;
		}

	};

	ProvidedType load(KeyType key);

	KeyType store(KeyType key, ProvidedType content);

}
