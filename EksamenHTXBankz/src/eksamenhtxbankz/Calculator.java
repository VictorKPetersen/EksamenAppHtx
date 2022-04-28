/**
* HTX Eksamen i Programering B
* @author : Victor Kahl Petersen, Victor Bøgesvang Pedersen & Kristian Ritter Rasmussen
*/
package eksamenhtxbankz;

public class Calculator {
    public double hourlyRate;
    public double hours;
    
    //Tax for 2022 taken from https://www.legaldesk.dk/artikler/topskat
    private double taxCap = 5448000;
    
    private double totalLowerTax;
    
    //Upper tax bracket ceiling taken from https://www.legaldesk.dk/artikler/topskat
    private double totalUpperTax = 0.5207;
    
    public Calculator(double hourlyRate, double hours) {
        this.hourlyRate = hourlyRate;
        this.hours = hours;
    }
    
    // Calculates your expected salary based on your amount of hours and your hourly rate
    public double calcNonTaxedSalary(){
        return hourlyRate * hours;
    }
    
    public double  calcTaxedSalary() {
        
        return 0.0;
    }
    
    public void setLowerTaxBracket(double decimalCommunalTax) {
        this.totalLowerTax = decimalCommunalTax + 0.1216 + 0.08;
    }
}
