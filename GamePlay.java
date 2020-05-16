
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * @author Ruchi Vora
 */
public class GamePlay extends JPanel implements KeyListener , ActionListener{
    
    private boolean play = false ;
    private int score    = 0 ;
    
    /*
        Total bricks is counted so that if total_brick = 0
        that means "You Won ! " .
    */
    private int total_bricks = 21 ;
    
    /*
        Timer class is needed that decides how fast the ball should move .
    */
    private Timer timer ;
    private int delay = 8 ;
   
    /*
        States starting position of the Slider .
    */
    private int playerX = 310 ;
    
    /*
        States starting position of the Ball .
    */
    private int ballposX = 120 ;
    private int ballposY = 350 ;
    private int ballXdir = -1 ;
    private int ballYdir = -2 ;
    
    private MapGenerator map ; 
    
    GamePlay()
    {
        map = new MapGenerator(3,7) ;
        addKeyListener(this) ;
        setFocusable(true) ;
        //setFocusTraversalKeysEnabled(false) ;
        timer = new Timer( delay , this ) ;
        System.out.println(this);
        timer.start() ;
    }
    
    public void paint(Graphics g)
    {
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        
        /*
            drawing map 
        */
        map.draw((Graphics2D)g);
        
        /*
            Setting border
        */
        g.setColor(Color.yellow) ;
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);
        /*
            setting score board
        */
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25) ) ;
        g.drawString(""+score, 590, 30);
        /*
            The paddle
        */
        g.setColor(Color.green) ;
        g.fillRect(playerX , 550,100 ,8 ) ;
        
        /*
            The Ball
        */
        g.setColor(Color.yellow) ;
        g.fillOval( ballposX , ballposY , 20 , 20 ) ;
        
        /*
            Game Over
        */
        if( ballposY > 570 )
        {
            play = false ;
            ballXdir = 0 ;
            ballYdir = 0 ;
            g.setColor(Color.RED) ;
            g.setFont(new Font("serif",Font.BOLD,30)) ;
            g.drawString("Game Over , Scores : "+score,190,300) ;
            
            g.setFont(new Font("serif",Font.BOLD,30)) ;
            g.drawString("Press Enter To Restart",190,350) ;
        }
        
        /*
            If the game is won .
        */
        if( total_bricks ==  0 )
        {
            play = false ;
            ballXdir = 0 ;
            ballYdir = 0 ;
            g.setColor(Color.BLUE);
            g.setFont(new Font("serif",Font.BOLD,30)) ;
            g.drawString("You Won , Scores : "+score,190,300) ;
       
            g.setFont(new Font("serif",Font.BOLD,30)) ;
            g.drawString("Press Enter To Restart",190,350) ;
        }
        
        g.dispose() ;
    }

   
    @Override
    public void keyPressed(KeyEvent e) {
    
         if( e.getKeyCode() == KeyEvent.VK_RIGHT )
        {
            if(playerX >= 600 ){
                playerX = 600 ;
            }
            else
                moveRight() ;
        }
        
        if( e.getKeyCode() == KeyEvent.VK_LEFT )
        {
            if( playerX < 10 )
            {
                playerX = 10 ;
            }else
                moveLeft() ;
        }
        if( e.getKeyCode() == KeyEvent.VK_ENTER )
        {
           if(!play) 
           {
               play         = true ;
               ballposX     = 120 ;
               ballposY     = 350 ;
               ballXdir     = -1  ;
               ballYdir     = -2  ;
               playerX      = 310 ;
               score        = 0 ;
               total_bricks = 21 ;
               /*
                    Needs to set this parameter again 
                    because otherwise the game will resume 
                    with same number of bricks .
               */
               map = new MapGenerator(3,7) ; 
               repaint() ;
               
           }
        }
    }
    
    public void moveRight(){
        play = true ;
        playerX +=20 ;
    }
    public void moveLeft(){
        play =true ;
        playerX -=20 ;
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
       timer.start() ;
       if( play == true ) 
       {
           if( new Rectangle(ballposX , ballposY ,20 , 20).intersects( new Rectangle(playerX , 550 , 100 , 8) ) )
           {
                ballYdir = -ballYdir ;
           } 
           
           A : for( int i = 0 ; i < map.map.length ; i++ )
           {
               for( int j = 0 ; j < map.map[0].length ; j++ )
               {
                   if( map.map[i][j] > 0 )
                   {
                       int brickX = j * map.brickWidth + 80  ; 
                       int brickY = i * map.brickHeight + 50 ;
                       int brickWidth  = map.brickWidth  ;
                       int brickHeight = map.brickHeight ;
                       
                       Rectangle rect = new Rectangle(brickX ,brickY,brickWidth,brickHeight) ; ;
                       Rectangle ballRect = new Rectangle(ballposX , ballposY , 20, 20 ) ;
                       Rectangle brickRect = rect ;
                       
                       if( ballRect.intersects(brickRect) )
                       {
                           map.setBrickValue(0 ,i ,j) ; 
                           total_bricks -- ;
                           score += 5 ;
                           
                           if( ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width)
                           {
                               ballXdir = -ballXdir ;
                           }
                           else
                           {
                               ballYdir = -ballYdir ;
                           }
                           break A ;
                       }
                   }
               }
           }
           
           
            ballposX += ballXdir ;
            ballposY += ballYdir ;
            
            if( ballposX < 0 )
            {
                ballXdir = -ballXdir ; 
            }
            if( ballposY < 0 )
            {
                ballYdir = -ballYdir ;
            }
            if( ballposX > 670 )
            {
                ballXdir = -ballXdir ;
            } 
       }
       repaint() ;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
        

    @Override
    public void keyReleased(KeyEvent e) {}
    

    
    
    
}
