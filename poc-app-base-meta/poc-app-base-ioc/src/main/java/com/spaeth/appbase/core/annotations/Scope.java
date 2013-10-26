package com.spaeth.appbase.core.annotations;

import com.spaeth.appbase.core.service.ScopeConstants;

public @interface Scope {

	String value() default ScopeConstants.SINGLETON_SCOPE;

}
