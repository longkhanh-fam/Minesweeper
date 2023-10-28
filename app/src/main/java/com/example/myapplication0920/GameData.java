package com.example.myapplication0920;

import java.util.Random;

public class GameData {

    private int nCols;
    private int nRows;
    private int totalMines;
    private boolean[][] mineField; // Represents the grid with mines
    private int[][] surroundingMines; // Stores the number of surrounding mines



    // Constructor to initialize the game data
    public void initGame(int nRows, int nCols) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.mineField = new boolean[nRows][nCols];
        this.surroundingMines = new int[nRows][nCols];

    // Initialize the mineField with false (no mines initially)
        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                mineField[row][col] = false;
            }
        }



    }
    // Getters and setters for nRows and nCols
    public int getNumberOfRows() {
        return nRows;
    }
    public int getNumberOfColumns() {
        return nCols;
    }
    public int getNumberOfTotalMines() {
        return totalMines;
    }
    // Methods to manipulate the mineField
    public boolean isMineAt(int row, int col) {
    // Check if there is a mine at the specified row and column
        if (isValidPosition(row, col)) {
            return mineField[row][col];
        }
        return false;
    }
    public void setMineAt(int row, int col, boolean isMine) {
    // Set the presence of a mine at the specified row and column
        if (isValidPosition(row, col)) {
            mineField[row][col] = isMine;
        }
    }
    // Methods to access and manipulate the surroundingMines array
    public int getSurroundingMinesCount(int row, int col) {
// Get the number of surrounding mines at the specified position
        if (isValidPosition(row, col)) {
            return surroundingMines[row][col];
        }
        return 0;
    }



    public void setSurroundingMinesCount(int row, int col, int count) {
// Set the number of surrounding mines at the specified position
        if (isValidPosition(row, col)) {
            surroundingMines[row][col] = count;
        }
    }
    // Helper method to check if a position is within bounds
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < nRows && col >= 0 && col < nCols;
    }





    public  void generateMinesweeperGrid() {
        int totalCells = nRows * nCols;
        totalMines = (int) (0.15 * totalCells); // 15% of the total cells

        Random random = new Random();

        // Randomly place mines
        for (int i = 0; i < totalMines; i++) {
            int row, col;

            // Ensure that we don't place more than one mine in the same cell
            do {
                row = random.nextInt(nRows);
                col = random.nextInt(nCols);
            } while (mineField[row][col]);

            setMineAt(row, col, true); // -1 represents a mine
        }
        calculateNumbers();

    }
    private void calculateNumbers()
    {
        for (int i = 0; i < nRows; i++)
        {
            for (int j = 0; j < nCols; j++)
            {
                if (isMineAt(i, j)) {
                    surroundingMines[i][j] = -1;
                    continue; // Skip calculation for mine cells
                }
                int count = 0;

                // Check surrounding cells for mines
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int newRow = i + dx;
                        int newCol = j + dy;
                        if (isValidPosition(newRow, newCol)) {
                            if(isMineAt(newRow, newCol))
                                count++;
                        }
                    }
                }
                setSurroundingMinesCount(i, j, count);

            }
        }
    }


}
