package ee.moo.moocraft.util;

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

    public static String join(Collection<String> tokens) {
        return join(tokens, " ");
    }

    public static String join(Collection<String> tokens, String separator) {

        StringBuilder builder = new StringBuilder();
        Iterator iterator = tokens.iterator();

        while (iterator.hasNext()) {
            builder.append(iterator.next());

            if (!iterator.hasNext()) {
            break;
            }

            builder.append(separator);
        }

        return builder.toString();
    }

}
