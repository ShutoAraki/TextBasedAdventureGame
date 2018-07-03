import java.util.Scanner;


public class Driver {

	// Containers
	static ContainerItem myInventory = new ContainerItem("Backpack", "bag", "A handy backpack that contains your tools for hunting and survival in the"
			+ " Alaskan wilderness", 10.0, "medium");	

	public static void help() {
		System.out.println("----------INSTRUCTIONS----------");
		System.out.println();
		System.out.println("look");
		System.out.println("\tlooks around and tells you where you are and what you see");
		System.out.println();
		System.out.println("examine [name of an item]");
		System.out.println("\ttells you the description of the item in your location");
		System.out.println();
		System.out.println("drop [name of an item]");
		System.out.println("\tdrops the specified item from your belt to the ground");
		System.out.println();
		System.out.println("take [name] <from [container]>");
		System.out.println("\ttakes an item from your location and put it into your backpack\n"
				+ "\t(optional) You can take from a specific container instead of the ground");
		System.out.println();
		System.out.println("put [name] in [container]");
		System.out.println("\ttransfers an item into a specified container");
		System.out.println();
		System.out.println("backpack");
		System.out.println("\tlists the items in your backpack");
		System.out.println();
		System.out.println("go [direction]");
		System.out.println("\tYou can move to other locations in Alaska.");
		System.out.println();
		System.out.println("use [name]");
		System.out.println("\tuses an item from your backpack");
		System.out.println();
		System.out.println("map");
		System.out.println("\tshows a partially completed map");
		System.out.println();
		System.out.println("help");
		System.out.println("\tcalls this help function");
		System.out.println();
		System.out.println("quit");
		System.out.println("\tquits the game");
		System.out.println();
		System.out.println("----------END----------");
	}


	public static void wait(int milSec) {

		try {
			Thread.sleep(milSec);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

	}


	public static void startGameAt(Location location) {

		// Commands
		while (true) {

			System.out.print("Command?: ");
			Scanner s = new Scanner(System.in);
			String line = s.nextLine();
			String[] input = line.split(" ");

			switch (input[0].toLowerCase()) {

			case "quit":
				s.close();
				System.exit(0);

			case "look":
				System.out.print(location);
				break;

			case "examine":

				if (location.getDark()) {
					System.out.println("It's too dark! I can't see anything...\n");
					break;
				}
				else if (input.length == 2) {

					String shortName = input[1].toLowerCase();

					if (location.getItemByName(shortName) != null)
						System.out.println(location.getItemByName(shortName).getDescription());
					else {
						System.out.println("There is no item of the name you specified.");
						break;
					}

				} else
					System.out.println("You have to specify the name of an item you would like to examine.");

				break;


			case "drop" : 

				if (input.length == 2) {

					String name = input[1].toLowerCase(); 

					if (myInventory.findItem(name)) {
						if (!myInventory.getItemByName(name).getDroppable())
							System.out.println("You can't drop this item! It's too important!");
						else {
							location.addItem(myInventory.removeItemByName(name)); 
							System.out.println("You dropped " + name + " on the ground.");
						}

					} else {
						System.out.println("There is no item of the name you specified.");
					}

				} else {
					System.out.println("Incorrect command: drop [name]");
				}

				break;

			case "backpack":

				if (myInventory.countItem() == 0) {
					System.out.println("You don't have anything in your backpack.");
				} else {
					System.out.print(myInventory.getDescription());
				}

				break; 

			case "put": 
				if (input.length == 4) { 

					String name = input[1].toLowerCase(); 
					if (!input[2].equals("in")) {
						System.out.println("Incorrect command: put [name] in [container]");
						break;
					}
					String cont = input[3].toLowerCase(); 
					if(location.getItemByName(cont) != null && location.getItemByName(cont) instanceof ContainerItem && !location.getItemByName(cont).getLocked()) {
						if(myInventory.findItem(name)) {
							((ContainerItem) location.getItemByName(cont)).addItem(myInventory.removeItemByName(name));
							System.out.println("You put " + name + " in " + cont + " from your backpack!");
						}
						else 
							System.out.println("There is no such item on your backpack.");
					} else if (location.getItemByName(cont).getLocked())
						System.out.println("It is locked!");
					else
						System.out.println("There is no such container.");

				} else 
					System.out.println("Incorrect command: put [name] in [container]"); 

				break; 

			case "build": 
				if(myInventory.findItem("wood") && myInventory.findItem("vines")) { 
					if(!myInventory.findItem("raft"))  {
						myInventory.addItem(new Item("Raft", "vehicle", "You can cross the river!", 1000.0, "big"));
						myInventory.removeItemByName("wood");
						myInventory.removeItemByName("vines");

						for (int i = 0; i < 5; i++) {
							System.out.print(".");
							wait(500);
						}

						System.out.println("You used the wood and vines to create a floatable raft!");
						System.out.println("Raft added to backpack");

					} else {
						System.out.println("You already have a raft.");
					}
				} else {
					System.out.println("Not enough raw materials.");
				}
				
				break;

			case "take":

				if (input.length == 4) {
					String name = input[1].toLowerCase();	

					if (input[2].toLowerCase().equals("from")) { // take [name] from [container]
						String c = input[3].toLowerCase();
						if (!location.getItemByName(c).getLocked()) {
							if (location.getItemByName(c) != null && location.getItemByName(c) instanceof ContainerItem) {

								if (((ContainerItem) location.getItemByName(c)).findItem(name)) {
									myInventory.addItem(((ContainerItem) location.getItemByName(c)).removeItemByName(name));
									System.out.println("You took " + name + " from " + ((ContainerItem) location.getItemByName(c)).getName() + "!");
								}
								else
									System.out.println("There's no such item in the " + ((ContainerItem) location.getItemByName(c)).getName() + ".");

							} else {
								System.out.println("Such container does not exist.");
							}
						} else {
							System.out.println("It is locked!");
						}

					} else {
						System.out.println("The command is incorrect: take [name] <from [container]>");
					}
				} else if (input.length == 2) {

					String name = input[1].toLowerCase();

					if (location.getItemByName(name) != null) {
						if (location.getItemByName(name) instanceof ContainerItem)
							System.out.println("You can't take this " + name + "! It's too big!");
						else {
							if (myInventory.isOverCapacity()) {
								System.out.println("You can only store " + myInventory.getCapacity() + " items in backpack.");
							} else {
								myInventory.addItem(location.removeItem(name));
								System.out.println("You took " + name + " and put it in your backpack!");
							}
						}

					} else {
						System.out.println("Such item does not exist.");
					}

				} else {
					System.out.println("The command is incorrect: take [name] <from [container]>");
				}

				break;

			case "go":

				if (input.length == 2) {

					String dir = input[1].toLowerCase();
					if (!(dir.equals("north") || dir.equals("south") || dir.equals("west") || dir.equals("east"))) {

						System.out.println("The direction has to be north, south, west, or east.");

					} else if (location.getDark() && location.getLocked()) {

						System.out.println("It's too dark. You can't go anywhere. Now you are back to Camp.\n");
						startGameAt(location.getLocation("north"));

					} else if (location.getLocked()) {
						
						System.out.println("You can't go across this location! You are now back at cave");
						startGameAt(location.getLocation("north"));
						
					} else if (location.getLocation(dir) == null) {

						System.out.println("Ouch! You just ran straight into the wall! Don't go that way!!");

					} else {

						String place = location.getLocation(dir).getName();

						if (place.toLowerCase().equals("cave") || place.toLowerCase().equals("woods"))
							location.getLocation(dir).setDark(true);

						if (location.getLocation(dir).getDark())
							place = "somewhere dark";

						System.out.println("You are now in " + place + "\n");

						startGameAt(location.getLocation(dir));

					}

				}
				else if (input.length == 1) 
					System.out.println("You have to specify the direction (north, south, east, or west).");
				else 
					System.out.println("Incorrect command. It's go [direction (north, south, east, or west)]");
				break;

			case "use":

				if (input.length == 2) {
					String item = input[1].toLowerCase();
					if (myInventory.findItem(item)) {
						if (item.equals("flashlight")) {
							if (location.getDark()) {
								System.out.println("Now you can see everything!");
								location.setDark(false);
								location.setLocked(false);

								System.out.println();
								if (location.getName().equals("Cave") && location.getItemByName("bear") != null) {

									wait(1500);
									for (int i = 0; i < 5; i++) {
										System.out.print(".");
										wait(500);
									}

									((Creature) location.getItemByName("bear")).roar();

									if (myInventory.findItem("shotgun")) {
										wait(1000);
										System.out.println("The lengendary Bear!!!");
										System.out.print("Use the shotgun in your backpack? (Yes/No): ");
										Scanner s2 = new Scanner(System.in); // Don't close this scanner.
										String ans = s2.nextLine();

										if (ans.toLowerCase().equals("yes")) {
											System.out.println("BAAAAN");
											wait(1000);

											for (int i = 0; i < 3; i++) {
												System.out.print(".");
												wait(500);
											}

											System.out.println("Successfully hunted! The pathway appeared.");
											myInventory.addItem(location.removeItem("bear"));

										}
										else if (ans.toLowerCase().equals("no")) {
											System.out.println("GAME OVER: The bear ate you. I don't understand why you didn't use your shotgun. You just suck at hunting.");
											System.exit(0);
										} else {
											System.out.println("GAME OVER: The bear ate you. I told you to answer by yes or no. Bigfoot doesn't wait for you.");
											System.exit(0);
										}
									} else {
										wait(1000);
										System.out.println("Noooooo I forgot my shotg...");
										wait(2000);
										System.out.println("GAME OVER: The bear ate you.");
										System.exit(0);
									}


								} else
									System.out.println("It's already bright here...");
							}

						} else if (item.equals("compass")) {

							if(location.getDark()) {
								System.out.println("The compass does not even work in this dark place...");
							} else {

								System.out.println("North: ");
								if (location.getLocation("north") != null)
									System.out.println("\t" + location.getLocation("north").getName());
								else
									System.out.println("\tThe Unknown");
								System.out.println("South: ");
								if (location.getLocation("south") != null)
									System.out.println("\t" + location.getLocation("south").getName());
								else
									System.out.println("\tThe Unknown");
								System.out.println("East: ");
								if (location.getLocation("east") != null)
									System.out.println("\t" + location.getLocation("east").getName());
								else
									System.out.println("\tThe Unknown");
								System.out.println("West: ");
								if (location.getLocation("west") != null)
									System.out.println("\t" + location.getLocation("west").getName());
								else
									System.out.println("\tA wall");

							}

						} else if (item.equals("key")) {

							if (location.getItemByName("chest") != null) {
								location.getItemByName("chest").setLocked(false);
								System.out.println("You unlocked the chest!");
							} else {
								System.out.println("You can't use the key here.");
							}

						} else if (item.equals("raft")) {
							if(myInventory.findItem("raft")) {
								if (location.getName().equals("river")) {

									for (int i = 0; i < 5; i++) {
										System.out.print(".");
										wait(500);
									}

									System.out.println("You used the raft to cross the river!");
									System.out.println("You are at a road!");
									startGameAt(location.getLocation("east"));
								}
							}

						} else if (item.equals("truck")) {
							System.out.println("Congrats! You win the game. Thanks for playing!");
							System.exit(0);
						}
						else 
							System.out.println("I don't know how to use this item here...");

					} else
						System.out.println("No such item found in your backpack.");

				} else
					System.out.println("Incorrect command: use [name]");


				break;


			case "map":

				String cave = "????";
				if (location.getName().equals("Cave") && !location.getDark())
					cave = "Cave";

				
				
				
			    System.out.println("                       ------------                      ");
                System.out.println("                       |          |                     ");
                System.out.println("                       | Mountain |                     ");
                System.out.println("                       |          |                     ");
				System.out.println("                       -----  -----                     ");
				System.out.println("                       |          |                     ");
				System.out.println("                       |  Plains  |                     ");
				System.out.println("                       |          |                     ");
				System.out.println("            ----------------| |--------------------        ");
				System.out.println("            |          |          |    Deserted   |        ");
				System.out.println("            |   Woods  =   Camp   =    Science    =        ");
				System.out.println("            |          |          |       Lab     |        ");
				System.out.println("            ----------------| |--------------------         ");
				System.out.println("                       |          |                   ");
				System.out.println("                       |   " + cave + "   |                   ");
				System.out.println("                       |          |                   ");
				System.out.println("                       -----| |-------------     ---------- ");
				System.out.println("                       |          |        |     |        | ");
				System.out.println("                       |  River   =  Road  =======  ????  | ");
				System.out.println("                       |          |        |     |        | ");
				System.out.println("                       ---------------------     ---------- ");

				break;

			case "help":

				help();

				break;

			default:
				System.out.println("I don't know how to do that");
				break;
			}
			System.out.println();
		}
	}





	public static void main(String[] args) {

		//Containers 

		ContainerItem chest = new ContainerItem("Chest", "storage", "You can store up to 15 items in this chest.", 10.0, "middle");
		chest.setCapacity(15);
		Item flashlight = new Item("Flashlight", "light", "It is used to find your way in dark areas.", 20.0, "small");
		flashlight.setDroppable(false);
		ContainerItem weaponsCloset = new ContainerItem("Weapon Closet", "storage", "A closet that might contain high tech weapons", 10.0, "large");

		// Locations

		//Location #1 
		Location camp =  new Location("Camp", "We are in Alaska for a hunt!");

		camp.addItem(new Item("Pickaxe", "tool", "It is used to destroy or dig something.", 30.0, "small"));
		camp.addItem(flashlight);
		camp.addItem(new Item("Compass", "instrument", "An intrument to find your way and see your surroundings", 10.0, "small"));
		camp.addItem(new Item("Lighter", "device", "Comes in handy to light some fire. Also, doesn't hurt to smoke a cig' or two in such weather?", 1000.0, "small"));
		camp.addItem(new Item("Snowboots", "footwear", "They keeps your feet warm in a cold environment", 49.9, "small"));
		camp.addItem(chest);
		chest.addItem(new Item("Shotgun", "weapon", "A weapon to stay protected against Alaskan bears and to hunt other dangerous creatures", 500.0, "medium"));
		chest.setCapacity(10);
		chest.setLocked(true);

		//Location #2
		Location desertedLab = new Location("Deserted Science Lab", "A research lab that was once used to build weapons of mass destruction, what mysteries does it hold?"); // This is a secret place
		desertedLab.addItem(new Item("Flamethrower", "weapon", "A very powerful weapon that can be used against almost any creature, but that was specially built here to fend off a mysterious creature that roams the Alaskan highlands", 500.0, "medium"));
		desertedLab.addItem(new Item("Potion", "health", "A highly preserved energy drink, that gives energy and increases life", 500.0, ""));
		desertedLab.addItem(weaponsCloset);
		weaponsCloset.setCapacity(15);

		//Location #3
		Location desertedLabBack = new Location("Deserted Science Lab - Back ", "The back of the science lab, where "); // This is a secret place
		desertedLabBack.addItem(new Item("Truck" , "Vehicle", "An abandoned army truck that looks like its in good shape! Can this be our redemption?", 0.0, "large"));

		//Location #4
		Location plains = new Location("Plains", ""); // This is a secret place
		plains.addItem(new Item("Document", "document", "There seems to be a hidden location in the east of Desrted Science Lab", 0.0, "small"));

		//Location #5
		Location cave = new Location("Cave", "A dark cave where you can seek shelter, but one where finding your path is not easy.");
		cave.addItem(new Creature("Bear", "Animal", "Fluffy, but not always friendly", 10000.0, "large"));
		cave.setDark(true);
		cave.setLocked(true);

		//Location #6
		Location road = new Location("Road", "The only road in the Alaskan wilderness where you might find a ride home.");

		//Location #7
		Location river = new Location("River", "Fast flowing, river. A possible obstacle between you and what is beyond. Is there a way to get across...?");
		river.addItem(new Item("Trout Fish", "health", "Good source of nutrition - keeps you healthy", 0.0, "small"));
		river.setLocked(true);
		
		//Location #8
		Location woods = new Location("Woods", "Refuge from the cold with lots of....wood");
		woods.addItem(new Item("Wood", "raw material", "This could come in handy", 0.0, "medium"));
		woods.addItem(new Item("Vines", "raw material", "Strong, flexible vines that can be used to hold things together ", 0.0, "long"));

		//Location #9
		Location mountain = new Location("Mountain", " A lofty icy mountain where a blizzards blow and a mysterious creature resides according to folklore");
		mountain.addItem(new Item("Key", "secret key", "It seems like it can open something.", 0.0, "small"));

		//Location #10
		Location home = new Location("Home", "We are back to Julian Science and Mathematics Center Room 278! Professor Chad Byers is here!");

		// The Map construction
		camp.addLink("south", cave);
		camp.addLink("west", woods);
		camp.addLink("north", plains);
		camp.addLink("east", desertedLab);
		desertedLab.addLink("north", desertedLabBack);
		plains.addLink("north", mountain);
		cave.addLink("south", river);
		river.addLink("east", road);
		road.addLink("east", home);


		// Start the game at the initial location: Alaska
		startGameAt(camp);




	}

}

