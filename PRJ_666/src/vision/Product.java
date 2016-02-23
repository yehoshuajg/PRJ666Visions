package vision;

public class Product {
	private int id;
	private String name;
	private String description;
	private int categoryID;
	private int subCategoryID;
	private double salePrice;
	private int quantity;
	private String notes;
	
	Product(){
		id = 0;
		name = null;
		description = null;
		categoryID = 0;
		subCategoryID = 0;
		//unitCost = 0;
		salePrice = 0;
		quantity = 0;
		//supplierID = 0;
		notes = null;
	}
	/*
	Product(int id, String name, String description, int categoryID, int subCategoryID, double unitCost, double salePrice, int quantity, int supplierID, String notes){
		this.id = id;
		this.name = name;
		this.description = description;
		this.categoryID = categoryID;
		this.subCategoryID = subCategoryID;
		this.unitCost = unitCost;
		this.salePrice = salePrice;
		this.quantity = quantity;
		this.supplierID = supplierID;
		this.notes = notes;
	}*/
	Product(int id, String name, String description, int categoryID, int subCategoryID, double salePrice, int quantity, String notes){
		this.id = id;
		this.name = name;
		this.description = description;
		this.categoryID = categoryID;
		this.subCategoryID = subCategoryID;
		//this.unitCost = unitCost;
		this.salePrice = salePrice;
		this.quantity = quantity;
		//this.supplierID = supplierID;
		this.notes = notes;
	}
	
	//Setters
	void setID(int id){
		this.id = id;
	}
	void setName(String name){
		this.name = name;
	}
	void setDescription(String description){ 
		this.description = description;
	}
	void setCategoryID(int categoryID){
		this.categoryID = categoryID;
	}
	void setSubCategoryID(int subCategoryID){
		this.subCategoryID = subCategoryID;
	}
	void setSalePrice(double salePrice){
		this.salePrice = salePrice;
	}
	void setQuantity(int quantity){
		this.quantity = quantity;
	}
	void setNotes(String notes){
		this.notes = notes;
	}
	
	//Getters
	int getID(){ return this.id; }
	String getName(){ return this.name; }
	String getDescription(){ return this.description; }
	int getCategoryID(){ return this.categoryID; }
	int getSubCategoryID(){ return this.subCategoryID; }
	double getSalePrice(){ return this.salePrice; }
	int getQuantity(){ return this.quantity; }
	String getNotes(){ return this.notes; }
}
