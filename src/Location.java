import java.util.ArrayList;
import java.util.HashMap;

public class Location {

	private String name;
	private String description;
	private ArrayList<Item> items;
	private HashMap<String, Location> map;
	private boolean dark;
	private boolean locked;
	
	public Location(String pName, String pDes) {
		name = pName;
		description = pDes;
		items = new ArrayList<Item>();
		map = new HashMap<String, Location>();
		dark = false;
		locked = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getDark() {
		return dark;
	}
	
	public void setDark(boolean pDark) {
		dark = pDark;
	}
	
	public boolean getLocked() {
		return locked;
	}
	
	public void setLocked(boolean pLocked) {
		locked = pLocked;
	}
	
	
	/**
	 * Adds an item into this location
	 * @param item: An item that will be added
	 */
	public void addItem(Item item) {
		items.add(item);
	}

	public Item removeItem(String pName) {
		String lName = pName.toLowerCase();
		for(Item i : items) {
			if(i.getName().toLowerCase().equals(lName)) {
				items.remove(i); 
				return i; 
			}
		}
		return null;
	}

	/**
	 * Gets an item from the list given its short name
	 * @param pName: short name of an item
	 * @return a first item in the list that matches with the given short name
	 */
	public Item getItemByName(String pName) {
		for (Item i : items) {
			if (i.getName().toLowerCase().equals(pName)) {
				return i;
			}
		}
		return null;
	}

	/**
	 * Returns the number of items in this location
	 */
	public int numOfItems() {
		return items.size();
	}

	/**
	 * Gets an item from the list given its index
	 * Make sure the index is within the range!
	 * Otherwise it returns null
	 */
	public Item getItemByIndex(int index) {
		if (index < 0 || items.size() <= index) {
			return null;
		}
		return items.get(index);
	}
	
	public void addLink(String dir, Location loc) {
		
		switch (dir.toLowerCase()) {
		
		case "north":
			map.put("north", loc);
			loc.map.put("south", this);
			break;
		case "south":
			map.put("south", loc);
			loc.map.put("north", this);
			break;
		case "west":
			map.put("west", loc);
			loc.map.put("east", this);
			break;
		case "east":
			map.put("east", loc);
			loc.map.put("west", this);
			break;
		default:
			break;
		
		}
		
	}
	
	public Location getLocation(String dir) {
		if (map.containsKey(dir))
			return map.get(dir);
		return null;
	}
	
	public String toString() {
		
		if (dark) 
			return "It's too dark! I can't see anything...\n";
		else if (locked)
			return "You can't go across this location!";
		
		String ret = "Location: " + name + "\n";
		ret += "Description: \n\t" + description + "\n";
		ret += "Items: \n";
		
		for (Item i : items) {
			if (i instanceof ContainerItem) {
				ret += "\t" + i.getName() + " [Container]" + "\n";
				ret += "\t" + ((ContainerItem) i).getItemNames() + "\n";
			}
			else
				ret += "\t" + i.getName() + "\n";
		}
		
		return ret;
	}
	
	
}
