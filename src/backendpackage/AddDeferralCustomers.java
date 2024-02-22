package backendpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.InputMismatchException;

import globalPackage.GlobalVariables;

public class AddDeferralCustomers {

	 public String blueColor = "\u001B[34m";
	 public String redColor = "\u001B[31m";
	 public String resetColor = "\u001B[0m";
	 String Date;
	 GlobalVariables globalVariables = new GlobalVariables();
	 int DBbilldate;
	 String DBname;
	 int DBbillno;
	 boolean  billExists = false;
	 int Totalamt;
	 
	public void addinfo() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost/bill","root","root");
			PreparedStatement ps1 = con1.prepareStatement("select * from bill_details");
			ResultSet rs1 = ps1.executeQuery();
			while(rs1.next()) {
				
			DBname =  rs1.getString("Name");
			
			DBbillno = rs1.getInt("Bill_no");
			
				
			}
			System.out.println(resetColor + " Name :- ");
			String customername = globalVariables.sc.next();
			
			System.out.println(" Bill no. :- ");
			int Bill_no = globalVariables.sc.nextInt();
			
			if(customername.equals(DBname) && Bill_no==DBbillno) {
				billExists =true;
			}
			rs1.close();
			ps1.close();
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/GroceryManagementSystem","root","root");
			Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost/bill","root","root");
			PreparedStatement ps2 = con2.prepareStatement("select Total_amount from bill_details where bill_no = ?");
			 ps2.setInt(1, Bill_no);
			 ResultSet rs2 = ps2.executeQuery();
			 while(rs2.next()) {
				 Totalamt = rs2.getInt("Total_amount");
			}
			 
			 
			if(billExists) {
			PreparedStatement ps = con.prepareStatement(" insert into customerdata values (?,?,?,?,?,?)");
			
			System.out.println(redColor + " Add the bellow details of the Udhar taken customer " + "\n  ");
			try {
			
				
			ps.setString(1, customername);
			ps.setInt(2, Bill_no );
			
			System.out.println(" Contact Number:- ");
			
			int ContactNumber =  globalVariables.sc.nextInt();
			
			ps.setInt(3,ContactNumber);
			
			System.out.println(" Your Total amount is " + Totalamt);
			ps.setInt(4, Totalamt);
			
			System.out.println(" Pending Payment :- ");
			ps.setInt(5, globalVariables.sc.nextInt());
			
			
			try {
			System.out.println(" Due date:- \n *enter date in dd/mm/yyyy format." );
			 Date = globalVariables.sc.next();
			
			}catch (Exception e) {
				System.err.println("Please follow the format to enter a date. ");
			}
			
			
			
			//to convert date into yyyy/mm/dd format
			String[] date1 = Date.split("/");
			String temp = date1[0];
			date1[0]=date1[2];
			date1[2]=temp;

		    //store the converted date in Database
			String DBDate = String.join("/", date1);
			ps.setString(6,DBDate);

		
			int row= ps.executeUpdate();
			//System.err.println(row + " entry added." + "\t" + "Name :- " + ps.getResultSet() );
			con.close();
			
			
			
			}
			catch (InputMismatchException e) {
				System.err.println(" invalid input..");
			}
			
			
			}else {
				System.out.println(" Bill Id not found..");
			}
		}
		 catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void updatepayment() {
		
		System.out.println(" Update the Pending Payments ");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/GroceryManagementSystem","root","root");	
			PreparedStatement ps = con.prepareStatement(" select PendingPayment from customerdata where Name= ? AND ContactNumber = ?");
			
			
			System.out.println("Enter Name :");

//			if(globalVariables.sc.hasNextLine()) {
//				 name = globalVariables.sc.nextLine();
//			}
//			else {
//				 name = globalVariables.sc.next();
//			}
			
			
			String name = globalVariables.sc.next();
			
			ps.setString(1, name);
			
			System.out.println(" Enter Contact Number: ");
			ps.setInt(2, globalVariables.sc.nextInt());
			
			ResultSet rs = ps.executeQuery();
			int pendingamount =0;
			while(rs.next()) {
				 pendingamount =  rs.getInt("PendingPayment");
				System.out.println(" Your Pending Payment is " + pendingamount);
			}
			
			ps = con.prepareStatement("update customerdata set PendingPayment = ? where name = ?");
			
			System.out.println(" Enter amount to be paid: ");
		  int paidamount = globalVariables.sc.nextInt();
		  paidamount  = pendingamount-paidamount;
		    ps.setInt(1,paidamount );
		    ps.setString(2, name);
		    
		    int row = ps.executeUpdate();
		    
		   // System.out.println(row + " is updated");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
public void showdetails() {
	
	try {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/GroceryManagementSystem","root","root");		
		 
		System.out.println(blueColor + "Name " + "\t\t"+ blueColor + "Bill no " + "\t"+ blueColor + "Contact Number "+ "\t" + " Total M.R.P"+ "\t" + blueColor + "Pending Payment "+ "\t\t" + "Due Date");
		 
		//ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY 
		//This allows the resulset to move the cursor backward or forward.
		
		
		PreparedStatement ps = con.prepareStatement(" select * from customerdata ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		 ResultSet rs= ps.executeQuery();
		 
		 while(rs.next()) {
			java.sql.Date DueDate = rs.getDate("Duedate");
			LocalDate localDueDate = DueDate.toLocalDate();
			LocalDate Currentdate = LocalDate.now();
			
			//Find the difference of dates using period method
			//period.between is a method of period class
			//of time package the package import java.time.Period; is imported.
			
		
			Period period = Period.between(Currentdate,localDueDate );
			 int daysDifference = period.getDays(); // Extract days from the Period

			 if (daysDifference <= 5) {
			        System.out.println(
			            redColor + rs.getString(1) + "\t\t" + redColor + rs.getInt(2) + "\t\t\t" + redColor + rs.getInt(3) + "\t\t" +
			            redColor + rs.getInt(4) + "\t\t" + redColor + rs.getInt(5) + "\t\t\t" + redColor + rs.getDate(6)
			        );
			    } 
		 }
		 rs.beforeFirst();
		 while (rs.next()) {
			    java.sql.Date dueDate = rs.getDate("Duedate");
			    LocalDate localDueDate = dueDate.toLocalDate();
			    LocalDate currentDate = LocalDate.now();

			    Period period = Period.between(currentDate, localDueDate);
			    int daysDifference = period.getDays(); // Extract days from the Period

			    if (daysDifference > 5) {
			        System.out.println(
			            resetColor + rs.getString(1) + "\t\t" + resetColor + rs.getInt(2) + "\t\t\t" + resetColor + rs.getInt(3) + "\t\t" +
			            resetColor + rs.getInt(4) + "\t\t" + resetColor + rs.getInt(5) + "\t\t\t" + resetColor + rs.getDate(6)
			        );
			    }
		 }
		 
        con.close();
		 
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
}
