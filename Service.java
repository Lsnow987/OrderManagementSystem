//Service
package edu.yu.cs.intro.orderManagement;
/**
* An implementation of item which represents a Service provided by the business.
* Has a price per billable hour as well a number of hours this service takes.
* The price returned by getPrice must be the per hour price multiplied by the number of hours the
service takes
*/
public class Service implements Item {

	private double pricePerHour;
	private int numberOfHours;
	private int serviceID;
	private String description;

	public Service(double pricePerHour, int numberOfHours, int serviceID, String description){
		this.pricePerHour = pricePerHour;
		this.numberOfHours = numberOfHours;
		this.serviceID = serviceID;
		this.description = description;
	}
	
	/**
	* @return the number of house this service takes
	*/
	public int getNumberOfHours(){
		return this.numberOfHours;
	}

	@Override
	public int getItemNumber() {
		return this.serviceID;
	}

	@Override
	public String getDescription() {
		//String description = "";
		//description += ("The price per hour is " + this.pricePerHour + ". The number of hours is " + this.numberOfHours + ". The Service Id is " + this.serviceID + ". The description is ");
		return this.description;
	}

	@Override
	public double getPrice() {
		return (this.pricePerHour * this.numberOfHours);
	}

	@Override
	public boolean equals(Object o) {
		//see if it's the same object
		if(this == o) {
			return true;
		}
		//see if it's null
		if(o == null) {
			return false;
		}
		//see if they're from the same class
		if(getClass()!=o.getClass()) {
			return false;
		}
		//cast other object to my class
		Service newService = (Service) o;
		//test equality of relevant instance variables
		return this.serviceID == newService.getItemNumber(); 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + serviceID;
		//result = prime * result + ((Double)price).hashCode();
		//result = prime * result + this.name.hashCode();
		return result;
	}
}
