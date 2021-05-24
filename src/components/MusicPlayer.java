package components;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MusicPlayer implements Runnable {
    File soundFile;
    Thread thread;
    boolean circulate;

    public MusicPlayer(String filepath, boolean circulate)
            throws FileNotFoundException {
        this.circulate = circulate;
        soundFile = new File(filepath);
        if (!soundFile.exists()) {
            throw new FileNotFoundException(filepath + "未找到");
        }
    }

    public void run() {
        byte[] auBuffer = new byte[1024 * 320];
        do {
            AudioInputStream audioInputStream = null;
            SourceDataLine auLine = null;
            try {
                //从音乐文件夹获取音频输入流
                audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                AudioFormat format = audioInputStream.getFormat(); //获取音频格式
                //按照源数据行类型和直接订音频格式创建数据行对象
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                // 利用音频系统类获得与指定 Line.Info 对象中的描述匹配的行对象
                auLine = (SourceDataLine) AudioSystem.getLine(info);
                auLine.open(format);   //按照指定格式打开源数据行
                auLine.start();  // 源数据行开始读写活动
                int byteCount = 0;  // 记录音频输入流独处的字节数
                while (byteCount != -1) {  // 如果音频输入流读取的字节数不为-1
                    byteCount = audioInputStream.read(auBuffer, 0, auBuffer.length);//从音频数据流读出128k的数据
                    if (byteCount >= 0) {  //如果读出有效数据
                        auLine.write(auBuffer, 0, byteCount);//将有效数据写入数据行中
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } finally {
                auLine.drain();  //清空数据行
                auLine.close();  //关闭数据行
            }
        } while (circulate);
    }

    public void play() {
        thread = new Thread(this);  //创建线程对象
        thread.start();  //开启线程
    }

    public void stop() {
        thread.stop(); //强制关闭线程
    }
}
