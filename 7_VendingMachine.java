/*
MICP Summer 2017
Author: Cindy Chen
Language: Java
*/

/*
Problem description:
Design a vending machine.

Clarify:
No more restrictions, just design a general vending machine we are familiar with.

Use case list:

select item and get price
accept bills/coins
dispense items purchased and return change
refund when cancelling the request
Possible exceptions:

Sold out
Not fully paid
Not enough changes
Design pattern:
Someone proposed state design pattern, but I am not familiar with it. Refer to this post.
*/
/*
1. Talk:
What is it? General vending machine
Who's going to use it? Buyer
When will it be used? Anytime
Where will it be used? Any place
Why will it be used? Automatic sales
How will it be used? User request -> Sell product automaticly

Expected behavior of user: 
	Request (selecte item/pay)
	Cancel request

Expected behavior of vending machine:
	Handle user request(get item price, accept bill, dispense item, return changes, refund etc.)
	Check stock
	Add stock
	Add changes

2. Examples
Test Code										Behavior
VendingMachine vm = new VendingMachine();		Create a new vending machine with unique id;
vm.addStock(item, amount);						Add a certain amount of items into item list;
vm.getRequest(request);							User send a request(with a selected item)
vm.cancelRequest();								Cancel current user request;

3. Brute Force
Create one class: VendingMachine, which contains all the state and behavior

4. Optimize 
See the attached diagram

5. Walk through
Can this handle all the user requests? Yes.

6. Implementation
*/

/*****************Item******************/
public class Item {
	public String itemId;
	public String name;
	public double price;
	public int amount;

	public Item(String itemId, String name) {
		this.itemId = itemId;
		this.name = name;
		this.price = 0.0;
		this.amount = 0;
	}

	public Item(String itemId, String name, double price, int amount) {
		this.itemId = itemId;
		this.name = name;
		this.price = price;
		this.amount = amount;
	}

	public void updateAmount(int amount) {
		this.amount += amount;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
/*****************User Request******************/
public class UserRequest {
	public Item selectedItem;
	public double billPaid;

	public UserRequest(Item selectedItem) {
		this.selectedItem = selectedItem;
		this.billPaid = 0.0;
	}

	public UserRequest(Item selectedItem, double billPaid) {
		this.selectedItem = selectedItem;
		this.billPaid = billPaid;
	}

	public void pay(Currency currency) {
		this.billPaid += currency.value;
	}
}
/*****************Currency******************/
public class Currency {
	public double value;
	public String type;
	
	public Currency(double value, String type) {
		this.value = value;
		this.type = type;
	}
}

public class Bill extends Currency {
	public Bill(double value) {
		super(value, "Bill");
	}

	public void setValue(double value) {
		this.value = value;
	}
}

public class Coin extends Currency {
	public Coin(double value) {
		super(value, "Coin");
	}
}
/*****************Vending Machine******************/
public class SuperVendingMachine {
	private static int machineCount;
	protected String id;
	protected double changes;
	protected double sales;
	protected Map<String, Item> stock;
	protected UserRequest userRequest;

	public SuperVendingMachine() {
		this.machineCount++;
		this.changes = 0.0;
		this.sales = 0.0;
		this.stock = new HashMap<String, Item>();
		this.userRequest = null;
	}

	public int getMachineCount() {
		return this.machineCount;
	}

	public double getChanges() {
		return changes;
	}

	public void addChanges(double changes) {
		this.changes += changes;
	}

	public double getSales() {
		return sales;
	}

	public void addSales() {
		if (!validRequest()) {
			throw new Exception("No Vaild Request");
		}
		this.sales += userRequest.selectedItem.price;
	}

	public Map<String, Item> getStock() {
		return stock;
	} 

	public boolean inStock(Item item) {
		String itemId = item.id;
		//item dosen't exist
		if (!stock.containsKey(itemId)) {
			return false;
		}
		//item amount = 0
		int amount = stock.get(itemId).amount;
		if (amount == 0) {
			return false;
		}
		return true;
	}

	public void addStock(Item item) {
		String itemId = item.id;
		if (stock.containsKey(itemId)) {
			Item oldItem = stock.get(itemId);
			item.addMoreItem(oldItem.amount);
		}
		stock.put(itemId, item);
	}

	public boolean validRequest() {
		if (userRequest == null) {
			return false;
		}
		reutrn true;
	}

	public void receiveUserRequest(UerRequest userRequest) {
		this.userRequest = userRequest;
	}

	public void cancelUserRequest() {
		this.userRequest = null;
	}
}

public class VendingMachine extends SuperVendingMachine {

	public VendingMachine() {
		super();
		this.id = getMachineCount();
	}

	public double selectItemAndGetPrice(Item item) {
		UserRequest userRequest = new UserRequest(item);
		receiveUserRequest(userRequest);
		return getPrice(item);
	}

	public double getPrice(Item item) {
		if (!inStock(item)) {
			throw new Exception("Sold Out");
		}

		String itemId = item.id;
		double price = stock.get(itemId).price;
		return price;
	}

	public void acceptBillsOrCoins(Currency currency) {
		if (!validRequest()) {
			throw new Exception("Please Select Item");
		}
		Item selectedItem = userRequest.selectedItem;
		if (!inStock(selectedItem)) {
			refund();
			throw new Exception("Sold Out");
		}
		userRequest.pay(currency);
		if (!fullyPaid()) {
			throw new Exception("Payment Insufficient");
		}
		if (!enoughChanges()) {
			refund();
			throw new Exception("No Enough Changes");
		}
		//can sell item successfully
		deal();
	}

	public void deal() {
		addSales();
		returnChanges();
		dispenseItem();
		cancelUserRequest();
	}

	public double refund() {
		if (!validRequest()) {
			throw new Exception("No Vaild Request");
		}
		double refund = userRequest.billPaid; 
		cancelUserRequest();
		return refund;
	}

	public boolean fullyPaid() {
		if (!validRequest()) {
			return false;
		}
		return userRequest.billPaid >= userRequest.selectedItem.price;
	}

	public boolean enoughChanges() {
		if (!validRequest()) {
			return false;
		}
		int changesNeeded = userRequest.billPaid - userRequest.selectedItem.price;
		return changesNeeded <= changes;
	}

	public double returnChanges() {
		if (!validRequest()) {
			throw new Exception("No Vaild Request");
		}
		double paid = userRequest.billPaid;
		double price = userRequest.selectedItem.price;
		double changesNeed = paid - price;
		this.changes -= changesNeed;
		return changesNeed;
	}

	public Item dispenseItem() {
		if (!validRequest()) {
			throw new Exception("No Vaild Request");
		}
		Item selectedItemId = userRequest.selectedItem.id;
		stock.get(selectedItemId).updateAmount(-1);
		return userRequest.selectedItem;
	}

}





