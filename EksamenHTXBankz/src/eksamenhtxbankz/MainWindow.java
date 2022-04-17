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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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
     
    //Buttons for navigating panels
    private JButton homeKontoBtn;
    private JButton homeUdgiftBtn;
    private JButton homeIndkomstBtn;
    private JButton homeOpsparBtn;
    
    //Universal return/back button
    private JButton backHomeBtn;
    
    //Indkomst Panel
    private JLabel totalIncomeTxtLabel;
    private JLabel totalIncomeLabel;
 
    private JTextField hoursAmountTxtField;
    private JTextField hourlyRateTxtField;
    
    private JLabel salaryTaxedLabel;
    private JLabel salaryNonTaxedLabel;
    
    private JTextField stateHelpTxtField;
    private JTextField otherIncomeTxtField;
    
    private JButton addOtherIncomeBtn;
    
    private Locale activeLocale;
    
    
    public MainWindow(Locale choosenLocale) {
        CreateComponentsHome();
        CreateComponentsKonto();
        CreateComponentsUdgift();
        CreateComponentsIndkomst();
        CreateComponentsOpspar();
           
        activeLocale = choosenLocale;
    }
    
    private void CreateComponentsHome(){
        this.setTitle("Din Økonomiske Hjælper");
        homePanel = new JPanel();
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
        homePanel.setAlignmentX(CENTER_ALIGNMENT);
        
        homeKontoBtn = new JButton("Konto"); homeKontoBtn.setAlignmentX(CENTER_ALIGNMENT);
        homeUdgiftBtn = new JButton("Udgifter"); homeUdgiftBtn.setAlignmentX(CENTER_ALIGNMENT);
        homeIndkomstBtn = new JButton("Indkomst"); homeIndkomstBtn.setAlignmentX(CENTER_ALIGNMENT);
        homeOpsparBtn = new JButton("Hjælp til opspar"); homeOpsparBtn.setAlignmentX(CENTER_ALIGNMENT);
        
        
        
        homeKontoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels(homePanel, kontoPanel, "Din Økonomiske Hjælper - Konto");
            }
        });
        
        homeUdgiftBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels(homePanel, udgiftPanel, "Din Økonomiske Hjælper - Udgifter");
            }
        });
        
        homeIndkomstBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels(homePanel, indkomstPanel, "Din Økonomiske Hjælper - Indkomst");
            }
        });
        
        homeOpsparBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels(homePanel, opsparPanel, "Din Økonomiske Hjælper - Hjælp til Opspar");
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
                switchPanels(kontoPanel, homePanel, "Din Økonomiske Hjælper");
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
                switchPanels(udgiftPanel, homePanel, "Din Økonomiske Hjælper");
            }
        });
        
        
        ExpensesChart expenseChart = new ExpensesChart(600,600); // build chart
        //expenseChart.addExpense("mad", 20); // tilføj udgift, værdi insættes som kr brugt
        //expenseChart.addExpense("el", 20); // tilføj udgift
        
        //JLabel udgiftTemp = expenseChart.addExpense("mad", 20); udgiftTemp.setAlignmentX(CENTER_ALIGNMENT);
        ResultSet rs = bankDB.getUdgifter();
        JLabel[] udgiftsListe = new JLabel[bankDB.getCountOfCollumns()];
        
        //brug for loop til addexpense fra database
        
        ResultSetMetaData rsmd = bankDB.getMetaRS(rs);
        JLabel udgiftTemp;
        for(int i = 0; i <= bankDB.getCountOfCollumns(); i++){
            System.out.println("i: "+bankDB.getCountOfCollumns()+" name: ");
            
            try {
                udgiftTemp = expenseChart.addExpense(rsmd.getColumnName(i), rs.getFloat(i));
                udgiftsListe[i] = udgiftTemp;
            } catch (SQLException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
        }
        
        
        
        XChartPanel<PieChart> expensePanel = expenseChart.getPanel();
        
        udgiftPanel.add(backHomeBtn);
        udgiftPanel.add(expensePanel);
        
        for(int i = 0; i <= udgiftsListe.length; i++){
            udgiftPanel.add(udgiftsListe[i]);
        }
        
        
        
        
        
        //new SwingWrapper(pie_chart).displayChart();
    }
    
    private void CreateComponentsIndkomst(){
        indkomstPanel = new JPanel();
        indkomstPanel.setLayout(new BoxLayout(indkomstPanel, BoxLayout.Y_AXIS));
        
        backHomeBtn = new JButton("Tilbage"); backHomeBtn.setAlignmentX(CENTER_ALIGNMENT);
        
        backHomeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels(indkomstPanel, homePanel, "Din Økonomiske Hjælper");
            }
        });
        
        totalIncomeTxtLabel = new JLabel("Forventet Indkomst: "); totalIncomeTxtLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        double doubleTotalPlaceHolder = 10000000.5923;
        totalIncomeLabel = new JLabel(String.format(activeLocale, "%,.2f", doubleTotalPlaceHolder)); totalIncomeLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        
        JPanel incomeNestedPanel = new JPanel(); incomeNestedPanel.setLayout(new BoxLayout(incomeNestedPanel, BoxLayout.X_AXIS));
        incomeNestedPanel.setMaximumSize(new Dimension(Main.frameWidth / 3, Main.frameHeight / 8));

        hoursAmountTxtField = new JTextField("Antal Timer"); hoursAmountTxtField.setAlignmentY(CENTER_ALIGNMENT);
        
        hoursAmountTxtField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                hoursAmountTxtField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (hoursAmountTxtField.getText().replaceAll("\\s", "").equals("")) {
                    hoursAmountTxtField.setText("Antal Timer");
                }
            }
        });
        
        hourlyRateTxtField = new JTextField("Time løn"); hourlyRateTxtField.setAlignmentY(CENTER_ALIGNMENT);
        
        hourlyRateTxtField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                hourlyRateTxtField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (hourlyRateTxtField.getText().replaceAll("\\s", "").equals("")) {
                    hourlyRateTxtField.setText("Time løn");
                }
            }
        });
        
        double doubleTaxedPlaceHolder = 1000.234;
        salaryTaxedLabel = new JLabel(String.format(activeLocale, "Løn inkl. Skat: %,.2f", doubleTaxedPlaceHolder)); salaryTaxedLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        double doubleNonTaxedPlaceHolder = 10000.54356;
        salaryNonTaxedLabel = new JLabel(String.format(activeLocale, "Løn ekskl. Skat: %,.2f", doubleNonTaxedPlaceHolder)); salaryNonTaxedLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        double doubleStateHelpPlaceHolder = 963;
        stateHelpTxtField = new JTextField(String.format(activeLocale, "SU: %,.2f", doubleStateHelpPlaceHolder)); stateHelpTxtField.setAlignmentX(CENTER_ALIGNMENT);
        stateHelpTxtField.setMaximumSize(new Dimension(Main.frameWidth / 3, Main.frameHeight / 8));
        
        JPanel otherIncomeNestedPanel = new JPanel(); otherIncomeNestedPanel.setLayout(new BoxLayout(otherIncomeNestedPanel, BoxLayout.X_AXIS));
        otherIncomeNestedPanel.setMaximumSize(new Dimension(Main.frameWidth / 3, Main.frameHeight / 8));
        
        double doubleOtherIncomePlaceHolder = 0;
        otherIncomeTxtField = new JTextField(String.format(activeLocale, "Anden enkelt indtægt: %,.2f", doubleOtherIncomePlaceHolder)); otherIncomeTxtField.setAlignmentY(CENTER_ALIGNMENT);
        
        addOtherIncomeBtn = new JButton("Tilføj"); addOtherIncomeBtn.setAlignmentY(CENTER_ALIGNMENT);

        
        incomeNestedPanel.add(hoursAmountTxtField); incomeNestedPanel.add(Box.createRigidArea(new Dimension(10, 0))); incomeNestedPanel.add(hourlyRateTxtField);
        
        otherIncomeNestedPanel.add(otherIncomeTxtField); otherIncomeNestedPanel.add(Box.createRigidArea(new Dimension(10, 0))); otherIncomeNestedPanel.add(addOtherIncomeBtn);
        
        
        indkomstPanel.add(backHomeBtn);
        indkomstPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        
        indkomstPanel.add(totalIncomeTxtLabel);
        indkomstPanel.add(totalIncomeLabel);
        
        indkomstPanel.add(Box.createRigidArea(new Dimension(0, Main.frameHeight / 97)));
        
        indkomstPanel.add(CreateHorizontalSeperator(Color.BLACK));
        
        indkomstPanel.add(Box.createRigidArea(new Dimension(0, Main.frameHeight / 97)));
        indkomstPanel.add(incomeNestedPanel);
        indkomstPanel.add(Box.createRigidArea(new Dimension(0, Main.frameHeight / 97)));
        indkomstPanel.add(CreateHorizontalSeperator(Color.BLACK));
        
        indkomstPanel.add(Box.createRigidArea(new Dimension(0, Main.frameHeight / 10)));
        
        indkomstPanel.add(salaryTaxedLabel);
        indkomstPanel.add(Box.createRigidArea(new Dimension(0, Main.frameHeight / 97)));
        
        indkomstPanel.add(salaryNonTaxedLabel);
        indkomstPanel.add(Box.createRigidArea(new Dimension(0, Main.frameHeight / 97)));
        
        indkomstPanel.add(CreateHorizontalSeperator(Color.BLACK));
        
        indkomstPanel.add(stateHelpTxtField);
        indkomstPanel.add(Box.createRigidArea(new Dimension(0, Main.frameHeight / 97)));
        
        indkomstPanel.add(otherIncomeNestedPanel);
        indkomstPanel.add(Box.createRigidArea(new Dimension(0, Main.frameHeight / 97)));
    }
    
    private void CreateComponentsOpspar(){
        opsparPanel = new JPanel();
        
        backHomeBtn = new JButton("Tilbage");
        
        backHomeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels(opsparPanel, homePanel, "Din Økonomiske Hjælper");
            }
        });
        
        
        opsparPanel.add(backHomeBtn);
    }
    
    private JSeparator CreateHorizontalSeperator(Color color){
        JSeparator HorizontalSeperator = new JSeparator(SwingConstants.HORIZONTAL);
        HorizontalSeperator.setForeground(color); HorizontalSeperator.setBackground(color);
        return HorizontalSeperator;
    } 
    
    public void switchPanels(JPanel currentPanel, JPanel newPanel, String newTitle){
        
        remove(currentPanel);
        add(newPanel);
        this.setTitle(newTitle);
        revalidate();
        repaint();
        
        
    }
}
