package ee.moo.moocraft.util;

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

}
