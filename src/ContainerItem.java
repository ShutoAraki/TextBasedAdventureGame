import java.util.ArrayList; 

public class ContainerItem extends Item {
	private ArrayList<Item> collection;
	private int capacity;
	
	public ContainerItem(String pName, String pType, String pDes, double pPrice, String pSize) {
		super(pName, pType, pDes, pPrice, pSize);
		collection = new ArrayList<Item>(); 
		capacity = 5;
	}
	
	public void addItem(Item pItem) {
		collection.add(pItem);
	}
	
	public boolean isOverCapacity() {
		return collection.size() >= capacity;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int pCap) {
		capacity = pCap;
	}
	
	public Item removeItemByName(String pName) {
		String lName = pName.toLowerCase();
		for(Item i : collection) {
			if(i.getName().toLowerCase().equals(lName)) {
				collection.remove(i); 
				return i; 
			}
		}
		return null; 
	}
	
	public Item removeItemByIndex(int index) {
		if (index < 0 || collection.size() <= index) {
			return null; 
		}
		return collection.remove(index); 
	}
	
	public Item getItemByName(String pName) {
		for (Item i : collection) {
			if (i.getName().toLowerCase().equals(pName.toLowerCase()))
				return i;
		}
		return null;
	}
	
	public int countItem() {
		return collection.size(); 
	}

	public boolean findItem(String pName) {
		String lName = pName.toLowerCase();
		for (Item i : collection) {
			if (i.getName().toLowerCase().equals(lName))
				return true;
		}
		return false;
	}
	
	public boolean contains(Item item) {
		String lName = item.getName().toLowerCase();
		for (Item i : collection) {
			if (i.getName().toLowerCase().equals(lName))
				return true;
		}
		return false;
	}
	
	public String getDescription() {
		String ret = super.getDescription() + "\n";
		ret += "Items:\n";
		for (Item i : collection) {
			ret += "\t" + i.getName() + ": " + i.getDescription() + "\n";
		}
		return ret;
	}
	
	public String getItemNames() {
		String ret = "\n";
		
		for (Item i : collection) {
			ret += "\t\t" + i.getName() + "\n";
		}
		
		return ret;
	}
	
}
