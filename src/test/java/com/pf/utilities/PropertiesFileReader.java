package com.pf.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.testng.Assert;

/**
 * 
 * 
 * This Class is used to get the instance of properties from application.properties file
 * 
 * 
 * 
 */

public class PropertiesFileReader {
	
	private static PropertiesFileReader INSTANCE = null;
	static Properties properties = new Properties();
	private static final String PROP_FILE= "application.properties";
	public static String excep;
	
	
	static InputStream in,input = null;
	static{
	
		try {
		    in = PropertiesFileReader.class.getResourceAsStream("/resources/"+PROP_FILE);
			properties.load(in);
		   }catch (IOException e) {
			   excep=e.toString();
			   Assert.fail(excep);
			   e.printStackTrace();
		}	
	}
	private PropertiesFileReader(){

	}
	
	public static PropertiesFileReader getInstance() {
        if (INSTANCE==null) {
        	INSTANCE = new PropertiesFileReader();
        }
        return INSTANCE;
    }
	
	public static Properties readProperty(String name){
		
		try {
			if(null != name){
			in = properties.getClass().getResourceAsStream("/resources/"+name);
			properties.load(in);
			}
		} catch (IOException e) {
			 excep=e.toString();
			 Assert.fail(excep);
			 e.printStackTrace();
		}
		return properties;
	}


	
	public static  String readvalueOfKey(String keyName){
		 
			String value = null;
			// True If keyName Is Not Null And Not Empty
			if(null != keyName && !"".equalsIgnoreCase(keyName)){
				value = (String)properties.get(keyName);
				
			}
			return value;
		}


}


