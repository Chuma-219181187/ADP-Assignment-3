/**
 *This is a CreateCustomerSer.java
 * This class will serialize  the objects of customer
 *
 * @author Chuma Nxazonke (219181187) Date: 06 June 2021
 */
package za.ac.cput.adp_assignment_3;

import java.io.*;

import java.time.LocalDate;

import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateCustomerSer {

    private ObjectInputStream input;
    Customer customer;

    ObjectOutputStream output;
    ArrayList<Customer> aCustomer = new ArrayList<Customer>();

//a)
    public void openFile() //The file will be created and opened  for writting the value of the customer 
    {
        try {
            output = new ObjectOutputStream(new FileOutputStream("customerOutputFile.ser"));
            System.out.println("*** ser file is created and opened for writting");

        } catch (IOException ioe) {

            System.out.println("The error opening ser file: " + ioe.getMessage());
            System.exit(1);

        }

    }

    public void readFile() throws EOFException { //reads the next byte of the data from the the input stream and returns int in the range of 0 to 255

        try {

            while (true) {

                customer = (Customer) input.readObject();
                System.out.println(customer);

            }

        } catch (EOFException eofe) {
            System.out.println("The file has been reached");
        } catch (ClassNotFoundException ioe) {

            System.out.println("*** class erro reading the ser file on the disck" + ioe);
        } catch (IOException ioe) {

            System.out.println("*** class erro reading the ser file on the disck" + ioe);
            System.out.println("Error reading the ser file");
        } finally {

            System.out.println("Error reading the ser file");
        }
        sortCustomerData();

    }

    public void closeFile() {
        try {

            output.close();

        } catch (IOException ioe) {

            System.out.println("the error closing ser file: " + ioe.getMessage());
            System.exit(1);

        }

    }

//b)
    public void sortCustomerData() { //This method will sort the contents of the customer arraylist in ascending order of stakeholderId

        String customerSortID[] = new String[aCustomer.size()];
        ArrayList<Customer> sortAscending = new ArrayList<Customer>();

        int count = aCustomer.size();
        for (int j = 0; j < count; j++) {
            customerSortID[j] = aCustomer.get(j).getStHolderId();

        }

        Arrays.sort(customerSortID);

        for (int j = 0; j < count; j++) {
            for (int k = 0; k < count; k++) {
                if (customerSortID[j].equals(aCustomer.get(k).getStHolderId())) {
                    sortAscending.add(aCustomer.get(k));
                }
            }
        }

        aCustomer.clear();
        aCustomer = sortAscending;

    }

    //c)
    public int customergetAge(String dateOfBirth) { //Determine the age of each customer.

        String separation[] = dateOfBirth.split("-");

        LocalDate birth = LocalDate.of(Integer.parseInt(separation[0]), Integer.parseInt(separation[1]), Integer.parseInt(separation[2]));
        LocalDate currentD = LocalDate.now();
        Period difference = Period.between(birth, currentD);
        int age = difference.getYears();

        return age;

    }

    public String formatDateOfBirth(Customer dateOfBirth) {

        LocalDate dobToFormat = LocalDate.parse(dateOfBirth.getDateOfBirth());
        DateTimeFormatter changeFormat = DateTimeFormatter.ofPattern("dd MMM yyyy");

        return dobToFormat.format(changeFormat);

    }

    //e)
    public void writeToFile() throws IOException {

        try {
            FileWriter writeData = new FileWriter("customerOutputFile.ser");

            BufferedWriter buffwriter = new BufferedWriter(writeData);
            buffwriter.write(String.format("%s\n", "==============================Customer======================"));

            buffwriter.write(String.format("%-15s %-15 %-15s %-15s\n", "ID", "Name", "Surname", "Date of Birth", "Age"));
            buffwriter.write(String.format("%s\n", "===================================================================="));
            for (int i = 0; i < aCustomer.size(); i++) {
                buffwriter.write(String.format("%-15s %-15 %-15s %-15s\n", aCustomer.get(i).getStHolderId(), aCustomer.get(i).getFirstName(), aCustomer.get(i).getSurName(), aCustomer.get(i).getDateOfBirth(), aCustomer.get(i)));

            }
            buffwriter.write(String.format("%s\n", "  "));
            buffwriter.write(String.format("%s\n", "  "));
            buffwriter.write(String.format("%s\n", rent()));

            buffwriter.close();

        } catch (IOException fnfe) {
            System.out.println(fnfe);

            System.out.println("error closing text file on the disck: " + fnfe.getMessage());

            System.exit(1);

        }

    }

    public String rent() {

        int count = aCustomer.size();
        int cusCanRent = 0;
        int cusNotRent = 0;

        for (int i = 0; i < count; i++) {
            if (aCustomer.get(i).getCanRent()) {
                cusCanRent++;
            } else {
                cusNotRent++;
            }
        }

        String line = " The number of customer who can rent : " + '\t' + cusCanRent + '\n' + "The number of customers who cannot rent' : " + '\t' + cusNotRent;

        return line;

    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // ArrayList<Customer> aCustomer = new ArrayList<>();

        CreateCustomerSer obj = new CreateCustomerSer();

        obj.openFile();
        obj.readFile();
        obj.closeFile();
        obj.writeToFile();

        // obj.readFile();
        //writeToFile("customerOutputFile.ser",Customer);
    }

}
