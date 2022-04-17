/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eksamenhtxbankz;


import javax.swing.JLabel;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;


/**
 *
 * @author Victor B. Pedersen
 */
public class ExpensesChart {
    PieChart pie_chart;
    
    public ExpensesChart(int chartWidth, int chartHeight) {
        
        pie_chart = new PieChartBuilder().width(chartWidth).height(chartHeight).title("Udgifter").theme(Styler.ChartTheme.XChart).build();
        
    }
    
    
    public JLabel addExpense(String name, float value){
        pie_chart.addSeries(name, value);
        
        return new JLabel("Udgift: "+name+"        "+value+" kr.");
    }
    
    public XChartPanel<PieChart> getPanel(){
        XChartPanel<PieChart> chartPane = new XChartPanel<>(pie_chart);
        return chartPane;
    }
}
