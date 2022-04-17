/*
* HTX Eksamen i Programering B
* Lavet af: Victor Kahl Petersen, Victor Bøgesvang Pedersen & Kristian Ritter Rasmussen
*/
package eksamenhtxbankz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;




public class MainWindow extends JFrame{
    //Panel Class Variables
    private JPanel homePanel;
    private JPanel kontoPanel;
    private JPanel udgiftPanel;
    private JPanel indkomstPanel;
    private JPanel opsparPanel;
    
    //screenSize using java.awt.Toolkit
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    //Buttons for navigating panels
    private JButton homeKontoBtn;
    private JButton homeUdgiftBtn;
    private JButton homeIndkomstBtn;
    private JButton homeOpsparBtn;
    
    //Universal return/back button
    private JButton backHomeBtn;
    
    //Indkomst Panel
    private JLabel totalIncomeTxt;
    private JLabel totalIncome;
 

    
    private JTextField hoursAmountTxtField;
    private JTextField hourlyRateTxtField;
    
    public databaseConnection bankDB;
    
    public MainWindow() {
        bankDB = new databaseConnection();
        
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
        
        backHomeBtn = new JButton("Tilbage");
        
        backHomeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels(kontoPanel, homePanel);
            }
        });
        
        
        kontoPanel.add(backHomeBtn);
        
    }
    
    private void CreateComponentsUdgift(){
        udgiftPanel = new JPanel();
        udgiftPanel.setLayout(new BoxLayout(udgiftPanel, BoxLayout.Y_AXIS)); 
        
        backHomeBtn = new JButton("Tilbage");
        
        backHomeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels(udgiftPanel, homePanel);
            }
        });
        
        ExpensesChart expenseChart = bankDB.getUdgifterListe( new ExpensesChart(600,600));
        //ExpensesChart expenseChart = new ExpensesChart(600,600); // build chart
        //expenseChart.addExpense("mad", 20); // tilføj udgift, værdi insættes som kr brugt
        //expenseChart.addExpense("el", 20); // tilføj udgift
        
        //JLabel udgiftTemp = expenseChart.addExpense("mad", 20); udgiftTemp.setAlignmentX(CENTER_ALIGNMENT);
        ResultSet rs = bankDB.getUdgifter();
        JLabel[] udgiftsListe = new JLabel[bankDB.getCountOfCollumns()];
        
        //brug for loop til addexpense fra database
        
        ResultSetMetaData rsmd = bankDB.getMetaRS(rs);
        JLabel udgiftTemp;
        /*for(int i = 0; i <= bankDB.getCountOfCollumns(); i++){
            System.out.println("i: "+bankDB.getCountOfCollumns()+" name: ");
            
            try {
                udgiftTemp = expenseChart.addExpense(rsmd.getColumnName(i), rs.getFloat(i));
                udgiftsListe[i] = udgiftTemp;
            } catch (SQLException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
        }*/
        
        
        
        XChartPanel<PieChart> expensePanel = expenseChart.getPanel();
        
        udgiftPanel.add(backHomeBtn);
        udgiftPanel.add(expensePanel);
        /*
        for(int i = 0; i <= udgiftsListe.length; i++){
            udgiftPanel.add(udgiftsListe[i]);
        }
        */
        
        
        
        
        //new SwingWrapper(pie_chart).displayChart();
    }
    
    private void CreateComponentsIndkomst(){
        indkomstPanel = new JPanel();
        indkomstPanel.setLayout(new BoxLayout(indkomstPanel, BoxLayout.Y_AXIS));
        
        backHomeBtn = new JButton("Tilbage"); backHomeBtn.setAlignmentX(CENTER_ALIGNMENT);
        
        backHomeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels(indkomstPanel, homePanel);
            }
        });
        
        totalIncomeTxt = new JLabel("Forventet Indkomst: "); totalIncomeTxt.setAlignmentX(CENTER_ALIGNMENT);
        
        double doublePlaceHolder = 10000000.5923;
        totalIncome = new JLabel(String.format(Locale.GERMAN, "%,.2f", doublePlaceHolder)); totalIncome.setAlignmentX(CENTER_ALIGNMENT);
        
        
        JPanel incomeNestedPanel = new JPanel(); incomeNestedPanel.setLayout(new BoxLayout(incomeNestedPanel, BoxLayout.X_AXIS));
        incomeNestedPanel.setMaximumSize(new Dimension(Main.frameWidth / 3, Main.frameHeight / 8));

        hoursAmountTxtField = new JTextField("Antal Timer"); hoursAmountTxtField.setAlignmentY(CENTER_ALIGNMENT);
        hourlyRateTxtField = new JTextField("Time løn"); hourlyRateTxtField.setAlignmentY(CENTER_ALIGNMENT);

        
        incomeNestedPanel.add(hoursAmountTxtField);
        incomeNestedPanel.add(hourlyRateTxtField);
        
        indkomstPanel.add(backHomeBtn);
        indkomstPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        
        indkomstPanel.add(totalIncomeTxt);
        indkomstPanel.add(totalIncome);
        
        indkomstPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        indkomstPanel.add(CreateHorizontalSeperator(Color.BLACK));
        
        indkomstPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        indkomstPanel.add(incomeNestedPanel);
        indkomstPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        indkomstPanel.add(CreateHorizontalSeperator(Color.BLACK));
    }
    
    private void CreateComponentsOpspar(){
        opsparPanel = new JPanel();
        
        backHomeBtn = new JButton("Tilbage");
        
        backHomeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels(opsparPanel, homePanel);
            }
        });
        
        
        opsparPanel.add(backHomeBtn);
    }
    
    private JSeparator CreateHorizontalSeperator(Color color){
        JSeparator HorizontalSeperator = new JSeparator(SwingConstants.HORIZONTAL);
        HorizontalSeperator.setForeground(color); HorizontalSeperator.setBackground(color);
        return HorizontalSeperator;
    } 
    
    public void switchPanels(JPanel currentPanel, JPanel newPanel){
        
        remove(currentPanel);
        add(newPanel);
        revalidate();
        repaint();
        
        
    }
}
