/* Assignment			 : Simple Terminal
 * Author				 : Nenad Samardzic
 * Date					 : 02/15/2015
 * Description			 : Consider a store where each item has a price per unit, and may also have a volume price.
 * 				  		   For example, apples may be $1.00 each or 4 for $3.00.
 * 				  		   Implement a point-of-sale scanning API that accepts an arbitrary ordering of products
 * 						   (similar to what would happen at a checkout line) and then returns the correct total price for an entire
 * 						   shopping cart based on the per unit prices or the volume prices as applicable.
 * Additional requirement: Extend the solution by adding an additional pricing option. Item can also have a special price - until a
 * 						   threshold has been reached unit price is applied, but after that all of the products are charged 
 * 						   by the special price.
 */

package SimpleTerminal;

import java.util.Scanner;

public class SimpleTerminal {

	public static void main(String[] args) {
		
		Scanner reader = new Scanner(System.in);
		
		// Upload new, or use the existing price list file
		System.out.println("Would you like to set a new pricing list (Y/N)?\n(E to exit)");

		boolean haveAnswer = false;
		Terminal terminal = new Terminal();
		while (!haveAnswer) {
			String pricingList = reader.next().toUpperCase();
			if (pricingList.equals("Y")) {
				haveAnswer = true;
				terminal.setPricing();
			} else if (pricingList.equals("N")) {
				haveAnswer = true;
			} else if (pricingList.equals("E")) {
				System.out.println("Goodbye.");
				reader.close();
				System.exit(0);
			} else {
				System.out.println("Would you like to set a new pricing list? Please choose Y or N!\n(E to exit)");
			}
		}
		
		// Load price list file for usage
		terminal.loadPrices();
		
		// Scan items until '*' is entered
		System.out.println("Please, scan the next item. (* to finish your purchase)");
		boolean endScan = false;
		while (!endScan) {
			String purchaseItem = reader.next();
			if (purchaseItem.equals("*")) {
				endScan = true;
			} else {
				terminal.scan(purchaseItem);
			}
		}
		reader.close();

		System.out.printf("Your purchase bill is: $%.2f.", terminal.total);
	}
}
