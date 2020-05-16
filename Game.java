
import javax.swing.JFrame;

/**
 *
 * @author Ruchi Vora
 */
public class Game {
  
    public static void main(String[] args)
    {
        JFrame frame = new JFrame() ;
        GamePlay gameplay = new GamePlay() ;
        frame.setBounds(10,10,700,600);
        frame.setResizable(false);
        frame.setTitle("Brick Game") ;
        frame.setVisible(true) ;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        frame.setLocationRelativeTo(null);
        frame.add(gameplay) ;
    }
}
