
package eksamenhtxbankz;


import javax.swing.JLabel;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;


/**
 * Til at lave udgiftspanelets piechart for udgifter
 * @author Victor B. Pedersen
 */
public class ExpensesChart {
    PieChart pie_chart;
    
    /**
     * ExpenseChart klassens constructor, initialiserer et piechart med givne størrelser
     * @param chartWidth piechartets bredde
     * @param chartHeight piechartets højde
     */
    public ExpensesChart(int chartWidth, int chartHeight) {
        
        pie_chart = new PieChartBuilder().width(chartWidth).height(chartHeight).title("Udgifter").theme(Styler.ChartTheme.XChart).build();
        
    }
    
    /**
     * Til tilføjning af udgift til piechart
     * @param name navn på udgift
     * @param value værdi for udgift, i kr
     * @return - JLabel for udgift
     */
    public JLabel addExpense(String name, float value){
        pie_chart.addSeries(name, value);
        
        return new JLabel("Udgift: "+name+"        "+value+" kr.");
    }
    
    /**
     * til returnering af piechart konveret til panel
     * @return - piechart panel
     */
    public XChartPanel<PieChart> getPanel(){
        XChartPanel<PieChart> chartPane = new XChartPanel<>(pie_chart);
        return chartPane;
    }
}
