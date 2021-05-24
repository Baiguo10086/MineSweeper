package controller;

import entity.GridStatus;
import minesweeper.GamePanel;
import entity.Player;
import minesweeper.MainFrame;
import minesweeper.ScoreBoard;


import java.io.*;
import java.util.ArrayList;


public class GameController {

    private Player p1;
    private Player p2;

    private Player onTurn;
    public static int turnNumber;
    public static int singleTurnNumber;
    private int clickCnt = 1;

    private GamePanel gamePanel;
    private ScoreBoard scoreBoard;

    public GameController(Player p1, Player p2) {
        this.init(p1, p2);
        this.onTurn = p1;
    }

    /**
     * 初始化游戏。在开始游戏前，应先调用此方法，给予游戏必要的参数。
     *
     * @param p1 玩家1
     * @param p2 玩家2
     */
    public void init(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.onTurn = p1;
        singleTurnNumber = 5;
        //TODO: 在初始化游戏的时候，还需要做什么？
        // 设置回合限制(直接给选项，1-5就行,这里我先直接给了5作为测试，后期更改的话对接用户需求就行），
        // 玩家自定义姓名，
        // 多人玩家模式还有多出来的自定义，而且多人的话对于存储当要求更高
    }

    /**
     * 进行下一个回合时应调用本方法。
     * 在这里执行每个回合结束时需要进行的操作。
     * <p>
     * (目前这里没有每个玩家进行n回合的计数机制的，请自行修改完成哦~）
     */
    public void nextTurn() {
        if (onTurn == p1 && clickCnt < singleTurnNumber) {
            clickCnt++;
            onTurn = p1;
        } else if (onTurn == p1 && clickCnt == singleTurnNumber) {
            clickCnt = 1;
            onTurn = p2;
        } else if (onTurn == p2 && clickCnt < singleTurnNumber) {
            clickCnt++;
            onTurn = p2;
        } else if (onTurn == p2 && clickCnt == singleTurnNumber) {
            clickCnt = 1;
            onTurn = p1;
            turnNumber++;
        }
        if (p1.getScore() - p2.getScore() > MainFrame.mineRest) {
            System.out.println(p1.getUserName() + "is the winner.");
            //TODO:end the game with something like pop windows
        }
        if (p2.getScore() - p1.getScore() > MainFrame.mineRest) {
            System.out.println(p2.getUserName() + "is the winner.");
            //TODO:end the game with something like pop windows
        }
        if (MainFrame.mineRest == 0 && p1.getMistake() > p2.getMistake()) {
            System.out.println(p2.getUserName() + "is the winner.");
            //TODO:end the game with something like pop windows
        }
        if (MainFrame.mineRest == 0 && p1.getMistake() < p2.getMistake()) {
            System.out.println(p1.getUserName() + "is the winner.");
            //TODO:end the game with something like pop windows
        }
        if (MainFrame.mineRest == 0 && p1.getMistake() == p2.getMistake()) {
            System.out.println("It ends in a draw.");
            //TODO:end the game with something like pop windows
        }
        System.out.println("Now it is " + onTurn.getUserName() + "'s turn.");
        scoreBoard.update();
        //TODO: 在每个回合结束的时候，还需要做什么 (例如...检查游戏是否结束？)

    }


    /**
     * 获取正在进行当前回合的玩家。
     *
     * @return 正在进行当前回合的玩家
     */
    public Player getOnTurnPlayer() {
        return onTurn;
    }


    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void removeGamePanel(GamePanel gamePanel){
        this.gamePanel=null;

    }

    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }


    public void readFileData(String fileName) throws IOException {
        File f = new File("D:/学习资料/javapro/Minesweeper2.0/src/" + fileName + ".txt");
        Reader r = new FileReader(f);
        ArrayList<Integer> dataList = new ArrayList<>();
        ArrayList<String> playerList = new ArrayList<>();
        BufferedReader br = new BufferedReader(r);
        int dataCnt = 0;
        if (f.exists()) {
            String s;
            while ((s = br.readLine()) != null) {
                String[] split = s.split("\t");
                for (int i = 0; i < split.length; i++) {
                    if (i<split.length - 4) {
                        dataList.add(Integer.parseInt(split[i]));
                        dataCnt++;
                    }
                    if (i>=split.length-4){
                        String[] strings=split[i].split(" ");
                        playerList.add(strings[0]);
                        playerList.add(strings[1]);
                    }
                }
            }
        }
        br.close();
        int[][] dataRe = new int[dataCnt][4];
        int arrayCnt = 0;
        int zCnt = 0;
        for (int i = 0; i < dataList.size(); i += 4) {
            for (int j = 0; j < 4; j++) {
                dataRe[zCnt][j] = dataList.get(arrayCnt);
                arrayCnt++;
            }
            zCnt++;
        }

        //todo: read date from file

    }


    public void writeDataToFile(String fileName) throws IOException {
        String data[][] = new String[GamePanel.chessboard.length * GamePanel.chessboard[0].length + 1][4];
        int dataCnt = 0;
        for (int i = 0; i < GamePanel.chessboard.length; i++) {
            for (int j = 0; j < GamePanel.chessboard[0].length; j++) {
                data[dataCnt][0] = String.valueOf(i);//row
                data[dataCnt][1] = String.valueOf(j);//col
                data[dataCnt][2] = String.valueOf(GamePanel.chessboard[i][j]);//mine number 0~8 and -1
                switch (GamePanel.mineField[i][j].getStatus()) {
                    case Covered:
                        data[dataCnt][3] = GridStatus.Covered.toString();
                        break;
                    case Flag:
                        data[dataCnt][3] = GridStatus.Flag.toString();
                        break;
                    case Clicked:
                        data[dataCnt][3] = GridStatus.Clicked.toString();
                        break;
                    case Scanned:
                        data[dataCnt][3] = GridStatus.Scanned.toString();
                        break;
                }
            }
        }//利用两层for循环把每一个格子存在的属性逐一保存
        data[GamePanel.chessboard.length * GamePanel.chessboard[0].length][0] = p1.getScore() + " " + p2.getScore();
        data[GamePanel.chessboard.length * GamePanel.chessboard[0].length][1] = p1.getUserName() + " " + p2.getUserName();
        data[GamePanel.chessboard.length * GamePanel.chessboard[0].length][2] = p1.getMistake() + " " + p2.getMistake();
        data[GamePanel.chessboard.length * GamePanel.chessboard[0].length][3] = MainFrame.controller.getOnTurnPlayer().getUserName() + " " + clickCnt;
        File f = new File("D:/学习资料/javapro/Minesweeper2.0/src/" + fileName + ".txt");
        if (f.exists()) {
            f.createNewFile();
        }
        FileWriter fw = new FileWriter(f);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < 4; j++) {
                fw.write(data[i][j] + "\t");
            }
            fw.write("\r\n");
        }
        fw.close();

        //todo: write data into file
    }


}
