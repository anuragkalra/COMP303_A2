import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * 
 * @author Anurag Kalra
 * Stores info and data structure elements of the Airplane object
 */
public class Airplane {
	private final static int ROWS = 5;
	private final static int COLUMNS = 3;
	
	private static int[][] seats = new int[ROWS][COLUMNS];
	private JTable table = new JTable(ROWS, COLUMNS);
	
	private int rowMan;
	private int colMan;
	
	/**
	 * Constructor for the Airplane class and builds GUI.
	 */
	public Airplane(){
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		
		//TABLE PANEL
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new FlowLayout());
		
		table.setGridColor(Color.GREEN);
		table.setBackground(Color.ORANGE);
		
		//BUILD TABLE PANEL
		tablePanel.add(table);
		
		//SEAT SELECTION PANEL
		JPanel selectionPanel = new JPanel();
		selectionPanel.setLayout(new GridLayout(4, 1));
		
		
		//ROW PANEL
		JPanel rowPanel = new JPanel();
		rowPanel.setLayout(new FlowLayout());
		//ROW LABEL + TEXTFIELD
		JLabel rowLabel = new JLabel("Select row:"); 
		JTextField rowSelect = new JTextField(2);
		
		//BUILD ROW PANEL
		rowPanel.add(rowLabel);
		rowPanel.add(rowSelect);
		
		
		//COLUMN PANEL
		JPanel columnPanel = new JPanel();
		columnPanel.setLayout(new FlowLayout());
		//COLUMN LABEL + TEXTFIELD
		JLabel colLabel = new JLabel("Select column:");
		JTextField colSelect = new JTextField(2);
		
		//BUILD COLUMN PANEL
		columnPanel.add(colLabel);
		columnPanel.add(colSelect);
		
		
		//CONFIRMATION BUTTON + action listener
		JButton confirmButton = new JButton("Confirm seat");
		confirmButton.addActionListener(new
			ActionListener(){
				public void actionPerformed(ActionEvent event){
					String rowText = rowSelect.getText();
					String colText = colSelect.getText();
					int rowSelection = Integer.parseInt(rowText);
					int colSelection = Integer.parseInt(colText);
					
					rowMan = rowSelection;
					colMan = colSelection;
				}
			});
		
		
		//SELECTION MESSAGE
		JLabel confirmMessage = new JLabel("<message>");
		
		//BUILD SELECTION PANEL
		selectionPanel.add(rowPanel);
		selectionPanel.add(columnPanel);
		selectionPanel.add(confirmButton);
		selectionPanel.add(confirmMessage);
		
		//BUILD ENTIRE FRAME
		frame.add(tablePanel);
		frame.add(selectionPanel);
		
		frame.setSize(200, 500);
		frame.setLocationRelativeTo(null);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		
		frame.setVisible(true);
	}
	
	/**
	 * Getter for COLUMNS field.
	 * @return number of columns in plane.
	 */
	public static int getColumns(){
		return COLUMNS;
	}
	
	/**
	 * Getter for ROWS field
	 * @return number of rows in plane.
	 */
	public static int getRows(){
		return ROWS;
	}
	
	public int getRowMan(){
		return rowMan;
	}
	
	public int getColMan(){
		return colMan;
	}
	
	/**
	 * Notifies whether the airplane is full or not.
	 * @return true if it finds a 0 inside the seating chart (empty seat).
	 */
	public boolean isFull(){
		boolean isFull = true;
		for(int row = 0; row < ROWS; row++){
			for(int col = 0; col < COLUMNS; col++){
				if(seats[row][col] == 0){
					return false;
				}
			}
		}
		return isFull;
	}
	
	/**
	 * Fills up a JTable with 0's to signify and empty (new) plane.
	 * @param t The table to fill up.
	 */
	private void updateTable(int row, int col, int id, JTable t){
		t.setValueAt(id, row, col);
	}
	
	/**
	 * Modify a seat on the plane to contain the ID of the thing that selects it. 
	 * @param row
	 * @param column
	 */
	public synchronized void toggleSeat(int row, int column, int threadID) throws InterruptedException{
		if(seatAvailable(row, column)){
			seats[row][column] = threadID;
			updateTable(row, column, threadID, table);
		}
	}
	
	/**
	 * Tells if seat is available
	 * @param row
	 * @param column
	 * @return true if the seat is empty, false if not.
	 */
	private synchronized boolean seatAvailable(int row, int column){
		if(seats[row][column] != 0){	//if the seat is booked return false
			return false;
		}else{
			return true;	//if the seat is empty return true
		}
	}
}
