package edu.iastate.cs2280.hw1;

/**
 * @author Mohammad Mu Tasim Chowdhury
 */
public class Basketball extends SportsHouseholds {
    public Basketball(NeighborhoodGrid grid, int row, int column, int level) {
        super(grid, row, column, level);
    }

    @Override
    public Sports getPreference() {
        return Sports.BASKETBALL;
    }

    @Override
    public Household next(NeighborhoodGrid newGrid, int month) {
        int[] c = new int[Sports.values().length];
        survey(c);

        int A = c[Sports.BASEBALL.ordinal()];
        int B = c[Sports.BASKETBALL.ordinal()];
        int F = c[Sports.FOOTBALL.ordinal()];
        int S = c[Sports.SOCCER.ordinal()];

        if (interestLevel >= MAX_INTEREST) {
            return new Nothing(newGrid, row, column);
        } else if (F > 5) {
            return new Football(newGrid, row, column, 2);
        } else if (S >= 2) {
            return new Soccer(newGrid, row, column, 0);
        } else if (B < 2) {
            return new Everything(newGrid, row, column);
        } else if ((A + B + F) > 6) {
            return new Everything(newGrid, row, column);
        } else {
            return new Basketball(newGrid, row, column, Math.min(MAX_INTEREST, interestLevel + 1));
        }
    }

    @Override
    public String toString() {
        int lvl = Math.max(0, Math.min(MAX_INTEREST, interestLevel));
        return "B" + lvl + " ";
    }
}
