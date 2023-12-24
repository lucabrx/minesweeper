package org.battle;


import java.util.Random;

public class Minesweeper {
    private static final int FIELD_SIZE = 9;
    private static final char MINE_SYMBOL = 'X';
    private static final char SAFE_CELL_SYMBOL = '.';
    private static final char MARKED_CELL_SYMBOL = '*';
    private static final char FREE_CELL_SYMBOL = '/';
    private static final char[] NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8'};

    private final char[][] field = new char[FIELD_SIZE][FIELD_SIZE];
    private final boolean[][] isMine = new boolean[FIELD_SIZE][FIELD_SIZE];
    private final boolean[][] isExplored = new boolean[FIELD_SIZE][FIELD_SIZE];
    private final boolean[][] isMarked = new boolean[FIELD_SIZE][FIELD_SIZE];
    private boolean firstMove = true;
    private int minesCount;

    public Minesweeper(int minesCount) {
        this.minesCount = minesCount;
        // Initialize the field with unexplored cells
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                field[i][j] = SAFE_CELL_SYMBOL;
            }
        }
    }

    public void placeMines(int firstX, int firstY) {
        Random random = new Random();
        int placedMines = 0;
        while (placedMines < minesCount) {
            int x = random.nextInt(FIELD_SIZE);
            int y = random.nextInt(FIELD_SIZE);
            if ((x != firstX || y != firstY) && !isMine[x][y]) {
                isMine[x][y] = true;
                placedMines++;
            }
        }
    }

    public void toggleMark(int x, int y) {
        if (isMarked[x][y]) {
            isMarked[x][y] = false;
        } else {
            isMarked[x][y] = true;
        }
    }

    public boolean explore(int x, int y) {
        if (firstMove) {
            firstMove = false;
            placeMines(x, y);
        }

        if (isMine[x][y]) {
            return false; // Stepped on a mine
        }

        exploreCell(x, y);
        return true;
    }

    private void exploreCell(int x, int y) {
        if (x < 0 || x >= FIELD_SIZE || y < 0 || y >= FIELD_SIZE || isExplored[x][y] || isMine[x][y]) {
            return;
        }

        isExplored[x][y] = true;
        isMarked[x][y] = false;
        int adjacentMines = countAdjacentMines(x, y);

        if (adjacentMines == 0) {
            field[x][y] = FREE_CELL_SYMBOL;
            // Explore surrounding cells
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue;
                    exploreCell(x + i, y + j);
                }
            }
        } else {
            field[x][y] = NUMBERS[adjacentMines];
        }
    }

    private int countAdjacentMines(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newX = x + i;
                int newY = y + j;
                if (newX >= 0 && newX < FIELD_SIZE && newY >= 0 && newY < FIELD_SIZE && isMine[newX][newY]) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean checkWin() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (!isExplored[i][j] && !isMine[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printField(boolean showMines) {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < FIELD_SIZE; i++) {
            System.out.print((i + 1) + "|");
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (showMines && isMine[i][j]) {
                    System.out.print(MINE_SYMBOL);
                } else if (isMarked[i][j]) {
                    System.out.print(MARKED_CELL_SYMBOL);
                } else {
                    System.out.print(isExplored[i][j] ? field[i][j] : SAFE_CELL_SYMBOL);
                }
            }
            System.out.println("|");
        }
        System.out.println("-|---------|");
    }
}
