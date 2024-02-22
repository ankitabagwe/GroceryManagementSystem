package backendpackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import GROCERY_MANAGEMENT_SYSTEM.MainScreen;
import globalPackage.GlobalVariables;

public class Bill {
	GlobalVariables globalVariables;
	static int billid = 10001;
	static String ShopName = " RAMESH SUPERMARKET ";
	static String Address = "Anand Nagar, Kurar Village, Malad-(east) \n\t\t  Mumbai-(400097), Maharashtra";
	public String blueColor = "\u001B[34m";
	 public String redColor = "\u001B[31m";
	 public String resetColor = "\u001B[0m";
	 String DBName;
	 int DBcontsct;
	 String Name;
	 int ContactNumber;
	 int Total_amount;
	 int Total_item;
	 
public void CreateBill() {
	GlobalVariables globalVariables = new GlobalVariables();
	int currentbill_id = bill_id();
	int TotalQty = 0;
	int TotalMRP =0;
	int Totalitem=0;
 try {
		FileWriter writer = new FileWriter("C:\\Users\\AnkitaBagwe\\OneDrive - Adapt Fintech Advisors Private Limited\\Documents\\ankita\\Desktop\\GroceryManagementSystem\\Bill.txt",true);
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bill","root","root");
		PreparedStatement ps = con.prepareStatement(" insert into bill_details values (?,?,?,?,?,?,?)");
		
		
		writer.append("\n\t\t\t" + ShopName );
		writer.append("\n\t\t"+ Address );
		
		writer.append("\nBill NO: "+ currentbill_id  );
		ps.setInt(3, currentbill_id);
		
		writer.append("\t\t\t\t\t\t Bill date:- " + LocalDate.now());
		writer.append("\n\n Particluars \t\t Qty \t\t M.R.P");
		
		boolean addmoreitems = true;
		while(addmoreitems) {
		System.out.println("Item :- "  );
		String item;
		if(globalVariables.sc.hasNextLine()) {
			item = globalVariables.sc.nextLine();
		}
		else {
			item = globalVariables.sc.next();
		}
		
		
		
//		String item = globalVariables.sc.next();
		
		
	    writer.write("\n"+item);
	    
	    //Totalitem = Integer.parseInt(item);
	    Totalitem++;
	  
	   
	    
	    System.out.println("Qty:- ");
	    int qty = globalVariables.sc.nextInt();
	    writer.write("\t\t\t"+ qty);
	   
	     TotalQty =   qty +  TotalQty ;
	    
	    System.out.println("M.R.P :- ");
	    int mrp = globalVariables.sc.nextInt();
	    
	    TotalMRP += mrp;
	    writer.write("\t\t\t"+ mrp );
	    
	    System.out.println("Add more items? Press yes or no (yes/no): ");
        String choice = globalVariables.sc.next();
        if (!choice.equalsIgnoreCase("yes")) {
        	addmoreitems = false;
        	writer.append("\n_______________________________________________________________________\n");
        	writer.append("Total Items: "  + Totalitem  );
        	ps.setInt(5, Totalitem);
        	writer.append("\t\t Total Qty: " + TotalQty );
        	ps.setInt(6, TotalQty);
        	writer.append("\t\t Total M.R.P: " + TotalMRP );
        	ps.setInt(7, TotalMRP);
        	writer.append("\n__ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __\n");
        }
    }
		//Data that needs to store in file and database both
	    
	    LocalDate currentDate = LocalDate.now();
	    ps.setDate(4, Date.valueOf(currentDate));
	    
		
		System.out.println("Name of the subscriber:- ");
		ps.setString(1, globalVariables.sc.next());
		
		System.out.println(" Contact Number of the subscriber :- ");
		ps.setInt(2, globalVariables.sc.nextInt());
		
		int rs = ps.executeUpdate();
	   writer.close();
	   
	 } catch (IOException e) {
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println(" Bill created .." + currentbill_id);
	
	}
	
///fetch the bill id  from database to increment it

public int bill_id() {
	int newbillid=0;
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bill","root","root");
		PreparedStatement ps = con.prepareStatement(" SELECT Bill_no FROM bill_details ORDER BY Bill_no DESC LIMIT 1; ");
		ResultSet rs = ps.executeQuery();
		
		  if (rs.next()) {
	            int lastBillNo = rs.getInt("Bill_no");
	            
	            newbillid = lastBillNo + 1;
	        } else {
	            newbillid = 10001; // Set to default value if no bill exists
	        }
		  billid=newbillid;
		con.close();
		
		
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return newbillid;
	
}
	
	


public void viewBill() {
	GlobalVariables globalVariables = new GlobalVariables();
	 int billid1 =0;
	 
	
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bill","root","root");
		
		PreparedStatement ps = con.prepareStatement("select * from bill_details ");
		ResultSet rs1= ps.executeQuery();
		while(rs1.next()) {
		 DBName = rs1.getString("Name");
		 DBcontsct = rs1.getInt("Mobile_no");
		}
		System.out.println(" Enter Name: ");
		 Name = globalVariables.sc.next();
		
		System.out.println(" Enter Contact Number: ");
		 ContactNumber = globalVariables.sc.nextInt();
		
	 
			 
		 ps = con.prepareStatement("select Bill_no, Total_items from bill_details where Name= ? AND Mobile_no=?; ");
		
		ps.setString(1, Name);
		
		
		ps.setInt(2, ContactNumber);
		
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			 billid1 =rs.getInt("Bill_no");
		 //Total_amount = rs.getInt("Total_amount");
		 Total_item =rs.getInt("Total_items");
		 
		
		}
		

	}
	
	catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	 
	
	File file = new File("C:\\Users\\AnkitaBagwe\\OneDrive - Adapt Fintech Advisors Private Limited\\Documents\\ankita\\Desktop\\GroceryManagementSystem\\bill.txt");
	Scanner datareader;
	System.out.println("\n\t\t\t" + redColor + ShopName + "\n\t\t"+ blueColor+  Address + resetColor);
	try {
		datareader = new Scanner(file);
		 //String newLine="";
		 while (datareader.hasNextLine()) {  
	         String fileData = datareader.nextLine(); 
	         
	         if (fileData.startsWith("Bill NO: " + billid1)) {
	             System.out.println(fileData); 
	             
	             while (datareader.hasNextLine()) {
	                 fileData = datareader.nextLine();
	                 System.out.println(fileData); // Output the line
	                 
	                 if (fileData.startsWith("Total Items: " + Total_item)) {
	                     break; // Exit the loop when reaching the desired line
	                 }
	             }
	             break;
	         }
		 }
		 datareader.close();  
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
}
	
}
	
	



