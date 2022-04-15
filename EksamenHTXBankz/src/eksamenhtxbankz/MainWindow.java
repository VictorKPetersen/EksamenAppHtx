/*
* HTX Eksamen i Programering B
* Lavet af: Victor Kahl Petersen, Victor Bøgesvang Pedersen & Kristian Ritter Rasmussen
*/
package eksamenhtxbankz;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;




public class MainWindow extends JFrame{
    JPanel homePanel;
    JPanel kontoPanel;
    JPanel udgiftPanel;
    JPanel indkomstPanel;
    JPanel opsparPanel;
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    private JButton homeKontoBtn;
    private JButton homeUdgiftBtn;
    private JButton homeIndkomstBtn;
    private JButton homeOpsparBtn;
    
    private JButton backBtnHome;
    
    
    public MainWindow() {
        CreateComponentsHome();
        CreateComponentsKonto();
        CreateComponentsUdgift();
        CreateComponentsIndkomst();
        CreateComponentsOpspar();
        
    }
    
    private void CreateComponentsHome(){
        homePanel = new JPanel();
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
        //homePanel.setBorder(BorderFactory.createEmptyBorder(screenSize.height/5, screenSize.width/10, 0, 0));
        homePanel.setAlignmentX(CENTER_ALIGNMENT);
        
        homeKontoBtn = new JButton("Konto"); homeKontoBtn.setAlignmentX(CENTER_ALIGNMENT);
        homeUdgiftBtn = new JButton("Udgifter"); homeUdgiftBtn.setAlignmentX(CENTER_ALIGNMENT);
        homeIndkomstBtn = new JButton("Indkomst"); homeIndkomstBtn.setAlignmentX(CENTER_ALIGNMENT);
        homeOpsparBtn = new JButton("Hjælp til opspar"); homeOpsparBtn.setAlignmentX(CENTER_ALIGNMENT);
        
        
        
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
        
        backBtnHome = new JButton("Back");
        
        backBtnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels(kontoPanel, homePanel);
            }
        });
        
        
        kontoPanel.add(backBtnHome);
        
    }
    
    private void CreateComponentsUdgift(){
        udgiftPanel = new JPanel();
        udgiftPanel.setLayout(new BoxLayout(udgiftPanel, BoxLayout.Y_AXIS)); 
        
        backBtnHome = new JButton("Back");
        
        backBtnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels(udgiftPanel, homePanel);
            }
        });
        
        
        ExpensesChart expenseChart = new ExpensesChart(600,600); // build chart
        //expenseChart.addExpense("mad", 20); // tilføj udgift, værdi insættes som kr brugt
        expenseChart.addExpense("el", 20); // tilføj udgift
        
        JLabel udgiftTemp = expenseChart.addExpense("mad", 20); udgiftTemp.setAlignmentX(CENTER_ALIGNMENT);
        
        //JLabel[] udgiftsListe = new JLabel[database.length];
        
        //brug for loop til addexpense fra database
        /*for(int i = 0; i <= database.length; i++){
            JLabel udgiftTemp = expenseChart.addExpense(database[i].name, database[i].value);
            udgiftPanel.add(expensePanel);
            
            udgiftsListe[i] = udgiftTemp;
        }*/
        XChartPanel<PieChart> expensePanel = expenseChart.getPanel();
        
        udgiftPanel.add(backBtnHome);
        udgiftPanel.add(expensePanel);
        /*
        for(int i = 0; i <= udgiftsListe.length; i++){
            udgiftPanel.add(udgiftsListe[i]);
        }
        
        */
        
        udgiftPanel.add(udgiftTemp);
        
        //new SwingWrapper(pie_chart).displayChart();
    }
    
    private void CreateComponentsIndkomst(){
        indkomstPanel = new JPanel();
        
        backBtnHome = new JButton("Back");
        
        backBtnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels(indkomstPanel, homePanel);
            }
        });
        
        
        indkomstPanel.add(backBtnHome);
        
    }
    
    private void CreateComponentsOpspar(){
        opsparPanel = new JPanel();
        
        backBtnHome = new JButton("Back");
        
        backBtnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels(opsparPanel, homePanel);
            }
        });
        
        
        opsparPanel.add(backBtnHome);
    }
    
    public void switchPanels(JPanel currentPanel, JPanel newPanel){
        
        remove(currentPanel);
        add(newPanel);
        revalidate();
        repaint();
        
        
    }
}
