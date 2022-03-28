/*
* HTX Eksamen i Programering B
* Lavet af: Victor Kahl Petersen, Victor Bøgesvang Pedersen & Kristian Ritter Rasmussen
*/
package eksamenhtxbankz;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;



public class Main {


    public static void main(String[] args) {
        JFrame gui = new MainWindow();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        
        gui.setSize(screenWidth, screenHeight);
        
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        gui.setVisible(true);
    }
    
}
