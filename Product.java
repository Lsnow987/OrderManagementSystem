package edu.yu.cs.intro.orderManagement;
/**
* Is a "physical" item that is "stocked" in the warehouse.
*/
public class Product implements Item {


	/*public static void main(String[] args) {
		Product item = new Product("hello",3.2,21);
 		//how would i call the methods in the class?
 		Item s = item;
 		System.out.println(s.getItemNumber());
 		System.out.println(s.getDescription());
 		System.out.println(s.getPrice());
 		System.out.println(s.equals(3));
 	}*/
	
	private String name;
	private double price;
	private int productID;

	public Product(String name, double price, int productID){
		this.name = name;
		this.price = price;
		this.productID = productID;
	}

	@Override
	public int getItemNumber() {
		return this.productID;
	}

	@Override
	public String getDescription() {
		String description = "";
		description += ("The name of the product is " + this.name + ". The price is " + this.price + ". The Product Id is " + this.productID + ".");
		return description;
	}
	
	@Override
	public double getPrice() {
		return this.price;
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
		Product newProduct = (Product) o;
		//test equality of relevant instance variables
		return this.productID == newProduct.getItemNumber(); 
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + productID;
		//result = prime * result + ((Double)price).hashCode();
		//result = prime * result + this.name.hashCode();
		return result;
	}
}
