package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * David Yu
 * dy3834
 * 15460
 * Slip days used: <0>
 * Fall 2016
 */

import java.util.Scanner;
import java.io.*;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        System.out.println("GLHF");
        
        // TODO: remove in Stage 3
        // Create 100 Algae and 25 Craig
        try {
        	/*
        	for (int i = 0; i < 20; i++) {
        		Critter.makeCritter("assignment4.Algae");
        	}
        	*/
        	for (int i = 0; i < 50; i++) {
        		Critter.makeCritter("assignment4.Craig");
        	}
        } catch (InvalidCritterException e) {
        	System.out.println("Oops, something went wrong");
        }
        
        while (true) {
        	String rawCmd = kb.nextLine();
        	String[] input = rawCmd.trim().split("\\s+");
        	
        	// "quit" command terminates simulation
        	if (input[0].equals("quit")) {
        		break;
        	}
        	
        	// "show" command displays 2D grid
        	else if (input[0].equals("show")) {
        		Critter.displayWorld();
        	}
        	
        	// "step <count>" command performs specified number of world time steps
        	// TODO: take care of <count> argument in Stage 2
        	else if (input[0].equals("step")) {
        		Critter.worldTimeStep();
        	}
        	
        	// TODO: "seed" is Stage 2
        	else if (input[0].equals("seed")) {
        		
        	}
        	
        	// TODO: "make" is Stage 3. For 1 and 2, main() makes 100 Algae and 25 Craigs.
        	else if (input[0].equals("make")) {
        		
        	}
        	
        	// TODO: "stats" is Stage 3
        	else if (input[0].equals("stats")) {
        		
        	}
        	
        	// TODO: Invalid command handling
        	else {
        		break;
        	}
        }

        
        /*
        for (int i = 0; i < 10; i++) {
            Critter.displayWorld();
            Critter.worldTimeStep();
        }
        */
        
        /* Write your code above */
        System.out.flush();

    }
}
