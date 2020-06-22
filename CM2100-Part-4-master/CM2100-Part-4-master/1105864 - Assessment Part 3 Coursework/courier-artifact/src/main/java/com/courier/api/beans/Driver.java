/**
 * 
 */
package com.courier.api.beans;

import java.util.Comparator;

/**
 * Author Ross Morrison 1105864
 *
 */

/**Task 2 -Start**/
public class Driver implements Comparable<Driver>{
  private String name;
  private Integer numberParcels;
  
  
  
	public Driver(String name) {
	super();
	this.name = name;
	this.numberParcels = 0;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the numberParcels
	 */
	public Integer getNumberParcels() {
		return numberParcels;
	}
	/**
	 * @param numberParcels the numberParcels to set
	 */
	public void setNumberParcels(Integer numberParcels) {
		this.numberParcels = numberParcels;
	}
	
	public void incrementNumberParcels() {
		this.setNumberParcels(getNumberParcels()+1);
	}
	
	public int compareTo(Driver anotherDriver) {
		// TODO Auto-generated method stub
		return this.getName().toUpperCase().compareTo(anotherDriver.getName().toUpperCase());
	}
	/**Task 7(2) -Start**/
	@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return getName().hashCode();
		}
	/**Task 7(2) -End**/
	@Override
		public String toString() {
			// TODO Auto-generated method stub
		    StringBuffer driverStringBuffer = new StringBuffer();
		    driverStringBuffer.append("Name of the driver: "+getName());
		    driverStringBuffer.append(System.lineSeparator());
		    driverStringBuffer.append("Number of Parcels: "+getNumberParcels());
			return driverStringBuffer.toString();
		}
	
	public static class CompareDriverLoad implements Comparator<Driver>{

		public int compare(Driver firstDriver, Driver secondDriver) {
			// TODO Auto-generated method stub
			return firstDriver.getNumberParcels().compareTo(secondDriver.getNumberParcels());
		}
		
	}
  
  
}
/**Task 2 -End**/
