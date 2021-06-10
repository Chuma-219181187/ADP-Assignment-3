/**
 *This is a CreateCustomer_and_SupplierSer
 *
 * @author Chuma Nxazonke (219181187)
 * Date: 06 June 2021
 */
package za.ac.cput.adp_assignment_3;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateCustomer_and_SupplierSer {

    ObjectInputStream input;
    FileWriter writerFile;
    BufferedWriter buffWriter;
    ArrayList<Customer> aCustomer = new ArrayList<Customer>(); //Declaring the arraylist of customer
    ArrayList<Supplier> aSupplier = new ArrayList<Supplier>(); //Declaring the arraylist of supplier

    public void openFile() { //This method will open the file and the create the file
        try {
            input = new ObjectInputStream(new FileInputStream("stakeholder.ser"));
            System.out.println("*** ser file created and opened for reading  ***");
        } catch (IOException ioe) {
            System.out.println("error opening ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }

    public void readTheFile() { //This method will read the data from the file text and display it here
        try {
            while (true) {
                Object line = input.readObject();
                String customer = "Customer";
                String supplier = "Supplier";
                String name = line.getClass().getSimpleName();
                if (name.equals(customer)) {
                    aCustomer.add((Customer) line);
                } else if (name.equals(supplier)) {
                    aSupplier.add((Supplier) line);
                } else {
                    System.out.println("*** class error reading the ser file on the disck");
                    System.out.println(line);
                }
            }
        } catch (EOFException eofe) {
            System.out.println("The file has been reached");
        } catch (ClassNotFoundException ioe) {
            System.out.println("*** class erro reading the ser file on the disck: " + ioe);
        } catch (IOException ioe) {
            System.out.println("Error reading the ser file: " + ioe);
        }

        sortCustomerDetails();
        sortSuppliersDetails();
        
    }

    public void readCloseFile() { //This method will close the file after its been created
        try {
            input.close(); //Closing the text file
        } catch (IOException ioe) {
            System.out.println("error closing ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }

    public void sortCustomerDetails() { //This method will sort the details of the customer by shareholder id
        String[] sortCustomerID = new String[aCustomer.size()];
        ArrayList<Customer> sortAscending = new ArrayList<Customer>();
        int count = aCustomer.size();
        for (int j = 0; j < count; j++) {
            sortCustomerID[j] = aCustomer.get(j).getStHolderId();
        }
        Arrays.sort(sortCustomerID);

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                if (sortCustomerID[i].equals(aCustomer.get(j).getStHolderId())) {
                    sortAscending.add(aCustomer.get(j));
                }
            }
        }
        aCustomer.clear();
        aCustomer = sortAscending;
    }

    public int getCustomerAge(String dateOfBirth) { //This method will determine the age of each customeR
        String[] seperate = dateOfBirth.split("-");

        LocalDate birth = LocalDate.of(Integer.parseInt(seperate[0]), Integer.parseInt(seperate[1]), Integer.parseInt(seperate[2]));
        LocalDate current = LocalDate.now();
        Period difference = Period.between(birth, current);
        int customerAge = difference.getYears();
        return customerAge;
    }

    public String formatDateOfBrith(Customer dateOfBirth) { //This method will re-format the date-of-birth: 1993-01-24  24 Jan 1993
        LocalDate dob = LocalDate.parse(dateOfBirth.getDateOfBirth());
        DateTimeFormatter changeFormat = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return dob.format(changeFormat);
    }

    public String rent() { //This method will determine the number of customers who can rent and those who cannot
        int count = aCustomer.size();
        
        int cusCanRent = 0;
        int cusNotRent = 0;
        
        for (int i = 0; i < count; i++) {
            if (aCustomer.get(i).getCanRent()) {
                cusCanRent = cusCanRent + 1;
            } else {
                cusNotRent = cusNotRent + 1;
            }
        }
        String line = "Number of customers who can rent : " + '\t' + cusCanRent + '\n' + "Number of customers who cannot rent : " + '\t' + cusNotRent;
        
        return line;
    }

    public void writeToCustomerFile() { //This method will the details (sorted) of each customer to a text file 
        try {
            writerFile = new FileWriter("customerOutFile.txt");
            buffWriter = new BufferedWriter(writerFile);
            buffWriter.write(String.format("%s\n", "===========================Customers================================"));

            buffWriter.write(String.format("%-15s %-15s %-15s %-15s %-15s\n", "ID", "Name", "Surname", "Date of Birth", "Age"));
            buffWriter.write(String.format("%s\n", "===================================================================="));
            for (int j = 0; j < aCustomer.size(); j++) {
                buffWriter.write(String.format("%-15s %-15s %-15s %-15s %-15s \n", aCustomer.get(j).getStHolderId(), aCustomer.get(j).getFirstName(), aCustomer.get(j).getSurName(), formatDob(aCustomer.get(j)), getCustomerAge(aCustomer.get(j).getDateOfBirth())));
            }
            buffWriter.write(String.format("%s\n", " "));
            buffWriter.write(String.format("%s\n", " "));
            buffWriter.write(String.format("%s\n", rent()));
        } catch (IOException fnfe) {
            System.out.println(fnfe);
            System.exit(1);
        }
        try {
            buffWriter.close();
        } catch (IOException ioe) {
            System.out.println("error closing text file: " + ioe.getMessage());
            System.exit(1);
        }
        rent();
    }

    public void sortSuppliersDetails() { //This method sort the contents of the supplier arraylist in ascending (alphabetical) order of name.
        String[] sortSupplierID = new String[aSupplier.size()];
        ArrayList<Supplier> sortAscending = new ArrayList<Supplier>();
        int count = aSupplier.size();
        for (int i = 0; i < count; i++) {
            sortSupplierID[i] = aSupplier.get(i).getName();
        }
        Arrays.sort(sortSupplierID);

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                if (sortSupplierID[i].equals(aSupplier.get(j).getName())) {
                    sortAscending.add(aSupplier.get(j));
                }
            }
        }
        aSupplier.clear();
        aSupplier = sortAscending;
    }

    public void writeToSupplierFile() { //This method will write the details (sorted) of each supplier to another text file 
        try {
            writerFile = new FileWriter("supplierOutFile.txt");
            buffWriter = new BufferedWriter(writerFile);
            buffWriter.write(String.format("%s\n", "============================ SUPPLIERS ===================================="));

            buffWriter.write(String.format("%-15s %-15s \t %-15s %-15s \n", "ID", "Name", "Prod Type", "Description"));
            buffWriter.write("===========================================================================\n");
            for (int i = 0; i < aSupplier.size(); i++) {
                buffWriter.write(String.format("%-15s %-15s \t %-15s %-15s \n", aSupplier.get(i).getStHolderId(), aSupplier.get(i).getName(), aSupplier.get(i).getProductType(), aSupplier.get(i).getProductDescription()));
            }
            System.out.println("The supplier text file has been created and the information is displayed.");

        } catch (IOException fnfe) {
            System.out.println(fnfe);
            System.exit(1);
        }
        try {
            buffWriter.close();
        } catch (IOException ioe) {
            System.out.println("error closing text file: " + ioe.getMessage());
            System.exit(1);
        }
        
    }

    public static void main(String args[]) {
        CreateCustomer_and_SupplierSer obj = new CreateCustomer_and_SupplierSer(); //Creating an instance of a object
        obj.openFile();
        obj.readTheFile();          //Calling all the method
        obj.readCloseFile();
        obj.writeToCustomerFile();
        obj.writeToSupplierFile();

    }

}//End of CreateCustomer_and_SupplierSer class
