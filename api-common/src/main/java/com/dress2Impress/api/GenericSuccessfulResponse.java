package com.dress2Impress.api;

import com.fasterxml.jackson.annotation.JsonInclude;

public class GenericSuccessfulResponse<T> extends SuccessfulResponse {

	@JsonInclude(value = JsonInclude.Include.ALWAYS)
	private T data;

	public GenericSuccessfulResponse() {
	}

	public T getData() {
		return data;
	}

	public GenericSuccessfulResponse setData(T data) {
		this.data = data;
		return this;
	}

}
