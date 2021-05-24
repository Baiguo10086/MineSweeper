package minesweeper;


import components.GridComponent;
import controller.GameController;
import entity.Player;

import javax.swing.*;

public class MainFrame extends JFrame {
    public static GameController controller;
    public static int xCount;
    public static int yCount;
    public static int mineCount;
    public static int mineRest;

    public MainFrame() {
        //todo: change the count of xCount, yCount and mineCount by passing parameters from constructor
        xCount = 12;//grid of row
        yCount = 10;// grid of column
        mineCount = 20;// mine count
        mineRest = mineCount;

        this.setTitle("2021 CS102A Project Demo 2");
        this.setLayout(null);
        this.setSize(yCount * GridComponent.gridSize + 20, xCount * GridComponent.gridSize + 200);
        this.setLocationRelativeTo(null);

        Player p1 = new Player();
        Player p2 = new Player();

        controller = new GameController(p1, p2);
        GamePanel gamePanel = new GamePanel(xCount, yCount, mineCount);
        controller.setGamePanel(gamePanel);
        ScoreBoard scoreBoard = new ScoreBoard(p1, p2, xCount, yCount);
        controller.setScoreBoard(scoreBoard);

        this.add(gamePanel);
        this.add(scoreBoard);


        JButton clickBtn = new JButton("Click");
        clickBtn.setSize(80, 20);
        clickBtn.setLocation(5, gamePanel.getHeight() + scoreBoard.getHeight());
        add(clickBtn);
        clickBtn.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(this, "input here");
            System.out.println("fileName :" + fileName);

//            controller.readFileData(fileName);
//            controller.writeDataToFile(fileName);
        });

        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
