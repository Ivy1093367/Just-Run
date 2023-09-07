public class Group14_Object{
    private Group14_Player player;
    public Group14_Object(Group14_Player player){
        this.player=player;
    }
    
    private void paper(){
        player.addPaper(1);    
    }
    private void magnet(){
        player.addCount(300-player.getSleep());
        Thread magnet=new Thread(){
            public void run(){
                while(player.getCount()>0){
                    try{
                        player.setMagnet(true);
                        Thread.sleep(player.getSleep());
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    player.addCount(-1);
                }
                player.setMagnet(false);
            }
        };
        magnet.start();
    }
    
    private void bandaid(){
        if(player.getLife()<3){
            player.setLife(player.getLife()+1);
        }
    }
    private void ladder(){
        if(!player.isLadderSet()){
            player.letLadderSet();
        }        
    }
    
    private void up(){
        if(player.getHeight()<100){
            if(player.isLadderSet()){
                player.useLadder();
            }else{
                player.setLife(player.getLife()-1);
            }
        }
    }
    private void down(){
        if(player.getHeight()>9){
            if(player.isLadderSet()){
                player.useLadder();
            }else{
                player.setLife(player.getLife()-1);
            }
        }
    }
    private void boom(){
        if(player.isLadderSet()){
            player.useLadder();
        }else{
            player.setLife(player.getLife()-1);
        }
    }
    
    public void runObject(int code){
        switch (code){
            case -1:
                ladder();                
                break;
            case 0:
                if(player.getLadderNum()<10){
                    player.addLadderNum(1);
                }
                break;
            case 1:
                paper();
                break;
            case 2:
                magnet();
                break;
            case 3:
                bandaid();
                break;
            case 5: case 6: 
                    up();
                break;
            case 9: case 10:
                    down();
                break;
            case 7: case 8: case 11:
                    boom();
                break;
        }
    }  
}