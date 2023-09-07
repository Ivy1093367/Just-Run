public class Group14_Player{
    private int game=0;
    private int runLength;
    private int ladder=0;
    private int magnetCount=0;
    private boolean ladderFlag;
    private boolean magnetFlag;
    private boolean bgmFlag;
    private int sleep=0;
    private int life;
    private int paper=0;
    private int col=1;
    private int hight=10;
    private int position;
    private Group14_Object playerObject;
    public Group14_Player(int ladder ,int sleep){
        playerObject=new Group14_Object(this);
        this.ladder=ladder;
        this.sleep=sleep;
        this.life=3;
        this.ladderFlag=false;
        this.magnetFlag=false;
        this.bgmFlag=true;
        this.runLength=0;
    }
    
    public boolean isMagnetGet(){
        return magnetFlag;
    }
    public void addCount(int c){
        this.magnetCount+=c;
    }
    public int getCount(){
        return this.magnetCount;
    }

    public void setMagnet(boolean mag){
        magnetFlag=mag;
    }
    public boolean isLadderSet(){
        return ladderFlag;
    }
    public void useLadder(){
        if(ladderFlag){
            ladderFlag=false;
        }
    }
    public void letLadderSet(){
        if(!ladderFlag&&ladder>0){
            ladder--;
            ladderFlag=true;
        }
    }
    public boolean isBGMOn(){
        return bgmFlag;
    }
    public void turnbgm(){
        bgmFlag=!bgmFlag;
    }

    public int getCol(){
        return col;
    }
    public void addCol(int c){
        this.col+=c;
    }
    public void setCol(int c){
        this.col=c;
    }
    public int getHeight(){
        return hight;
    }
    public void setHieght(int h){
        this.hight=h;
    }
    public int getLadderNum(){
        return this.ladder;
    }
    public void addLadderNum(int l){
        this.ladder+=l;
    }
    public int getSleep(){
        return this.sleep;         
    }
    public void setSleep(int a){
        this.sleep=a;
    }
    public void setLife(int l){
        this.life=l;
    }
    public int getLife(){
        return this.life;
    }
    public int getLength(){
        return this.runLength;
    }
    public void addLength(int r){
        this.runLength+=r;
    }
    public int getPaper(){
        return this.paper;
    }
    public void addPaper(int m){
        this.paper+=m;
    }
    public Group14_Object getObject(){
        return this.playerObject;
    }
    public void setObject(Group14_Object o){
        this.playerObject=o;
    }
    public int getPosition(){
        return this.position;
    }
    public void setPosition(int p){
        this.position=p;
    }
    public int getGame(){
        return this.game;
    }
    public void setGame(int g){
        this.game=g;
    }
}