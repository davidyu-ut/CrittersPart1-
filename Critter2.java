package assignment4;

// smart critter
public class Critter2 extends Critter {
	private int prevEnergy;
	private boolean movedThisStep;
	private boolean walked;
	private boolean ran;
	private static int numChildren;
	
	public Critter2() {
		prevEnergy = Params.start_energy;
		movedThisStep = false;
		walked = false;
		ran = false;
	}
	
	@Override
	public void doTimeStep() {
		movedThisStep = false;
		// If we acquired energy in our previous time step, reproduce
		int compensation = 0;		// need to add this to current energy to compensate for movement + rest energy
		if (walked) {
			compensation = Params.walk_energy_cost + Params.rest_energy_cost;
		} else if (ran) {
			compensation = Params.run_energy_cost + Params.rest_energy_cost;
		} else if (this.getEnergy() != Params.start_energy){
			compensation = Params.rest_energy_cost;
		}
		if (((this.getEnergy() + compensation) > prevEnergy) && (this.getEnergy() >= Params.min_reproduce_energy)) {
			try {
				Class critter = Class.forName("assignment4.Critter2");
				Critter childCritter = (Critter)critter.newInstance();
				reproduce(childCritter, getRandomInt(8));
				numChildren++;
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				System.out.println("Cannot reproduce for Critter2");
			}
		}
		
		// Only move when low on energy (<50%) to look for Algae
		if (this.getEnergy() < (Params.start_energy / 2)) {
			walk(getRandomInt(8));	// Move in random direction looking for Algae
			movedThisStep = true;
			walked = true;
		} else {
			walked = false;
		}
		ran = false;
		
		// Update previous energy
		prevEnergy = this.getEnergy();
	}

	@Override
	public boolean fight(String opponent) {
		// Fight if opponent is Algae, or cannot move again (already moved this step)
		if (opponent.equals("@") || movedThisStep) {
			return true;
		}
		
		// Try to run away
		if (this.getEnergy() > Params.run_energy_cost) {
			run(getRandomInt(8));
			ran = true;
			walked = false;
			return false;
		} 
		// Try to walk away
		else if (this.getEnergy() > Params.walk_energy_cost) {
			walk(getRandomInt(8));
			walked = true;
			ran = false;
			return false;
		}
		// Not enough energy to move away, so fight
		else {
			walked = false;
			ran = false;
			return true;
		}
	}
	
	public String toString() {
		return "S";
	}
	
	public static void runStats(java.util.List<Critter> critter2s) {
		int totNumChildren = 0;
		for (Object obj : critter2s) {
			Critter2 currCritter2 = (Critter2) obj;
			totNumChildren += currCritter2.numChildren;
		}
		System.out.println(critter2s.size() + " total Critter2s");
		System.out.println("Number of naturally reproduced Critter2s: " + totNumChildren);
	}
}
