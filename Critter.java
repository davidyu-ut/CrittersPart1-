package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * David Yu
 * dy3834
 * 15460
 * Slip days used: <0>
 * Fall 2016
 */


import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	private void readjust() {
		// Right wraps around to left
		if (this.x_coord > (Params.world_width - 1)) {
			this.x_coord = this.x_coord - Params.world_width;
		}
		// Left wraps around to right
		if (this.x_coord < 0) {
			this.x_coord = Params.world_width + this.x_coord;
		}
		// Up wraps around to down
		if (this.y_coord < 0) {
			this.y_coord = Params.world_height + this.y_coord;
		}
		// Down wraps around to up
		if (this.y_coord > (Params.world_height - 1)) {
			this.y_coord = this.y_coord - Params.world_height;
		}
	}
	
	protected final void walk(int direction) {
		// Critter kills himself trying to walk (not enough energy)
		// TODO: check it <= is correct, or maybe just < and add = somewhere else
		if ((this.energy - Params.walk_energy_cost) <= 0) {
			this.energy = 0;
			population.remove(this);	// remove from population
			return;
		}
		// Critter successfully walks
		else {
			this.energy -= Params.walk_energy_cost;		// Update energy cost
			switch (direction) {
			case 0:		// Move east
				this.x_coord++;
				break;
			case 1: 	// Move north-east
				this.x_coord++;
				this.y_coord--;
				break;
			case 2:		// Move north
				this.y_coord--;
				break;
			case 3:		// Move north-west
				this.x_coord--;
				this.y_coord--;
				break;
			case 4:		// Move west
				this.x_coord--;
				break;
			case 5:		// Move south-west
				this.x_coord--;
				this.y_coord++;
				break;
			case 6:		// Move south
				this.y_coord++;
				break;
			case 7:		// south-east
				this.x_coord++;
				this.y_coord++;
				break;
			default:
				break;
			}
			
			readjust();		// If critter is out of bounds, wrap around grid
		}
	}
	
	protected final void run(int direction) {
		// Critter kills himself trying to run (not enough energy)
		if ((this.energy - Params.run_energy_cost) < 0) {
			this.energy = 0;
			return;
		}
		// Critter successfully runs
		else {
			this.energy -= Params.run_energy_cost;		// Update energy cost
			switch (direction) {
			case 0:		// Move east
				this.x_coord += 2;
				break;
			case 1: 	// Move north-east
				this.x_coord += 2;
				this.y_coord -= 2;
				break;
			case 2:		// Move north
				this.y_coord -= 2;
				break;
			case 3:		// Move north-west
				this.x_coord -= 2;
				this.y_coord -= 2;
				break;
			case 4:		// Move west
				this.x_coord -= 2;
				break;
			case 5:		// Move south-west
				this.x_coord -= 2;
				this.y_coord += 2;
				break;
			case 6:		// Move south
				this.y_coord += 2;
				break;
			case 7:		// south-east
				this.x_coord += 2;
				this.y_coord += 2;
				break;
			default:
				break;
			}
			
			readjust();		// If critter is out of bounds, wrap around grid
		}
	}
	
	protected final void reproduce(Critter offspring, int direction) {
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name is the name of the Critter class
	 * @throws InvalidCritterException if specified an unknown Critter class
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			Class critter = Class.forName(critter_class_name);
			Critter newCritter = (Critter)critter.newInstance();
			population.add(newCritter);	// Add newly created critter to population
		
			newCritter.x_coord = getRandomInt(Params.world_width - 1);	// Random x-coor
			newCritter.y_coord = getRandomInt(Params.world_height - 1);	// Random y-coor
			newCritter.energy = Params.start_energy;	// Initial energy
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
	
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	private static boolean samePosition(Critter crit1, Critter crit2) {
		if ((crit1.x_coord == crit2.x_coord) && (crit1.y_coord == crit2.y_coord)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		// Complete this method.
	}
	
	public static void worldTimeStep() {
		// Complete this method.
		
		//TODO: increment (aka keep track of) timestep??
		
		// Allow each Critter to do timestep (move, reproduce, etc.)
		for (Critter currCritter : population) {
			currCritter.doTimeStep();
		}
		
		// Add Critters with same position into one array list
		ArrayList<Critter> conflict = new ArrayList<Critter>();
		for (int slow = 0; slow < population.size(); slow++) {
			Critter crit1 = population.get(slow);
			for (int fast = slow + 1; fast < population.size(); fast++) {
				Critter crit2 = population.get(fast);
				
				if (samePosition(crit1, crit2)) {
					if (!conflict.contains(crit1)) {
						conflict.add(crit1);
					}
					if (!conflict.contains(crit2)) {
						conflict.add(crit2);
					}
				}
				
			}
		}
		
		// Resolve conflict
		for (int ind = 0; ind < conflict.size(); ind++) {
			int crit1Roll, crit2Roll;								// What each creature rolls
			boolean crit1Removed = false, crit2Removed = false;		// Facilitates indexing
			List<Critter> toRemove = new ArrayList<Critter>();		// Which creatures have died
			int slow = 0, fast = 0;									// Pointers into population
			while (slow < conflict.size()) {
				Critter crit1 = conflict.get(slow);
				fast = slow + 1;
				while (fast < conflict.size()) {
					crit1Removed = false;
					crit2Removed = false;
					Critter crit2 = conflict.get(fast);
					
					// If two creatures are in the same position
					if (samePosition(crit1, crit2)) {
						// Invoke both fights to determine Critters' intentions
						boolean crit1Fight = crit1.fight(crit2.toString());
						boolean crit2Fight = crit2.fight(crit1.toString());
					
						// After fight() invoked, both Critters are still alive and in same position
						if ((crit1.energy > 0) && (crit2.energy > 0) && samePosition(crit1, crit2)) {						
							if (!crit1Fight) {	// crit1 did not intend to fight
								crit1Roll = 0;
							} else {			// crit1 did intend to fight
								crit1Roll = getRandomInt(crit1.energy);	// Random # between 0 and crit1.energy
							}
							
							if (!crit2Fight) {	// crit2 did not intend to fight
								crit2Roll = 0;
							} else {			// crit2 did intend to fight
								crit2Roll = getRandomInt(crit2.energy);	// Random # between 0 and crit2.energy
							}
							
							// crit1 won the fight
							// If rolls are equal, crit1 arbitrarily wins
							if (crit1Roll >= crit2Roll) {
								crit1.energy += (Math.floor(crit2.energy / 2));	// Half of loser's energy goes to winner
								crit2.energy = 0;	// crit2 has no energy
								crit2Removed = true;
							}
							
							// crit2 won the fight
							if (crit2Roll > crit1Roll) {
								crit2.energy += (Math.floor(crit1.energy / 2));	//Half of loser's energy goes to winner
								crit1.energy = 0;	// crit1 has no energy
								crit1Removed = true;
							}
						}
	
						// crit1 energy depleted
						if (crit1.energy <= 0) {
							toRemove.add(crit1);
							conflict.remove(crit1);		// Remove from population
						}
							
						// crit2 energy depleted
						if (crit2.energy <= 0) {
							toRemove.add(crit2);
							conflict.remove(crit2);		// Remove from population
						}
						
						// Take care of indices after removing elements
						if (crit1Removed || crit2Removed) {
							slow = 0;
							fast = slow;
						}
						
					}
					fast++;
				}	
				slow++;
			}		
			population.removeAll(toRemove);		// Remove Critters who have died
		}
		// TODO: remove once done testing
		for (int lag = 0; lag < population.size(); lag++) {
			Critter crit1 = population.get(lag);
			for (int lead = lag + 1; lead < population.size(); lead++) {
				Critter crit2 = population.get(lead);
				if (samePosition(crit1, crit2)) {
					System.out.println("We got a problem!");
				}
			}
		}
		
		// Update rest energy
		List<Critter> toRemove = new ArrayList<Critter>();
		for (Critter currCritter : population) {
			currCritter.energy -= Params.rest_energy_cost;
			if (currCritter.energy <= 0) {
				toRemove.add(currCritter);
			}
		}
		population.removeAll(toRemove);
	}
	
	public static void displayWorld() {
		// Complete this method.
		
		// Initialize 2D grid with single space " "
		String[][] grid = new String[Params.world_height][Params.world_width]; 
		for (int row = 0; row < Params.world_height; row++) {
			for (int col = 0; col < Params.world_width; col++) {
				grid[row][col] = " ";
			}
		}
		
		// Place Critters on grid
		for (Critter currCritter : population) {
			// TODO: check if this access is correct
			grid[currCritter.y_coord][currCritter.x_coord] = currCritter.toString();
		}
		
		// Print top border
		System.out.print("+");
		for (int col = 0; col < Params.world_width; col++) {
			System.out.print("-");
		}
		System.out.println("+");
		
		// Print grid
		for (int row = 0; row < Params.world_height; row++) {
			System.out.print("|");
			for (int col = 0; col < Params.world_width; col++) {
				System.out.print(grid[row][col]);
			}
			System.out.println("|");
		}
		
		// Print bottom border
		System.out.print("+");
		for (int col = 0; col < Params.world_width; col++) {
			System.out.print("-");
		}
		System.out.println("+");
		System.out.println(population.size());		//TODO: Remove when done testing
	}
}
