/*
* HTX Eksamen i Programering B
* Lavet af: Victor Kahl Petersen, Victor Bøgesvang Pedersen & Kristian Ritter Rasmussen
*/
package eksamenhtxbankz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainWindow extends JFrame{
    JPanel homePanel;
    JPanel kontoPanel;
    JPanel udgiftPanel;
    JPanel indkomstPanel;
    JPanel opsparPanel;
    
    
    
    private JButton homeKontoBtn;
    private JButton homeUdgiftBtn;
    private JButton homeIndkomstBtn;
    private JButton homeOpsparBtn;
    
    public MainWindow() {
        CreateComponentsHome();
        CreateComponentsKonto();
        CreateComponentsUdgift();
        CreateComponentsIndkomst();
        CreateComponentsOpspar();
        
    }
    
    private void CreateComponentsHome(){
        homePanel = new JPanel();
        
        
        
        homeKontoBtn = new JButton("Konto");
        homeUdgiftBtn = new JButton("Udgifter");
        homeIndkomstBtn = new JButton("Indkomst");
        homeOpsparBtn = new JButton("Hjælp til opspar");
        
       
        
        homeKontoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels(homePanel, kontoPanel);
            }
        });
        
        homeUdgiftBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels(homePanel, udgiftPanel);
            }
        });
        
        homeIndkomstBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels(homePanel, indkomstPanel);
            }
        });
        
        homeOpsparBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels(homePanel, opsparPanel);
            }
        });
        
        homePanel.add(homeKontoBtn);
        homePanel.add(homeUdgiftBtn);
        homePanel.add(homeIndkomstBtn);
        homePanel.add(homeOpsparBtn);
        
        
        
        add(homePanel);
        
        
    }
    
    private void CreateComponentsKonto(){
        kontoPanel = new JPanel();
        
    }
    
    private void CreateComponentsUdgift(){
        udgiftPanel = new JPanel();
        
    }
    
    private void CreateComponentsIndkomst(){
        indkomstPanel = new JPanel();
        
    }
    
    private void CreateComponentsOpspar(){
        opsparPanel = new JPanel();
        
    }
    
    public void switchPanels(JPanel currentPanel, JPanel newPanel){
        
        remove(currentPanel);
        add(newPanel);
        revalidate();
        repaint();
        
        
    }
}
