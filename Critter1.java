package assignment4;

// Predatory critter
public class Critter1 extends Critter {
	private int origin;
	
	public Critter1() {
		// Pick random direction we don't go towards upon first creation
		origin = Critter.getRandomInt(8);
	}
	
	
	@Override
	public void doTimeStep() {
		int currEnergy = this.getEnergy();
		int moveDir = Critter.getRandomInt(8);
		
		// Don't attempt to move in direction we came from (don't backtrack)
		while (moveDir == origin) {
			moveDir = Critter.getRandomInt(8);
		}
		
		// We are on low energy (less than 10% of starting), so do nothing
		if (currEnergy <= (Params.start_energy * 0.10)) {
			return;
		}
		
		// If we have enough energy to run at least 3 more times, run
		else if (currEnergy >= (Params.run_energy_cost * 3)) {
			run(moveDir);
		}
		
		// We have medium energy, so walk
		else {
			walk(moveDir);
		}

	}

	@Override
	public boolean fight(String opponent) {
		// Opponent is Algae
		if (opponent.equals("@")) {
			return true;
		}
		
		// We have less than 50% of starting energy going into fight
		if ((this.getEnergy() < (Params.start_energy / 2)) && (this.getEnergy() >= Params.min_reproduce_energy)) {
			// Reproduce before fighting as backup
			try {
				Class critter = Class.forName("assignment4.Critter1");
				Critter childCritter = (Critter)critter.newInstance();
				reproduce(childCritter, getRandomInt(8));
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				System.out.println("Cannot reproduce for Critter1");
			}
		}
		return true;
	}
	
	public String toString() {
		return "P";
	}
}
