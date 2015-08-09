package moneymanager.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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