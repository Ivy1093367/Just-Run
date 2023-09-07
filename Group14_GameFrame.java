import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Group14_GameFrame extends JFrame{
    private Group14_Player player;
    private Group14_BGM bgm;
    private Image frameIcon=new ImageIcon("./Image/Icon.png").getImage();
    private ruleFrame rule=new ruleFrame("Rule");
    private Group14_GamePanel jP;
    private Group14_Threads thread;
    private Thread start;
    private boolean sound=true;
    private boolean downsound=true;
    private int page=1;
    public Group14_GameFrame(Group14_Player player, Group14_BGM bgm){
        super("Just Run!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280,720);
        setIconImage(frameIcon);
        setResizable(false);
        this.player=player;
        this.bgm=bgm;
        this.bgm.turnOn();
        jP=new Group14_GamePanel(player);
        add(jP);
        thread = new Group14_Threads(player,this,jP);
        start = new Thread(thread);
        addMouseMotionListener(new mouseHandler());;
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                int x=e.getX();
                int y=e.getY();
                if(player.getGame()==0){
                    int choose=player.getCol();
                    if(choose==1){
                        rule.setVisible(false);
                        bgm.startSound();
                        gameStart();
                    }else if(choose==2){
                        rule.reset();
                    }else if(choose==3){
                        System.exit(0);
                    }
                }
                if(x>1210&&x<1260&&y>45&&y<85){
                    player.turnbgm();
                    if(player.isBGMOn()){
                        bgm.turnOn();
                    }else{
                        bgm.turnOff();
                    }
                    repaint();
                }
            }
        });
        
        addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(player.getGame()==1){
                    if((KeyEvent.getKeyText(e.getKeyCode())).equals("Left")){
                        if((player.getHeight()<=20)&&(player.getCol()>1)){
                            //bgm.moveSound();
                            player.addCol(-1);
                        }
                    }
                    if((KeyEvent.getKeyText(e.getKeyCode())).equals("Right")){
                        if((player.getHeight()<=20)&&(player.getCol()<3)){
                            //bgm.moveSound();
                            player.addCol(1);
                        }
                    }
                    if((KeyEvent.getKeyText(e.getKeyCode())).equals("Up")){
                        bgm.jumpSound();
                        if(player.getHeight()<=10){
                            Thread jT=new Thread(){
                                int t=1;
                                public void run(){
                                    double dHieght=0;
                                    do{
                                        dHieght=(50*t)-(9.8*t*t/2);
                                        int hieght=(int)dHieght;
                                        if(hieght<0){
                                            break;
                                        }
                                        player.setHieght(10+Math.round(hieght));
                                        t++;
                                        try{
                                            Thread.sleep(50);
                                        }catch(InterruptedException iE){
                                            System.exit(0);
                                        }
                                    }while(dHieght>0);
                                    player.setHieght(10);
                                }
                            };
                            jT.start();
                        }
                    }
                    if((KeyEvent.getKeyText(e.getKeyCode())).equals("Down")){
                        if(player.getHeight()<=10){
                            if(downsound){
                                bgm.moveSound();
                                downsound=false;
                            }
                            player.setHieght(0);
                        }
                    }
                    if((KeyEvent.getKeyText(e.getKeyCode())).equals("Shift")){
                        player.getObject().runObject(-1);
                    }
                }
                repaint();
            }
            
            public void keyReleased(KeyEvent e){
                if((KeyEvent.getKeyText(e.getKeyCode())).equals("Down")){
                    if(player.getHeight()==0){
                        downsound=true;
                        player.setHieght(10);
                    }
                }
            }
        });   
    }
    
    private void gameStart(){
        player.setGame(1);
        Thread frameThread=new Thread(){
            public void run(){
                while(player.getGame()==1){
                    int sleep=player.getSleep();
                    try{
                        Thread.sleep(sleep/6);
                    }catch(InterruptedException iE){
                        System.exit(0);
                    }
                    repaint();
                }
            }
        };
        start.start();
        frameThread.start();
    }

    private class ruleFrame extends JFrame{
        private JLabel label;
        public ruleFrame(String str){
            super(str);
            setSize(787,556);
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            setLocation(230,130);
            setIconImage(frameIcon);
            label=new JLabel(new ImageIcon("./Image/Rules1.png"));
            add(label);                           
            setResizable(false);
            addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    int X=e.getX();
                    int Y=e.getY();
                    if((page<4)&&(X>693)&&(X<750)&&(Y>271)&&(Y<370)){
                        page++;
                    }else if((page>1)&&(X>=39)&&(X<96)&&(Y>268)&&(Y<340)){
                        page--;
                    }
                    if(page==1){
                        label.setIcon(new ImageIcon("./Image/Rules1.png"));
                    }else if(page==2){
                        label.setIcon(new ImageIcon("./Image/Rules2.png"));
                    }else if(page==3){
                        label.setIcon(new ImageIcon("./Image/Rules3.png"));
                    }if(page==4){
                        label.setIcon(new ImageIcon("./Image/Rules4.png"));
                    }
                }
            });
        }

        private void reset(){
            label.setIcon(new ImageIcon("./Image/Rules1.png"));
            page=1;
            rule.setVisible(true);
        }
    }
        
    private class mouseHandler implements MouseMotionListener{
        @Override
        public void mouseDragged(MouseEvent e){
        }
        @Override
        public void mouseMoved(MouseEvent e){
            if(player.getGame()==0){
                int X=e.getX();
                int Y=e.getY();
                if((X<1178&&X>948)&&(Y>110&&Y<375)){
                    if(Y<185){
                        if(sound&&Y<195){
                            bgm.mouseSound();
                            sound=false;
                        }
                        player.setCol(1);
                    }else if(Y>205&&Y<280){
                        if(sound&&Y>225&&Y<260){
                            bgm.mouseSound();
                            sound=false;
                        }
                        player.setCol(2);
                    }else if(Y>290){
                        if(sound&&Y>310){
                            bgm.mouseSound();
                            sound=false;
                        }
                        player.setCol(3);
                    }else{
                        sound=true;
                        player.setCol(0);
                    }
                }else{
                    sound=true;
                    player.setCol(0);
                }
                repaint();
            }
        }
    }
}