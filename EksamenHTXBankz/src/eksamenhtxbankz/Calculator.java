/**
* HTX Eksamen i Programering B
* @author : Victor Kahl Petersen, Victor Bøgesvang Pedersen & Kristian Ritter Rasmussen
*/
package eksamenhtxbankz;

/**
 * 
 * Calculator klasse, til udregning af alt indkomst relateret
 */
public class Calculator {
    public double hourlyRate;
    public double hours;
    public double suSalary;
    private double otherIncome;
    private double nonTaxedSalary;
    private double taxedSalary;

    public double communalTax;
    //Tax for 2022 https://skat.dk/skat.aspx?oid=1719985
    private double bottomTaxCap;
    
    //Tax for 2022 taken from https://www.legaldesk.dk/artikler/topskat
    private double topTaxCap;
    
    private double totalLowerTax;
    
    //Upper tax bracket ceiling taken from https://www.legaldesk.dk/artikler/topskat
    private double totalUpperTax;
    
    /**
     * Calculator.java's constructor, sætter instansens forskellige variabler
     */
    public Calculator() {
        this.bottomTaxCap = 46600;
        this.topTaxCap = 50045;
        this.suSalary = 0.0;
        this.otherIncome = 0.0;
        this.totalLowerTax = 0.1216 + 0.08;
        this.totalUpperTax = 0.1216 + 0.08 + 0.15;
    }
    
    // Calculates your expected salary based on your amount of hours and your hourly rate
    /**
     * Til udregning af indkomst uden skat, udfra timer og timeløn
     * @return - indkomst uden skat aftrukket
     */
    public double calcNonTaxedSalary(){
        nonTaxedSalary = hourlyRate * hours + suSalary + otherIncome;
        return nonTaxedSalary;
    }
    
    /**
     * Til udregning af indkomst med skat, udfra timer og timeløn
     * @return - indkomst med skat aftrukket
     */
    public double calcTaxedSalary() {
        if(nonTaxedSalary <= bottomTaxCap){
            taxedSalary = nonTaxedSalary * (1 - 0.08 - communalTax);
            return  taxedSalary;
        }
        if (nonTaxedSalary <= topTaxCap && nonTaxedSalary >= bottomTaxCap) {
            double moneyOverBottomTax = nonTaxedSalary - bottomTaxCap;
            taxedSalary = moneyOverBottomTax * (1 - totalLowerTax) + bottomTaxCap * (1 - 0.08 - communalTax);
            return  taxedSalary;
        } else {
            double moneyOverUpperTax = nonTaxedSalary - topTaxCap;
            taxedSalary = moneyOverUpperTax * (1 - totalUpperTax) + topTaxCap * (1 - totalLowerTax);
            return taxedSalary;
        }
    }
    
    /**
     * Til sætning af skat procent ved tillægning af kommuneskat
     * @param decimalCommunalTax kommuneskat 
     */
    public void setTaxBrackets(double decimalCommunalTax) {
        this.communalTax = decimalCommunalTax;
        totalLowerTax = decimalCommunalTax + 0.1216 + 0.08;
        if (totalLowerTax >= 0.5207) {
            totalLowerTax = 0.5207;
        }
        
        if (totalLowerTax + 0.15 <= 0.5207) {
            totalUpperTax = totalLowerTax + 0.15;;
        } else {
            totalUpperTax = 0.5207;
        }
    }
    
    public void addOtherIncome (double incomeToAdd){
        otherIncome += incomeToAdd;
    }
            
    
    /**
     * Til at få indkomsternete samlet
     * @return - Samlet forventet indkomst
     */
    public double getExpectedIncome() {
        return taxedSalary;
    }
    
}
