/**
 *This is a CreateSupplierSer.java
 * This class will serialize the objects of the supplier
 *
 * @author Chuma Nxazonke (219181187) Date: 06 June 2021
 */
package za.ac.cput.adp_assignment_3;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateSupplierSer {

    private ObjectInputStream input;
    Supplier supplier;

    ObjectOutputStream output;
    ArrayList<Supplier> aSupplier = new ArrayList<Supplier>();

    public void openFile() //The file will be created and opened  for writting the value of the supplier 
    {
        try {
            output = new ObjectOutputStream(new FileOutputStream("supplierOutputFile.ser"));
            System.out.println("*** ser file is created and opened for writting");

        } catch (IOException ioe) {

            System.out.println("The error opening ser file: " + ioe.getMessage());
            System.exit(1);

        }

    }

    public void readFile() throws IOException { //reads the next byte of the data from the the input stream and returns int in the range of 0 to 255

        try {

            while (true) {

                supplier = (Supplier) input.readObject();
                System.out.println(supplier);
            }

        } catch (EOFException eofe) {

            System.out.println("The file has been reached");

        } catch (ClassNotFoundException ioe) {

            System.out.println("*** class erro reading the ser file on the disck" + ioe);
        } finally {

            System.out.println("Error reading the ser file");
        }
        sortSupplierData();
    }

    public void closeFile() {
        try {

            output.close();

        } catch (IOException ioe) {

            System.out.println("the error closing ser file: " + ioe.getMessage());
            System.exit(1);

        }

    }

    public void sortSupplierData() { //This method will sort the contents of the customer arraylist in ascending order of stakeholderId

        String supplierSortID[] = new String[aSupplier.size()];
        ArrayList<Supplier> sortAscending = new ArrayList<Supplier>();

        int count = aSupplier.size();
        for (int j = 0; j < count; j++) {
            supplierSortID[j] = aSupplier.get(j).getName();

        }

        Arrays.sort(supplierSortID);

        for (int j = 0; j < count; j++) {
            for (int k = 0; k < count; k++) {
                if (supplierSortID[j].equals(aSupplier.get(k).getStHolderId())) {
                    sortAscending.add(aSupplier.get(k));
                }
            }
        }

        aSupplier.clear();
        aSupplier = sortAscending;

    }

    //e)
    public void writeToFile() throws IOException { //write to file

        try {
            FileWriter writeData = new FileWriter("supplierOutputFile.ser");

            BufferedWriter buffwriter = new BufferedWriter(writeData);
            buffwriter.write(String.format("%s\n", "==============================Supplier======================"));

            buffwriter.write(String.format("%-15s %-15 \t %-15s \n", "ID", "Name", "Product Type", "Description"));
            buffwriter.write(String.format("%s\n", "===================================================================="));
            for (int i = 0; i < aSupplier.size(); i++) {
                buffwriter.write(String.format("%-15s %-15 \t %-15s %-15s \n", aSupplier.get(i).getStHolderId(), aSupplier.get(i).getName(), aSupplier.get(i).getProductType(), aSupplier.get(i).getProductDescription()));

            }
            System.out.println("The supplier text file has been created and the information is diplayed");

            buffwriter.close();

        } catch (IOException fnfe) {
            System.out.println(fnfe);

            System.out.println("error closing text file on the disck: " + fnfe.getMessage());

            System.exit(1);

        }

    }

    public static void main(String[] args) throws IOException {
        CreateSupplierSer obj = new CreateSupplierSer();

        obj.openFile();
        obj.readFile();
        obj.closeFile();
        obj.writeToFile();

    }

}
