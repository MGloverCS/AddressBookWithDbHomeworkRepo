package addressBookPackage;

import java.util.Scanner; //Imports scanner in order for code to accept inputs.

public class address { // Creates address class

	// Sets each element of contact as string values.
	public String phoneNumber;
	public String firstName;
	public String lastName;
	public String address;

	// Constructor #1 prompts and records inputs from user for each element of a contact.
	public address() {
		System.out.print("Enter phone number : ");
		phoneNumber = addressManager.inputReader.nextLine();
		System.out.printf("Enter first name : ");
		firstName = addressManager.inputReader.nextLine();
		System.out.printf("Enter last name : ");
		lastName = addressManager.inputReader.nextLine();
		System.out.printf("Enter address : ");
		address = addressManager.inputReader.nextLine();
	}

	// Constructor #2 Accepts initialization as parameter
	public address(String argPhoneNumber, String argFirstName, String argLastName, String argAddress) {
		phoneNumber = argPhoneNumber;
		firstName = argFirstName;
		lastName = argLastName;
		address = argAddress;
	}

	// Print address function
	public void printAddress() {
		System.out.println("Phone Number : " + phoneNumber);
		System.out.println("First Name : " + firstName);
		System.out.println("Last Name : " + lastName);
		System.out.println("Address : " + address + "\n");
	}
}
