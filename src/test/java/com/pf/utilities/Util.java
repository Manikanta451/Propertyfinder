package com.pf.utilities;

import java.util.LinkedList;
import org.testng.Assert;
import com.pf.datainitialization.DataInt;

/**
 * 
 * 
 * This Class is to read and set all the xlsx file data to the getter/setter methods
 * 
 * 
 * 
 * 
 */

public class Util {


public static String excep;


		public static LinkedList<Object[]> getInfo(String sheetName1,Xls_Reader xls) {
		
			LinkedList<Object[]> info = new LinkedList<Object[]>();
		
			try {
				DataInt dataInitialization = null;
				int dataStartRowNum = 3;
				int totalRows = 0;
		
				while (!xls.getCellData(sheetName1, 0, dataStartRowNum + totalRows).equals("")) {
					
					totalRows++;
				}
		
				for (int i = dataStartRowNum; i <= (dataStartRowNum + totalRows); i++) {
		
					dataInitialization = new DataInt();
					Object obj[] = new Object[1];
		
					if (xls.getCellData(sheetName1, 0, i).equalsIgnoreCase("Info")) {
						dataInitialization.setCity(xls.getCellData(sheetName1, 1, i));
						obj[0] = dataInitialization;
						info.add(obj);
					}
				}
		
					} catch (Exception e) {
						 excep=e.toString();
						 Assert.fail(excep);
						 e.printStackTrace();
				
					}
				return info;
		
		    }
		
		
}
