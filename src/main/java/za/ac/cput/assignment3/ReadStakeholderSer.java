/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput.assignment3;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
/**
 *
 * @author M. Nur
 */
public class ReadStakeholderSer{
    
    ObjectInputStream input;
    ArrayList<Customer>customerArray = new ArrayList<>();
    ArrayList<Supplier>supplierArray = new ArrayList<>();
    Stakeholder stake;
    int age[];
    LocalDate Datetoday;
    LocalDate DateOB ;
    Period CalcAges;
    
//Creates and opens ser file    
public void openFile() {

    try {
            input = new ObjectInputStream (new FileInputStream("stakeholder.ser"));
            System.out.println("... ser file opened for reading ...");
        }

    catch (IOException ioe) {
    
            System.out.println("error while opening ser file..." + ioe.getMessage());
            System.exit(1);

}

}

//Adds to customer array
public void AddToCustomerArray() {
    
   try {
          
         while (true) {                         
                            
          stake = (Stakeholder) input.readObject();
          
          if(stake instanceof Customer) {
              
              customerArray.add((Customer) stake); 
             
          }
          
         }
   }
   
   catch (EOFException ex) {
       
       System.out.println("EOF reached");
   }
   
   catch (ClassNotFoundException cnfe) {
       
       System.out.println("error reading from ser file..." + cnfe);
   }
   
   catch (IOException ioe) {
       
       System.out.println("error reading from ser file..." + ioe);
       
   }
                         
}

//Writes to customer array
public void writeCustomer () {
    
    for (int i = 0; i<customerArray.size(); i++) {
    System.out.println(customerArray.get(i));
}
}

//Gets age of customers
 public void DetermineAge()
            {

                age = new int[customerArray.size()];
                for(int i=0;i<customerArray.size();i++)

                    {
                        Datetoday = LocalDate.now();
                        DateOB = LocalDate.parse(customerArray.get(i).getDateOfBirth());
                        CalcAges = Period.between(DateOB, Datetoday);
                        age[i] = CalcAges.getYears();
                    }
            }
       
                
//Formats DOB to correct format                
public void FormatDOB () throws ParseException{
    
   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   
   for(int i = 0; i<customerArray.size(); i++) {
     
   String birth = customerArray.get(i).getDateOfBirth();    
   
   Date date1 = sdf.parse(birth);
   SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyy");
   
   customerArray.get(i).setDateOfBirth(sdf1.format(date1));
   
  
          
   }
}

//Code for customers that can rent
      public int CustomerDoesRent() {
        int canRent = 0;

        for (int i = 0; i < customerArray.size(); i++) {
            customerArray.get(i);

            if (customerArray.get(i).getCanRent() == true) {
                canRent++;
            }
        }
        return canRent;
    }

      //Code for customers that can't rent
      public int CustomerDoesNotRent() {
        int cannotRent = 0;

        for (int i = 0; i < customerArray.size(); i++) {
            customerArray.get(i);

            if (customerArray.get(i).getCanRent() == false) {
                cannotRent++;
            }
        }
        return cannotRent;
    }

//Adds to supplier array      
public void AddToSupplierFile () {
    
try {
            
        while (true) {
               
        stake = (Stakeholder) input.readObject();
                
             if (stake instanceof Supplier) {
                    
                supplierArray.add((Supplier) stake);  
           }
       }
    }
    
catch (EOFException ex) {
                
    System.out.println("EOF reached");
                        
    }

catch(ClassNotFoundException cnfe) {

        System.out.println("error reading from ser file..." + cnfe);

}
        
catch (IOException ioe) {
    
    System.out.println("error reading from ser file..." + ioe);
    
}             
 
}

//Writes to supplier array
public void writeSupplier() {
    
    for (int i = 0; i<supplierArray.size(); i++) {
    System.out.println(supplierArray.get(i));
}
}

//Sort customer array list
public void sortCustomerArrayList() {
   
        customerArray.sort(Comparator.comparing(Customer::getStHolderId));
  
}
    
//Sort suppplier array list
public void sortSupplierArrayList() {
    
   supplierArray.sort(Comparator.comparing(Supplier::getName));
    
}

//Close file
public void closeFile() {
    
    try {
        
        input.close();
        
        }

catch (IOException ioe) {
    
    System.out.println("error while closing file..." + ioe.getMessage());
    System.exit(1);

}

    


}

//Creates and writes to new txt file for customer
public void WriteToCustomerTxt () {
 
    try {
    BufferedWriter bw = new BufferedWriter (new FileWriter("customer.txt"));
    
    String str = "======================Customers==============================";
    bw.write(str);
    bw.newLine();
    
    str="ID   \t   Name   \t   Surname   \t Date of Birth\t  Age";
    bw.write(str);
    bw.newLine();
    
    str = "=============================================================";
    bw.write(str);
    bw.newLine();
    
    for (int i = 0; i < customerArray.size(); i++)
    
    {
        str=customerArray.get(i).getStHolderId()+"   \t   "
        +customerArray.get(i).getFirstName()+"   \t   "+customerArray.get(i).getSurName()+"  \t  "+customerArray.get(i).getDateOfBirth()+"  \t   "+age[i];
        bw.write(str);
        bw.newLine();
        
        
    }
    bw.write("Number of customers who can rent: "+CustomerDoesRent() );
                bw.newLine();
                bw.write("Number of customers who cannot rent: "+CustomerDoesNotRent() );
    bw.close();
    
 }
    catch (IOException e) {
                System.out.println("Error");
                
            }

    
    
}
//Creates and writes to new txt file for supplier

public void WriteToSupplierTxt () {
 try {
    BufferedWriter bw = new BufferedWriter (new FileWriter("supplier.txt"));
    
    String str = "================Supppliers====================";
    bw.write(str);
    bw.newLine();
    
    str="ID   \t   Name   \t   Prod Type   \t   Description";
    bw.write(str);
    bw.newLine();
    
    str = "==============================================";
    bw.write(str);
    bw.newLine();
    
    for (int i = 0; i < supplierArray.size(); i++)
    
    {
        str=supplierArray.get(i).getStHolderId()+"\t"+supplierArray.get(i).getName()+"\t"
           +supplierArray.get(i).getProductType()+"    \t  "+supplierArray.get(i).getProductDescription()+"     \t  ";
        bw.write(str);
        bw.newLine();
    }
    bw.close();
 }
    catch (IOException e) {
                System.out.println("Error");
                
            }

    
}

//Main method
public static void main(String[] args) throws ParseException {
        ReadStakeholderSer obj = new ReadStakeholderSer ();
        obj.openFile();
        obj.AddToCustomerArray();
        obj.sortCustomerArrayList();
        obj.DetermineAge();
        obj.FormatDOB();
        obj.writeCustomer();
        obj.closeFile();
        obj.WriteToCustomerTxt();
        
        
        
        obj.openFile();
        obj.AddToSupplierFile();
        obj.sortSupplierArrayList();
        obj.writeSupplier();
        obj.closeFile();
        obj.WriteToSupplierTxt();
    }


}