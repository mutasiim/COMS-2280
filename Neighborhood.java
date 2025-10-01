package edu.iastate.cs2280.hw1;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Performs simulations over the neighborhood grid.
 *
 * @author Mohammad Mu Tasim Chowdhury
 */
public class Neighborhood {
    /**
     * Updates the grid by calculating the next state of each household in the grid.
     * Each household in the old grid determines its new state and updates the
     * corresponding position in the new grid.
     *
     * @param oldGrid      the current state of the neighborhood grid (old grid)
     * @param newGrid      the updated state of the neighborhood grid (new grid)
     * @param currentMonth the current month of the simulation, used for household updates
     */
    public static void updateGrid(NeighborhoodGrid oldGrid, NeighborhoodGrid newGrid, int currentMonth) {
        if (oldGrid == null || newGrid == null) {
            throw new IllegalArgumentException("Grids must not be null.");
        }
        int n = oldGrid.getSize();
        if (newGrid.getSize() != n) {
            throw new IllegalArgumentException("Grid sizes must match.");
        }

        /**
         *For each cell, ask the OLD grid cell what it becomes next month,
         *and place that NEW household into the NEW grid at the same (r,c).
         */
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                Household current = oldGrid.grid[r][c];
                if (current == null) {
                    /**
                     *If your grid is always fully initialized, you can throw instead.
                     *Leaving it defensive here:
                     */
                    continue;
                }
                Household next = current.next(newGrid, currentMonth);
                newGrid.grid[r][c] = next;
            }
        }
    }

    /**
     * The main method serves as the entry point for the simulation of neighborhood sports preferences.
     * It allows users to initialize grids (randomly or from a file), set the duration of the simulation,
     * and view the evolution of the grid over time.
     *
     * @param args command-line arguments passed to the application
     * @throws FileNotFoundException  if the specified file for grid input is not found
     * @throws ParseException         if there is an error in parsing the input file for the grid
     * @throws InputMismatchException if user input does not match the expected input type
     */
    public static void main(String[] args) throws FileNotFoundException, ParseException, InputMismatchException {
        System.out.println("Neighborhood Sports Preferences");
        System.out.println("===============================");
        Scanner scan = new Scanner(System.in);

        // Show menu
        System.out.println("Keys: 1 (random grid); 2 (file input); 3 (exit)\n");

        int sim = 1;
        boolean running = true;

        while (running) {
            System.out.print("Simulation Number " + sim + ": ");

            if (!scan.hasNextInt()) {
                System.out.println("Please enter 1, 2, or 3.");
                scan.next(); // consume bad token
                continue;
            }

            int key = scan.nextInt();

            switch (key) {
                case 1: { // Random grid
                    System.out.println("Random grid");
                    System.out.print("Enter grid width: ");
                    int size = scan.nextInt();
                    System.out.print("Enter the number of months: ");
                    int months = scan.nextInt();

                    NeighborhoodGrid current = new NeighborhoodGrid(size);
                    current.randomInit(); // make sure this exists

                    System.out.println();
                    System.out.println("Initial grid:\n");
                    System.out.println(current);

                    for (int m = 1; m <= months; m++) {
                        NeighborhoodGrid next = new NeighborhoodGrid(current.getSize());
                        updateGrid(current, next, m - 1); // month index starts at 0
                        System.out.println("Updated grid for " + m + ":\n");
                        System.out.println(next);
                        current = next;
                    }

                    System.out.println("Final grid:\n");
                    System.out.println(current);
                    break;
                }

                case 2: { // File input
                    System.out.println("Grid input from a file");
                    System.out.print("File name: ");
                    String fileName = scan.next();
                    System.out.print("Enter the number of months: ");
                    int months = scan.nextInt();

                    NeighborhoodGrid current = new NeighborhoodGrid(fileName); // make sure file ctor exists

                    System.out.println();
                    System.out.println("Initial grid:\n");
                    System.out.println(current);

                    for (int m = 1; m <= months; m++) {
                        NeighborhoodGrid next = new NeighborhoodGrid(current.getSize());
                        updateGrid(current, next, m - 1);
                        System.out.println("Updated grid for " + m + ":\n");
                        System.out.println(next);
                        current = next;
                    }

                    System.out.println("Final grid:\n");
                    System.out.println(current);
                    break;
                }

                case 3: { // Exit
                    running = false;
                    break;
                }

                default:
                    System.out.println("Invalid key. Use 1, 2, or 3.");
            }

            if (running) {
                System.out.println();
                System.out.println("Keys: 1 (random grid); 2 (file input); 3 (exit)\n");
                sim++;
            }
        }

        scan.close();

    }
}

