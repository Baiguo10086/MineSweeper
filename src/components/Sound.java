package components;

import java.io.FileNotFoundException;

public class Sound {
    static final String DIR = "music/";// 音乐文件夹
    static final String BACKGROUND = "PacManFever.wav";// 背景音乐
    static final String BOOM = "duang.wav";// 爆炸音效
    static final String BINGO = "金币.wav";// 得分音效

    static public void boom() {
        play(DIR + BOOM, false);// 播放一次爆炸音效
    }

    static public void bingo() {
        play(DIR + BINGO, false);// 播放一次得分音效
    }

    static public void background() {
        play(DIR + BACKGROUND, true);// 循环播放背景音乐
    }

    private static void play(String file, boolean circulate) {
        try {
            // 创建播放器
            MusicPlayer player = new MusicPlayer(file, circulate);
            player.play();// 播放器开始播放
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }




}
