package SimpleTerminal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Terminal {
	
	float total = 0.00f;
	
	Map<String, Item> purchase = new HashMap<String, Item>();
	
	// Handles a new set pricing file logic
	public void setPricing() {
		Scanner reader = new Scanner(System.in);
		System.out.println("Please, provide the full path to your pricing file.\n(D to use default pricing file or E to exit.)");
		
		boolean havePricingList = false;
		while (!havePricingList) {
			String pricingListPath = reader.next();
			if (pricingListPath.equals("e") || pricingListPath.equals("E")) {
				System.out.println("Goodbye.");
				reader.close();
				System.exit(0);
			} else if (pricingListPath.equals("d") || pricingListPath.equals("D")) {
				havePricingList = true;
			} else {
				File pricingListFile = new File(pricingListPath);
				
				if(!pricingListFile.exists() || pricingListFile.isDirectory()) {
					System.out.println("File doesn't exist! Please, provide the full path to your pricing file.\n(D to use default pricing file or E to exit.)");
				} else {
					havePricingList = true;
					this.setNewPricing(pricingListPath);
				}
			}
		}
	}

	// Saves pricing file
	private void setNewPricing(String pricingListPath) {		
		String newPricing = readPricing(pricingListPath);
		writePricing(newPricing);
	}
	
	// Reads prices from the file
	private String readPricing(String pricingListPath) {
		String newPricing = "", fileRow = "";
		
		File pricingListFile = new File(pricingListPath);
		
		BufferedReader buffReader = null;
		try {
			buffReader = new BufferedReader(new FileReader(pricingListFile));

		    while ((fileRow = buffReader.readLine()) != null) {
		    	newPricing = newPricing + fileRow + "\n";
		    }
		} catch (Exception ex) {
		    ex.printStackTrace();
		} finally {
			try {
				if (buffReader != null) {
					buffReader.close();
				}
			} catch (IOException ex) {
			}
		}
		return newPricing;
	}

	// Save new pricing to the default file
	private void writePricing(String newPricing) {
		BufferedWriter buffWriter = null;
		try {
			buffWriter = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/Pricing/Pricing.txt"));
			buffWriter.write(newPricing);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
		    try {
		        if (buffWriter != null) {
		        	buffWriter.close();
		        }
		    } catch (IOException ex) {
		    }
		}
	}
	
	// Loads pricing from the pricing file into the map
	public void loadPrices() {
		String fileRow = "";

		File pricingListFile = new File(System.getProperty("user.dir") + "/Pricing/Pricing.txt");
		
		BufferedReader buffReader = null;
		try {
			buffReader = new BufferedReader(new FileReader(pricingListFile));

		    while ((fileRow = buffReader.readLine()) != null) {
		    	List<String> itemList = Arrays.asList(fileRow.split(";"));
		    	Item item;
		    	if (itemList.size() == 5) {
		    		item = new Item(itemList.get(0),
		    						Float.parseFloat(itemList.get(1)),
		    						itemList.get(2),
		    						Float.parseFloat(itemList.get(4)), 
		    						Integer.parseInt((itemList.get(3))));
		    	} else {
		    		//"A" stands for Any
		    		item = new Item(itemList.get(0),
    						Float.parseFloat(itemList.get(1)),
    						"A",
    						Float.parseFloat(itemList.get(1)), 
    						1);
		    	}
		    	purchase.put(itemList.get(0), item);
		    }
		} catch (Exception ex) {
		    ex.printStackTrace();
		} finally {
			try {
				if (buffReader != null) {
					buffReader.close();
				}
			} catch (IOException ex) {
			}
		}
	}
	
	// for the scanned item checks its existence and calculates the total amount
	public void scan(String item) {
		if (purchase.get(item) == null) {
			System.out.println("Scanned item doesn't exist in our price list. Please try a different item.");
		} else {
			total += this.updatePrice(purchase, item);
			System.out.printf("Item added. Current bill is: $%.2f. Please, scan the next item.\n(* to finish your purchase)\n", total);
		}
	}
	
	// Calculates the price and handles the item count
	private float updatePrice(Map<String, Item> purchase, String item) {
		if (purchase.get(item).getQtyOption() - purchase.get(item).getCount() > 1) {
			purchase.get(item).updateCount(false);
			return purchase.get(item).getPriceSingle();
		} else {
			//"V" stands for Volume
			if (purchase.get(item).getSpecial().equals("V")) {
				float correction = purchase.get(item).getPriceOption() - purchase.get(item).getCount() * purchase.get(item).getPriceSingle();
				purchase.get(item).updateCount(true);
				return correction;
			} else {
				if (purchase.get(item).getQtyOption() - purchase.get(item).getCount() == 1) {
					float correction = purchase.get(item).getQtyOption() * purchase.get(item).getPriceOption() - purchase.get(item).getCount() * purchase.get(item).getPriceSingle();
					purchase.get(item).updateCount(false);
					return correction;
				} else {
					return purchase.get(item).getPriceOption();
				}				
			}			
		}
	}
}
