package com.lingxi.framework.utils;

import java.util.UUID;

public class StringUtil {

    /**
     * 首字母大写
     * @param str
     * @return
     */
    public static String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

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
