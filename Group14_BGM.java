import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;

import java.io.File;
import java.io.IOException;

public class Group14_BGM{
    private Clip bgm;
    private File fileB=new File("BGM.wav");
    private Clip mouse;
    private File fileM=new File("switch.wav");
    private Clip start;
    private File fileS=new File("start.wav");
    private Clip move;
    private File fileR=new File("run.wav");
    private Clip jump;
    private File fileJ=new File("jump.wav");
    private AudioInputStream input1;
    private AudioInputStream input2;
    private AudioInputStream input3;
    private AudioInputStream input4;
    private AudioInputStream input5;

    public Group14_BGM(){
        try{
            bgm=AudioSystem.getClip();
            input1=AudioSystem.getAudioInputStream(fileB);
            bgm.open(input1);
            mouse=AudioSystem.getClip();
            input2=AudioSystem.getAudioInputStream(fileM);
            mouse.open(input2);
            start=AudioSystem.getClip();
            input3=AudioSystem.getAudioInputStream(fileS);
            start.open(input3);
            move=AudioSystem.getClip();
            input4=AudioSystem.getAudioInputStream(fileR);
            move.open(input4);
            jump=AudioSystem.getClip();
            input5=AudioSystem.getAudioInputStream(fileJ);
            jump.open(input5);
        }catch (UnsupportedAudioFileException e) {
            System.out.println("audio1 Exception!");
            System.exit(0);
        }catch (LineUnavailableException e) {
            System.out.println("audio2 Exception!");
            System.exit(0);
        }catch (IOException e) {
            System.out.println("audio3 Exception!");
            System.exit(0);
        }
    }

    public void turnOn(){
        bgm.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void turnOff(){
        bgm.stop();
    }
    public void mouseSound(){
        mouse.stop();
        mouse.loop(1);
    }
    public void moveSound(){
        move.stop();
        move.loop(1);
    }
    public void jumpSound(){
        jump.stop();
        jump.loop(1);
    }
    public void startSound(){
        start.start();
    }
}