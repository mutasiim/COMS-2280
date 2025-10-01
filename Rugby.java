package edu.iastate.cs2280.hw1;

/**
 * @author Mohammad Mu Tasim Chowdhury
 */

public class Rugby extends SportsHouseholds {
    public Rugby(NeighborhoodGrid grid, int row, int column, int level) {
        super(grid, row, column, level);
    }

    @Override
    public Sports getPreference() {
        return Sports.RUGBY;
    }

    @Override
    public Household next(NeighborhoodGrid newGrid, int month) {
        int[] c = new int[Sports.values().length];
        survey(c);

        int A = c[Sports.BASEBALL.ordinal()];
        int B = c[Sports.BASKETBALL.ordinal()];
        int F = c[Sports.FOOTBALL.ordinal()];
        int R = c[Sports.RUGBY.ordinal()];
        int S = c[Sports.SOCCER.ordinal()];

        if (interestLevel >= MAX_INTEREST) {
            return new Nothing(newGrid, row, column);
        } else if ((F + S) >= 8) {
            return new Soccer(newGrid, row, column, 2);
        } else if (A > 2 * B) {
            return new Baseball(newGrid, row, column, 4);
        } else if (R < 2) {
            return new Football(newGrid, row, column, 0);
        } else {
            return new Rugby(newGrid, row, column, Math.min(MAX_INTEREST, interestLevel + 1));
        }
    }

    @Override
    public String toString() {
        int lvl = Math.max(0, Math.min(MAX_INTEREST, interestLevel));
        return "R" + lvl + " ";
    }
}
