package edu.towson.cis.cosc442.project3.vendingmachine;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VendingMachineTest {
	
	VendingMachine VM;
	VendingMachineItem VMI;
	VendingMachineItem VMI2;
	
	@Before
	public void setUp() throws Exception {
		
		VM = new VendingMachine();
		VMI = new VendingMachineItem("Candy", 1.00);
		VMI2 = new VendingMachineItem("Chips", 2.00);
		
	}

	@After
	public void tearDown() throws Exception {
		//VM.removeItem("A");
		//VM.removeItem("B");
		
	}

	@Test // Test adding item to empty slot
	public void addItemTest() {
		
		VM.addItem(VMI, "A");
		
		assertEquals(VMI, VM.getItem("A"));
		
	}
	
	// Testing trying to add an item to an already occupied slot. First adds an item, then adds another item to same slot
	@Test
	public void addItemToOccupiedSlotTest() {
		boolean caught = false;
		VM.addItem(VMI, "A");
		
		
		try{
			VM.addItem(VMI2, "A");
		}
		catch (VendingMachineException exception) {
			caught = true;
		}
		
		assertTrue(caught);
		
	}
	
	// Tests adding an item to an invalid slot. Tries to add item to slot E
	@Test
	public void addItemInvalidSlotTest() {
		boolean caught = false;
		
		try{
			VM.addItem(VMI2, "E");
		}
		catch (VendingMachineException exception) {
			caught = true;
		}
		assertTrue(caught);
		
	}
	
	// Test removing item normally. First adding the item, then removing it, then trying to get the removed item
	@Test
	public void removeItemTest() {
		
		boolean caught = false;
		VM.addItem(VMI, "A");
		
		VM.removeItem("A");
		
		try {
			VM.getItem("A");
		}
		catch (VendingMachineException exception) {
			caught = true;
		}
		
		assertTrue(caught);
		
	}
	
	@Test // Testing removing an item from a slot that has no item in it, should throw an error
	public void removeItemEmptySlotTest() {
		
		boolean caught = false;
		
		try {
			VM.removeItem("A");
		}
		catch (VendingMachineException exception) {
			caught = true;
		}
		
		assertTrue(caught);
		
	}
	
	
	@Test // Testing ability to insert money into a machine with balance of 0. 
	public void insertMoneyTest() {
		
		VM.insertMoney(10.00);
		
		assertEquals(10.00, VM.getBalance(), 0);
		
		
	}
	
	@Test // Tests adding a negative amount to the balance, should throw error if correct
	public void insertNegativeAmountTest() {
		
		boolean caught = false;
		try { 
			VM.insertMoney(-1.00);
		}
		catch (VendingMachineException exception) {
			
			caught = true;
		}
		
		assertTrue(caught);
		
		
	}
	
	@Test // Test getBalance method after adding money multiple times. 
	public void testGetBalance() {
		
		
		VM.insertMoney(10.00);
		VM.insertMoney(5.00);
		
		assertEquals(15.00, VM.getBalance(), 0);
		
		
	}
	
	@Test // Test to make sure after purchasing an item that item is removed
		  // Adds item and balance, purchases item, then tries to getItem 
	public void testMakePurchaseItemRemoved() {
		
		boolean caught = false;
		VM.addItem(VMI, "A");
		
		VM.insertMoney(5.00);
		
		try {
			VM.makePurchase("A");
			VM.getItem("A");
		}
		catch (VendingMachineException e) {
			caught = true;
		}
		
		assertTrue(caught);
		
	}
	
	@Test // Tests that the balance is updated after purchasing an item. 
	public void makePurchaseBalanceTest() {
		VM.addItem(VMI, "A"); // Price of item A is 1.00
		
		VM.insertMoney(5.00);
		
		VM.makePurchase("A");
		
		assertEquals(4.00, VM.getBalance(), 0);
		
	}
	
	@Test // Testing to see if makePurchase will fail if balance is not high enough
		  // Add less than is required in balance, add item, try to make purchase
	public void makePurchaseBalanceLowTest() {
		
		
		VM.addItem(VMI, "A");
		
		VM.insertMoney(.50);
		
		boolean failed = VM.makePurchase("A");
		assertFalse(failed);
		
		
	}
	
	@Test //Testing that the return change method returns the correct amount of change
	public void getCorrectChangeTest() {
		
		VM.insertMoney(5.00);
		assertEquals(5.00, VM.returnChange(), 0);
		
	}
	
	@Test // Testing that the balance gets reset to 0 after returnChange is called
	public void balanceSetZeroTest() {
		
		VM.insertMoney(6.00);
		
		VM.returnChange();
		
		assertEquals(0.00, VM.getBalance(), 0 );
		
	}
	

	
	
	
	
	
	
	
	
	
	
	
}
