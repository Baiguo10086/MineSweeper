package minesweeper;

import components.MusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {
    private JFrame firstFrame;
    private JButton start, setting;
    private int st = 0;


    Icon icon = new ImageIcon("image/小黄人警报.gif");
    Icon startIcon = new ImageIcon("image/开始游戏3.png");
    Icon setIcon = new ImageIcon("image/齿轮3.png");
    public Frame() {
        firstFrame = new JFrame("快乐扫雷");
        Container fFContainer = firstFrame.getContentPane();
        fFContainer.setLayout(null);
        firstFrame.setResizable(false);//不可变窗体


        JLabel jl = new JLabel(icon);
        //jl.setIcon(icon);
        jl.setOpaque(true);
        jl.setBounds(0, 0, 800, 500);
        fFContainer.add(jl);

        start = new JButton();
        start.setSize(214, 66);
        start.setIcon(startIcon);
        start.setLocation(490, 280);
        start.setHideActionText(true);
        start.setBorderPainted(false);//去边框
        start.setContentAreaFilled(false);//去填充

        start.addActionListener(new jbAction());
        jl.add(start);

        setting = new JButton();
        setting.setSize(70, 59);
        setting.setIcon(setIcon);
        setting.setLocation(715, 405);
        setting.setHideActionText(true);
        setting.setBorderPainted(false);//去边框
        setting.setContentAreaFilled(false);//去填充
        setting.addActionListener(e -> {//音乐 音效 作弊

            //JFrame frame = new JFrame();
            //Container setContainer = new Container();

//            Container c = firstFrame.getParent();// 获取面板父容器
//            while (!(c instanceof MainFrame)) {// 如果父容器不是主窗体类
//                c = c.getParent();// 继续获取父容器的父容器
//            }
//            MainFrame frame = (MainFrame) c;// 将容器强制转换为主窗体类
            new SetFrame(firstFrame);// 弹出

        });
        jl.add(setting);

        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("image/小黄人鼠标.png").getImage(), new Point(10, 20), "stick");
        firstFrame.setCursor(cursor);
        firstFrame.setSize(800, 500); //设置显示屏幕大小800*500
        firstFrame.setLocationRelativeTo(null);//中间弹出 写于main中
        firstFrame.setVisible(true);
        firstFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
/*
    public static void main(String[] args) {
        new Frame();
    }

 */

    class jbAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if ((e.getSource() == start) && st == 0) {
                //调取音乐关闭
                start.setIcon(setIcon);
                st = 1;
            } else if ((e.getSource() == start) && st == 1) {
                //调取音乐开启
                start.setIcon(startIcon);
                st = 0;
            }

        }
    }
}
