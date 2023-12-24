package org.battle;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? > ");
        int numberOfMines = Integer.parseInt(scanner.nextLine().trim());

        Minesweeper minesweeper = new Minesweeper(numberOfMines);

        while (true) {
            minesweeper.printField(false);
            System.out.print("Set/unset mines marks or claim a cell as free: > ");
            String[] parts = scanner.nextLine().trim().split(" ");
            int y = Integer.parseInt(parts[0]) - 1;
            int x = Integer.parseInt(parts[1]) - 1;
            String command = parts[2];

            if ("mine".equals(command)) {
                minesweeper.toggleMark(x, y);
            } else if ("free".equals(command)) {
                if (!minesweeper.explore(x, y)) {
                    minesweeper.printField(true);
                    System.out.println("You stepped on a mine and failed!");
                    break;
                }
            }

            if (minesweeper.checkWin()) {
                minesweeper.printField(false);
                System.out.println("Congratulations! You found all the mines!");
                break;
            }
        }
        scanner.close();
    }
}