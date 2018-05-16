package com.pf.datainitialization;

import java.io.Serializable;

/**
 * 
 * 
 * This Class is for the initialization of the data from the XLSX.
 *
 */

public class DataInt implements Serializable {

	
	private static final long serialVersionUID = -5841500346110682370L;
	
	
	//------- Info ----------//
	
		public String City="";
		
		public String Propertytype="";

		public String MinArea="";
	
		public String MaxArea="";
		
		public String MinPrice="";

		public String MaxPrice="";
		
		public String MinBed="";
		
		public String MaxBed="";

		
		
		
		
		
		
		
	//------ Info getter and Setter Methods---//
		
		
		public String getCity() {
			return City;
		}

		public void setCity(String city) {
			City = city;
		}

		public String getPropertytype() {
			return Propertytype;
		}

		public void setPropertytype(String propertytype) {
			Propertytype = propertytype;
		}

		public String getMinArea() {
			return MinArea;
		}

		public void setMinArea(String minArea) {
			MinArea = minArea;
		}

		public String getMaxArea() {
			return MaxArea;
		}

		public void setMaxArea(String maxArea) {
			MaxArea = maxArea;
		}

		public String getMinPrice() {
			return MinPrice;
		}

		public void setMinPrice(String minPrice) {
			MinPrice = minPrice;
		}

		public String getMaxPrice() {
			return MaxPrice;
		}

		public void setMaxPrice(String maxPrice) {
			MaxPrice = maxPrice;
		}

		public String getMinBed() {
			return MinBed;
		}

		public void setMinBed(String minBed) {
			MinBed = minBed;
		}

		public String getMaxBed() {
			return MaxBed;
		}

		public void setMaxBed(String maxBed) {
			MaxBed = maxBed;
		}
		
	
}	