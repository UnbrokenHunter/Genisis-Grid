package Folder;

public class Settings {

    public static final int GRID_SIZE = 25;
    public static final int CELL_SIZE = 30;

    public static final int updateSpeed = 100;

    public static Cell[][] grid = new Cell[GRID_SIZE][GRID_SIZE];

    public static int round = 0;
    public static final int maxCellAge = 5;
    public static final int maxCellAgeVariance = 10;
    public static final double MASS_PROBABILITY = 0.2; // % chance for mass
    public static final double ENERGY_PROBABILITY = 0.1; // % chance for energy

    public static final double DOUBLED_ENERGY_PROBABILITY = 7;

}