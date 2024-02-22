package GROCERY_MANAGEMENT_SYSTEM;

import backendpackage.Bill;
import backendpackage.AddDeferralCustomers;
import backendpackage.BestBuyers;
import globalPackage.GlobalVariables;

public class MainScreen {
	public static String Cyan = "\u001B[35m";
	public static String bold = "\u001B[1m";
	public static String underline = "\u001B[4m";
	public static String resetColor = "\u001B[0m";
	public static String redColor = "\u001B[31m";
	public static String bgcolor = "\u001B[48;5;226m";
	 
		
	public static void main(String[] args) {
		
		GlobalVariables globalVariables = new GlobalVariables();
		AddDeferralCustomers addDeferralCustomers = new AddDeferralCustomers();
		BestBuyers bestBuyers = new BestBuyers();
		Bill bill = new Bill();
		final String LoginId = "RameshSupermarket";
		final String Password = "harrypotter";
		
		
	System.out.println(bgcolor+ bold +  " \t\t\t******* Welcome to RAMESH SUPERMARKET ******* " + resetColor);
	System.out.println(redColor + "\n Namaste Ramesh Ji, \n\n "+resetColor+ "Enter your login Id :- " + resetColor);
//	String LoginId1 = globalVariables.sc.next();
//	System.out.println("\nEnter Password:- ");
//	String Password1 = globalVariables.sc.next();
//	
//	if(LoginId.equals(LoginId1) && Password.equals(Password1) ) {
		do {
		System.out.println(Cyan +"\n *** Welcome *** ");

		System.out.println("1. *** Udhar Book ***  ");
		System.out.println("2. *** Check Best Buyers *** ");
		System.out.println("3. *** Bill *** ");
        System.out.println("4. Exit" + resetColor);
       
        switch(globalVariables.sc.nextInt()) {
        case 1:
        	System.out.println(" a. Add Customer ");
    		System.out.println(" b. View Customer");
    		System.out.println(" c. Update pending Payment ");
    		
        	switch(globalVariables.sc.next()) {
        	case "a":
        		addDeferralCustomers.addinfo();
        		break;
        	case "b":
        		addDeferralCustomers.showdetails();
        		break;
        	case "c":
        		addDeferralCustomers.updatepayment();
        		break;
        	}
        	break;
        case 2:
        	bestBuyers.BestBuyer();
        	break;
        case 3:
        	System.out.println("a. Create Bill ");
        	System.out.println("b. View Bill ");
        	
        	switch(globalVariables.sc.next()) {
        	case "a":
        		bill.CreateBill();

        		break;
        	case "b":
        	    bill.viewBill();
        	    break;
        	}
        break;
        case 4:
        	System.exit(0);
            break;
        }
        

	}
		while(true);
//	}
//	else {
//		System.out.println(" Enter correct login credentials!!");
//	}
		}
		

}
