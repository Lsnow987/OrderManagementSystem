package edu.yu.cs.intro.orderManagement;
/**
* 1) has a Set of services that he can provide
* 2) can only work on one order at a time - once assigned to a customer, can not take another assignment until 3 other orders have been placed with the order management system
* 3) is uniquely identified by its ID
*/
import java.util.Set;
import java.util.HashSet;

public class ServiceProvider {
	private Set<Service> services;
	private String name;
	private int id;
	private boolean busy = false;
	/**
	 *
	 * @param name
	 * @param id unique id of the ServiceProvider
	 * @param services set of services this provider can provide
	 */
 	public ServiceProvider(String name, int id, Set<Service> services){
 		this.name = name;
 		this.id = id;
 		this.services = services;
 	}
 		
 	public String getName(){
 		return this.name;
 	}

 	public int getId(){
 		return this.id;
 	}

	/**
	 * Assign this provider to a customer. Record the fact that he is busy.
	 * @throws IllegalStateException if the provider is currently assigned to a job
	 */
	protected void assignToCustomer() throws IllegalStateException {
		if(busy == false){
			busy = true;
		}

		else{
			throw new IllegalStateException ("Provider is currently assigned to a job");
		}
	}


	/**
	 * Free this provider up - is not longer assigned to a customer
	 * @throws IllegalStateException if the provider is NOT currently assigned to a job
	 */
	protected void endCustomerEngagement() throws IllegalStateException{
		if(busy == true){
			busy = false;
		} //end of if

		else{
			throw new IllegalStateException ("Provider is not currently assigned to a job");
		} 
	} 


	 /**
	 * @param s add the given service to the set of services this provider can provide
	 * @return true if it was added, false if not
	 */
	protected boolean addService(Service s){
		return this.services.add(s);
	}


	/**
	* @param s remove the given service to the set of services this provider can provide
	* @return true if it was removed, false if not
	*/
	protected boolean removeService(Service s){
		return this.services.remove(s);
	}

	/**
	*
	* @return a COPY of the set of services. MUST NOT return the Set instance itself, since that would allow a caller to then add/remove services
	*/
	public Set<Service> getServices(){
		Set<Service> copySet = new HashSet<Service>();
		copySet.addAll(services);
		return copySet;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o){
			return true;
		}

		if(o == null){
			return false;
		}

		if (this.getClass()!=o.getClass()){
			return false;
		}

		ServiceProvider otherService = (ServiceProvider) o;
		return this.id==otherService.getId();
	}
	
	@Override
	public int hashCode() {
		return this.id;
	}
}
