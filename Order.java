//Order
package edu.yu.cs.intro.orderManagement;
/**
* models an order placed by a customer. An item in the order can be an instance of either Product
or Service
*/
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;
public class Order {
	private HashMap<Item,Integer> itemToQuantity = new HashMap<>();
	private ArrayList<Item> itemArrayList = new ArrayList<Item>();
	private boolean isItCompleted = false;
	public Order(){}
	/**
	* @return all the items (products and services) in the order
	*/
	public Item[] getItems(){
		Item itemArray[] = new Item[itemArrayList.size()];
		return itemArrayList.toArray(itemArray);
		
		/*Set<Item> itemSet = itemToQuantity.keySet();
		Item[] itemArray =  new Item[itemSet.size()-1]; //should i have minus 1????
		for (Item currentItem : itemSet) {
			itemArray
		}*/
	}
	
	protected Set<Service> getServices(){
		Set<Item> serviceAndProductSet = itemToQuantity.keySet();
		Set<Service> serviceSet = new HashSet<>();
		for(Item currentItem : serviceAndProductSet){ 
			if(currentItem instanceof Service){
				serviceSet.add((Service)currentItem);
			}
		}
		return serviceSet;
	}

	protected Set<Product> getProducts(){
		Set<Item> serviceAndProductSet = itemToQuantity.keySet();
		Set<Product> productSet = new HashSet<>();
		for(Item currentItem : serviceAndProductSet){ 
			if(currentItem instanceof Product){
				productSet.add((Product)currentItem);
			}
		}
		return productSet;
	}
	
	/**
	* @param b
	* @return the quantity of the given item ordered in this order. Zero if the item is not in the
	order.
	*/
	public int getQuantity(Item b){
		return itemToQuantity.getOrDefault(b, 0); 
	}
	/**
	* Add the given quantity of the given item (product or service) to the order
	* @param item
	* @param quantity
	*/
	public void addToOrder(Item item, int quantity){
		itemArrayList.add(item);
		itemToQuantity.put(item,quantity);
	}
	/**
	* Calculate the total price of PRODUCTS in the order. Must multiply each item's price by the
	quantity.
	* @return the total price of products in this order
	*/
	public double getProductsTotalPrice(){
		Item[] storeAllItems = this.getItems();
		double store = 0;
		for(int i = 0; i < storeAllItems.length; i++){
			if(storeAllItems[i] instanceof Product){
				double price = storeAllItems[i].getPrice();
				int quantity = getQuantity(storeAllItems[i]);
				store += (price*quantity);
			}
		}
		return store;
	}
	/**
	* Calculate the total price of the SERVICES in the order. Must multiply each item's price by
	the quantity.
	* @return the total price of products in this order
	*/
	public double getServicesTotalPrice(){
		Item[] storeAllItems = this.getItems();
		double store = 0;
		for(int i = 0; i < storeAllItems.length; i++){
			if(storeAllItems[i] instanceof Service){
				double price = storeAllItems[i].getPrice();
				int quantity = getQuantity(storeAllItems[i]);
				store += (price*quantity);
			}
		}
		return store;
	}
	/**
	* @return has the order been completed by the order management system?
	*/
	public boolean isCompleted() {
		return this.isItCompleted;
	}
	/**
	* Indicate if the order has been completed by the order management system
	* @param completed
	*/
	public void setCompleted(boolean completed) {
		this.isItCompleted = completed;
	}
	
	
}
