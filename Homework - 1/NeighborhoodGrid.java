package edu.iastate.cs2280.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Random;
import java.util.Scanner;

/**
 * NeighborhoodGrid refers to the households' layout for each simulation.
 * It is a square grid [size X size].
 *
 * @author Mohammad Mu Tasim Chowdhury
 */
public class NeighborhoodGrid {
    /**
     * Represents the size of the neighborhood grid.
     */
    private final int size;
    /**
     * A 2D grid representing a neighborhood where each cell is occupied by a household.
     * Each the rows and columns define the position of a household within the neighborhood.
     */
    public Household[][] grid;

    /**
     * Constructs a NeighborhoodGrid by reading household data from an input file.
     * Each square in the grid corresponds to a household defined by a specific letter
     * and (if applicable) a passion level.
     *
     * @param inputFileName the name of the file that contains the household data
     *                      used to populate the grid
     * @throws FileNotFoundException if the specified file does not exist or cannot be opened
     * @throws ParseException        if the data in the file is not in the expected format or
     *                               contains invalid household specifications
     */
    public NeighborhoodGrid(String inputFileName) throws FileNotFoundException, ParseException {
        // Read non-empty lines, split by whitespace; require a square grid
        java.util.ArrayList<String[]> rows = new java.util.ArrayList<>();
        try (Scanner sc = new Scanner(new File(inputFileName))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                // Strip UTF-8 BOM if present, then trim whitespace
                if (!line.isEmpty() && line.charAt(0) == '\uFEFF') {
                    line = line.substring(1);
                }
                line = line.trim();
                if (line.isEmpty()) continue;
                rows.add(line.split("\\s+"));
            }
        }

        if (rows.isEmpty()) {
            throw new ParseException("Empty grid file: " + inputFileName, 0);
        }

        int n = rows.size();
        for (int i = 0; i < n; i++) {
            if (rows.get(i).length != n) {
                throw new ParseException("Grid must be square. Row " + i + " has " + rows.get(i).length + " columns; expected " + n, i);
            }
        }

        // Initialize final size and grid
        this.size = n;
        this.grid = new Household[n][n];

        // Parse tokens into Household objects
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                String tok = rows.get(r)[c];
                if (tok == null || tok.isEmpty()) {
                    throw new ParseException("Empty token at (" + r + "," + c + ")", r * n + c);
                }
                char L = Character.toUpperCase(tok.charAt(0));

                // Everything / Nothing have no interest digit
                if (L == 'E') { grid[r][c] = new Everything(this, r, c); continue; }
                if (L == 'N') { grid[r][c] = new Nothing(this, r, c);    continue; }

                // Sports must have a single digit 0..5 after the letter
                if (tok.length() < 2 || !Character.isDigit(tok.charAt(1))) {
                    throw new ParseException("Expected digit 0–5 after '" + L + "' at (" + r + "," + c + "): " + tok, r * n + c);
                }
                int level = tok.charAt(1) - '0';
                if (level < 0 || level > Household.MAX_INTEREST) {
                    throw new ParseException("Interest must be 0..5 for '" + L + "' at (" + r + "," + c + "): " + tok, r * n + c);
                }

                switch (L) {
                    case 'A': grid[r][c] = new Baseball(this,   r, c, level); break;
                    case 'B': grid[r][c] = new Basketball(this, r, c, level); break;
                    case 'F': grid[r][c] = new Football(this,   r, c, level); break;
                    case 'R': grid[r][c] = new Rugby(this,      r, c, level); break;
                    case 'S': grid[r][c] = new Soccer(this,     r, c, level); break;
                    default:
                        throw new ParseException("Unknown token '" + tok + "' at (" + r + "," + c + ")", r * n + c);
                }
            }
        }
    }

    /**
     * Constructs a NeighborhoodGrid of a specified size.
     * If the provided size is greater than zero, it initializes an empty grid of the
     * specified dimensions. Otherwise, it throws an IllegalArgumentException.
     *
     * @param size the size of the grid (number of rows and columns). Must be greater than 0.
     * @throws IllegalArgumentException if the size is not greater than 0.
     */
    public NeighborhoodGrid(int size) {
        this.size = size;
        if (size > 0) {
            grid = new Household[size][size];
        } else {
            throw new IllegalArgumentException("Width must be > 0");
        }
    }

    /**
     * Retrieves the size of the neighborhood grid.
     *
     * @return the size of the grid, representing the number of rows and columns.
     */
    public int getSize() {
        return size;
    }


    /**
     * Randomly initializes the grid of the NeighborhoodGrid instance with various household types.
     * Each cell in the grid is assigned one of the household types (Basketball, Football, Baseball,
     * Soccer, Everything, Nothing, or Rugby) based on a random selection.
     */
    public void randomInit() {
        Random rnd = new Random();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                int t = rnd.nextInt(7); // 0..6
                switch (t) {
                    case 0: grid[r][c] = new Baseball(this,   r, c, rnd.nextInt(Household.MAX_INTEREST + 1)); break;
                    case 1: grid[r][c] = new Basketball(this, r, c, rnd.nextInt(Household.MAX_INTEREST + 1)); break;
                    case 2: grid[r][c] = new Everything(this, r, c); break;
                    case 3: grid[r][c] = new Football(this,   r, c, rnd.nextInt(Household.MAX_INTEREST + 1)); break;
                    case 4: grid[r][c] = new Nothing(this,    r, c); break;
                    case 5: grid[r][c] = new Rugby(this,      r, c, rnd.nextInt(Household.MAX_INTEREST + 1)); break;
                    case 6: grid[r][c] = new Soccer(this,     r, c, rnd.nextInt(Household.MAX_INTEREST + 1)); break;
                }
            }
        }
    }

    /**
     * Output the Neighborhood grid.
     * For each square, output the letter associated with the household occupying the square.
     * If the household is interested in a sport, output the interest level in that sport followed
     * by a blank space. Otherwise, output two blank spaces after the letter. One of the blank space
     * is part of toString() implementation of the households
     *
     * @return String
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                sb.append(grid[r][c].toString()); // e.g., "A0 ", "E  ", "N  "
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Write the Neighborhood grid to a file.
     *
     * @param outputFileName
     * @throws FileNotFoundException
     */
    public void write(String outputFileName) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(outputFileName)) {
            pw.print(this.toString());
        }
    }
}