package dk.manaxi.unikpay.plugin.utils;


import net.md_5.bungee.api.ChatColor;

import java.util.List;

public class ColorUtils {
    public static String plain(String s) {
        return s.replaceAll("§", "&");
    }
    public static String[] getColored(String... stringList) {
        if (stringList == null)
            return null;
        for (int i = 0; i < stringList.length; i++)
            stringList[i] = getColored(stringList[i]);
        return stringList;
    }

    public static List<String> getColored(List<String> stringList) {
        if (stringList == null)
            return null;
        for (int i = 0; i < stringList.size(); i++)
            stringList.set(i, getColored(stringList.get(i)));
        return stringList;
    }

    public static String getColored(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
