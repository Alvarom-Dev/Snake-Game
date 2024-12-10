
package pkgGame;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
    LobbyPanel lobbyPanel = new LobbyPanel(this);
    GamePanel gamePanel = new GamePanel(this);
    
    GameFrame(){
        System.out.println("Se ejecuta el construtor del Frame");
        this.add(lobbyPanel);
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
       
    }
   
    
    public void switchToGamePanel(){
        
        lobbyPanel.setVisible(false);
        gamePanel.setVisible(true);
        gamePanel.requestFocus();
       
        
        
                
    }
    
    public void switchToLobbyPanel(){
        gamePanel.setVisible(false);
        this.add(lobbyPanel);
        lobbyPanel.setVisible(true);
        lobbyPanel.requestFocus();
        this.revalidate();
        this.repaint();
         
    }
    
    public void exitGameFrame(){
        dispose();
    }
    
   
}
