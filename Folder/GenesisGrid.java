package Folder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

/*
 * Mass in isolation stays
 * Energy in isolation moves randomly
 *
 * Mass diagonal to Energy Moves Diagonally
 * Mass Adjacent to Energy Creates new Mass
 * 
 * Mass that is more than x cycles old will decay back into energy
 * Based on x percentage, soemtimes, decaying mass will result in two energies
 */

public class GenesisGrid extends JFrame {
    private Timer timer;

    private boolean requireMouse = false;
    private boolean goNext = false;

    public GenesisGrid() {
        setTitle("Homostasis");
        setSize(Settings.GRID_SIZE * Settings.CELL_SIZE, Settings.GRID_SIZE * Settings.CELL_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Add a MouseListener to the grid panel
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleGridClick(e.getX(), e.getY());
                goNext = true;
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        // Initialize the grid with random mass and energy
        initializeGrid();

        Random rand = new Random();

        // Timer to control the simulation
        timer = new Timer(Settings.updateSpeed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (goNext || !requireMouse) {

                    updateGrid(rand);
                    repaint();
                    Settings.round++;

                    Analytics.calcOverview();

                    if (Settings.round % 10 == 0)
                        System.out.println(Analytics.overviewString());

                    goNext = false;
                }
            }
        });
        timer.start();
    }

    private void handleGridClick(int x, int y) {
        // Convert pixel coordinates to grid coordinates
        int gridX = x / Settings.CELL_SIZE;
        int gridY = y / Settings.CELL_SIZE;

        // Check if the click is within the grid bounds
        if (gridX >= 0 && gridX < Settings.GRID_SIZE && gridY >= 0 && gridY < Settings.GRID_SIZE) {
            Analytics.printCell(gridX, gridY);
            System.out.println();
            System.out.println(Analytics.overviewString());
            System.out.println("--------------------------------------");
        }

        // Redraw the grid or the specific cell if necessary
        repaint();
    }

    private void initializeGrid() {
        Random rand = new Random();
        for (int i = 0; i < Settings.GRID_SIZE; i++) {
            for (int j = 0; j < Settings.GRID_SIZE; j++) {
                double chance = rand.nextDouble();
                if (chance < Settings.MASS_PROBABILITY) {
                    Settings.grid[i][j] = new Cell(Cell.Type.MASS);
                } else if (chance < Settings.MASS_PROBABILITY + Settings.ENERGY_PROBABILITY) {
                    Settings.grid[i][j] = new Cell(Cell.Type.ENERGY);
                } else {
                    Settings.grid[i][j] = new Cell(Cell.Type.EMPTY);
                }
            }
        }
    }

    private void updateGrid(Random rand) {
        Cell[][] newGrid = new Cell[Settings.GRID_SIZE][Settings.GRID_SIZE]; // Initialize a new grid

        // Deep copy the original grid to the new grid
        for (int i = 0; i < Settings.GRID_SIZE; i++) {
            for (int j = 0; j < Settings.GRID_SIZE; j++) {
                newGrid[i][j] = new Cell(Settings.grid[i][j].getType(), Settings.grid[i][j].getAge());
            }
        }

        // Process each cell in the grid
        for (int i = 0; i < Settings.GRID_SIZE; i++) {
            for (int j = 0; j < Settings.GRID_SIZE; j++) {
                Cell currentCell = newGrid[i][j];

                // Apply rules based on cell type
                if (currentCell.getType() == Cell.Type.MASS) {
                    updateMass(i, j, currentCell, rand, newGrid);
                } else if (currentCell.getType() == Cell.Type.ENERGY) {
                    updateEnergy(i, j, currentCell, newGrid, rand);
                }
            }
        }

        Settings.grid = newGrid; // Update the main grid
    }

    private void updateMass(int x, int y, Cell cell, Random rand, Cell[][] newGrid) {
        cell.incrementAge();

        if (cell.getAge() > Settings.maxCellAge + ((int) (Math.random() * Settings.maxCellAgeVariance))) {
            // Disolve Cell Into Energy
            cell.setType(Cell.Type.ENERGY);

            if (rand.nextInt(100) < Settings.DOUBLED_ENERGY_PROBABILITY) {

                // Move to a random location
                moveCellRandomly(x, y, rand, newGrid);

                // Set new empty space to energy as well
                newGrid[x][y].setType(Cell.Type.ENERGY);
                newGrid[x][y].setColor(Color.YELLOW);
            }
        }
    }

    private void updateEnergy(int x, int y, Cell cell, Cell[][] newGrid, Random rand) {
        ArrayList<Point> adjacentMassDirections = findMassDirections(x, y, true, Settings.grid);
        ArrayList<Point> diagonalMassDirections = findMassDirections(x, y, false, Settings.grid);

        if (adjacentMassDirections.isEmpty() && diagonalMassDirections.isEmpty()) {
            moveCellRandomly(x, y, rand, newGrid);
        }

        // New Mass
        if (!adjacentMassDirections.isEmpty()) {
            createMass(x, y, newGrid, rand);
        }

        // Move Mass
        if (!diagonalMassDirections.isEmpty()) {
            Point direction = diagonalMassDirections.get(0);
            moveCellRandomly(
                    Math.abs((int) direction.getX()),
                    Math.abs((int) direction.getY()),
                    rand,
                    newGrid);
        }
    }

    ColorInterpolator interp = new ColorInterpolator(ColorInterpolator.COOL_BLUES, 1);

    private void createMass(int x, int y, Cell[][] newGrid, Random rand) {
        // Check boundaries and empty spot in newGrid
        if (x >= 0 && x < Settings.GRID_SIZE && y >= 0 && y < Settings.GRID_SIZE) {
            newGrid[x][y].setType(Cell.Type.MASS);
            newGrid[x][y].setColor(interp.getColor(newGrid[x][y].getAge()));
        }

    }

    private void moveCellRandomly(int x, int y, Random rand, Cell[][] newGrid) {
        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };
        ArrayList<Point> validDirections = new ArrayList<>();

        // Check each direction for a valid move
        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            // Check boundaries and empty spot in newGrid
            if (newX >= 0 && newX < Settings.GRID_SIZE && newY >= 0 && newY < Settings.GRID_SIZE &&
                    Settings.grid[newX][newY].getType() == Cell.Type.EMPTY) {
                validDirections.add(new Point(newX, newY));
            }
        }

        // Move the energy cell to a new location, if possible
        if (!validDirections.isEmpty()) {
            Point selectedPoint = validDirections.get(rand.nextInt(validDirections.size()));
            moveCell(x, y, ((int) selectedPoint.getX()), ((int) selectedPoint.getY()), newGrid);
        } else {
            // If no valid move is possible, retain the energy cell in its current location
        }
    }

    private void moveCell(int oldX, int oldY, int newX, int newY, Cell[][] newGrid) {

        // Check boundaries
        if (newX >= 0 && newX < Settings.GRID_SIZE && newY >= 0 && newY < Settings.GRID_SIZE) {
            if (Settings.grid[newX][newY].getType() == Cell.Type.EMPTY) {

                newGrid[newX][newY] = Settings.grid[oldX][oldY];
                newGrid[oldX][oldY].setType(Cell.Type.EMPTY);
            }
        }
    }

    private ArrayList<Point> findMassDirections(int x, int y, boolean isAdjacent, Cell[][] gridToCheck) {
        ArrayList<Point> massDirections = new ArrayList<>();
        int[][] directions = isAdjacent ? new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }
                : new int[][] { { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            // Check boundaries
            if (newX >= 0 && newX < Settings.GRID_SIZE && newY >= 0 && newY < Settings.GRID_SIZE) {
                if (gridToCheck[newX][newY].getType() == Cell.Type.MASS) {
                    massDirections.add(new Point(newX, newY));
                }
            }
        }
        return massDirections;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < Settings.GRID_SIZE; i++) {
            for (int j = 0; j < Settings.GRID_SIZE; j++) {
                if (Settings.grid[i][j].getType() == Cell.Type.MASS) {
                    g.setColor(Settings.grid[i][j].getColor());
                } else if (Settings.grid[i][j].getType() == Cell.Type.ENERGY) {
                    g.setColor(Settings.grid[i][j].getColor());
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(i * Settings.CELL_SIZE, j * Settings.CELL_SIZE, Settings.CELL_SIZE, Settings.CELL_SIZE);
            }
        }

        g.setColor(Color.GRAY);
        g.setFont(new Font(Font.SERIF, 0, 20));
        g.drawString("Settings.round: " + Settings.round, 15, 50);

        g.setColor(Color.GRAY);
        g.drawRect(Settings.CELL_SIZE * Settings.GRID_SIZE, 0, 300, Settings.CELL_SIZE * Settings.GRID_SIZE);

        g.setColor(Color.WHITE);
        g.drawString(Analytics.overviewString(), Settings.CELL_SIZE * Settings.GRID_SIZE, 30);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GenesisGrid();
            }
        });
    }

}
