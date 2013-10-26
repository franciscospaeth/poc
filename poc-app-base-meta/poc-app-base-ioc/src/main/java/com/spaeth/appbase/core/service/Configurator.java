package com.spaeth.appbase.core.service;

public interface Configurator {

	public static enum ConfigurationType {
		UNORDERED, ORDERED, MAPPED;
	}

	public static interface ConfiguratorTag<T> {
	}

	public static interface UnorderedConfiguration<T> extends ConfiguratorTag<T> {

		void add(T clazz);

		void addInstance(Class<? extends T> instance);

	}

	public static interface OrderedConfiguration<T> extends ConfiguratorTag<T> {

		void add(String id, T object, String... constraints);

		void override(String id, T object, String... constraints);

		void addInstance(String id, Class<? extends T> clazz, String... constraints);

		void overrideInstance(String id, Class<? extends T> clazz, String... constraints);

	}

	public static interface MappedConfiguration<K, V> extends ConfiguratorTag<V> {

		void add(K key, V value);

		void override(K key, V value);

		void addInstance(K key, Class<? extends V> clazz);

		void overrideInstance(K key, Class<? extends V> clazz);

	}

}