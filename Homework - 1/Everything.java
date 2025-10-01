package edu.iastate.cs2280.hw1;

/**
 * @author Mohammad Mu Tasim Chowdhury
 */


public class Everything extends Household {
    public Everything(NeighborhoodGrid grid, int row, int column) {
        super(grid, row, column);
    }

    @Override
    public Sports getPreference() {
        return Sports.EVERYTHING;
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

        if (S >= 3 * E) {
            return new Soccer(newGrid, row, column, 3);
        } else if (F > 3) {
            return new Football(newGrid, row, column, 0);
        } else if (N < (F + S)) {
            return new Basketball(newGrid, row, column, 2);
        } else if (R > E) {
            return new Rugby(newGrid, row, column, 0);
        } else {
            return new Everything(newGrid, row, column);
        }
    }

    @Override
    public String toString() {
        return "E  ";
    }
}
