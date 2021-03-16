package com.company;

import com.sun.istack.internal.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author youssef ezzeldeen
 */
public class Sudoku {
    private int[][] BigBox;
    private static final int EMPTY_VALUE = 0;
    private static final int COLUMN_SIZE = 9;
    private static final int ROW_SIZE = 9;

    public Sudoku() {
    }

    private boolean checkRow(int row, int number) {
        for (int i = 0; i < ROW_SIZE; i++) {
            if (BigBox[row][i] == number) {
                return true;
            }
        }

        return false;
    }

    private boolean checkColumn(int column, int number) {
        for (int i = 0; i < COLUMN_SIZE; i++) {
            if (BigBox[i][column] == number) {
                return true;
            }
        }

        return false;
    }

    private boolean checkBox(int row, int column, int number) {
        int r = row - row % 3; // 7 - 7%3 = 6
        int c = column - column % 3; //5 - 5%3 = 3

        for (int i = r/*7 */; i < r + 3/* 9 */; i++) {
            for (int j = c/*4*/; j < c + 3/*6*/; j++) {
                if (BigBox[i][j] == number) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isOk(int row, int column, int number) {
        return !checkRow(row, number) && !checkColumn(column, number) && !checkBox(row, column, number);
    }

    private boolean solve() {
        for (int row = 0; row < ROW_SIZE; row++) {
            for (int column = 0; column < COLUMN_SIZE; column++) {

                if (BigBox[row][column] == EMPTY_VALUE) {
                    for (int number = 1; number <= COLUMN_SIZE; number++) {
                        if (isOk(row, column, number)) {
                            BigBox[row][column] = number;
                            if (solve()) {
                                return true;
                            } else {
                                BigBox[row][column] = EMPTY_VALUE;
                            }
                        }
                    }

                    return false;
                }
            }
        }

        return true;
    }

    private void print() {
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COLUMN_SIZE; j++) {
                System.out.print(" " + BigBox[i][j]);
            }

            System.out.println();
        }

        System.out.println();
    }

    private void FileResult() {
        File file = new File(System.getProperty("user.home") + "\\Desktop" + "\\result.txt");
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter(file, true);

            for (int i = 0; i < ROW_SIZE; i++) {
                for (int j = 0; j < COLUMN_SIZE; j++) {
                    myWriter.write(" " + BigBox[i][j]);
                }

                myWriter.write("\n");
            }

            myWriter.write("\n");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<int[][]> readDataFile(@NotNull String path) {
        List<int[][]> res = new ArrayList<>();
        int[][] Grid = new int[COLUMN_SIZE][ROW_SIZE];
        try {
            Scanner myReader = new Scanner(new File(path));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.contains(".")) {
                    data = data.replace(".", "0");
                    data = "f" + data;
                    int j = 0;
                    for (int i = 1; i < data.length(); i++) {
                        Grid[j][(i - 1) % 9] = Integer.parseInt(data.charAt(i) + "");
                        if (i % 9 == 0) {
                            j++;
                        }
                    }
                    res.add(Grid);
                } else {
                    if (!Character.isAlphabetic(data.charAt(0))) {
                        for (int i = 0; i < Grid.length; i++) {
                            if (i != 0) {
                                data = myReader.nextLine();
                            }
                            for (int j = 0; j < Grid[i].length; j++) {
                                Grid[i][j] = Integer.parseInt(data.charAt(j) + "");
                            }
                        }
                        res.add(Grid);
                    } else {
                        Grid = new int[COLUMN_SIZE][COLUMN_SIZE];
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("خطأ : " + e.getMessage());
        }
        return res;
    }

    public void result(String path) {
        List<int[][]> datas = readDataFile(path);
        for (int i = 0; i < datas.size(); i++) {
            BigBox = datas.get(i);
            System.out.println("المصفوفة التي رقم" + (i + 1));
            print();
            if (solve()) {
                System.out.println("الحل للمصفوفة رقم " + (i + 1));
                print();
            } else {
                System.out.println("لا يوجد حل للمصفوفة رقم" + (i + 1));
            }
        }
    }

    public void resultFile(String path) {
        List<int[][]> datas = readDataFile(path);
        for (int i = 0; i < datas.size(); i++) {
            BigBox = datas.get(i);
            System.out.println("المصفوفة التي رقم" + (i + 1));
            print();
            if (solve()) {
                FileResult();
            } else {
                System.out.println("لا يوجد حل للمصفوفة رقم" + (i + 1));
            }
        }
    }

    public void result(int[][] data) {
        BigBox = data;
        System.out.println("المصفوفة التي قمت بإدخالها");
        print();
        if (solve()) {
            System.out.println("حل المصفوفة التي قمت بإدخالها");
            print();
        } else {
            System.out.println("لا يوجد حل للمصفوفة");
        }
    }
}

