import java.util.ArrayList;
import java.util.HashMap;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.ImageIcon;


public class Group14_GamePanel extends JPanel{
    private Image backGround=new ImageIcon("./Image/back.png").getImage();
    private Image ending=new ImageIcon("./Image/ending.png").getImage();
    private Image playerImg1=new ImageIcon("./Image/person1.png").getImage();
    private Image playerImg2=new ImageIcon("./Image/person2.png").getImage();
    private Image playerImg3=new ImageIcon("./Image/down.png").getImage();
    private Image heart=new ImageIcon("./Image/heart.png").getImage();
    private Image bgmOn=new ImageIcon("./Image/bgmOn.png").getImage();
    private Image bgmOff=new ImageIcon("./Image/bgmOff.png").getImage();
    private Boolean personFlag;
    private int personImageCount;
    private Image ladderImg2=new ImageIcon("./Image/ladderSet.png").getImage();
    private HashMap<Integer,String> obstacleType=new HashMap<>();
    private ArrayList<Integer[]> roadNow=new ArrayList<>();
    private ArrayList<Integer> paperNow=new ArrayList<>();
    private Group14_Player player;
    public Group14_GamePanel(Group14_Player player){
        this.player=player;
        personFlag=true;
        personImageCount=1;
        loadHashMap();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int h=525-player.getHeight();
        int c=player.getCol()*160+250;
        Font font=new Font("Serif",1,48);
        g.setFont(font);
        if(player.getGame()==2){
            g.drawImage(ending,0,0,1280,720,null);
            String str=String.valueOf(player.getLength());
            g.drawString(str, 124, 190);
            str=String.valueOf(player.getPaper());
            g.drawString(str, 124, 280);
            
            font=new Font("Serif",1,96);
            g.setFont(font);
            g.drawString("Nice Work!" ,420,365);
        }else if(player.getGame()==0){
            Image backGround=new ImageIcon("./Image/first.png").getImage();
            g.drawImage(backGround,0,0,1280,720,null);
            if(player.getCol()!=0){
                int y=player.getCol()*95-5;
                if(player.getCol()==1){
                    g.drawImage(new ImageIcon("./Image/start.png").getImage(), 950, y, 210, 60, null);
                }else if(player.getCol()==2){
                    g.drawImage(new ImageIcon("./Image/rule.png").getImage(), 950, y, 210, 60, null);
                }else if(player.getCol()==3){
                    g.drawImage(new ImageIcon("./Image/exit.png").getImage(), 950, y, 210, 60, null);
                }
                g.drawRoundRect(950, y-4, 220, 69,23,20);
            }
        }else{
            g.drawImage(backGround,0,0,1280,720,null);
            for(int i=0;i<player.getLife();i++){
                g.drawImage(heart,35+50*i,15,50,50,null);
            }
            for(int i=0;i<player.getLadderNum();i++){
                g.drawImage(new ImageIcon("./Image/ladder1.png").getImage(),22+40*i,70,75,50,null);
            }
            g.drawImage(new ImageIcon("./Image/paper.png").getImage(),30,127,50,50,null);
            String str=String.valueOf(player.getPaper());
            g.drawString(": "+str, 80, 169);
            if(player.isMagnetGet()){
                g.drawImage(new ImageIcon("./Image/magnet1.png").getImage(),25,184,50,50,null);
            }
            
            ArrayList<Integer[]> road=(ArrayList<Integer[]>)roadNow.clone();
            for(Integer[] obs:road){
                Image obsImg=new ImageIcon("./Image/"+obstacleType.get(obs[2])).getImage();
                int[] temp=getPosition((int)obs[0],(int)obs[1]);
                if(obs[0]-player.getPosition()>=0){
                    if(obs[2].equals(8)){
                        temp[1]-=40;
                        temp[3]+=40;
                    }else if(obs[2].equals(9)){
                        continue;
                    }else if(obs[2].equals(10)){
                        obsImg=new ImageIcon("./Image/shadow.png").getImage();
                    }
                    g.drawImage(obsImg,temp[0],temp[1],temp[2],temp[3],null);
                }
            }

            ArrayList<Integer> paper=(ArrayList<Integer>)paperNow.clone();
            for(int i=paper.size()-1;i>=0;i--){
                Image obsImg=new ImageIcon("./Image/paper.png").getImage();
                Image ringImg=new ImageIcon("./Image/paperRing.png").getImage();
                int[] temp=getPosition(i,(int)paper.get(i));
                if(i-player.getPosition()>4){
                    g.drawImage(obsImg,temp[0]+30,temp[1],temp[2]/2,temp[3]/2,null);
                    if(player.isMagnetGet()){
                        g.drawImage(ringImg,temp[0]+30,temp[1],temp[2]/2,temp[3]/2,null);
                    }
                }else if((!player.isMagnetGet())&&(i-player.getPosition()>=0)){
                    g.drawImage(obsImg,temp[0]+30,temp[1],temp[2]/2,temp[3]/2,null);
                }
            }
            if(player.getHeight()>9){
                if(personFlag){
                    g.drawImage(playerImg1,c,h,175,175,null);
                }else{
                    g.drawImage(playerImg2,c,h,175,175,null);
                }
            }else{
                g.drawImage(playerImg3,c,h,175,175,null);
            }
            personImageCount++;
            if(personImageCount==6){    
                personFlag=!personFlag;
                personImageCount=1;
            }
            if(player.isMagnetGet()){
                g.drawImage(new ImageIcon("./Image/magnet1.png").getImage(),c+25,h-5,35,35,null);
            }
            if(player.isLadderSet()){
                g.drawImage(ladderImg2,c+25,h+7,125,125,null);
            }
            
            for(Integer[] obs:road){
                Image obsImg=new ImageIcon("./Image/"+obstacleType.get(obs[2])).getImage();
                int[] temp=getPosition((int)obs[0],(int)obs[1]);
                if(obs[0]-player.getPosition()>=0){
                    if(obs[2].equals(9)||obs[2].equals(10)){
                        if(obs[2].equals(10)){
                            temp[0]-=10;
                            temp[1]-=120;
                        }
                        g.drawImage(obsImg,temp[0],temp[1],temp[2],temp[3],null);
                    }
                }
            }
        }
        if(player.isBGMOn()){
            g.drawImage(bgmOn,1210,10,50,50,null);
        }else{
            g.drawImage(bgmOff,1210,10,50,50,null);
        }
    }
    
    private int[] getPosition(int row ,int col){
        int[] obsGUIData=new int[4];
        int GUIRow=row-player.getPosition();
        if(col==1){
            obsGUIData[0]=445+GUIRow*2;
        }else if(col==2){
            obsGUIData[0]=605-GUIRow/2;
        }else if(col==3){
            obsGUIData[0]=755-GUIRow*2;
        }
        obsGUIData[1]=560-GUIRow*22;
        obsGUIData[2]=100+(7-GUIRow)*4/3;
        obsGUIData[3]=100+(7-GUIRow)*4/3;
        
        return obsGUIData;
    }
    
    public synchronized void setRoadNow(ArrayList<Integer[]> road){
        roadNow=road;
    }
    
    public void setPaperNow(ArrayList<Integer> paper){
        paperNow=paper;
    }
    
    private void loadHashMap(){
        
        obstacleType.put(0,"ladder.png");
        obstacleType.put(1,"paper.png");
        obstacleType.put(2,"magnet.png");
        obstacleType.put(3,"bandaid.png");
        obstacleType.put(4,"rift.png");
        
        obstacleType.put(5,"banana.png");
        obstacleType.put(6,"pit.png");
        obstacleType.put(7,"car.png");
        obstacleType.put(8,"bus.png");
        obstacleType.put(9,"kanbung.png");
        obstacleType.put(10,"bird.png");
        obstacleType.put(11,"pedestrian.png");
    }
}