/**
 * 
 */
package com.courier.api.beans;

/**
 * Author Ross Morrison 1105864
 *
 */

/** Task 1- Start**/
public class Parcel {
  private String recipient;
  private String address;
  private Double weight;
  
  
	
  
	public Parcel(String recipient, String address, Double weight) {
	super();
	this.recipient = recipient;
	this.address = address;
	this.weight = weight;
	}

   


	/**
	 * @return the recipient
	 */
	public String getRecipient() {
		return recipient;
	}




	/**
	 * @param recipient the recipient to set
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}




	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}




	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}




	/**
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}




	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}

    


	@Override
		public String toString() {
			// TODO Auto-generated method stub
		    StringBuffer stringBuffer = new StringBuffer();
		    stringBuffer.append("Name of the recipient: "+getRecipient());
		    stringBuffer.append(",");
		    stringBuffer.append("Address of the recipient: "+getAddress());
		    stringBuffer.append(",");
		    stringBuffer.append("Weight of courier: "+getWeight()+" Kg(s)");
		    stringBuffer.append(System.lineSeparator());
			return stringBuffer.toString();
		}
  
}
/** Task 1- End**/
