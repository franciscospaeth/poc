package com.spaeth.appbase.core.datasource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Descriptor object for a DataSource.
 * 
 * @author Francisco Spaeth (francisco.spaeth@gmail.com)
 * 
 */
public class DataSourceMeta {

	private List<DataSourceMeta> childrenMeta = new ArrayList<DataSourceMeta>();
	private final String name;
	private final Class<?> type;
	private final ServingDirective servingDirective;
	private boolean initialized = false;

	public DataSourceMeta(final String name, final Class<?> type) {
		super();
		this.name = name;
		this.type = type;
		this.servingDirective = ServingDirective.VALUE;
	}

	public DataSourceMeta(final String name, final Class<?> type, final ServingDirective servingDirective) {
		super();
		this.name = name;
		this.type = type;
		this.servingDirective = servingDirective;
	}

	/**
	 * Invoked in order to close the possibility to edit the object.
	 */
	public void initialize() {
		if (initialized) {
			throw new IllegalStateException("datasource meta for " + name + " was already been initialized");
		}
		this.childrenMeta = Collections.unmodifiableList(this.childrenMeta);
		this.initialized = true;
	}

	/**
	 * Type hold by the dataSource that owns this descriptor object.
	 * 
	 * @return type class of the object hold by the owner of this descriptor
	 *         object
	 */
	public Class<?> getType() {
		return type;
	}

	/**
	 * Simple name for the dataSource. This one will not represent the entire
	 * path to it in a hierarchical structure. Normally used by the parent
	 * dataSource in order to refer a child dataSource.
	 * 
	 * @return simple name for the dataSource.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Presents the serving directive for the dataSource owner of this
	 * descriptor object.
	 * 
	 * @return {@link ServingDirective} for the owner of this descriptor object
	 */
	public ServingDirective getServingDirective() {
		return servingDirective;
	}

	/**
	 * Get meta for all owner's children.
	 * 
	 * @return a list of {@link DataSourceMeta} that represents each child of
	 *         the dataSource owner of this descriptor object
	 */
	public List<DataSourceMeta> getChildrenMeta() {
		return childrenMeta;
	}

	public DataSourceMeta getChildrenMeta(final String... path) {
		if (path.length == 0) {
			return null;
		}
		for (DataSourceMeta dsm : childrenMeta) {
			if (path[0].equals(dsm.name)) {
				if (path.length > 1) {
					return dsm.getChildrenMeta(Arrays.copyOfRange(path, 1, path.length));
				} else {
					return dsm;
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
