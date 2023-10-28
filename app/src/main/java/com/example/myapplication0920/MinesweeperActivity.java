package com.example.myapplication0920;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Arrays;

public class MinesweeperActivity extends AppCompatActivity implements View.OnClickListener {
    private Switch switchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minesweeper);

        Button btn = (Button) findViewById(R.id.buttonStart);
        btn.setOnClickListener(this);

        switchButton = findViewById(R.id.switchButton);

    }



    @Override
    public void onClick(View v) {
        int nRows = getNumberFromEditText(R.id.editNumRows);
        int nCols = getNumberFromEditText(R.id.editNumCols);
        generateGame(nRows, nCols);
    }

    private int NextAvailableId = 65000;
    private void generateGame(int nRows, int nCols) {
 //     generateLevel1ForStudentsInClass(nRows, nCols);
        GameData gameData = new GameData();
        gameData.initGame(nRows, nCols);
        gameData.generateMinesweeperGrid();
        loadGame(gameData);
    }

    private void loadGame(GameData gameData) {
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridMain);
        gridLayout.removeAllViews();
        int numberOfRows = gameData.getNumberOfRows();
        gridLayout.setRowCount(numberOfRows);
        int numberOfColumns = gameData.getNumberOfColumns();
        gridLayout.setColumnCount(numberOfColumns);

        Button b;
        Button[][] buttons = new Button[numberOfRows][numberOfColumns];

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                b = new Button(this);
                b.setId(NextAvailableId++);
                int[] tag = new int[]{i, j};
                b.setTag(tag);
                gridLayout.addView(b);
                buttons[i][j] = b;
            }
        }
        setButtonListeners(buttons, gameData);

    }


    private void setButtonListeners(Button[][] buttons, GameData gameData) {

        int numberOfRows = gameData.getNumberOfRows();

        int numberOfColumns = gameData.getNumberOfColumns();

        for (int i=0; i<numberOfRows; i++)
            for (int j=0; j<numberOfColumns; j++)
            {
                final int row = i;
                final int col = j;
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle cell click here
                        Button btn = (Button) v;
                        onCellClick(buttons, (int[]) btn.getTag(), gameData);
                    }
                });
            }

    }

    private void onCellClick(Button[][] buttons, int[] cellCoordinates, GameData gameData) {
        int row = cellCoordinates[0];
        int col = cellCoordinates[1];
        Button btn =  buttons[row][col];

        if(switchButton.isChecked())
        {

                btn.setText("Flag");
                btn.setTextColor(Color.parseColor("#800000"));

        }
        else
        {
            btn.setEnabled(false);
            // Check if the cell contains a mine
            if (gameData.isMineAt(row, col)) {
                // Handle game over (player clicked on a mine)
                // You can show a game over message or reveal all mines.
                revealAllMines(buttons, gameData);
                showGameOverMessage();
            } else {
                // The cell does not contain a mine
                // Update the cell UI with surrounding mine count
                int surroundingMines = gameData.getSurroundingMinesCount(row, col);
                if (surroundingMines > 0) {
                    // Set the button text to the surrounding mine count
                    btn.setText(String.valueOf(surroundingMines));
                } else {
                    // Cell has no surrounding mines, reveal adjacent cells
                    revealEmptyCells(buttons, row, col, gameData);
                }

                // Mark the cell as "uncovered" to prevent further clicks
                btn.setEnabled(false);
                btn.setBackgroundColor(Color.parseColor("#0087af"));

                // Check if the player has won (all non-mine cells uncovered)
                if (checkWinCondition(buttons, gameData)) {
                    // Handle game won
                    // You can show a win message or perform any other actions.
                    revealAllMines(buttons, gameData);
                    showWinMessage();

                }
            }



        }

    }
    private void showWinMessage() {
        Toast.makeText(this, "Congratulations! You've won the game!", Toast.LENGTH_SHORT).show();
    }
    private void showGameOverMessage() {
        Toast.makeText(this, "Game Over! You've uncovered a mine. Better luck next time!", Toast.LENGTH_SHORT).show();
    }


    private void revealAllMines(Button[][] buttons, GameData gameData) {

        int numberOfRows = gameData.getNumberOfRows();

        int numberOfColumns = gameData.getNumberOfColumns();

        for (int i=0; i<numberOfRows; i++)
            for (int j=0; j<numberOfColumns; j++)
            {
                buttons[i][j].setEnabled(false);
                if (gameData.isMineAt(i, j))
                {

                    buttons[i][j].setText("*");
                    buttons[i][j].setTextColor(Color.parseColor("#ff0000"));
                }
                else
                {
                    int surroundingMines = gameData.getSurroundingMinesCount(i, j);
                    buttons[i][j].setText(String.valueOf(surroundingMines));
                }
            }
    }
    private boolean checkWinCondition(Button[][] buttons,GameData gameData) {

        int numRows = gameData.getNumberOfRows();
        int numCols = gameData.getNumberOfColumns();

        int totalCells = numRows * numCols;

        int revealedCells = 0;
        int totalMines = gameData.getNumberOfTotalMines();

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {


                if (!buttons[i][j].isEnabled() && !gameData.isMineAt(i, j)) {
                    revealedCells++;
                }
            }
        }
        return (revealedCells == (totalCells - totalMines));
    }



    private void revealEmptyCells(Button[][] buttons , int row, int col, GameData gameData)
    {
            Button cellButton = buttons[row][col];

            cellButton.setEnabled(false);
            cellButton.setBackgroundColor(Color.parseColor("#0087af"));
            int[][] directions = {
                    {-1, -1}, {-1, 0}, {-1, 1},
                    {0, -1}, {0, 1},
                    {1, -1}, {1, 0}, {1, 1}
            };

            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];
                if (gameData.isValidPosition(newRow, newCol)) {
                    int[] cor = {newRow, newCol};

                    Button btn = buttons[newRow][newCol];

                    if (!gameData.isMineAt(newRow, newCol) && btn.isEnabled()) {

                        int surMines = gameData.getSurroundingMinesCount(newRow, newCol);
                        btn.setEnabled(false);
                        btn.setBackgroundColor(Color.parseColor("#0087af"));
                        if (surMines > 0)
                        {
                            btn.setText(String.valueOf(surMines));
                        }
                        else
                        {
                            revealEmptyCells(buttons, newRow, newCol, gameData);
                        }

                    }
                }

            }




    }

    private int getNumberFromEditText(int idEdit) {
        EditText editText = (EditText) findViewById(idEdit);
        String sText = editText.getText().toString();
        int v = Integer.valueOf(sText);
        return v;
    }


}