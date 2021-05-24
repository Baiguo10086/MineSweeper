package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetFrame extends JDialog {

    private JButton bkGroundMusic, soundEffect, cheatBt;
    private JButton okBt;
    private JLabel settings, musicBk, musicEf, cheat;

    //0是开 1是关 作弊初始是关->1
    private int bk = 0;
    private int ef = 0;
    private int ch = 1;

    //1是开 2是关 作弊初始是关->2
    Icon bkIcon1 = new ImageIcon("image/音乐开");//要定
    Icon efIcon1 = new ImageIcon("image/音效开");//要定
    Icon cheatIcon1 = new ImageIcon("");//要定
    Icon okIcon1 = new ImageIcon("image/");//要定
    Icon bkIcon2 = new ImageIcon("image/音乐关");//要定
    Icon efIcon2 = new ImageIcon("image/音效开");//要定
    Icon cheatIcon2 = new ImageIcon("image/");//要定
    Icon okIcon2 = new ImageIcon("image/");//要定

    Icon icon = new ImageIcon("image/设置小黄人2.jpeg");

    public SetFrame(JFrame frame) {
        JDialog settings = new JDialog(frame, true);//true 阻断交流
        //Container container = this.getContentPane();
        //container.setLayout(null);
        settings.setResizable(false);//不可变窗体
        settings.setSize(260, 386);
        settings.setLocationRelativeTo(null);


        settings.setTitle("设置");
        settings.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        //frame.setSize(260, 386);
        //JPanel setP = new JPanel();
        JPanel setP = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("image/设置小黄人2.jpeg");
                img.paintIcon(this, g, 0, 0);
            }
        };
        setP.setSize(260, 386);
        settings.add(setP);
        //setP.setLayout(null);

        this.settings = new JLabel("设置", JLabel.CENTER);  // 标题标签，居中
        this.settings.setFont(new Font("黑体", Font.BOLD, 20));// 设置字体
        Color color = new Color(122, 162, 249);  //122,162,249
        this.settings.setForeground(color);// 蓝色体字
        setP.add(this.settings);
        //subtitle.setBounds(67,10,125,30);

        musicBk = new JLabel("背景音乐");
        musicBk.setFont(new Font("黑体", Font.BOLD, 17));//字体要换
        musicBk.setForeground(Color.BLACK);// 蓝色体字
        int musicX = (260 - musicBk.getWidth()) / 2;
        int musicY = settings.getY() + settings.getHeight() + 10;
        musicBk.setLocation(musicX, musicY);
        setP.add(musicBk);

        musicEf = new JLabel("音效");
        musicEf.setFont(new Font("黑体", Font.BOLD, 17));//字体要换
        musicEf.setForeground(Color.BLACK);// 蓝色体字
        int efX = (260 - musicEf.getWidth()) / 2;
        int efY = musicBk.getY() + musicBk.getHeight() + 10;
        musicEf.setLocation(efX, efY);
        setP.add(musicEf);

        cheat = new JLabel("音效");
        cheat.setFont(new Font("黑体", Font.BOLD, 17));//字体要换
        cheat.setForeground(Color.BLACK);// 蓝色体字
        int chX = (260 - cheat.getWidth()) / 2;
        int chY = musicEf.getY() + musicEf.getHeight() + 10;
        cheat.setLocation(chX, chY);
        setP.add(cheat);

        bkGroundMusic = new JButton();
        bkGroundMusic.setSize(214, 66);//要改
        bkGroundMusic.setIcon(bkIcon1);
        bkGroundMusic.setLocation(490, 280);
        bkGroundMusic.setHideActionText(true);
        bkGroundMusic.setBorderPainted(false);//去边框
        bkGroundMusic.setContentAreaFilled(false);//去填充

        bkGroundMusic.addActionListener(new jbAction());

        soundEffect = new JButton();
        soundEffect.setSize(214, 66);//要改
        soundEffect.setIcon(efIcon1);
        soundEffect.setLocation(490, 280);
        soundEffect.setHideActionText(true);
        soundEffect.setBorderPainted(false);//去边框
        soundEffect.setContentAreaFilled(false);//去填充

        soundEffect.addActionListener(new jbAction());

        cheatBt = new JButton();
        cheatBt.setSize(214, 66);//要改
        cheatBt.setIcon(cheatIcon2);
        cheatBt.setLocation(490, 280);
        cheatBt.setHideActionText(true);
        cheatBt.setBorderPainted(false);//去边框
        cheatBt.setContentAreaFilled(false);//去填充

        cheatBt.addActionListener(new jbAction());

        okBt = new JButton();
        okBt.setIcon(efIcon1);
        okBt.setLocation(490, 280);
        okBt.setHideActionText(true);
        okBt.setBorderPainted(false);//去边框
        okBt.setContentAreaFilled(false);//去填充

        okBt.addActionListener(new jbAction());

        //setP.setOpaque(true);

        this.setLocationRelativeTo(this);
        settings.setVisible(true);// 显示对话框

    }

    class jbAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if ((e.getSource() == bkGroundMusic) && bk == 0) {
                //调取音乐关闭
                bkGroundMusic.setIcon(bkIcon2);
                bk = 1;
            } else if ((e.getSource() == bkGroundMusic) && bk == 1) {
                //调取音乐开启
                bkGroundMusic.setIcon(bkIcon1);
                bk = 0;
            } else if ((e.getSource() == soundEffect) && ef == 0) {
                //调取音效关闭
                soundEffect.setIcon(efIcon2);
                ef = 1;
            } else if ((e.getSource() == soundEffect) && ef == 1) {
                //调取音效开启
                soundEffect.setIcon(efIcon1);
                bk = 0;
            } else if ((e.getSource() == cheatBt) && ch == 0) {
                //调取作弊关闭
                cheatBt.setIcon(cheatIcon2);
                ch = 1;
            } else if ((e.getSource() == cheatBt) && ch == 1) {
                //调取作弊开启
                cheatBt.setIcon(cheatIcon1);
                ch = 0;
            }


        }
    }

}
