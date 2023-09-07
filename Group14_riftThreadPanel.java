import java.util.Random;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.ImageIcon;


public class Group14_riftThreadPanel extends JPanel implements Runnable{
    private Random ra=new Random();
    //private int[] road=new int[560];
    private int[] road=new int[280];
    //private boolean[] roadstyle=new boolean[560];
    private boolean[] roadstyle=new boolean[280];
    private Group14_Player player;
    private Image backGround=new ImageIcon(".\\Image\\riftback.png").getImage();
    private Image playerImg1=new ImageIcon(".\\Image\\person1.png").getImage();
    private Image playerImg2=new ImageIcon(".\\Image\\person2.png").getImage();
    private Image soundOn=new ImageIcon(".\\Image\\bgmOn.png").getImage();
    private Image soundOff=new ImageIcon(".\\Image\\bgmOff.png").getImage();
    private int playerRow=1;
    private Boolean personFlag;
    private int personImageCount;
    public Group14_riftThreadPanel(Group14_Player player){
        newRoad();
        this.player=player;
        personFlag=true;
        personImageCount=1;
        setVisible(true);
    }
    
    public void run(){
        if(player.isMagnetGet()){
            //player.addCount(560);
            player.addCount(280);
        }
        //for(int i=0;i<560;i++){
            for(int i=0;i<280;i++){
            if(roadstyle[i]){
                if((road[playerRow-1]==player.getCol())){
                    player.getObject().runObject(1);
                }
            }else{
                if((road[playerRow-1]!=player.getCol())){
                    player.getObject().runObject(1);
                }
            }
            playerRow++;
            player.setPosition(playerRow);
            try{
                Thread.sleep(50);
            }catch(InterruptedException iE){
                System.exit(0);
            }
        }
        player.setSleep(player.getSleep()+10);
        setVisible(false);
    }
    
    private void newRoad(){
        //for(int i=0;i<80;i++){
            for(int i=0;i<40;i++){
            int temp=ra.nextInt(3)+1;
            Boolean tmp=ra.nextBoolean();
            for(int k=0;k<7;k++){
                road[i*7+k]=temp;
                roadstyle[i*7+k]=tmp;
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        int c=player.getCol()*160+250;
        int h=525-player.getHeight();
        Font font=new Font("Serif",1,48);
        g.setFont(font);    
        g.setColor(Color.WHITE);    
        g.drawImage(backGround,0,0,1280,720,null);
        //for(int i=559;i>=0;i--){
            for(int i=279;i>=0;i--){
            Image obsImg=new ImageIcon(".\\Image\\paper.png").getImage();
            int[] temp=getPosition(i,1);
            int[] temp2=getPosition(i,2);
            int[] temp3=getPosition(i,3);
            if(i-player.getPosition()>=0){
                if(roadstyle[i]){
                    if(road[i]==1){
                        g.drawImage(obsImg,temp[0]+30,temp[1],temp[2]/2,temp[3]/2,null);
                    }else if(road[i]==2){
                        g.drawImage(obsImg,temp2[0]+30,temp2[1],temp2[2]/2,temp2[3]/2,null);
                    }else if(road[i]==3){
                        g.drawImage(obsImg,temp3[0]+30,temp3[1],temp3[2]/2,temp3[3]/2,null);
                    }
                }else{
                    if(road[i]!=1){
                        g.drawImage(obsImg,temp[0]+30,temp[1],temp[2]/2,temp[3]/2,null);
                    }
                    if(road[i]!=2){
                        g.drawImage(obsImg,temp2[0]+30,temp2[1],temp2[2]/2,temp2[3]/2,null);
                    }
                    if(road[i]!=3){
                        g.drawImage(obsImg,temp3[0]+30,temp3[1],temp3[2]/2,temp3[3]/2,null);
                    }
                }
            }
        }
        if(personFlag){
            g.drawImage(playerImg1,c,h,175,175,null);
        }else{
            g.drawImage(playerImg2,c,h,175,175,null);
        }
        personImageCount++;
        if(personImageCount==3){    
            personFlag=!personFlag;
            personImageCount=1;
        }
        g.drawImage(new ImageIcon(".\\Image\\paper.png").getImage(),30,15,50,50,null);
        String str=String.valueOf(player.getPaper());
        g.drawString(": "+str, 80, 56);
        if(player.isBGMOn()){
            g.drawImage(soundOn,1210,10,50,50,null);
        }else{
            g.drawImage(soundOff,1210,10,50,50,null);
        }
    }
    
    private int[] getPosition(int row ,int col){
        int[] obsGUIData=new int[4];
        int GUIRow=row-player.getPosition();
        if(col==1){
            obsGUIData[0]=450+GUIRow*2;
        }else if(col==2){
            obsGUIData[0]=610-GUIRow/2;
        }else if(col==3){
            obsGUIData[0]=760-GUIRow*2;
        }
        obsGUIData[1]=590-GUIRow*22;
        obsGUIData[2]=100+(7-GUIRow)*4/3;
        obsGUIData[3]=100+(7-GUIRow)*4/3;
        return obsGUIData;
    }
}