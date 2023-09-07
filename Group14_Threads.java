import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;

public class Group14_Threads implements Runnable{
    private Random random=new Random();
    private Thread addtime=new Thread();
    private Group14_GameFrame jF;
    private Group14_GamePanel jP;
    private Group14_Player player;
    private Group14_Object object;
    private Integer[][] obstacleZero={{1,1,2,2,2,2,2},{4,1,5},{5,3,6},{6,1,7}};
    private Integer[][] obstacleOne={{2,2,2,2,1,1,2},{2,1,8},{3,3,6},{4,2,0},{5,3,3},{6,1,9},{6,2,2}};
    private Integer[][] obstacleTwo={{3,3,3,3,2,2,2},{2,2,8},{2,3,5},{3,1,11},{4,3,3},{5,1,4},{7,2,6},{7,1,5}};
    private Integer[][] obstacleThree={{2,2,1,1,2,2,3},{1,3,2},{2,2,0},{2,1,10},{3,3,11},{4,2,9},{5,1,5},{6,1,4},{6,3,6}};
    private Integer[][] obstacleFour={{2,1,1,2,3,3,2},{1,3,10},{2,1,2},{3,2,7},{4,1,11},{4,3,3},{6,1,8},{7,3,0}};
    private HashMap<Integer,Integer[][]> roadMap=new HashMap<>();
    private ArrayList<Integer[]> roadNow=new ArrayList<>();
    private ArrayList<Integer> paper=new ArrayList<>();
    private int playerRow=1;
    private int[] roadNONow=new int[2];
    public Group14_Threads(Group14_Player player, Group14_GameFrame jF, Group14_GamePanel jP){
        super();
        this.player=player;
        object=new Group14_Object(player);
        loadHashMap();
        setNextRoad(obstacleZero,obstacleOne);
        roadNONow[0]=0;
        roadNONow[1]=1;
        this.jF=jF;
        this.jP=jP;
        this.jP.setRoadNow(roadNow);
        this.jP.setPaperNow(paper);
    }
    @Override
    public void run(){
        addTime();
        addtime.start();
        int arrayfriendly=0;
        while(player.getGame()==1){
            for(int i=arrayfriendly;i<roadNow.size();i++){
                Integer[] rowNow=roadNow.get(i);
                if(playerRow==rowNow[0]){
                    if(player.getCol()==rowNow[1]){
                        if(rowNow[2].equals(4)){
                            rift();
                        }else{
                            object.runObject(rowNow[2]);
                        }
                    }
                    arrayfriendly++;
                }else{
                    break;
                }
            }
            if(roadNow.get(arrayfriendly)[0]>28){
                arrayfriendly=0;
            }
            if((paper.get(playerRow-1)==player.getCol())||player.isMagnetGet()){
                object.runObject(1);
            }
            playerRow++;
            if(playerRow>28){
                changed();
            }
            player.setPosition(playerRow);
            if(player.getLife()<=0){
                player.setGame(2);
            }
            player.addLength(1);
            try{
                Thread.sleep(player.getSleep());
            }catch(InterruptedException iE){
                System.exit(0);
            }
        }
    }
    
    
    private void setNextRoad(Integer[][] rFront ,Integer[][] rBack){
        
        roadNow.clear();
        paper.clear();
        
        for(int i=0;i<7;i++){
            paper.add(rFront[0][i]);
            paper.add(rFront[0][i]);
            paper.add(rFront[0][i]);
            paper.add(rFront[0][i]);
        }
        for(int i=0;i<7;i++){
            paper.add(rBack[0][i]);
            paper.add(rBack[0][i]);
            paper.add(rBack[0][i]);
            paper.add(rBack[0][i]);
        }
        
        for(int i=1;i<rFront.length;i++){
            Integer[] temp1=rFront[i].clone();
            temp1[0]=temp1[0]*4-2;
            this.roadNow.add(temp1);
        }
        for(int i=1;i<rBack.length;i++){
            Integer[] temp2=rBack[i].clone();
            temp2[0]=temp2[0]*4-2+28;
            this.roadNow.add(temp2);
        }
    }    
    
    private synchronized void changed(){
        int nextnum=random.nextInt(4)+1;
        roadNONow[0]=roadNONow[1];
        roadNONow[1]=nextnum;
        setNextRoad(roadMap.get(roadNONow[0]), roadMap.get(roadNONow[1]));
        this.jP.setRoadNow(roadNow);
        playerRow=1;
    }
    
    private void addTime(){
        addtime=new Thread(){
            public void run(){
                while(player.getGame()==1){
                    try{
                        if(player.getSleep()>10){
                            player.setSleep(player.getSleep()-10);
                        }
                        Thread.sleep(15000);
                    }catch(InterruptedException iE){
                        System.exit(0);
                    }
                }
            }
        };
    }
    
    private void rift(){
        try{
            Group14_riftThreadPanel rift=new Group14_riftThreadPanel(player);
            jF.add(rift);
            jP.setVisible(false);
            jF.revalidate();
            Thread riftThread=new Thread(rift);
            riftThread.start();
            riftThread.join();
            jP.setVisible(true);
        }catch(InterruptedException e){
            System.exit(0);
        }
    }
    
    private void loadHashMap(){
        
        roadMap.put(0,obstacleZero);
        roadMap.put(1,obstacleOne);
        roadMap.put(2,obstacleTwo);
        roadMap.put(3,obstacleThree);
        roadMap.put(4,obstacleFour);
        
    }
}