package usee.com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * 获取配置文件的内容
 * @author sunzequn
 *
 */
public class PropertiesUtil {

    /**
     * 静态方法根据key获取value值
     * @param key
     * @return value
     */
	public static String getValue(String key){
		Properties prop=new Properties();
		InputStream in=new PropertiesUtil().getClass().getResourceAsStream("/conf.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
}
