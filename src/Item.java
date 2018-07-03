
public class Item {

	private String name;
	private String type;
	private String description;
	private double price;
	private String size;
	private boolean droppable; // Whether you can drop the item on the ground
	private boolean locked;

	public Item(String pName, String pType, String pDes, double pPrice, String pSize) {
		name = pName;
		type = pType;
		description = pDes;
		price = pPrice;
		size = pSize; 
		droppable = true;
		locked = false;
	}

	public String getName() { return name; }
	public String getType() { return type; }
	public String getDescription() { return description; }
	public String getSize() { return size; } 
	public double getPrice() { return price; }
	public boolean getDroppable() { return droppable; }
	public boolean getLocked() { return locked; }
	public void setName(String pName) { name = pName; }
	public void setType(String pType) { type = pType; }
	public void setDescription(String pDes) { description = pDes; }
	public void setPrice(double pPrice) { price = pPrice; }
	public void setSize(String pSize) { size = pSize; } 	
	public void setDroppable(boolean pDrop) { droppable = pDrop; }
	public void setLocked(boolean pLocked) { locked = pLocked; }
	
	
	@Override
	public String toString() {
		String ret = "Item:\n";
		ret += "\tshortName: " + name + "\n";
		ret += "\ttype: " + type + "\n";
		ret += "\tdescription: " + description + "\n";
		ret += "\tsize: " + size + "\n";
		return ret;
	}
}


