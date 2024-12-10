
package pkgGame;

import javax.swing.JFrame;

public class LobbyFrame extends JFrame {
    
    LobbyFrame(){
       // this.add(new LobbyPanel());
        this.setTitle("Lobby Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
       
    }
    
     
     
}
