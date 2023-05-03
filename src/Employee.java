import java.time.LocalDate;

public class Employee {
    private String employeeNumber;
    private String firstName;
    private String lastName;
    private static double hourlyRate;
    private static double riceAllowance;
    private static double phoneAllowance;
    private static double clothingAllowance;
    private String sssNumber;
    private String philhealthNumber;
    private String pagibigNumber;
    private String tin;
    private static int sickLeave;
    private static int vacationLeave;
    private static int emergencyLeave;

    public Employee(String employeeNumber, String firstName, String lastName,
                    String sssNumber, String philhealthNumber, String pagibigNumber, String tin,double hourlyRate, double riceAllowance, double phoneAllowance, double clothingAllowance, int sickLeave, int vacationLeave, int emergencyLeave) {
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sssNumber = sssNumber;
        this.philhealthNumber = philhealthNumber;
        this.pagibigNumber = pagibigNumber;
        this.tin = tin;
        this.hourlyRate = hourlyRate;
        this.riceAllowance = riceAllowance;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.sickLeave = 5;
        this.vacationLeave = 10;
        this.emergencyLeave = 5;

    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSssNumber() { return sssNumber; }
    public void setSssNumber (String sssNumber) { this.sssNumber = sssNumber; }

    public  String getPhilhealthNumber() { return  philhealthNumber;}
    public void setPhilhealthNumber(String philhealthNumber) { this.philhealthNumber = philhealthNumber;}
    public  String getPagibigNumber() { return pagibigNumber;}
    public void setPagibigNumber(String pagibigNumber) { this.pagibigNumber = pagibigNumber;}
    public String getTin() { return tin;}
    public void setTin(String tin) { this.tin =tin;}
    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public static double getRiceAllowance() {
        return riceAllowance;
    }

    public void setRiceAllowance(double riceAllowance) {
        this.riceAllowance = riceAllowance;
    }

    public static double getPhoneAllowance() {
        return phoneAllowance ;
    }

    public void setPhoneAllowance(double phoneAllowance) {
        this.phoneAllowance = phoneAllowance;
    }

    public static double getClothingAllowance() {
        return clothingAllowance ;
    }

    public void setClothingAllowance(double clothingAllowance) {
        this.clothingAllowance = clothingAllowance;
    }

    public static int getSickLeave(){
        return sickLeave;
    }
    public void setSickLeave(int sickLeave){
        this.sickLeave= sickLeave;
    }
    public static int getVacationLeave(){
        return  vacationLeave ;
    }
    public void setVacationLeave(int vacationLeave){
        this.vacationLeave = vacationLeave;
    }
    public static int getEmergencyLeave() {
        return  emergencyLeave;
    }
    public  void setEmergencyLeave(int emergencyLeave){
        this.emergencyLeave =emergencyLeave;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void setName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static double computeGrossPay(double hoursWorked) {
        return hoursWorked * hourlyRate + riceAllowance + phoneAllowance + clothingAllowance;
    }
    public static double computeSSSDeduction(double grossPay) {
        return grossPay * 0.10;
    }

    public static double computePhilhealthDeduction(double grossPay) {
        return grossPay * 0.03;
    }

    public static double computePagibigDeduction(double grossPay) {
        return grossPay * 0.02;
    }

    public static double computeTaxDeduction(double grossPay) {
        double taxableIncome = grossPay - 5000.0;
        double tax = 0.0;
        if (taxableIncome > 0 && taxableIncome <= 20000) {
            tax = taxableIncome * 0.1;
        } else if (taxableIncome > 20000 && taxableIncome <= 40000) {
            tax = 2000.0 + (taxableIncome - 20000) * 0.15;
        } else if (taxableIncome > 40000 && taxableIncome <= 60000) {
            tax = 5000.0 + (taxableIncome - 40000) * 0.2;
        } else if (taxableIncome > 60000 && taxableIncome <= 80000) {
            tax = 9000.0 + (taxableIncome - 60000) * 0.25;
        } else if (taxableIncome > 80000 && taxableIncome <= 180000) {
            tax = 16000.0 + (taxableIncome - 80000) * 0.3;
        } else if (taxableIncome > 180000 && taxableIncome <= 1000000) {
            tax = 49000.0 + (taxableIncome - 180000) * 0.32;
        } else if (taxableIncome > 1000000) {
            tax = 285000.0 + (taxableIncome - 1000000) * 0.35;
        }
        return Math.min(tax, grossPay * 0.4); // tax should not exceed 40% of gross pay
    }

    public double computeNetPay(double grossPay) {
        double totalDeductions = computeSSSDeduction(grossPay) +
                computePhilhealthDeduction(grossPay) +
                computePagibigDeduction(grossPay) +
                computeTaxDeduction(grossPay);
        return grossPay - totalDeductions;
    }


    public String toString() {
        return "Employee{" +
                "empNum='" + employeeNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hourlyRate=" + hourlyRate +
                ", riceAllowance=" + riceAllowance +
                ", phoneAllowance=" + phoneAllowance +
                ", clothingAllowance=" + clothingAllowance +
                '}';
    }

}
