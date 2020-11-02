package com.sbs.webp.lolHi.util;

import java.math.BigInteger;


public class Util {

	public static int getAsInt(Object object) {
		return getAsInt(object, -1);
	}

	public static int getAsInt(Object object, int defaultValue) {
		if (object instanceof BigInteger) {
			return ((BigInteger) object).intValue();
		}
		else if (object instanceof Integer) {
			return (int)object;
		}
		else if (object instanceof Long) {
			return (int)((long) object);
		}
		else if (object instanceof String) {
			int value = defaultValue;
			try {
				return Integer.parseInt((String) object);
			}
			catch (NumberFormatException e) {
				return defaultValue;
			}
		}
		
		return defaultValue;
	}

	public static String getAsStr(Object object, String defaultValue) {
		if (object == null) {
			return defaultValue;
		}
		
		if (object instanceof String) {
			return (String)(object);
		}
		
		return object.toString();
	}
}
