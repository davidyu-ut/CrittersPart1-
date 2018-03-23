# CrittersPart1-

Main.java
This is the driver for this assignment. Recognized commands:
quit - terminates the program
show - displays the 2d grid with alive critters
step [<count>] - performs world time steps for all alive critters 
seed <number> - sets seed for the random number generator
make <class_name> [<count>] - creates instance of specified critter
stats <class_name> - provides statistics about specified critter


Critter.java
The adult critter population is held in an ArrayList<Critter> while the newborn Critters are
held in a different ArrayList<Critter>. Since Critter is an abstract class, each class that
implements in must have its own doTimeStep() and fight() methods.


Critter1.java
Critter1 is classified as an aggressive/predatory Critter. It runs to search for prey if it has
high energy (energy > (3*running_energy_cost)), walks if it has low energy, and remains dormant
if it is about to run out of energy. Critter1 never backtracks.

Critter1 always returns true if given an opportunity to fight, but will reproduce during a fight 
if it has less than 50% of starting energy. It is represented by a "P" on the grid.


Critter2.java
Critter2 is classified as a passive/reactive Critter. It only moves when low on energy to look 
for Algae or to run during a fight. Critter2 reproduces if it gained energy in the last time 
step (ate Algae). It is represented by an "S" on the grid.
