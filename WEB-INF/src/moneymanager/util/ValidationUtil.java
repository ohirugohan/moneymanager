package moneymanager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ValidationUtil {

    public static boolean validateInteger(String param) {
        if (param == null || param.length() == 0) {
            return false;
        }

        try {
            int numberParam = Integer.parseInt(param);
            if (numberParam < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static boolean validateDate(String param) {
        if (param == null || param.length() == 0) {
            return false;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date result = formatter.parse(param);
            formatter.setLenient(false);
            String reverse = formatter.format(result);
            // 戻して一致するか
            return param.equals(reverse) ? true : false;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean validateString(String param) {
        if (param == null || param.length() == 0) {
            return false;
        }

        return true;
    }

}