/*
* HTX Eksamen i Programering B
* Lavet af: Victor Kahl Petersen, Victor Bøgesvang Pedersen & Kristian Ritter Rasmussen
*/
package eksamenhtxbankz;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.JFrame;



public class Main {

    
    public static void main(String[] args) {
        //Start of GUI, creates an instance from MainWindow class which inherits from JFrame
        JFrame gui = new MainWindow();
        
        //Gets the screen size, soloution from: https://www.codegrepper.com/code-examples/java/how+to+get+screen+width+and+hiehgt+java
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        double screenWidthData = screenSize.width / 2.5;
        int screenWidth = (int)screenWidthData;
        
        int screenHeight = screenSize.height - (screenSize.height / 10);
        
        gui.setSize(screenWidth, screenHeight);
        
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
       
        gui.setVisible(true);
    }
    
}
