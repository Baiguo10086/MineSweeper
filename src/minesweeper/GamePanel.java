package minesweeper;

import components.GridComponent;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel {
    public static GridComponent[][] mineField;
    public static int[][] chessboard;
    private final Random random = new Random();

    /**
     * 初始化一个具有指定行列数格子、并埋放了指定雷数的雷区。
     *
     * @param xCount    count of grid in column
     * @param yCount    count of grid in row
     * @param mineCount mine count
     */
    public GamePanel(int xCount, int yCount, int mineCount) {
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setSize(GridComponent.gridSize * yCount, GridComponent.gridSize * xCount);

        initialGame(xCount, yCount, mineCount);

        repaint();
    }

    public void initialGame(int xCount, int yCount, int mineCount) {
        mineField = new GridComponent[xCount][yCount];

        generateChessBoard(xCount, yCount, mineCount);

        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                GridComponent gridComponent = new GridComponent(i, j);
                gridComponent.setContent(chessboard[i][j]);
                gridComponent.setLocation(j * GridComponent.gridSize, i * GridComponent.gridSize);
                mineField[i][j] = gridComponent;
                this.add(mineField[i][j]);
            }
        }

    }

    public void initialAgain() {
        generateChessBoard(MainFrame.xCount, MainFrame.yCount, MainFrame.mineCount);

        for (int i = 0; i < MainFrame.xCount; i++) {
            for (int j = 0; j < MainFrame.yCount; j++) {
                mineField[i][j].setContent(chessboard[i][j]);
                mineField[i][j].setLocation(j * GridComponent.gridSize, i * GridComponent.gridSize);
            }
        }
    }

    public static void reloadGame(int[][] data) {

    }//用于读取存档加载游戏


    public void generateChessBoard(int xCount, int yCount, int mineCount) {
        //todo: generate chessboard by your own algorithm
        chessboard = new int[xCount][yCount];
        int mineCnt = 0;
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                // suppose -1 represents mine
                chessboard[i][j] = random.nextInt(10) - 1;
                if (chessboard[i][j] == -1) mineCnt++;
                if (mineCnt == mineCount && chessboard[i][j] == -1) {
                    boolean judge = false;
                    while (!judge) {
                        chessboard[i][j] = random.nextInt(10) - 1;
                        if (chessboard[i][j] != -1) judge = true;
                    }
                }//防止雷超出个数
                if (i == xCount - 1 && j == yCount - 1 && mineCnt < mineCount) {
                    do {
                        int xRan = random.nextInt(xCount);
                        int yRan = random.nextInt(yCount);
                        if (chessboard[xRan][yRan] != -1) {
                            chessboard[xRan][yRan] = -1;
                            mineCnt++;
                        }
                    } while (mineCnt < mineCount);
                }//防止雷个数不够，补足至需求雷个数
            }
        }
        for (int i = 1; i < xCount - 1; i++) {
            for (int j = 1; j < yCount - 1; j++) {
                if (chessboard[i][j] == -1 && mineCounter(i, j) == 9) generateChessBoard(xCount, yCount, mineCount);
            }
        }//检测是否产生雷区过度密集，九雷连坐

        for (int k = 0; k < xCount; k++) {
            for (int m = 0; m < yCount; m++) {
                // suppose -1 represents mine
                if (chessboard[k][m] != -1) {
                    chessboard[k][m] = mineCounter(k, m);
                }
            }
        }
        //更改数字，使其显示地雷个数0~8
    }

    /**
     * 获取一个指定坐标的格子。
     * 注意请不要给一个棋盘之外的坐标哦~
     *
     * @param x 第x列
     * @param y 第y行
     * @return 该坐标的格子
     */
    public GridComponent getGrid(int x, int y) {
        try {
            return mineField[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public static int mineCounter(int row, int col) {
        int cnt = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i < 0 || i >= MainFrame.xCount || j < 0 || j >= MainFrame.yCount) continue;
                if (GamePanel.chessboard[i][j] == -1) {
                    cnt++;
                }
            }
        }
        return cnt;
    }//计算九宫格内的地雷个数
}
