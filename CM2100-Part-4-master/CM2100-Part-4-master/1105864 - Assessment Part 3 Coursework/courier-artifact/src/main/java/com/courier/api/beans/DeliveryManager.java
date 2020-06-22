/**
 * 
 */
package com.courier.api.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Author Ross Morrison 1105864
 *
 */

/**Task 5-Start**/
public class DeliveryManager {
  private List<Parcel> parcels;
  private List<Driver> drivers;
  private Map<Parcel,Driver> deliveries;
  /**Task 7(3)**/
  private Map<Driver,List<Parcel>> loads;
  
  /**Task 7(4) - Start**/
  public DeliveryManager(Map<Driver,List<Parcel>> loads) {
	// TODO Auto-generated constructor stub
	  parcels = new ArrayList<>();
	  drivers = new ArrayList<>();
	  deliveries = new HashMap<>();
	  this.loads = loads;
  }
  /**Task 7(4) - End**/
  
  public void addDriver(Driver driver) {
	  drivers.add(driver);
	  
	  /**Task 7(5) - Start**/
	  loads.put(driver, null);
	  /**Task 7(5) - Start**/
  }
  
  public void addParcel(Parcel parcel) {
	  parcels.add(parcel);
  }
  
  public void addUrgentParcel(Parcel parcel) {
	  parcels.add(0, parcel);
  }
  
  public Parcel nextParcel() {
	 return parcels.remove(0);
  }
  
  /**Task 7(7) - Start**/
  public Map<Driver,List<Parcel>> printByDriver() { 
	  return loads.entrySet().stream().sorted(Map.Entry.<Driver,List<Parcel>>comparingByKey()).collect(LinkedHashMap::new, (m,v)->m.put(v.getKey(), v.getValue()), LinkedHashMap::putAll);
  }
  /**Task 7(7) - End**/
  
  /**Task 7(8) - Start**/
  public Map<Parcel,Driver> printByParcel() { 
	  return deliveries.entrySet().stream().sorted((parcel1,parcel2)->{return parcel1.getKey().getAddress().compareTo(parcel2.getKey().getAddress());}).collect(LinkedHashMap::new, (m,v)->m.put(v.getKey(), v.getValue()), LinkedHashMap::putAll); 
  }
  /**Task 7(8) - End**/
  
  /**Task 7(1)-Start**/
  public void allocateParcels() {
	  int driverIndex = 0;
	  while(!parcels.isEmpty()) {
	  if(driverIndex > drivers.size()-1) {
		  driverIndex = 0;
	  }
	  Collections.sort(drivers, new Driver.CompareDriverLoad());	  
	  Driver driver = drivers.remove(0);
	  Parcel parcel = this.nextParcel();
	  
	  deliveries.put(parcel, driver);
	  List<Parcel> parcelsAssociated = null;
	  
	  /**Task 7(6) - Start**/
	  if(loads.containsKey(driver)) {
		  parcelsAssociated = loads.get(driver);
	  }
	  if(null == parcelsAssociated) {
		  parcelsAssociated = new ArrayList<>();
	  }
	  parcelsAssociated.add(parcel);
	  driver.setNumberParcels(driver.getNumberParcels()+1);
	  loads.put(driver, parcelsAssociated);
	  /**Task 7(6) - End**/
	  
	  drivers.add(driverIndex,driver);
	  driverIndex+=1;
	  }
  }
  /**Task 7(1)-End**/
  
  @Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Parcels to manage: "+parcels+" , Drivers managed: "+drivers+", Deliveries: "+ deliveries;
	}
}
/**Task 5-End**/
