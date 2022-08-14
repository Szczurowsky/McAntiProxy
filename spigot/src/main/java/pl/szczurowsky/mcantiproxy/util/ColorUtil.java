package pl.szczurowsky.mcantiproxy.util;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ColorUtil {

    private static final String HEX_PATTERN = "^#([A-Fa-f\\d]{6}|[A-Fa-f\\d]{3})$";
    private static final Pattern pattern = Pattern.compile(HEX_PATTERN);

    /**
     * Translates custom colors with HEX and default minecraft color tag
     *
     * @param string Given string
     * @return String with translated color
     */
    public static String format(String string) {
        string = string.replace("§", "&");
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * Translates custom colors with HEX and default minecraft color tag
     *
     * @param list Given list of strings
     * @return List of strings with translated color
     */
    public static List<String> formatList(List<String> list) {
        List<String> formattedList = new ArrayList<>();

        for (String line : list) {
            formattedList.add(format(line));
        }

        return formattedList;
    }

}
