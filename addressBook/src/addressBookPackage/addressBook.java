package addressBookPackage;

import java.sql.DriverManager;
import java.util.ArrayList; // Imports array list. 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class addressBook { // Creates address book class
	private ArrayList<address> addressContent = new ArrayList<address>(); // Creates specific array list that will be
																			// used as master list for program.
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	int currentSelection = -1; // Currect Selection set to defualt value.

	public addressBook() { // This address book function creates three stock contacts to initially populate
							// the address book.

		try {
			connectToDB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Connects to contactsdb database.
	public void connectToDB() throws Exception {
		try {
			// Step 1. This will load the MySQL driver. Each DB has its own driver.
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Step 2. Setup the connection with the DB
			connect = DriverManager.getConnection("jdbc:mysql://localhost/contactsdb?"
					+ "useLegacyDatetimeCode=false&serverTimezone=UTC" + "&user=root&password=t1mmy");
		} catch (Exception e) {
			throw e;
		}
	}

	// prints and formats all necessary data from contactsdb onto the console.
	public void printAddresses() { // Prints Entire address book.
		
		try {
			address addressToPrint = null;
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from contactsdb.contacts");
			while(resultSet.next()) {
				addressToPrint = new address(resultSet.getString("phone_number"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("address"));
				addressToPrint.printAddress();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void addAddress() { // Adds address to array list.
		address addedAddress = new address();
		try {
			// Use PreparedStatement when inserting with variables. It's very efficient.
			// Adds inputed address into contacts table.
			preparedStatement = connect.prepareStatement("insert into contactsdb.contacts"
					+ "(id, phone_number, first_name, last_name, address)" + "values (default, ?, ?, ?, ?)");
			preparedStatement.setString(1, addedAddress.phoneNumber);
			preparedStatement.setString(2, addedAddress.firstName);
			preparedStatement.setString(3, addedAddress.lastName);
			preparedStatement.setString(4, addedAddress.address);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void search() { // Search function finds existing contact based on user input. If user input
							// contacts first name, last name, OR phone number of contact, function will
							// display that contact.
		System.out.print("Enter first name, last name, or phone number of your contact : ");
		String userInput = addressManager.inputReader.nextLine();
		for (int i = 0; i < addressContent.size(); i++) {
			if (addressContent.get(i).phoneNumber.compareTo(userInput) == 0 // If statement compares each element (first
																			// name, last name, and phone number) of
																			// each item in array list to user input.
					|| addressContent.get(i).firstName.compareTo(userInput) == 0
					|| addressContent.get(i).firstName.compareTo(userInput) == 0
					|| addressContent.get(i).lastName.compareTo(userInput) == 0) {
				currentSelection = i; // Search sets currentSelection value to contact whose element matched the user
										// input.
				System.out.print("CONTACT FOUND \n");
				addressContent.get(i).printAddress();
				return;
			}
		}
		System.out.print("ERROR: CONTACT NOT FOUND.");
	}

	public void deleteCurrentSelection() { // Function deletes currentSelection contact from Array list.
											// currentSelection contact is determined by using search function.
		if (currentSelection == -1) {
			System.out.print("ERROR: Please use search (option 5) to find a contact to delete.");
		} else {
			addressContent.get(currentSelection).printAddress();
			addressContent.remove(currentSelection);
			System.out.print("ABOVE CONTACT HAS BEEN DELETED\n");
			currentSelection = -1;
		}
	}

	public void bubbleSort() { // Bubblesort function is used to sort array list by contact last name.
		// number of passes
		for (int i = 1; i < addressContent.size(); i++) {
			// comparison loop
			for (int j = 0; j < addressContent.size() - 1; j++) {
				if (addressContent.get(j).lastName.compareTo(addressContent.get(j + 1).lastName) > 0) {
					address temp = addressContent.get(j + 1);
					addressContent.set(j + 1, addressContent.get(j));
					addressContent.set(j, temp);
				}
			}
		}
		currentSelection = -1;
	}

	public void updateContact() { // updateContact function updates individual elements of already existing
									// contacts.
		if (currentSelection == -1) {
			System.out.print("ERROR: Please use search (option 5) to find a contact to update.");
		} else {

			// Function goes through each individual element of contact. If element does not
			// need to be changed, user hits ENTER. If element does need to be changed, the
			// user instead enters the new value for stated element.
			addressContent.get(currentSelection).printAddress();
			System.out.print("\nAbove is current contact information\n");
			System.out.println("Current phone number is : " + addressContent.get(currentSelection).phoneNumber);
			System.out.print("Press ENTER if correct, or input new phone number : ");
			String newInput = addressManager.inputReader.nextLine();
			if (newInput.length() > 0) {
				addressContent.get(currentSelection).phoneNumber = newInput;
			}
			System.out.println("Current first name is : " + addressContent.get(currentSelection).firstName);
			System.out.print("Press ENTER if correct, or input new first name : ");
			newInput = addressManager.inputReader.nextLine();
			if (newInput.length() > 0) {
				addressContent.get(currentSelection).firstName = newInput;
			}
			System.out.println("Current last name is : " + addressContent.get(currentSelection).lastName);
			System.out.print("Press ENTER if correct, or input new last name : ");
			newInput = addressManager.inputReader.nextLine();
			if (newInput.length() > 0) {
				addressContent.get(currentSelection).lastName = newInput;
			}
			System.out.println("Current address is : " + addressContent.get(currentSelection).address);
			System.out.print("Press ENTER if correct, or input new address name : ");
			newInput = addressManager.inputReader.nextLine();
			if (newInput.length() > 0) {
				addressContent.get(currentSelection).address = newInput;

			}
			bubbleSort();
		}
	}

	public void close() {
		try {
			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
