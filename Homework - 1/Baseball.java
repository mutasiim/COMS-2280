package edu.iastate.cs2280.hw1;

/**
 * @author Mohammad Mu Tasim Chowdhury
 */

public class Baseball extends SportsHouseholds {
    public Baseball(NeighborhoodGrid grid, int row, int column, int level) {
        super(grid, row, column, level);
    }

    @Override
    public Sports getPreference() {
        return Sports.BASEBALL;
    }

    @Override
    public Household next(NeighborhoodGrid newGrid, int month) {
        int[] c = new int[Sports.values().length];
        survey(c);

        int A = c[Sports.BASEBALL.ordinal()];
        int F = c[Sports.FOOTBALL.ordinal()];
        int S = c[Sports.SOCCER.ordinal()];

        if (interestLevel >= MAX_INTEREST) {
            return new Nothing(newGrid, row, column);
        } else if (S > 3) {
            return new Soccer(newGrid, row, column, 0);
        } else if (A < 2) {
            return new Rugby(newGrid, row, column, 0);
        } else if ((A + S) > 5) {
            return new Nothing(newGrid, row, column);
        } else if (F > 2 * A) {
            return new Football(newGrid, row, column, 0);
        } else {
            return new Baseball(newGrid, row, column, Math.min(MAX_INTEREST, interestLevel + 1));
        }
    }

    @Override
    public String toString() {
        int lvl = Math.max(0, Math.min(MAX_INTEREST, interestLevel));
        return "A" + lvl + " ";
    }
}
