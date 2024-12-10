
package pkgGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class LobbyPanel extends JPanel{
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    public static final Font MENU_FONT = new Font("Arial",0,25);
    protected static JButton btnPlay = new JButton("Jugar");
    protected static JButton btnScorePlayer = new JButton("Puntuaciones");
    protected static JButton btnExit = new JButton("Salir");
    GameFrame parentFrame;
   // GamePanel gamePanel1;
    
    
    LobbyPanel(JFrame frame){
        parentFrame = (GameFrame) frame;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setFocusable(true);
        this.setVisible(true);
        lobby();
        System.out.println("Se ejecuta el construtor de LobbyPanel");
    }
           
    public void lobby(){
        this.add(btnPlay);
        this.add(btnScorePlayer);
        this.add(btnExit);
        play();
        botons();
        scorePanelButton();
        exit();
              
    }
    
    
    /*
    public void title(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink free",Font.BOLD,30));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Snake Game" ,(SCREEN_WIDTH - metrics.stringWidth("Snake Game"))/2,80);
        
    }
    */
    public void botons(){
        //Boton jugar
        btnPlay.setBounds(205,490,200,50);
        btnPlay.setFont(MENU_FONT);
        //Boton Puntuaciones
        btnScorePlayer.setBounds(60,40,200,50);
        btnScorePlayer.setFont(MENU_FONT);
        //Boton salir
        btnExit.setBounds(350,40,200,50);
        btnExit.setFont(MENU_FONT);
    }
    
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        ImageIcon fondo = new ImageIcon(getClass().getResource("/image/SnakeGame.png"));
        g.drawImage(fondo.getImage(),0,0,getWidth(),getHeight(),this);   
        
    }
    
    public void play(){
        
        btnPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
             play1();
             
            }
         
        });
                   
    }
    
          
    public void exit(){
        btnExit.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
     });
    }
    /*
    public void backToLobbyPanel(){
        //parentFrame.remove(this);
        parentFrame.switchToLobbyPanel();
        
    }
    */
    public void play1(){
         GamePanel gamePanel1 = new GamePanel(parentFrame);
        
                  this.removeAll();
                  this.setVisible(false);
                  parentFrame.add(gamePanel1);
                  gamePanel1.revalidate();
                  gamePanel1.repaint();
                  gamePanel1.requestFocus();
                  
                  //parentFrame.switchToGamePanel();
                  System.out.println("Pasa por aquí");
                 
                 
                  
                  //backToLobbyPanel();
    }
 
    public void scorePanelButton(){
        btnScorePlayer.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                 scorePanel();
            }
        });
      
    }
    //Función para ver el JPanel de las puntuaciones
     public void scorePanel(){
           ScorePanel score_panel = new ScorePanel(parentFrame);
                 this.setVisible(false);
                 parentFrame.add(score_panel);
                 score_panel.readScore();
                 score_panel.revalidate();
                 score_panel.repaint();
       }
}
