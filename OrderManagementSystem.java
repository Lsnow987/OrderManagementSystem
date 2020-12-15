package edu.yu.cs.intro.orderManagement;
import java.util.Set;
import java.util.Map;
import java.util.Collection;
import java.util.HashSet;
import java.util.HashMap;
/**
* Takes orders, manages the warehouse as well as service providers
*/

public class OrderManagementSystem {
	private Set<Product> productSet;
	private int productStockLevel;
	private Set<ServiceProvider> serviceProviderSet;
	private Warehouse warehouseObject;
	private Set<Service> setOfServicesProvidedByTheServiceProviders = new HashSet<>();
	private Map<Service, Set<ServiceProvider>>   mapOfServicesToTheListOfServiceProviders = new HashMap<>();
	private Map<ServiceProvider, Integer> busyOrFree  = new HashMap<>();
	private Set<Service> itemsThatCannotBeAdded = new HashSet<>();

	/**
	* Creates a new Warehouse instance ******** and calls the other constructor
	*
	* @param products
	* @param defaultProductStockLevel
	* @param serviceProviders
	*/
	public OrderManagementSystem(Set<Product> products, int defaultProductStockLevel, Set<ServiceProvider> serviceProviders) {
		this(products, defaultProductStockLevel, serviceProviders, new Warehouse());
		//this.productSet = products;
		//this.productStockLevel = defaultProductStockLevel;
		//this.serviceProviderSet = serviceProviders;
		//this.warehouseObject = new Warehouse();
	}

	/**
	* 1) populate the warehouse with the products.
	* 2) retrieve set of services provided by the ServiceProviders, to save it as the set of
	services the business can provide
	* 3) create map of services to the List of service providers
	*
	* @param products - set of products to populate the warehouse with
	* @param defaultProductStockLevel - the default number of products to stock for any product
	* @param serviceProviders - set of service providers and the services they provide, to
	make up the services arm of the business
	* @param warehouse - the warehouse that we will store our products in
	*/
	public OrderManagementSystem(Set<Product> products, int defaultProductStockLevel,
	Set<ServiceProvider> serviceProviders, Warehouse warehouse) {
		this.productSet = products;
		this.productStockLevel = defaultProductStockLevel;
		this.serviceProviderSet = serviceProviders;
		this.warehouseObject = warehouse;

		//1) populate the warehouse with the products.
		for(Product currentProduct : this.productSet){
			this.warehouseObject.addNewProductToWarehouse(currentProduct, this.productStockLevel);
		}

		//2) retrieve set of services provided by the ServiceProviders, to save it as the set of
	//services the business can provide
	//for(ServiceProvider currentServiceProvider : serviceProviderSet){
		//this.setOfServicesProvidedByTheServiceProviders.addAll(currentServiceProvider.getServices());
	//}

	//3) create map of services to the List(set) of service providers
	//for(Service service : setOfServicesFromCurrentServiceProvider){
		for(ServiceProvider serviceProvider : serviceProviderSet){
			addServiceProvider(serviceProvider);
			//if(serviceProvider.getServices().contains(service)){
				//if(mapOfServicesToTheListOfServiceProviders.get(service) == null){
					//Set<ServiceProvider> serviceProviderSet = new HashSet<ServiceProvider>(); 
					//serviceProviderSet.add(serviceProvider);
					//mapOfServicesToTheListOfServiceProviders.put(service,serviceProviderSet);
				//}
				//else{
					//mapOfServicesToTheListOfServiceProviders.get(service).add(serviceProvider);
				//}
			//}
		//}	
	}
}
	 

	 
	/**
	 * Accept an order:
	 * 1) See if we have ServiceProviders for all Services in the order. If not, reject the order.
	 * 2) See if the we can fulfill the order for Items. If so, place the product orders with the warehouse and handle the service orders inside this class
	 * 2a) We CAN fulfill a product order if either the warehouse currently has enough quantity in stock OR if the product is NOT on the "do not restock" list.
	 *  In the case that the current quantity of a product is < the quantity in the order AND the product is NOT on the "do not restock" list, the order management system should
	 *  first instruct the warehouse to restock the item, and then tell the warehouse to fulfill this order.
	 * 3) Mark the order as completed
	 * 4) Update busy status of serviced providers...
	 * @throws IllegalArgumentException if any part of the order for PRODUCTS can not be fulfilled
	 * @throws IllegalStateException if any part of the order for SERVICES can not be fulfilled
	 */
	public void placeOrder(Order order) {
		//number 1
		Set<ServiceProvider> serviceProvidersForThisOrder = new HashSet<>();
		Set<Service> serviceSet = order.getServices(); //see order.java
		for(Service currentService : serviceSet){
			//serviceProvidersForThisOrder.addAll(mapOfServicesToTheListOfServiceProviders.get(currentService));
			Set<ServiceProvider> allServiceProvidersForOneOrder = new HashSet<>();
			allServiceProvidersForOneOrder.addAll(mapOfServicesToTheListOfServiceProviders.get(currentService));
			boolean allServiceProvidersAreBusy = true;
			for (ServiceProvider currentServiceProvider : allServiceProvidersForOneOrder) {
				int count = busyOrFree.getOrDefault(currentServiceProvider, 0);
				if (count%4 == 0) {
					serviceProvidersForThisOrder.add(currentServiceProvider);
					allServiceProvidersAreBusy = false;
					currentServiceProvider.assignToCustomer();
					break;
				}
			}
			if (allServiceProvidersAreBusy) {
				throw new IllegalStateException ("Provider is currently assigned to a job");
			}

			boolean mapContainsService = mapOfServicesToTheListOfServiceProviders.keySet().contains(currentService);
			if(!mapContainsService){
				throw new IllegalStateException("We do not have the Service Provider for " + currentService.getDescription());
			}
			//number 4
			//Set<Service> currentService = mapOfServicesToTheListOfServiceProviders.keySet();



			//Set<ServiceProvider> currentServiceProvider = mapOfServicesToTheListOfServiceProviders.get(currentService);
			 //keySet()

			//what do i do when he is busy???
		}
		//number 2 
		Set<Product> productSet = order.getProducts();
		for(Product currentProduct : productSet){
			int currentProductQuantityOrdered = order.getQuantity(currentProduct);
			int currentProductNumber = currentProduct.getItemNumber();
			boolean canTheOrderBeMadeWithoutRestocking = this.warehouseObject.canFulfill(currentProductNumber, currentProductQuantityOrdered); //int productNumber only expecting products not services????
			 //check if the product is restockable if i dont have enough
			boolean canTheOrderBeRestocked = this.warehouseObject.isRestockable(currentProductNumber);
			if(!canTheOrderBeMadeWithoutRestocking && !canTheOrderBeRestocked){
				throw new IllegalArgumentException("We do not have enough of the product in stock " + currentProduct.getDescription());
			}else{
				this.warehouseObject.restock(currentProductNumber, currentProductQuantityOrdered); //only restocks if their is not enough in stock, otherwise does nothing
				this.warehouseObject.fulfill(currentProductNumber, currentProductQuantityOrdered);
			}
		}
		//number 3 
		order.setCompleted(true);

		//number 4
		//for(ServiceProvider currentServiceProviderInTheOrder : serviceProvidersForThisOrder){

		//}
		//serviceProvidersForThisOrder
		//assignToCustomer() 



		for(ServiceProvider currentServiceProviderInTheSet : serviceProviderSet){
			int count = busyOrFree.getOrDefault(currentServiceProviderInTheSet, 0);
			count++;
			busyOrFree.put(currentServiceProviderInTheSet, count);
			if (count%4 == 0 && count>0) {
				currentServiceProviderInTheSet.endCustomerEngagement();
			}

			//currentServiceProvider.assignToCustomer(); //check for nullpointer if has key but no serviceprovider
		}
	}



	//  /**
//  * Validate that all the services being ordered can be provided. Make sure to check how many
// instances of a given service are being requested in the order, and see if we have enough providers
// for them.
//  * @param services the set of services which are being ordered inside the order
//  * @param order the order whose services we are validating
//  * @return itemNumber of a requested service that we either do not have provider for at all, or
// for which we do not have an available provider. Return 0 if all services are valid.
//  */
protected int validateServices(Collection<Service> services, Order order) {
	
	Set<Service> set = this.setOfServicesProvidedByTheServiceProviders; 
	// Map<Service, ServiceProvider> map = mapOfServicesToTheListOfServiceProviders;
	// Set<ServiceProvider> sPSet = serviceProviderSet;

	//Validate that all the services being ordered can be provided.
	for(Service currentService : services){
		if(!set.contains(currentService)){
			return currentService.getItemNumber();
		}
		//	Make sure to check how many
		// 	instances of a given service are being requested in the order, and see if we have enough providers
		// 	for them.
		int numberOfCurrentServiceBeingOdered = order.getQuantity(currentService);
		Set<ServiceProvider> sP = new HashSet<>();
		sP.addAll(mapOfServicesToTheListOfServiceProviders.get(currentService));
		int availableSP = 0;
			for (ServiceProvider currentSP : sP){
				if(!currentSP.isBusy()){
					availableSP++;
				}
			}
		if(availableSP < numberOfCurrentServiceBeingOdered){
			return currentService.getItemNumber();
		}
	}
	return 0;
}

	 
	//  /**
	//  * validate that the requested quantity of products can be fulfilled
	//  * @param products being ordered in this order
	//  * @param order the order whose products we are validating
	//  * @return itemNumber of product which is either not in the catalog or which we have
	// insufficient quantity of. Return 0 if we can fulfill.
	//  */
protected int validateProducts(Collection<Product> products, Order order) {
	Set<Product> allProductsInCatalog = new HashSet<Product>(); 
	allProductsInCatalog.addAll(this.warehouseObject.getAllProductsInCatalog());
	Map<Product,Integer> productToAmount= new HashMap<Product,Integer>();
	
	for(Product product : products){
		//return itemNumber of product which is either not in the catalog(realized that don't need this step but doesn't hurt)
		if(!allProductsInCatalog.contains(product)){	
			return product.getItemNumber();
		}
		//or which we have insufficient quantity of
		if(productToAmount.get(product) == null){
			Integer amount = new Integer(1);
			productToAmount.put(product,amount);
		}
		else{
			Integer newAmount = new Integer(productToAmount.get(product).intValue() + 1);
			productToAmount.put(product,newAmount);
		}
	}
	//or which we have insufficient quantity of(cont)
	for(Product product : products){
		if (!warehouseObject.canFulfill(product.getItemNumber(), productToAmount.get(product).intValue()) && !warehouseObject.isRestockable(product.getItemNumber())){
			return product.getItemNumber();
		}
	}
	return 0;
}



	//  /**
	//  * Adds new Products to the set of products that the warehouse can ship/fulfill
	//  * @param products the products to add to the warehouse
	//  * @return set of products that were actually added (don't include any products that were
	// already in the warehouse before this was called!)
	//  */
	protected Set<Product> addNewProducts(Collection<Product> products) {
  		//for (Product currentProduct : products) {
  			//if(itemsThatCannotBeAdded.contains((Item) currentProduct)){
			//what am i supposed to do here???
			//}
  		//}


  		
		
		for(Product currentProduct : products){ //do i need to check if product already exists
			this.warehouseObject.addNewProductToWarehouse(currentProduct, this.productStockLevel);
		}
		Set<Product> productSetAdded = new HashSet<>();
		this.productSet.addAll(products);
		productSetAdded.addAll(products);
		return productSetAdded;
	}
	 


	//  /**
	//  * Adds an additional ServiceProvider to the system. Update all relevant data about which
	// Services are offered and which ServiceProviders provide which services
	//  * @param provider the provider to add
	//  */
	protected void addServiceProvider(ServiceProvider provider) {
		for(Service currentService : provider.getServices()){
			setOfServicesProvidedByTheServiceProviders.add(currentService);
			if(mapOfServicesToTheListOfServiceProviders.get(currentService)==null){
				Set<ServiceProvider> serviceProvidersForCurrentService = new HashSet<>();
				serviceProvidersForCurrentService.add(provider);
				mapOfServicesToTheListOfServiceProviders.put(currentService, serviceProvidersForCurrentService);
			}else{
				Set<ServiceProvider> serviceProvidersForCurrentServiceAlreadyInMap = mapOfServicesToTheListOfServiceProviders.get(currentService);
				serviceProvidersForCurrentServiceAlreadyInMap.add(provider);
			}
		}	
		serviceProviderSet.add(provider);
	}



	//  /**
	//  *
	//  * @return get the set of all the products offered/sold by this business
	//  */
	public Set<Product> getProductCatalog() {
	    return this.productSet;
	}
	 

	 
	//  /**
	//  * @return get the set of all the Services offered/sold by this business
	//  */
	public Set<Service> getOfferedServices() {
		return this.setOfServicesProvidedByTheServiceProviders;
	}



	//  /**
	//  * Discontinue Item, i.e. stop selling a Service or Product.
	//  * Also prevent the Item from being added in the future.
	//  * If it's a Service - remove it from the set of provided services.
	//  * If it's a Product - still sell whatever instances of this Product are in stock, but do not
	// restock it.
	//  * @param item the item to discontinue see {@link Item}
	//  */
	//  protected void discontinueItem(Item item) {
		
	protected void discontinueItem(Item item) {
		if(item instanceof Service){
			setOfServicesProvidedByTheServiceProviders.remove((Service)item); //remove from here
			mapOfServicesToTheListOfServiceProviders.remove((Service)item); //also here
			//this.serviceProviderSet.removeService((Service)item); //can icall it on a set?????
			itemsThatCannotBeAdded.add((Service)item);
		}else{
			this.warehouseObject.doNotRestock(item.getItemNumber());
		}
		//Set<Item> itemsThatCannotBeAdded = new HashSet<>();
	 	//need to check this set always before i try adding so prob should make it an instance variable
	}
	//  /**
	//  * Set the default product stock level for the given product
	//  * @param prod
	//  * @param level
	//  */
	protected void setDefaultProductStockLevel(Product prod, int level) {
	  	this.warehouseObject.setDefaultStockLevel(prod.getItemNumber(), level);
	}
}
