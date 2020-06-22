package com.courier.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.courier.api.beans.DeliveryManager;
import com.courier.api.beans.Driver;
import com.courier.api.beans.Parcel;

/**
 * Author Ross Morrison 1105864
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
       /** Task 3 - Start **/
    	//Part 1
    	Driver andyDriver = new Driver("Andy");
    	Driver bradDriver = new Driver("Brad");
    	Driver charlesDriver = new Driver("Charles");
    	
    	Driver davidDriver = new Driver("David");
    	Driver emanuelDriver = new Driver("Emanuel");
    	Driver francisDriver = new Driver("Francis");
    	
    	List<Driver> driverUnsortedList = new ArrayList<Driver>();
    	driverUnsortedList.add(andyDriver);
    	driverUnsortedList.add(charlesDriver);
    	driverUnsortedList.add(bradDriver);
    	
    	System.out.println("Initial List value: "+driverUnsortedList.toString()+System.lineSeparator());
    	
    	//Part 2
    	List<Driver> driverSortedList = new LinkedList<Driver>();
    	driverSortedList.add(francisDriver);
    	driverSortedList.add(emanuelDriver);
    	driverSortedList.add(davidDriver);
    	
    	for(Driver driver:driverSortedList) {
    		driverUnsortedList.add(driver);
    	}
    	
    	System.out.println("Extended List value: "+driverUnsortedList.toString()+System.lineSeparator());
    	
    	Collections.sort(driverUnsortedList);
    	
    	System.out.println("Sorted List value: "+driverUnsortedList+System.lineSeparator());
    	
    	Collections.reverse(driverUnsortedList);
    	
    	System.out.println("Reversed List value: "+driverUnsortedList+System.lineSeparator());
    	
    	
      /** Task 3 - End **/
    	
    	/** Task 4 - Start **/
    	//Part 1
    	Set<Driver> initialSet = new TreeSet<Driver>();
    	initialSet.add(andyDriver);
    	initialSet.add(charlesDriver);
    	initialSet.add(bradDriver);
    	
    	System.out.println("Initial Set value: "+initialSet+System.lineSeparator());
    	
    	Set<Driver> distinctSet = new TreeSet<Driver>();
    	distinctSet.add(andyDriver);
    	distinctSet.add(charlesDriver);
    	distinctSet.add(francisDriver);
    	distinctSet.add(davidDriver);
    	
    	System.out.println("Set#2 value: "+distinctSet+System.lineSeparator());
    	
    	Set<Driver> resultantIntersectionSet = new TreeSet<Driver>();
    	resultantIntersectionSet.addAll(initialSet);
    	
    	resultantIntersectionSet.retainAll(distinctSet);
    	
    	List<String> commonDriverNames = new ArrayList<String>();
    	
    	for(Driver driver:resultantIntersectionSet) {
    		commonDriverNames.add(driver.getName());
    	}
    	
    	System.out.println("Intersecting set values: "+resultantIntersectionSet+System.lineSeparator());
    	
    	Set<Driver> resultantDifferentSet = new TreeSet<>();
    	
    	resultantDifferentSet.addAll(initialSet.stream().filter(driver -> !commonDriverNames.contains(driver.getName())).collect(Collectors.toSet()));
    	resultantDifferentSet.addAll(distinctSet.stream().filter(driver -> !commonDriverNames.contains(driver.getName())).collect(Collectors.toSet()));
    	
    	System.out.println("Different set values: "+resultantDifferentSet+System.lineSeparator());
    	
      /** Task 4 - End **/
    	
      /** Task 6 - Start **/
    	Parcel firstParcel = new Parcel("Customer1","AddressCust1",5.0);
    	Parcel secondParcel = new Parcel("Customer2","AddressCust2",2.5);
    	Parcel thirdParcel = new Parcel("Customer3","AddressCust3",1.5);
    	Parcel fourthParcel = new Parcel("Customer4","AddressCust4",1.5);
    	
    	DeliveryManager deliveryManager = new DeliveryManager(new TreeMap<Driver,List<Parcel>>());
    	
    	deliveryManager.addDriver(andyDriver);
    	deliveryManager.addDriver(charlesDriver);
    	deliveryManager.addDriver(emanuelDriver);
    	
    	deliveryManager.addParcel(fourthParcel);
    	deliveryManager.addParcel(secondParcel);
    	deliveryManager.addParcel(thirdParcel);
    	deliveryManager.addParcel(firstParcel);
    	
    	System.out.println("Delivery Manager details: "+deliveryManager.toString()+System.lineSeparator());
    	/**Task 6 - End**/
    	
    	/**Task 7(1) - Output - Start**/
    	deliveryManager.allocateParcels();
    	
    	System.out.println("Updated Delivery Manager details post allocation: "+deliveryManager+System.lineSeparator());
    	/**Task 7(1) - Output - End**/
    	
    	/**Task 7(2) - Output - Start**/
    	System.out.println("HashCodes for the drivers: "+System.lineSeparator());
    	System.out.println("Andy Driver Object hashcode: "+andyDriver.hashCode()+System.lineSeparator());
    	System.out.println("Brad Driver Object hashcode: "+bradDriver.hashCode()+System.lineSeparator());
    	System.out.println("Charles Driver Object hashcode: "+charlesDriver.hashCode()+System.lineSeparator());
    	System.out.println("David Driver Object hashcode: "+davidDriver.hashCode()+System.lineSeparator());
    	System.out.println("Emanuel Driver Object hashcode: "+emanuelDriver.hashCode()+System.lineSeparator());
    	/**Task 7(2) - Output - End**/
    	
    	/**Task 7(7) - Output - Start**/
    	System.out.println("Drivers in ascending order of names, along with their delivery details: "+deliveryManager.printByDriver()+System.lineSeparator());
    	/**Task 7(7) - Output - End**/
    	
    	/**Task 7(8) - Output - Start**/
    	System.out.println("Parcels in ascending order of addresses, along with the driver they are assigned to: "+deliveryManager.printByParcel()+System.lineSeparator());
    	/**Task 7(8) - Output - End**/
      /** Task 6 - End **/	
    }
}
