import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeManagementSystemTest {

    @org.junit.jupiter.api.Test
    void main() {
    }


    @Test
    void testValidateCredentials_ValidCredentials() {
        // Arrange
        EmployeeManagementSystem ems = new EmployeeManagementSystem();
        String username = "Christian.Mata";
        String password = "Welcome10002";

        // Act
        boolean result = ems.validateCredentials(username, password);

        // Assert
        assertTrue(result);
    }

    @Test
    void testValidateCredentials_InvalidCredentials() {
        // Arrange
        EmployeeManagementSystem ems = new EmployeeManagementSystem();
        String username = "user";
        String password = "pass";

        // Act
        boolean result = ems.validateCredentials(username, password);

        // Assert
        assertFalse(result);
    }

    @Test
    void testValidateCredentials_FileNotFound() {
        // Arrange
        EmployeeManagementSystem ems = new EmployeeManagementSystem();
        String username = "admin";
        String password = "password";

        // Act
        boolean result = ems.validateCredentials(username, password);

        // Assert
        assertFalse(result);
    }

    @Test
    void testValidateCredentials_IOError() {
        // Arrange
        EmployeeManagementSystem ems = new EmployeeManagementSystem();
        String username = "admin";
        String password = "password";

        // Act
        boolean result = ems.validateCredentials(username, password);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testSaveLeaveApplication() {
// Prepare test data
        String employeeNumber = "10003";
        String firstName = "Brad";
        String lastName = "San Jose";
        String leaveTypeName = "Vacation";
        String startDate = "08/10/2023";
        String endDate = "08/11/2023";


// Read the CSV file and check if the data is saved correctly
        try (CSVReader reader = new CSVReader(new FileReader("leaveApplication.csv"))) {
            String[] record;
            boolean isRecordFound = false;
            while ((record = reader.readNext()) != null) {
                if (record[0].equals(employeeNumber) &&
                        record[1].equals(firstName) &&
                        record[2].equals(lastName) &&
                        record[3].equals(leaveTypeName) &&
                        record[4].equals(startDate) &&
                        record[5].equals(endDate)) {
                    isRecordFound = true;
                    break;
                }
            }
            assertTrue(isRecordFound);
        } catch (IOException ex) {
            ex.printStackTrace();
            fail("IOException occurred");
        }

    }
}


