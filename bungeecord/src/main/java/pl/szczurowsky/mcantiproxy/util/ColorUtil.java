package pl.szczurowsky.mcantiproxy.util;

import net.md_5.bungee.api.ChatColor;

public class ColorUtil {

    private ColorUtil() {

    }

    /**
     * Translates custom colors with HEX and default minecraft color tag
     *
     * @param string Given string
     * @return String with translated color
     */
    public static String format(String string) {
        return ChatColor.translateAlternateColorCodes('&', string.replace("§", "&"));
    }

}
