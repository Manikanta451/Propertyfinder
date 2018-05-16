package com.pf.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import com.pf.testscripts.SetupEnvironment;
import org.testng.Assert;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;


public class AnnotationTransformer implements IAnnotationTransformer {
	
	public String excep;
	
	@SuppressWarnings({ "rawtypes"})
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod){ 
		SetupEnvironment se = new SetupEnvironment();
    	ArrayList<String> ae = new ArrayList<String>();
		
    	try {
			ae = se.start();
			annotation.setRetryAnalyzer(Retry.class);

    	}
    	catch (IllegalAccessException e) {
    		excep=e.toString();
	    	Assert.fail(excep);
			e.printStackTrace();
		}
    	catch (IllegalArgumentException e) {
    		excep=e.toString();
	    	Assert.fail(excep);
			e.printStackTrace();
		}
    	catch (InvocationTargetException e) {
    		excep=e.toString();
	    	Assert.fail(excep);
			e.printStackTrace();
		}
    	catch (NoSuchMethodException e) {
    		excep=e.toString();
	    	Assert.fail(excep);
			e.printStackTrace();
		}
    	catch (SecurityException e) {
    		excep=e.toString();
	    	Assert.fail(excep);
			e.printStackTrace();
		}	
		if (ae.contains(annotation.getTestName())) 
		{
		    annotation.setEnabled(true);
		}
	}
}