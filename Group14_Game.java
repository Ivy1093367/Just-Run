public class Group14_Game{
    public static void main(String[] args){
        Group14_Player player=new Group14_Player(3,250);
        Group14_BGM bgm=new Group14_BGM();
        Group14_GameFrame gF=new Group14_GameFrame(player,bgm);
        gF.setVisible(true);

    }
}