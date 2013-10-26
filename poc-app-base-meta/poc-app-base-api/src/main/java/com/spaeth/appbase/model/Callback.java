package com.spaeth.appbase.model;

import java.io.Serializable;

public interface Callback<ExpectedResultType extends Serializable> {

	void execute(ExpectedResultType result);

}
