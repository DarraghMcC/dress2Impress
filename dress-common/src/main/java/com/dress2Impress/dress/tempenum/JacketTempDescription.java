package com.dress2Impress.dress.tempenum;

import java.util.Arrays;

public enum JacketTempDescription {

	CARRY(18, ""),
	LIGHT(7, ""),
	GOOD(2,""),
	HEAVY(-5, ""),
	TOO_COLD(-30, "");
	
	private Integer tempLimit;
	private String messageCode;

	private JacketTempDescription(final Integer tempLimit, final String messageCode){
		this.tempLimit = tempLimit;
		this.messageCode = messageCode;
	}

	public static String getMessageCode(final Integer currentTemp){
		return Arrays.stream(JacketTempDescription.values()).filter(j -> j.tempLimit < currentTemp)
				.sorted().findFirst().get().messageCode;
	}
}
