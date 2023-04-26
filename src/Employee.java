public class Employee {
    private String empNum;
    private String firstName;
    private String lastName;
    private static double hourlyRate;
    private static double riceAllowance;
    private static double phoneAllowance;
    private static double clothingAllowance;

    public Employee(String empNum, String firstName, String lastName,
                    double hourlyRate, double riceAllowance,
                    double phoneAllowance, double clothingAllowance) {
        this.empNum = empNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hourlyRate = hourlyRate;
        this.riceAllowance = riceAllowance;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
    }

    public String getEmpNum() {
        return empNum;
    }

    public void setEmpNum(String empNum) {
        this.empNum = empNum;
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

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public static double getRiceAllowance() {
        return riceAllowance / 4;
    }

    public void setRiceAllowance(double riceAllowance) {
        this.riceAllowance = riceAllowance;
    }

    public static double getPhoneAllowance() {
        return phoneAllowance / 4;
    }

    public void setPhoneAllowance(double phoneAllowance) {
        this.phoneAllowance = phoneAllowance;
    }

    public static double getClothingAllowance() {
        return clothingAllowance / 4;
    }

    public void setClothingAllowance(double clothingAllowance) {
        this.clothingAllowance = clothingAllowance;
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
                "empNum='" + empNum + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hourlyRate=" + hourlyRate +
                ", riceAllowance=" + riceAllowance +
                ", phoneAllowance=" + phoneAllowance +
                ", clothingAllowance=" + clothingAllowance +
                '}';
    }
}
