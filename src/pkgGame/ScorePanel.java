
package pkgGame;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;


public class ScorePanel extends JPanel {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    public static final Font MENU_FONT = new Font("Arial",0,25);
    JFrame parentFrame;
    protected ArrayList <ScorePlayer> scorePlayerList = new ArrayList <>();
    JPanel scores;
    //public int [] px =  {50,100,150,200,250,300,350,400,450,500};
    protected JButton back = new JButton("Back",null);
    
    public ScorePanel(JFrame frame){
       parentFrame = (GameFrame) frame;
       //this.setLayout(null);
       this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
       this.setBackground(Color.black);
       this.setFocusable(true);
       this.setVisible(true);
       
       GridLayout grid = new GridLayout(12,2);
       scores = new JPanel(grid);
       scores.setBackground(new Color (0,0,0,0));
       scores.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
       
       backButton();
     
    }
    
    public JLabel label (String text){
        JLabel label1 = new JLabel(text,SwingConstants.CENTER);
        label1.setForeground(Color.RED);
        label1.setFont(new Font("Ink Free", 0,40));
        
        
        return label1;
        
    }
    
    public void readScore(){
        
        String path = "scoreList.txt";
        
        try{
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String [] sc;
            ScorePlayer aux;
          
            while((line = br.readLine())!= null){
             sc = line.split(",");
             aux = new ScorePlayer(sc[0], Integer.parseInt(sc[1]));  //Integer.parseInt(sc[1]));
             scorePlayerList.add(aux);
             Collections.sort(scorePlayerList, new Comparator<ScorePlayer>(){
                 @Override
                 public int compare(ScorePlayer score1, ScorePlayer score2){
                     return Integer.compare(score2.getScorePlayer(),score1.getScorePlayer());
                 }
             });
             System.out.println(scorePlayerList);   
            
        }
            
        }catch(IOException e){
            System.out.println("No se ha encontrado el archivo");
        }finally{
            scores.removeAll();
            scores.repaint();
            JLabel labelName = label("Name");
            JLabel labelScore = label("Score");
            scores.add(labelName);
            scores.add(labelScore);
            
            for(int i = 0; i<scorePlayerList.size();i++){
            ScorePlayer score1 = scorePlayerList.get(i);
            
            JLabel labelName1 = label(score1.getNamePlayer());
            JLabel labelScore1 = label(String.valueOf(score1.getScorePlayer())); //String.valueOf(score1.getScorePlayer()));
            scores.add(labelName1);
            scores.add(labelScore1);
            //System.out.println(100+px[i]);
            if(i>8){
                
                break;
            }
       }
           
            this.add(scores,BorderLayout.CENTER);
            scores.add(back);
                
     }
        
    }    
    /*
    public void drawScore(Graphics g){
        
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        //g.drawString("Score Player", (SCREEN_WIDTH - metrics.stringWidth("Score Player"))/2, g.getFont().getSize());
       
        for(int i = 0; i<scorePlayerList.size();i++){
            ScorePlayer score1 = scorePlayerList.get(i);
            g.drawString(score1.getNamePlayer() + score1.getScorePlayer(),(SCREEN_WIDTH - metrics.stringWidth(score1.getNamePlayer()))/2, 100+px[i]);
            System.out.println(100+px[i]);
            label(score1.getNamePlayer());
        }
     
    }
    
    */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //drawScore(g);
    }
    
    
    public void backButton(){
        back.setLayout(null);
        back.setBounds(100,300,50,50);
        back.setFont(MENU_FONT);
        back.addActionListener(new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e){
                backButtonFunction();
            }
            
        });
        
    }
    
    
    public void backButtonFunction(){
        LobbyPanel loby = new LobbyPanel(parentFrame);
        this.setVisible(false);
        parentFrame.add(loby);
        loby.revalidate();
        loby.repaint();
    }
    
    
    
    public class ScorePlayer{
        String namePlayer;
        int scorePlayer;
        
        public ScorePlayer(String namePlayer, int scorePlayer){
            this.namePlayer = namePlayer;
            this.scorePlayer = scorePlayer;
        }
        
        public String toString(){
            return "name: " + this.namePlayer + "score: " + this.scorePlayer;
        }
        
        public String getNamePlayer(){
            return this.namePlayer;
        }
        public int getScorePlayer(){
            return this.scorePlayer;
        }
    }
    /*
        public int bestScore(ScorePlayer bsc){
            int valor = Integer.parseInt(bsc.getScorePlayer());
            return valor;
        }
*/
}
