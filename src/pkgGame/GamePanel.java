
package pkgGame;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.*;
import java.util.Random;

//Para manejar eventos de acción en un componente, una clase debe implementar la interfaz ActionListener y 
//sobrescribir el método actionPerformed(ActionEvent e).
public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    boolean keyInput = false;
    String namePlayer;
    private ArrayList<Score> scoreList = new ArrayList<Score>();
    //Botonoes para reiniciar el juego o escribir el puntaje
    JButton saveScore = new JButton("Save Score");
    JButton playAgain = new JButton("Play Again");
    JButton backToLobby = new JButton("Back to lobby");
    GameFrame parentFrame;
    
    
    
    GamePanel(JFrame frame){
        parentFrame = (GameFrame) frame;
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.setVisible(true);
        
        startGame();
        
        System.out.println("Se ejecuta el construtor de GamePanel");
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        ScoreButton();
        playAgainButton();
        backLobbyButton();
    }
    //Este método es un método Override del metodo de la clase padre. Es el encargado de que se dibuje el juego. 
    public void paintComponent(Graphics g){
        super.paintComponent(g);//Llama al método de la superclase para asegurarse de que el componente se dibuje correctamente
        draw(g);//llamamos al método dibujar, que lo que hace es dibujar la serpiente y la manzana
    }
    
    public void draw(Graphics g){
        
        if (running) {
            //Este bucle for es para dibujar la cuadricula y ver mejor la separacion de píxeles de nuestro panel
            /*
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            */
            //Este código es para dibujar la manzana que comera la serpiente
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //Este código es para dibujar a la serpiente
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            //Este código se dibuja la puntuación.
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
        }else{
            gameOver(g);
        }
    }
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    
    public void move(){
        for(int i = bodyParts; i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
        
        keyInput = false;
    }
    
    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    
    public void checkCollisions(){
        
        //Este código comprueba si la cabeza de la serpiente choca con su cuerpo, si choca GAME OVER.
        for(int i = bodyParts; i>0; i --){
            if((x[0] == x[i]) && (y[0] == y[i])){
            running = false;
         }
            
        }
        //Este código comprueba si la serpiente choca con los bordes
        
        //Si choca con el borde izquierdo
        if(x[0]<0){
            running = false;
        }
        //si choca con el borde derecho
        if(x[0]>SCREEN_WIDTH){
            running = false;
        }
        //Si choca con el borde de arriba
        if(y[0]<0){
            running = false;
        }
        //Si choca con el borde de abajo
        if(y[0]>SCREEN_HEIGHT){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }
    
    public void gameOver(Graphics g){
        //Puntación una vez se ha acabado el juego
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
        
        //Las letras de Game Over una vez se ha acabado el juebo
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
        
        botonsGameOver();
       
        //saveNamePlayerAndScore();
    }
    
    //El método actionPerformed(ActionEvent e) es parte de la interfaz ActionListener en Java. Esta interfaz es utilizada para recibir eventos de acción.
    //Cuando un componente puede generar componentes de accion, como un botón por ejemplo es activado, se llama a este método.
    @Override
    public void actionPerformed(ActionEvent e) {
       if(running){
           move();
           checkApple();
           checkCollisions();
       }
       repaint();
    }
    
    public void saveNamePlayerAndScore(){
        namePlayer = JOptionPane.showInputDialog(null,"Introduzca su nombre");
        
        if (namePlayer != null && !namePlayer.trim().isEmpty()  ){
           // Score player = new Score(namePlayer,applesEaten);
            sortAndSave();
            
            
            //JOptionPane.showConfirmDialog(null,"¡Puntuación guardad con éxito!");
        }else{
            JOptionPane.showConfirmDialog(null,"No se ha guardado la puntuación");
        }
    }
    
    
    
    
    public void sortAndSave(){
        try{
            File file = new File("scoreList.txt");
            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            Score element = new Score(namePlayer,applesEaten);
            bw.write(element.name + "," + String.valueOf(element.score) + "\n");
            //bw.newLine();
            //scoreList.sort(Comparator.reverseOrder());
            /*
            for(int i = 0; i < 10; i++){
                Score element = scoreList.get(i);
                bw.write(element.name + "," + String.valueOf(element.score) + "\n");
                bw.newLine();
            }
            */
            bw.flush();
            
        }catch(Exception e){
            System.out.println("Error de escritura de archivo");
        }
     
    }
    
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R' && keyInput == false){
                        direction = 'L';
                        keyInput = true;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L' && keyInput == false){
                        direction = 'R';
                        keyInput = true;
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D' && keyInput == false){
                        direction = 'U';
                        keyInput = true;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U' && keyInput == false){
                        direction = 'D';
                        keyInput = true;
                    }
                    break;
            }
        }
    }
    
    public class Score implements Comparable{
        
        private String name;
        private int score;
        
        public Score(String name, int score){
            this.name = name;
            this.score = score;
        }
        
        public String toString(){
            return "name: " + this.name + " score: " + this.score;
        }
        
        public String getName(){
            return name;
        }
        
        public int getScore(){
            return score;
        }
        @Override
        public int compareTo(Object o){
            Score b = (Score) o;
            return this.score - b.score;
        }

        
    }
    
    public void botonsGameOver(){
        this.add(saveScore);
        this.add(playAgain);
        this.add(backToLobby);
        styleBotonsGameOver();
        
    }
    
    public void styleBotonsGameOver(){
        saveScore.setBounds(80,400,200,50);
        saveScore.setFont(new Font("Arial",0,25));
        
        playAgain.setBounds(350,400,200,50);
        playAgain.setFont(new Font("Arial",0,25));
        
        backToLobby.setBounds(218,500,200,50);
        backToLobby.setFont(new Font("Arial",0,25));
    }
    
    public void playAgainButton(){
        playAgain.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
          
         gamePanelAgain();
        }
     });
    }
    
    public void ScoreButton(){
        saveScore.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e){
               saveNamePlayerAndScore();
               
           }
        });
    }
    
    public void backLobbyButton(){
        backToLobby.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e){
              backToLobby();
              parentFrame.revalidate();
              parentFrame.repaint();
           }
        });
    }
    
    //Función para que aparezca el panel del juego de nuevo
    public void gamePanelAgain(){
         GamePanel gamePanel = new GamePanel(parentFrame);
         parentFrame.remove(this);
         parentFrame.add(gamePanel);
         gamePanel.requestFocus();
        
    }
    
    public void backToLobby(){
      LobbyPanel lobbyPanel2 = new LobbyPanel(parentFrame);
        
         this.removeAll();
         this.setVisible(false);
         parentFrame.add(lobbyPanel2);
         lobbyPanel2.revalidate();
         lobbyPanel2.repaint();
         
        //parentFrame.switchToLobbyPanel();
        //parentFrame.add(lobbyPanel2);
        
        //parentFrame.switchToLobbyPanel();
       
 
    }
    
}
