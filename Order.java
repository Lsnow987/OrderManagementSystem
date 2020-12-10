//Order
package edu.yu.cs.intro.orderManagement;
/**
* models an order placed by a customer. An item in the order can be an instance of either Product
or Service
*/
import java.util.HashMap;
import java.util.*;
public class Order {
	HashMap<Item,Integer> itemToQuantity = new HashMap<>();
	boolean isItCompleted = false;
	public Order(){}
	/**
	* @return all the items (products and services) in the order
	*/
	public Item[] getItems(){
		return (Item[])itemToQuantity.keySet().toArray();
	}
	/**
	* @param b
	* @return the quantity of the given item ordered in this order. Zero if the item is not in the
	order.
	*/
	public int getQuantity(Item b){
		return itemToQuantity.getOrDefault(b, 0); //why can it not find symbol????
	}
	/**
	* Add the given quantity of the given item (product or service) to the order
	* @param item
	* @param quantity
	*/
	public void addToOrder(Item item, int quantity){
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
	
	//need this method for order management 
	public Set<Service> getSetofAllServices(){}
}

