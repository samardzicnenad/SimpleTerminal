package SimpleTerminal;

public class Item {
	
	private String itemCode;
	private float priceSingle, priceOption;
	private int qtyOption, count;
	private String special;

	public Item(String itemCode, float priceSingle, String special, float priceOption, int qtyOption){
		this.itemCode = itemCode;
		this.priceSingle = priceSingle;
		this.special = special;
		this.priceOption = priceOption;
		this.qtyOption = qtyOption;
		this.count = 0;
	}
	
	public float getPriceSingle() {
		return this.priceSingle;
	}
	
	public String getSpecial() {
		return this.special;
	}
	
	public float getPriceOption() {
		return this.priceOption;
	}
	
	public int getQtyOption() {
		return this.qtyOption;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void updateCount(boolean withReset) {
		if (withReset) {
			this.count = 0;
		} else {
			this.count++;
		}
	}
}
