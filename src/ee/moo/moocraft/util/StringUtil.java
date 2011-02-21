package ee.moo.moocraft.util;

import org.bukkit.ChatColor;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

/**
 * User: Tanel Suurhans
 * Date: 2/17/11
 */
public class StringUtil {

    public static String capitalize(String string) {

        if (string.length() == 0) {
            return string;
        }

        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static String join(String[] tokens) {
        return join(tokens, " ");
    }

    public static String join(Object[] tokens, String separator) {

        if (tokens == null || tokens.length == 0) {
            return "";
        } else if (tokens.length == 1) {
            return (String) tokens[0];
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (Object token : tokens) {
            stringBuilder.append(separator);
            stringBuilder.append((String) token);
        }

        return stringBuilder.toString();
    }

}
