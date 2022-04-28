/**
* HTX Eksamen i Programering B
* @author : Victor Kahl Petersen, Victor Bøgesvang Pedersen & Kristian Ritter Rasmussen
*/
package eksamenhtxbankz;

public class Calculator {
    public double hourlyRate;
    public double hours;
    private double nonTaxedSalary;
    private  double taxedSalary;
    
    //Tax for 2022 taken from https://www.legaldesk.dk/artikler/topskat
    private double taxCap = 50045;
    
    private double totalLowerTax;
    
    //Upper tax bracket ceiling taken from https://www.legaldesk.dk/artikler/topskat
    private double totalUpperTax;
    
    public Calculator() {
        this.taxCap = 544800;
        this.totalLowerTax = 0.1216 + 0.08;
        this.totalUpperTax = 0.1216 + 0.08 + 0.15;
    }
    
    // Calculates your expected salary based on your amount of hours and your hourly rate
    public double calcNonTaxedSalary(){
        nonTaxedSalary = hourlyRate * hours;
        return nonTaxedSalary;
    }
    
    public double  calcTaxedSalary() {
        if (nonTaxedSalary <= taxCap) {
            taxedSalary = nonTaxedSalary * (1 - totalLowerTax);
            return  taxedSalary;
        } else {
            double moneyOverUpperTax = nonTaxedSalary - taxCap;
            taxedSalary = moneyOverUpperTax * (1 - totalUpperTax) + taxCap * (1 - totalLowerTax);
            return taxedSalary;
        }
    }
    
    public void setTaxBrackets(double decimalCommunalTax) {
        totalLowerTax = decimalCommunalTax + 0.1216 + 0.08;
        if (totalLowerTax + 0.15 <= 0.5207) {
            totalUpperTax = totalLowerTax + 0.15;
        } else {
            totalUpperTax = 0.5207;
        }
    }
    
}
