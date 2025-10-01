package edu.iastate.cs2280.hw1;

/**
 * Represents an abstract household within a neighborhood that has a specific interest
 * in sports. This class is designed to be extended by concrete sports-specific
 * households to define preferences and behavior.
 *
 * @author Mohammad Mu Tasim Chowdhury
 */
public abstract class SportsHouseholds extends Household implements InterestLevel {
    /**
     * Represents the interest level of the household in a specific sport.
     */
    protected int interestLevel; // Interest level of the household

    /**
     * Constructs a SportsHouseholds object with the specified neighborhood grid,
     * row and column position, and interest level.
     *
     * @param grid          the NeighborhoodGrid instance where the household resides
     * @param row           the row index of the household's position in the grid
     * @param column        the column index of the household's position in the grid
     * @param interestLevel the level of interest of the household in a specific sport
     */
    public SportsHouseholds(NeighborhoodGrid grid, int row, int column, int interestLevel) {
        super(grid, row, column);
        this.interestLevel = interestLevel;
    }

    /**
     * Retrieves the current interest level of the household in its specific sport.
     *
     * @return the interest level of the household as an integer
     */
    @Override
    public int getInterest() {
        return interestLevel;
    }
}
