package org.ys.common.utils;

import java.util.UUID;

public class IDGeneratorUtil {
	private IDGeneratorUtil(){}
	
	public static String generateUUID(){
		return UUID.randomUUID().toString();
	}
}
