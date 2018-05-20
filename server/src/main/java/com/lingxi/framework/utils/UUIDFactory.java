package com.lingxi.framework.utils;

import java.util.UUID;

public class UUIDFactory {

	/**
	 * ����UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	public static String getUUIDstr(){
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		str = str.replaceAll("[-]", "");
		return str.toUpperCase();
	}
	
}
