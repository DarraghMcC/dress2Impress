package com.dress2impress.test.util;

import java.lang.reflect.Field;

public class MockitoUtils {

	public static void setStatic(final Field field, final Object newValue) throws Exception {
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers());
		field.set(null, newValue);
	}
}
