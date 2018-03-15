package assignment4;

// smart critter
public class Critter2 extends Critter {
	private int prevEnergy;
	private boolean movedThisStep;
	
	public Critter2() {
		prevEnergy = Params.start_energy;
		movedThisStep = false;
	}
	
	@Override
	public void doTimeStep() {
		movedThisStep = false;
		// If our energy went up (won a fight against Algae), reproduce if we can
		if ((this.getEnergy() > prevEnergy) && (this.getEnergy() >= Params.min_reproduce_energy)) {
			try {
				Class critter = Class.forName("assignment4.Critter2");
				Critter childCritter = (Critter)critter.newInstance();
				reproduce(childCritter, getRandomInt(8));
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				System.out.println("Cannot reproduce for Critter2");
			}
		}
		
		// Only move when low on energy (<50%) to look for Algae
		if (this.getEnergy() < (Params.start_energy / 2)) {
			walk(getRandomInt(8));	// Move in random direction looking for Algae
			movedThisStep = true;
		}
		
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
			return false;
		} 
		// Try to walk away
		else if (this.getEnergy() > Params.walk_energy_cost) {
			walk(getRandomInt(8));
			return false;
		}
		// Not enough energy to move away, so fight
		else {
			return true;
		}
	}
	
	public String toString() {
		return "S";
	}
}
