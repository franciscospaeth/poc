package com.spaeth.appbase.core.datasource;

import org.apache.commons.lang3.StringUtils;

/**
 * Helper class for some common operation in DataSource handling.
 * 
 * @author Francisco Spaeth (francisco.spaeth@gmail.com)
 * 
 */
public class DataSourceUtils {

	/**
	 * Indicates if a DataSource owns other.
	 * 
	 * @param parent
	 *            the suspect owner dataSource
	 * @param from
	 *            the suspect owned dataSource
	 * @return if <code>parent</code> really owns <code>from</code>
	 */
	public static boolean isSuperior(final DataSource parent, final DataSource from) {
		DataSource own = from.getOwner();

		while (own != null) {
			if (own.equals(parent)) {
				return true;
			}
			own = own.getOwner();
		}

		return false;
	}

	/**
	 * Indicates if a DataSource is owned by other.
	 * 
	 * @param child
	 *            the suspect owned dataSource
	 * @param from
	 *            the suspect owner dataSource
	 * @return if <code>parent</code> really owns <code>from</code>
	 */
	public static boolean isInferior(final DataSource child, final DataSource from) {
		DataSource ds = child;
		while (ds != null) {
			if (ds.equals(from)) {
				return true;
			}
			ds = ds.getOwner();
		}
		return false;
	}

	public static void describe(final DataSource dataSource) {
		dataSource.accept(new DataSourceVisitor() {

			int step = 0;

			@Override
			public void visit(final DataSource dataSource) {
				System.out.println(StringUtils.repeat("   ", step) + dataSource.getName() + ", type: " + dataSource.getType());
				step++;
				for (DataSource ds : dataSource.getDataSources()) {
					ds.accept(this);
				}
				step--;
			}

		});
	}

}
