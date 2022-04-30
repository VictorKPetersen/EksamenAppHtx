/**
* HTX Eksamen i Programering B
* @author : Victor Kahl Petersen, Victor Bøgesvang Pedersen & Kristian Ritter Rasmussen
*/
package eksamenhtxbankz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.lang.Integer.parseInt;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.util.Locale;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;



/**
 * MainWindow til alt GUI relateret og startning af andre processer
 * @author Victor B. Pedersen og Victor K. Petersen
 */
public class MainWindow extends JFrame{
    //Panel Class Variables
    private JPanel homePanel;
    private JPanel kontoPanel;
    private JPanel udgiftPanel;
    private JPanel indkomstPanel;
    private JPanel opsparPanel;
     
    //Buttons for navigating panels på homepanelet
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
    private double salaryTaxedValue;
    private JLabel salaryTaxedLabel;
    private JLabel salaryNonTaxedLabel;
    private JTextField stateHelpTxtField;
    private JTextField otherIncomeTxtField;
    private JButton addOtherIncomeBtn;
    private JTextField communalTaxTxtField;
    
    private Locale activeLocale;
    
    //Udgifter panel
    private ExpensesChart expenseChart; //chart af udgifter
    private JLabel udgiftTemp; //temporary udgift som pulles fra DB
    private JLabel[] udgiftsListe; // liste af udgifts labels
    private JLabel samletUdgifterTxt;
    private XChartPanel<PieChart> expensePanel; // panel fra expensechart
            
    //Database
    public databaseConnection bankDB;
    private ResultSet rs;
    private ResultSetMetaData rsmd;
    
    //opspar panel
    private JLabel incomeTxt;
    private JTextField withdrawalPrMonthTxtField;
    private JSlider[] expenseSliders;
    private JSlider expenseTempSlider;
    private JLabel[] expenseCollumnLabels;
    private JLabel expenseCollumnLabel;
    private JTextField expenseCollumnValue;
    private JTextField[] expenseCollumnValues;
    private JLabel differenceToGoal;
    
    /**
     * MainWindow.java's constructor, bruges til at starte connection til db samt kreere alle paneler
     * @param choosenLocale specifik geografisk, politisk og kulturel region 
     */
    public MainWindow(Locale choosenLocale) {
        bankDB = new databaseConnection();
        
        CreateComponentsHome();
        CreateComponentsKonto();
        CreateComponentsUdgift();
        CreateComponentsIndkomst();
        CreateComponentsOpspar();
           
        activeLocale = choosenLocale;
    }
    
    /**
     * Til kreering af startspanelet og dets komponenter
     */
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
    /**
     * Til kreering af kontopanelet og dets komponenter
     */
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
    /**
     * Til kreering af udgiftspanelet og dets komponenter
     */
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
        
        //ExpensesChart expenseChart = bankDB.getUdgifterListe( new ExpensesChart(600,600));
        
        //expenseChart.addExpense("mad", 20); // tilføj udgift, værdi insættes som kr brugt
        //expenseChart.addExpense("el", 20); // tilføj udgift
        
        //JLabel udgiftTemp = expenseChart.addExpense("mad", 20); udgiftTemp.setAlignmentX(CENTER_ALIGNMENT);
        
        expenseChart = new ExpensesChart(600,600); // build chart
        
        rs = bankDB.getUdgifter();
        rsmd = bankDB.getMetaRS(rs);
        
        udgiftsListe = new JLabel[bankDB.getCountOfCollumns()];
        
        
        
        
        
        
        
        String collumnName; //temporary collumn navn som pulles fra DB
        float collumnValue; //temporary collumn værdi som pulles fra DB
        
        float samletUdgifter = 0;
        
        //tilføj udgifter
        for(int i = 1; i <= bankDB.getCountOfCollumns(); i++){
            
            collumnName = bankDB.getCollumnNameForI(i);
            collumnValue = bankDB.getCollumnValueForI(i);
            
            System.out.println("i: "+bankDB.getCountOfCollumns()+" name: "+collumnName + " value: " + collumnValue);
            
            if(collumnName != null){
               udgiftTemp = expenseChart.addExpense(collumnName, collumnValue); 
               udgiftsListe[i-1] = udgiftTemp;
               samletUdgifter += collumnValue;
            } 
            
            
            
        }
        samletUdgifterTxt = new JLabel("Samlet Udgifter: " + Float.toString(samletUdgifter));
        
        
        
        expensePanel = expenseChart.getPanel(); //lav expensechartet om til panel
        
        udgiftPanel.add(backHomeBtn);
        udgiftPanel.add(expensePanel);
        
        //tilføj udgifts liste
        
        for(int i = 0; i <= udgiftsListe.length-1; i++){
            udgiftPanel.add(udgiftsListe[i]);
        }  
        udgiftPanel.add(samletUdgifterTxt);
        
    }
    /**
     * Til kreering af indkomstspanelet og dets komponenter
     */
    private void CreateComponentsIndkomst(){
        Calculator salaryCalc = new Calculator();
        
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
        

        totalIncomeLabel = new JLabel(String.format(activeLocale, "%,.2f", 0000.0000)); totalIncomeLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        
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
        
        //Stor Inspiration Herfra: https://stackhowto.com/how-to-make-jtextfield-accept-only-numbers/
        hoursAmountTxtField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_PERIOD || e.getKeyChar() == KeyEvent.VK_DECIMAL){
                    //This seems iniefficent
                }
                else if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
                    e.consume();
                }
            }
        });
        
        hoursAmountTxtField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    salaryCalc.hours = Double.parseDouble(hoursAmountTxtField.getText());
                } catch (NumberFormatException TException) {
                    System.out.println("Non numberic characther in string: " + TException);
                }
                updateSalaryFields(salaryCalc);
                salaryTaxedValue = salaryCalc.calcTaxedSalary();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                //Only here becausse DocumentListener is not abstract and doesn't have an Adapter Class
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //Only here becausse DocumentListener is not abstract and doesn't have an Adapter Class
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
        
        hourlyRateTxtField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_PERIOD || e.getKeyChar() == KeyEvent.VK_DECIMAL){
                    //This seems iniefficent
                }
                else if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
                    e.consume();
                }
            }
        });
        
        hourlyRateTxtField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    salaryCalc.hourlyRate = Double.parseDouble(hourlyRateTxtField.getText());
                } catch (NumberFormatException TException) {
                    System.out.println("Non numberic characther in string: " + TException);
                }
                
                updateSalaryFields(salaryCalc);
                salaryTaxedValue = salaryCalc.calcTaxedSalary();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                //Only here becausse DocumentListener is not abstract and doesn't have an Adapter Class
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //Only here becausse DocumentListener is not abstract and doesn't have an Adapter Class
            }
        });
        
        communalTaxTxtField = new JTextField(String.format(activeLocale, "Kommunal Skat: %,.2f p.", 0000.0000)); communalTaxTxtField.setAlignmentX(CENTER_ALIGNMENT);
        communalTaxTxtField.setMaximumSize(new Dimension(Main.frameWidth / 3, Main.frameHeight / 8));
        
        communalTaxTxtField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                communalTaxTxtField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                String tempInput = communalTaxTxtField.getText();
                if (communalTaxTxtField.getText().replaceAll("\\s", "").equals("")) {
                    communalTaxTxtField.setText(String.format(activeLocale, "Kommunal Skat: %,.2f p.", 0000.0000));
                }
                else {
                    communalTaxTxtField.setText(String.format(activeLocale, "Kommunal Skat: %,.2f p.", Double.parseDouble(tempInput)));
                }
            }
        });
        
        communalTaxTxtField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_PERIOD || e.getKeyChar() == KeyEvent.VK_DECIMAL){
                    //This seems iniefficent
                }
                else if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
                    e.consume();
                }
            }
        });
        
        communalTaxTxtField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    Double textFieldValue = Double.parseDouble(communalTaxTxtField.getText().replaceAll(".^\\d", ""));
                    salaryCalc.setTaxBrackets(textFieldValue / 100);
                    updateSalaryFields(salaryCalc);
                } catch (NumberFormatException TException) {
                    System.out.println("Non numberic characther in string: " + TException);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                 //Only here becausse DocumentListener is not abstract and doesn't have an Adapter Class
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                 //Only here becausse DocumentListener is not abstract and doesn't have an Adapter Class
            }
        });
        

        salaryTaxedLabel = new JLabel(String.format(activeLocale, "Løn inkl. Skat: %,.2f", 0000.0000)); salaryTaxedLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        salaryNonTaxedLabel = new JLabel(String.format(activeLocale, "Løn ekskl. Skat: %,.2f", 0000.0000)); salaryNonTaxedLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        stateHelpTxtField = new JTextField(String.format(activeLocale, "SU: %,.2f", 0000.0000)); stateHelpTxtField.setAlignmentX(CENTER_ALIGNMENT);
        stateHelpTxtField.setMaximumSize(new Dimension(Main.frameWidth / 3, Main.frameHeight / 8));
        
        stateHelpTxtField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                stateHelpTxtField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                String tempInput = stateHelpTxtField.getText();
                if (stateHelpTxtField.getText().replaceAll("\\s", "").equals("")) {
                    stateHelpTxtField.setText(String.format(activeLocale, "SU: %,.2f", 0000.0000));
                }
                else{
                stateHelpTxtField.setText(String.format(activeLocale, "SU: %,.2f", Double.parseDouble(tempInput)));
                }
            }
        });
        
        
        stateHelpTxtField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_PERIOD || e.getKeyChar() == KeyEvent.VK_DECIMAL){
                    //This seems iniefficent
                }
                else if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
                    e.consume();
                }
            }
        });
        
        stateHelpTxtField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    Double textFieldValue = Double.parseDouble(stateHelpTxtField.getText().replaceAll(".^\\d", ""));
                    salaryCalc.suSalary = textFieldValue;
                    updateSalaryFields(salaryCalc);
                } catch (Exception TException) {
                    System.out.println("Non numberic characther in string: " + TException);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                //Only here becausse DocumentListener is not abstract and doesn't have an Adapter Class
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //Only here becausse DocumentListener is not abstract and doesn't have an Adapter Class
            }
        });
        
        
        
        JPanel otherIncomeNestedPanel = new JPanel(); otherIncomeNestedPanel.setLayout(new BoxLayout(otherIncomeNestedPanel, BoxLayout.X_AXIS));
        otherIncomeNestedPanel.setMaximumSize(new Dimension(Main.frameWidth / 3, Main.frameHeight / 8));
        
        otherIncomeTxtField = new JTextField(String.format(activeLocale, "Anden enkelt indtægt: %,.2f", 0000.0000)); otherIncomeTxtField.setAlignmentY(CENTER_ALIGNMENT);
        
        otherIncomeTxtField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                otherIncomeTxtField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (otherIncomeTxtField.getText().replaceAll("\\s", "").equals("")) {
                    otherIncomeTxtField.setText(String.format(activeLocale, "Anden enkelt indtægt: %,.2f", 0000.0000));
                    
                }
            }
        });
        
        otherIncomeTxtField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_PERIOD || e.getKeyChar() == KeyEvent.VK_DECIMAL){
                    //This seems iniefficent
                }
                else if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
                    e.consume();
                }
            }
        });
        
        
        addOtherIncomeBtn = new JButton("Tilføj"); addOtherIncomeBtn.setAlignmentY(CENTER_ALIGNMENT);
        
        addOtherIncomeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                double incomeToAdd = Double.parseDouble(otherIncomeTxtField.getText());
                salaryCalc.addOtherIncome(incomeToAdd);
                    updateSalaryFields(salaryCalc);
                } catch (Exception TException) {
                    System.out.println("Non numberic characther in string: " + TException);
                }

            }
        });

        
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
        
        indkomstPanel.add(communalTaxTxtField);
        
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
    
    /**
     * Til kreering af opsparingspanelet og dets komponenter
     */
    private void CreateComponentsOpspar(){
        opsparPanel = new JPanel();
        opsparPanel.setLayout(new BoxLayout(opsparPanel, BoxLayout.Y_AXIS));
        
        backHomeBtn = new JButton("Tilbage"); 
        
        backHomeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanels(opsparPanel, homePanel, "Din Økonomiske Hjælper");
            }
        });
        
        incomeTxt = new JLabel("forventet indkomst: "); 
        
        
        withdrawalPrMonthTxtField = new JTextField("Udtræk Pr Måned (kr.)");
        
        differenceToGoal = new JLabel("indkomst - udgift og månedlig opsparing: ");
        
        withdrawalPrMonthTxtField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                withdrawalPrMonthTxtField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (withdrawalPrMonthTxtField.getText().replaceAll("\\s", "").equals("")) {
                    withdrawalPrMonthTxtField.setText("Udtræk Pr Måned (kr.)");
                }
            }
        });
        
        withdrawalPrMonthTxtField.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateGoal();
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                
            });
        
        
        expenseSliders = new JSlider[bankDB.getCountOfCollumns()];
        expenseCollumnLabels = new JLabel[bankDB.getCountOfCollumns()];
        expenseCollumnValues = new JTextField[bankDB.getCountOfCollumns()];
        
        for(int i = 1; i <= bankDB.getCountOfCollumns(); i++){
            
            String collumnName = bankDB.getCollumnNameForI(i);
            float collumnValue = bankDB.getCollumnValueForI(i);
            
            
            if(collumnName != null){
                
               expenseCollumnLabel = new JLabel(collumnName); 
               expenseCollumnLabels[i-1] = expenseCollumnLabel; 
               
               expenseTempSlider = new JSlider(0,20000,(int)collumnValue); expenseTempSlider.setAlignmentX(LEFT_ALIGNMENT);
               expenseTempSlider.setMajorTickSpacing(5000);expenseTempSlider.setMinorTickSpacing(1000); 
               expenseTempSlider.setPaintTicks(true); expenseTempSlider.setPaintLabels(true); //expenseTempSlider.setSnapToTicks(true);
               
               expenseSliders[i-1] = expenseTempSlider;
               expenseCollumnValue = new JTextField(Integer.toString(expenseTempSlider.getValue()));
               expenseCollumnValues[i-1] = expenseCollumnValue;
            }  
        }
        
        
        opsparPanel.add(backHomeBtn);
        
        opsparPanel.add(incomeTxt);
        opsparPanel.add(withdrawalPrMonthTxtField);
        
        for(int i = 0; i <= expenseSliders.length-1; i++){
            opsparPanel.add(expenseCollumnLabels[i]);
            opsparPanel.add(expenseSliders[i]);
            
            expenseSliders[i].addChangeListener(new ChangeListener(){
                   @Override
                   public void stateChanged(ChangeEvent e) {
                       updateSliderLabelValues();
                       updateGoal();
                       //expenseCollumnValues[i].setText(Integer.toString(expenseSliders[i].getValue()));
                       //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                   }
               });
            opsparPanel.add(expenseCollumnValues[i]);
            expenseCollumnValues[i].addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateSliderValues();
                    updateGoal();
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                
            });
        }
        opsparPanel.add(differenceToGoal);
        
        
        
    }
    
    /**
     * Til opdatering af opsparings slider labels værdier for udgifter
     */
    public void updateSliderLabelValues(){
        for(int i = 0; i <= expenseSliders.length-1; i++){
            expenseSliders[i].setSnapToTicks(true);
            expenseCollumnValues[i].setText(Integer.toString(expenseSliders[i].getValue()));
            
            
        }
    }
    
    /**
     * Til opdatering af opsparings slider værdier for udgifter
     */
    public void updateSliderValues(){
        for(int i = 0; i <= expenseCollumnValues.length-1; i++){
            expenseSliders[i].setSnapToTicks(false);
            expenseSliders[i].setValue(parseInt(expenseCollumnValues[i].getText()));
            
        }
    }
    
    /**
     * Til opdatering af opsparings målet
     */
    public void updateGoal(){
        float sumOfSliderVals = 0;

        for(int i = 1; i <= bankDB.getCountOfCollumns(); i++){
            sumOfSliderVals += expenseSliders[i-1].getValue();

        }
        differenceToGoal.setText("indkomst - udgift og månedlig opsparing: " + (((float) salaryTaxedValue - Float.parseFloat(withdrawalPrMonthTxtField.getText())) - sumOfSliderVals));        
        //differenceToGoal.setText("mål - reel: " + ((sumOfDBVals - Float.parseFloat(withdrawalPrMonthTxtField.getText())) - sumOfSliderVals));
        
    }
    

    /**
     * Til kreering af horizontal separator med JSeperator
     * @param color farve til JSeperator
     * @return JSeperator
     */
    private JSeparator CreateHorizontalSeperator(Color color){
        JSeparator HorizontalSeperator = new JSeparator(SwingConstants.HORIZONTAL);
        HorizontalSeperator.setForeground(color); HorizontalSeperator.setBackground(color);
        return HorizontalSeperator;
    } 
  
    /**
     * Til opdatering af diverse felter som viser indkomst
     * @param sCalculator brugte instans af Calculator klassen
     */
    private void updateSalaryFields(Calculator sCalculator) {
        salaryNonTaxedLabel.setText(String.format(activeLocale, "Løn ekskl. Skat: %,.2f", sCalculator.calcNonTaxedSalary()));
        salaryTaxedLabel.setText(String.format(activeLocale, "Løn inkl. Skat: %,.2f", sCalculator.calcTaxedSalary()));
        totalIncomeLabel.setText(String.format(activeLocale, "%,.2f", sCalculator.getExpectedIncome()));
    }
    
    
    /**
     * Til skift af JPanel
     * bruges til hver skiftning mellem programmets forskellige funktioner
     * @param currentPanel nuværende JPanel i JFramet
     * @param newPanel JPanelet der skal skiftes til
     * @param newTitle ny titel til programmet, ved skiftning af panel
     */
    public void switchPanels(JPanel currentPanel, JPanel newPanel, String newTitle){
        
        remove(currentPanel);
        add(newPanel);
        this.setTitle(newTitle);
        revalidate();
        repaint();
        
        
    }
}
