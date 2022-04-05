/*
* HTX Eksamen i Programering B
* Lavet af: Victor Kahl Petersen, Victor Bøgesvang Pedersen & Kristian Ritter Rasmussen
*/
package eksamenhtxbankz;

public class Calculator {
    double hourlyRate;
    double hours;
    
    public Calculator(double hourlyRate, double hours) {
        this.hourlyRate = hourlyRate;
        this.hours = hours;
    }
    
    // Calculates your expected salary based on your amount of hours and your hourly rate
    public double calcSalary(){
        return hourlyRate * hours;
    }
}
