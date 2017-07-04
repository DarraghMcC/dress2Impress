package com.dress2Impress.dress.config;

import javax.validation.constraints.NotNull;

public final class JacketTempProperties {

	@NotNull
	private Integer maxTempLimit;

	public Integer getMaxTempLimit() {
		return maxTempLimit;
	}

	public void setMaxTempLimit(Integer maxTempLimit) {
		this.maxTempLimit = maxTempLimit;
	}
}
