/**
* HTX Eksamen i Programering B
* @author Victor Kahl Petersen, Victor Bøgesvang Pedersen & Kristian Ritter Rasmussen
*/
package eksamenhtxbankz;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Locale;
import javax.swing.JFrame;


/**
 * 
 * Sætter skærmens størrelse, og laver JFramet udfra MainWindow klassen
 */
public class Main {
    public static int frameWidth;
    public static int frameHeight;
    
    public static void main(String[] args) {
        //Gets the screen size using java.awt.Toolkit
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        double screenWidthData = screenSize.width / 2.5;
        frameWidth = (int)screenWidthData;
        
        frameHeight = screenSize.height - (screenSize.height / 10);
        
        //Start of GUI, creates an instance from MainWindow class which inherits from JFrame
        JFrame gui = new MainWindow(Locale.GERMAN);
        
        gui.setSize(frameWidth, frameHeight);
        
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        gui.setVisible(true);
    }
    
}
