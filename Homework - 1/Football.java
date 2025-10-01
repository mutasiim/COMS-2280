package edu.iastate.cs2280.hw1;

/**
 * @author Mohammad Mu Tasim Chowdhury
 */

public class Football extends SportsHouseholds {
    public Football(NeighborhoodGrid grid, int row, int column, int level) {
        super(grid, row, column, level);
    }

    @Override
    public Sports getPreference() {
        return Sports.FOOTBALL;
    }

    @Override
    public Household next(NeighborhoodGrid newGrid, int month) {
        int[] c = new int[Sports.values().length];
        survey(c);

        int A = c[Sports.BASEBALL.ordinal()];
        int B = c[Sports.BASKETBALL.ordinal()];
        int E = c[Sports.EVERYTHING.ordinal()];
        int F = c[Sports.FOOTBALL.ordinal()];
        int N = c[Sports.NOTHING.ordinal()];

        // Month-of-year (1..12) with April–June special case
        int m = (month % 12) + 1;
        if (m >= 4 && m <= 6 && (E >= 1 || N >= 1)) {
            // If any Everything or Nothing neighbor in Apr–Jun, only increase interest
            return new Football(newGrid, row, column, Math.min(MAX_INTEREST, interestLevel + 1));
        }

        if (interestLevel >= MAX_INTEREST) {
            return new Nothing(newGrid, row, column);
        } else if ((A + B + F) > 7) {
            return new Everything(newGrid, row, column);
        } else if (B > 3) {
            return new Basketball(newGrid, row, column, 0);
        } else if (F < 2) {
            return new Baseball(newGrid, row, column, 0);
        } else {
            return new Football(newGrid, row, column, Math.min(MAX_INTEREST, interestLevel + 1));
        }
    }

    @Override
    public String toString() {
        int lvl = Math.max(0, Math.min(MAX_INTEREST, interestLevel));
        return "F" + lvl + " ";
    }
}
