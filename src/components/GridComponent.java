package components;

import controller.GameController;
import entity.GridStatus;
import entity.Player;
import minesweeper.GamePanel;
import minesweeper.MainFrame;
//import sun.tools.jar.Main;

import java.awt.*;

public class GridComponent extends BasicComponent {
    public static int gridSize = 30;


    private int row;
    private int col;
    private GridStatus status = GridStatus.Covered;
    private int content = 0;

    public GridComponent(int x, int y) {
        this.setSize(gridSize, gridSize);
        this.row = x;
        this.col = y;
    }

    public GridComponent(int x, int y, GridStatus status) {
        this.setSize(gridSize, gridSize);
        this.row = x;
        this.col = y;
        this.status = status;
    }

    @Override
    public void onMouseLeftClicked() {
        System.out.printf("Gird (%d,%d) is left-clicked.\n", row, col);
        if (GamePanel.chessboard[row][col] == -1 && GameController.turnNumber == 0 && MainFrame.controller.getOnTurnPlayer() == MainFrame.controller.getP1()) {
            MainFrame.controller.getGamePanel().initialAgain();
            repaint();
        }//首发触雷

        if (GamePanel.chessboard[row][col] == 0) {
            openWhites(row, col);
            MainFrame.controller.nextTurn();
        }
        if (GamePanel.chessboard[row][col] == -1) {
            MainFrame.controller.getOnTurnPlayer().costScore();
            MainFrame.controller.getOnTurnPlayer().addMistake();
            MainFrame.mineRest--;
        }//左键点击中雷扣分
        if (this.status == GridStatus.Covered || this.status == GridStatus.See) {
            this.status = GridStatus.Clicked;
            repaint();
            MainFrame.controller.nextTurn();
        }

        //TODO: 在左键点击一个格子的时候，还需要做什么？
    }

    @Override
    public void onMouseRightClicked() {
        System.out.printf("Gird (%d,%d) is right-clicked.\n", row, col);
        if (GamePanel.chessboard[row][col] == -1 && (this.status == GridStatus.Covered || this.status == GridStatus.See)) {
            MainFrame.controller.getOnTurnPlayer().addScore();
            this.status = GridStatus.Flag;
            repaint();
            MainFrame.mineRest--;
            MainFrame.controller.nextTurn();
        }//TODO:插旗恰好命中地雷得分增加，需要更改显示方式
        if (GamePanel.chessboard[row][col] != -1 && (this.status == GridStatus.Covered || this.status == GridStatus.See)) {
            this.status = GridStatus.Clicked;//插旗错误需要把周围雷区的数字展开
            repaint();
            if (GamePanel.chessboard[row][col] == 0) {
                openWhites(row, col);
                MainFrame.controller.getOnTurnPlayer().addMistake();
                MainFrame.controller.nextTurn();
                return;
            }
            MainFrame.controller.getOnTurnPlayer().addMistake();
            MainFrame.controller.nextTurn();
        }//TODO；插旗错误，需要弹窗展示“你错了”类似提示语句，图片等也可
//        if (this.status == GridStatus.Covered) {
//            this.status = GridStatus.Flag;
//            repaint();
//            MainFrame.controller.nextTurn();
//        }

        //TODO: 在右键点击一个格子的时候，还需要做什么？
    }

    public void draw(Graphics g) {

        if (this.status == GridStatus.Covered) {
            g.setColor(Color.CYAN);
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
        if (this.status == GridStatus.Clicked) {

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(content), getWidth() / 2 - 5, getHeight() / 2 + 5);
        }
        if (this.status == GridStatus.Flag) {

            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
            g.setColor(Color.RED);
            g.drawString("F", getWidth() / 2 - 5, getHeight() / 2 + 5);
        }
        if (this.status == GridStatus.Scanned) {

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(content), getWidth() / 2 - 5, getHeight() / 2 + 5);
        }
        if (this.status == GridStatus.See) {

            g.setColor(Color.RED);
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }

    public void setContent(int content) {
        this.content = content;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.printComponents(g);
        draw(g);
    }

    private void openWhites(int i, int j) {
        if (i < 0 || i >= MainFrame.xCount || j < 0 || j >= MainFrame.yCount || GamePanel.chessboard[i][j] == 0 && GamePanel.mineField[i][j].status == GridStatus.Scanned)//越界
            return;
        if (GamePanel.chessboard[i][j] == 0) GamePanel.mineField[i][j].status = GridStatus.Scanned;
        if (GamePanel.chessboard[i][j] == 0) {
            for (int k = i - 1; k <= i + 1; k++) {
                for (int m = j - 1; m <= j + 1; m++) {
                    if (k < 0 || k >= MainFrame.xCount || m < 0 || m >= MainFrame.yCount) continue;
                    if (GamePanel.mineField[k][m].status == GridStatus.Covered) {
                        GamePanel.mineField[k][m].status = GridStatus.Clicked;
                        GamePanel.mineField[k][m].repaint();
                    }
                    if (GamePanel.mineField[k][m].status == GridStatus.Scanned) GamePanel.mineField[k][m].repaint();
                }
            }
            if (i - 1 >= 0 && GamePanel.chessboard[i - 1][j] == 0)
                openWhites(i - 1, j);
            if (i - 1 >= 0 && j - 1 >= 0 && GamePanel.chessboard[i - 1][j - 1] == 0)
                openWhites(i - 1, j - 1);
            if (i - 1 >= 0 && j + 1 < MainFrame.yCount && GamePanel.chessboard[i - 1][j + 1] == 0)
                openWhites(i - 1, j + 1);
            if (j - 1 >= 0 && GamePanel.chessboard[i][j - 1] == 0)
                openWhites(i, j - 1);
            if (j + 1 < MainFrame.yCount && GamePanel.chessboard[i][j + 1] == 0)
                openWhites(i, j + 1);
            if (i + 1 < MainFrame.xCount && GamePanel.chessboard[i + 1][j] == 0)
                openWhites(i + 1, j);
            if (i + 1 < MainFrame.xCount && j + 1 < MainFrame.yCount && GamePanel.chessboard[i + 1][j + 1] == 0)
                openWhites(i + 1, j + 1);
            if (i + 1 < MainFrame.xCount && j - 1 >= 0 && GamePanel.chessboard[i + 1][j - 1] == 0)
                openWhites(i + 1, j - 1);
        } else {
            if (GamePanel.mineField[i][j].status == GridStatus.Covered) {
                GamePanel.mineField[i][j].status = GridStatus.Clicked;
                GamePanel.mineField[i][j].repaint();
            }
        }
    }//递归算法展开空区域

    public GridStatus getStatus() {
        return status;
    }

    public static void seeAllMine() {
        for (int i = 0; i < MainFrame.xCount; i++) {
            for (int j = 0; j < MainFrame.yCount; j++) {
                if (GamePanel.chessboard[i][j] == -1 && GamePanel.mineField[i][j].getStatus() == GridStatus.Covered) {
                    GamePanel.mineField[i][j].status = GridStatus.See;
                    GamePanel.mineField[i][j].repaint();
                }
            }
        }
    }//直接显示所有的地雷
}
