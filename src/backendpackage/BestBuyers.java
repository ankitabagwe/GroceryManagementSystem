package backendpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;

import globalPackage.GlobalVariables;

public class BestBuyers {
	public String blueColor = "\u001B[34m";
	 public String redColor = "\u001B[31m";
	 public static String Cyan = "\u001B[35m";
	 public String resetColor = "\u001B[0m";
	public void BestBuyer() {
		int year;
		GlobalVariables globalVariables = new GlobalVariables();
		Year currentYear = Year.now();
        int Currentyear = currentYear.getValue();
		
		System.out.println(Cyan + " Below are the Best Buyers...of " + Currentyear + resetColor);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bill","root","root");
			
			System.out.println(" Enter a revenue of the current year: ");
			int Revenue = globalVariables.sc.nextInt();
			System.out.println(" Enter Total yearly expense: ");
			int Total_expense = globalVariables.sc.nextInt();
			int profit = Revenue - Total_expense ;
			System.out.println(redColor + "\n\n ** Our Calculation **\n Your Total annual PROFIT is " + blueColor +  profit );
			System.out.println(resetColor + " Suggest the cut off amount to sort the BEST BUYERS according to the profit: ");
			int cutoffAmt = globalVariables.sc.nextInt();
			
			//need to recollect only a data of 365 days. 
			LocalDate currentdate = LocalDate.now();
			
			PreparedStatement ps = con.prepareStatement("SELECT Name, Mobile_no, SUM(Total_amount) AS yearly_Total FROM bill.bill_details GROUP BY Name, Mobile_no HAVING SUM(Total_amount) >= ? ORDER BY yearly_Total DESC; ");
			
			ps.setInt(1,cutoffAmt);
			
			ResultSet rs = ps.executeQuery();
			System.out.println(blueColor + "Name " + "\t\t" + blueColor + "Mobile Number " + "\t\t" + blueColor + "Yearly Total Purchase " +resetColor );
			while(rs.next()) {
				
				System.out.println(rs.getString("Name") + "\t\t" + rs.getInt("Mobile_no")+ "\t\t\t"+ redColor + rs.getInt("yearly_Total") + resetColor);
			}
			
		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
