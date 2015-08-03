package moneymanager.util;

import java.util.*;
import java.lang.*;
import java.io.*;

public class MoneyManagerPropertyUtil {
    
    public Properties getProperties()
    {
        Properties props = new Properties();
        InputStream inputStream = MoneyManagerPropertyUtil.class.getClassLoader().getResourceAsStream("properties/basicproperties.properties");
        try {
            props.load(inputStream);
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}