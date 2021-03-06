package edu.yu.cs.intro.orderManagement;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
/**
* Stocks products, fulfills product orders, manages stock of products.
*/

public class Warehouse {
	private Map<Product, Integer> productList; 
	private Map<Integer, Product> productNumberList;
	private Map<Product, Integer> defaultStockLevel;
	private Set<Product> doNotRestockSet;
	private Set<Product> catalogContains;
	
	 /**
	 * create a warehouse, initialize all the instance variables
	 */
	 protected Warehouse(){
	 	productList = new HashMap<Product, Integer>();
	 	productNumberList = new HashMap<Integer, Product>();
	 	defaultStockLevel = new HashMap<Product, Integer>();
	 	doNotRestockSet = new HashSet<Product>();
	 	catalogContains = new HashSet<Product>();
	 }
	 /**
	 * @return all unique Products stocked in the warehouse
	 */
	 protected Set<Product> getAllProductsInCatalog(){
	 	return productList.keySet();
	 }
	 /**
	 * Add a product to the warehouse, at the given stock level.
	 * @param product
	 * @param desiredStockLevel the number to stock initially, and also to restock to when
	subsequently restocked
	 * @throws IllegalArgumentException if the product is in the "do not restock" set, or if the
	product is already in the warehouse 
	 */
	 protected void addNewProductToWarehouse(Product product, int desiredStockLevel){
	 	if (doNotRestockSet.contains(product.getItemNumber()) || productList.containsKey(product)) {
	 		throw new IllegalArgumentException();
	 	}
	 	defaultStockLevel.put(product, desiredStockLevel);
	 	productList.put(product, desiredStockLevel);
	 	productNumberList.put(product.getItemNumber(), product);
	 }
	 /**
	 * If the actual stock is already >= the minimum, do nothing. Otherwise, raise it to minimum OR
	the default stock level, whichever is greater
	 * @param productNumber
	 * @param minimum
	 * @throws IllegalArgumentException if the product is in the "do not restock" set, or if it is
	not in the catalog
	 */
	 protected void restock(int productNumber, int minimum){
	 	if (!isRestockable(productNumber)) {
	 		throw new IllegalArgumentException();
	 	}
	 	Product currentProduct = productNumberList.get(productNumber);
	 	int currentStock = productList.get(currentProduct);
	 	if (currentStock < minimum) {
	 		if (defaultStockLevel.get(currentProduct) > minimum) {
	 			productList.put(currentProduct, defaultStockLevel.get(currentProduct));
	 		}
	 		else{
	 			productList.put(currentProduct, minimum);
	 		}
	 	}
	 }
	 /**
	 * Set the new default stock level for the given product
	 * @param productNumber
	 * @param quantity
	 * @return the old default stock level
	 * @throws IllegalArgumentException if the product is in the "do not restock" set, or if it is
	not in the catalog
	 */
	 protected int setDefaultStockLevel(int productNumber, int quantity){
	 	if (!isRestockable(productNumber)) {
	 		throw new IllegalArgumentException();
	 	}
	 	Product currentProduct = productNumberList.get(productNumber);
	 	int previousDefaultStockLevel = defaultStockLevel.get(currentProduct);
	 	defaultStockLevel.put(currentProduct, quantity);
	 	return previousDefaultStockLevel;
	 }
	 /**
	 * @param productNumber
	 * @return how many of the given product we have in stock, or zero if it is not stocked
	 */
	 protected int getStockLevel(int productNumber){
	 	if(isInCatalog(productNumber)){
	 		Product currentProduct = productNumberList.get(productNumber);
	 		return productList.get(currentProduct);
	 	}
	 	else {
	 		return 0;
	 	}
	 }
	 /**
	 * @param itemNumber
	 * @return true if the given item number is in the warehouse's catalog, false if not
	 */
	 protected boolean isInCatalog(int itemNumber){
	 	Product currentProduct = productNumberList.get(itemNumber);
	 	if (productList.containsKey(currentProduct)) {
	 		return true;
	 	}
	 	else {
	 		return false;
	 	}
	 }
	 /**
	 *
	 * @param itemNumber
	 * @return false if it's not in catalog or is in the "do not restock" set. Otherwise true.
	 */
	 protected boolean isRestockable(int itemNumber){
	 	Product currentProduct = productNumberList.get(itemNumber);
	 	if (!isInCatalog(itemNumber) || doNotRestockSet.contains(currentProduct)) {
	 		return false;
	 	}
	 	else{
	 		return true;
	 	}
	 }
	 /**
	 * add the given product to the "do not restock" set
	 * @param productNumber
	 * @return the current actual stock level
	 */
	 protected int doNotRestock(int productNumber){
	 	Product currentProduct = productNumberList.get(productNumber);
	 	doNotRestockSet.add(currentProduct);
	 	return productList.get(currentProduct);
	 }
	 /**
	 * can the warehouse fulfill an order for the given amount of the given product?
	 * @param productNumber
	 * @param quantity
	 * @return false if the product is not in the catalog or there are fewer than quantity of the
	products in the catalog. Otherwise true.
	 */
	 protected boolean canFulfill(int productNumber, int quantity){
	 	Product currentProduct = productNumberList.get(productNumber);
	 	if (currentProduct == null){
	 		return false;
	 	}
	 	int currentStock = productList.get(currentProduct);
	 	if (!isInCatalog(productNumber) || currentStock < quantity) {
	 		return false;
	 	}
	 	else {
	 		return true;
	 	}
	 }
	 /**
	 * Fulfill an order for the given amount of the given product, i.e. lower the stock levels of
	the product by the given amount
	 * @param productNumber
	 * @param quantity
	 * @throws IllegalArgumentException if {@link #canFulfill(int, int)} returns false
	 */
	 protected void fulfill(int productNumber, int quantity){
	 	if (!canFulfill(productNumber, quantity)) {
	 		throw new IllegalArgumentException();
	 	}
	 	Product currentProduct = productNumberList.get(productNumber);
	 	int currentStock = productList.get(currentProduct);
	 	productList.put(currentProduct, currentStock - quantity);
	 }
}
