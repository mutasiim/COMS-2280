package edu.iastate.cs2280.hw1;

/**
 * @author Mohammad Mu Tasim Chowdhury
 */

public class Soccer extends SportsHouseholds {
    public Soccer(NeighborhoodGrid grid, int row, int column, int level) {
        super(grid, row, column, level);
    }

    @Override
    public Sports getPreference() {
        return Sports.SOCCER;
    }

    @Override
    public Household next(NeighborhoodGrid newGrid, int month) {
        int[] c = new int[Sports.values().length];
        survey(c);

        int A = c[Sports.BASEBALL.ordinal()];
        int B = c[Sports.BASKETBALL.ordinal()];
        int E = c[Sports.EVERYTHING.ordinal()];
        int F = c[Sports.FOOTBALL.ordinal()];
        int R = c[Sports.RUGBY.ordinal()];
        int S = c[Sports.SOCCER.ordinal()];

        if (interestLevel >= MAX_INTEREST) {
            return new Nothing(newGrid, row, column);
        } else if ((F + B) >= S) {
            return new Everything(newGrid, row, column);
        } else if (S > (A + B + F + R)) {
            return new Soccer(newGrid, row, column, MAX_INTEREST);
        } else if (R > 4) {
            return new Rugby(newGrid, row, column, 3);
        } else if (E == 0) {
            return new Nothing(newGrid, row, column);
        } else {
            return new Soccer(newGrid, row, column, Math.min(MAX_INTEREST, interestLevel + 1));
        }
    }

    @Override
    public String toString() {
        int lvl = Math.max(0, Math.min(MAX_INTEREST, interestLevel));
        return "S" + lvl + " ";
    }
}
