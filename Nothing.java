package edu.iastate.cs2280.hw1;

/**
 * @author Mohammad Mu Tasim Chowdhury
 */

public class Nothing extends Household {
    public Nothing(NeighborhoodGrid grid, int row, int column) {
        super(grid, row, column);
    }

    @Override
    public Sports getPreference() {
        return Sports.NOTHING;
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
        int R = c[Sports.RUGBY.ordinal()];
        int S = c[Sports.SOCCER.ordinal()];

        if (S > 5) {
            return new Soccer(newGrid, row, column, 5);
        } else if (F > 4) {
            return new Football(newGrid, row, column, 0);
        } else if (B > 3) {
            return new Basketball(newGrid, row, column, 0);
        } else if (A > 2) {
            return new Baseball(newGrid, row, column, 0);
        } else if (R > 1) {
            return new Rugby(newGrid, row, column, 2);
        } else if (E >= 1) {
            return new Everything(newGrid, row, column);
        } else {
            return new Nothing(newGrid, row, column);
        }
    }

    @Override
    public String toString() {
        return "N  ";
    }
}
