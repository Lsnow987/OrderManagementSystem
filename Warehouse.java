package edu.yu.cs.intro.orderManagement;

/**
* Stocks products, fulfills product orders, manages stock of products.
*/

public class Warehouse {
	private String product;
	private Int numberInStock;

	 /**
	 * create a warehouse, initialize all the instance variables
	 */
	 protected Warehouse(){
	 }
	 /**
	 * @return all unique Products stocked in the warehouse
	 */
	 protected Set<Product> getAllProductsInCatalog(){

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
	 }
	 /**
	 * If the actual stock is already >= the minimum, do nothing. Otherwise, raise it to the
	minimum level.
	 * @param productNumber
	 * @param minimum
	 * @throws IllegalArgumentException if the product is in the "do not restock" set, or if it is
	not in the catalog
	 */
	 protected void restock(int productNumber, int minimum){

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

	 }
	 /**
	 * @param productNumber
	 * @return how many of the given product we have in stock, or zero if it is not stocked
	 */
	 protected int getStockLevel(int productNumber){}
	 /**
	 * @param itemNumber
	 * @return true if the given item number is in the warehouse's catalog, false if not
	 */
	 protected boolean isInCatalog(int itemNumber){}
	 /**
	 *
	 * @param itemNumber
	 * @return false if it's not in catalog or is in the "do not restock" set. Otherwise true.
	 */
	 protected boolean isRestockable(int itemNumber){}
	 /**
	 * add the given product to the "do not restock" set
	 * @param productNumber
	 * @return the current actual stock level
	 */
	 protected int doNotRestock(int productNumber){}
	 /**
	 * can the warehouse fulfill an order for the given amount of the given product?
	 * @param productNumber
	 * @param quantity
	 * @return false if the product is not in the catalog or there are fewer than quantity of the
	products in the catalog. Otherwise true.
	 */
	 protected boolean canFulfill(int productNumber, int quantity){
		 //return false if the product is not in the catalog
	 	if(!isInCatalog(productNumber)){
	 		return false;
	 	}
		 //or there are fewer than quantity of the products in the catalog.
	 	if(getStockLevel(productNumber) < quantity){
	 		return false;
	 	}
		 //Otherwise true.
	 	return true;
	 }
	 /**
	 * Fulfill an order for the given amount of the given product, i.e. lower the stock levels of
	the product by the given amount
	 * @param productNumber
	 * @param quantity
	 * @throws IllegalArgumentException if {@link #canFulfill(int, int)} returns false
	 */
	 protected void fulfill(int productNumber, int quantity){}
}
