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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
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
        /*
        try {
        	
        	for (int i = 0; i < 5; i++) {
        		Critter.makeCritter("assignment4.Algae");
        	}
        	
        	
        	for (int i = 0; i < 10; i++) {
        		Critter.makeCritter("assignment4.Craig");
        	}
        	
        	
        	for (int i = 0; i < 5; i++) {
        		Critter.makeCritter("assignment4.Critter1");
        	}
        	
        	
        	for (int i = 0; i < 20; i++) {
        		Critter.makeCritter("assignment4.Critter2");
        	}
        	
        } catch (InvalidCritterException e) {
        	System.out.println("Oops, something went wrong");
        }
        */
        
        while (true) {
        	String rawCmd = kb.nextLine();
        	String[] inputArr = rawCmd.trim().split("\\s+");
        	ArrayList<String> input = new ArrayList<String>(Arrays.asList(inputArr));
        	ListIterator<String> params = input.listIterator();
        	String cmd = null;
        	
        	if (params.hasNext()) {
        		cmd = params.next();
        	}
        	
        	// "quit" command terminates simulation
        	if (cmd.equals("quit")) {
        		// Additional parameters considered parsing error
        		if (params.hasNext()) {
        			System.out.print("invalid command: " + cmd);
        			while (params.hasNext()) {
        				System.out.print(" " + params.next());
            		}
            		System.out.print("\n");
            		continue;
        		}
        		
        		break;
        	}
        	
        	// "show" command displays 2D grid
        	else if (cmd.equals("show")) {
        		// Additional parameters considered parsing error
        		if (params.hasNext()) {
        			System.out.print("invalid command: " + cmd);
        			while (params.hasNext()) {
        				System.out.print(" " + params.next());
            		}
            		System.out.print("\n");
            		continue;
        		}
        		
        		Critter.displayWorld();
        	}
        	
        	// "step <count>" command performs specified number of world time steps
        	else if (cmd.equals("step")) {
        		int count = 1;
        		// Check if we are given an int
        		if (params.hasNext()) {
        			String stepNum = params.next();
        			try {
        				count = Integer.parseInt(stepNum);
        			} catch (NumberFormatException e) {
        				System.out.print("error processing: " + cmd + " " + stepNum);
        				while (params.hasNext()) {
        					System.out.print(" " + params.next());
                		}
                		System.out.print("\n");
                		continue;
        			}
        		}
        		for (int stepCnt = 0; stepCnt < count; stepCnt++) {
        			Critter.worldTimeStep();
        		}
        	}
        	
        	// "seed <number>" allows user to provide random number seed
        	else if (cmd.equals("seed")) {
        		// Check if we are given a long
        		if (params.hasNext()) {
        			String seedNum = params.next();
        			try {
        				Critter.setSeed(Long.parseLong(seedNum));
        			} catch (NumberFormatException e) {
        				System.out.print("error processing: " + cmd + " " + seedNum);
        				while (params.hasNext()) {
        					System.out.print(" " + params.next());
                		}
                		System.out.print("\n");
                		continue;
        			}
        		}
        	}
        	
        	// "make" command creates specified type and number of Critters
        	else if (cmd.equals("make")) {
        		String className = null;
        		
        		// When no class name given
        		try {
        			className = params.next();
        		} catch (NoSuchElementException e) {
        			System.out.println("error processing: " + cmd);
        			continue;
        		}
        		
        		int count = 1;
        		// Check if we are given an int
        		if (params.hasNext()) {
        			String makeNum = params.next();
        			try {
        				count = Integer.parseInt(makeNum);
        			} catch (NumberFormatException e) {
        				System.out.print("error processing: " + cmd + " " + className + " " + makeNum);
        				while (params.hasNext()) {
        					System.out.print(" " + params.next());
                		}
                		System.out.print("\n");
                		continue;
        			}
        		}
        		
        		// Incorrect Critter name
        		try {
            		for (int makeCnt = 0; makeCnt < count; makeCnt++) {
            			Critter.makeCritter("assignment4." + className);
            		}
        		} catch (InvalidCritterException e) {
        			System.out.print("error processing: " + cmd + " " + className);
    				while (params.hasNext()) {
    					System.out.print(" " + params.next());
            		}
            		System.out.print("\n");
            		continue;
                }
        	}
        	
        	// "stats" command gives specific statistics about type of Critter
        	else if (cmd.equals("stats")) {
        		String className = null;
        		
        		// When no class name given
        		try {
        			className = params.next();
        		} catch (NoSuchElementException e) {
        			System.out.println("error processing: " + cmd);
        			continue;
        		}
        		
        		// Call specific Critter's runStats method
        		try {    		
        			List<Critter> instances;
        			java.lang.reflect.Method method;
        			instances = Critter.getInstances(className);	// Get instances of specified class			
        			Class critter = Class.forName("assignment4." + className);
        			Critter dummyCritter = (Critter)critter.newInstance();

        			// Use reflection to call custom Critter's runStats() method
        			Class[] cArg = new Class[1];
        			cArg[0] = List.class;
        			method = dummyCritter.getClass().getMethod("runStats", cArg);
        			method.invoke(dummyCritter, instances);
        			
        		} catch (Exception e) {
    				System.out.print("error processing: " + cmd + " " + className);
    				while (params.hasNext()) {
    					System.out.print(" " + params.next());
            		}
            		System.out.print("\n");
            		continue;
        		}
        		
        		
        	}
        	
        	// Invalid command
        	else {
        		System.out.print("invalid command: " + cmd);
        		while (params.hasNext()) {
        			System.out.print(" " + params.next());
        		}
        		System.out.print("\n");
        	}
        }
        
        /* Write your code above */
        System.out.flush();

    }
}
