package com.spaeth.appbase.core.datasource.event;

import com.spaeth.appbase.core.datasource.DataSource;
import com.spaeth.appbase.core.security.model.AccessPolicy;

public interface AccessPolicyChangeListener {

	void onAccessPolicyChanged(AccessPolicyChangeEvent event);

	public static class AccessPolicyChangeEvent extends DataSourceEvent {

		private static final long serialVersionUID = 1L;

		private AccessPolicy oldAccessPolicy;
		private AccessPolicy newAccessPolicy;

		public AccessPolicyChangeEvent(DataSource dataSource, AccessPolicy oldAccessPolicy,
				AccessPolicy newAccessPolicy) {
			super(dataSource);
			this.oldAccessPolicy = oldAccessPolicy;
			this.newAccessPolicy = newAccessPolicy;
		}

		public AccessPolicy getOldAccessPolicy() {
			return oldAccessPolicy;
		}

		public void setOldAccessPolicy(AccessPolicy oldAccessPolicy) {
			this.oldAccessPolicy = oldAccessPolicy;
		}

		public AccessPolicy getNewAccessPolicy() {
			return newAccessPolicy;
		}

		public void setNewAccessPolicy(AccessPolicy newAccessPolicy) {
			this.newAccessPolicy = newAccessPolicy;
		}

	}

}
