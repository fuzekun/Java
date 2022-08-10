package Chapter10;

import java.util.Date;

public class Loan {
    private double annulInteretRate;
    private int numberOfYears;
    private double loadAmout;
    private java.util.Date laonDate;

    public Loan(double annulInteretRate, int numberOfYears, double loadAmout) {
        this.annulInteretRate = annulInteretRate;
        this.numberOfYears = numberOfYears;
        this.loadAmout = loadAmout;
        this.laonDate = new java.util.Date();
     }

    public Loan() {
        this(2.5, 1, 1000);
    }

    public double getAnnulInteretRate() {
        return annulInteretRate;
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public double getLoadAmout() {
        return loadAmout;
    }

    public Date getLaonDate() {
        return laonDate;
    }

    public void setAnnulInteretRate(double annulInteretRate) {
        this.annulInteretRate = annulInteretRate;
    }

    public void setNumberOfYears(int numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public void setLoadAmout(double loadAmout) {
        this.loadAmout = loadAmout;
    }

    public void setLaonDate(Date laonDate) {
        this.laonDate = laonDate;
    }

    public double getMonthlyPayment() {
        double monthlyInterestsRate = annulInteretRate / 1200;
        double monthlyPayment = loadAmout * monthlyInterestsRate / (1 -
                (1 / Math.pow(1 + monthlyInterestsRate, numberOfYears * 12)));
        return monthlyPayment;
    }

    public double getTotalPayment() {
        double totalPayment = getMonthlyPayment() * numberOfYears * 12;
        return totalPayment;
    }

    public java.util.Date getLoanDate() {
        return this.laonDate;
    }
}
