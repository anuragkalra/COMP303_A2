import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * 
 * @author Anurag Kalra
 * Stores info and data structure elements of the Airplane object
 */
public class Airplane {
	private final int ROWS = 20;
	private final int COLUMNS = 3;
	
	private int[][] seats = new int[ROWS][COLUMNS];

	public Airplane(){
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		
		//TABLE PANEL
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new FlowLayout());
		
		//TABLE INFO
		JTable table = new JTable(ROWS, COLUMNS);
		this.initTable(table);
		
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
		
		
		//CONFIRMATION BUTTON
		JButton confirmButton = new JButton("Confirm seat");
		
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
		
		table.setValueAt(2, 2, 0);
		
		frame.setVisible(true);
	}
	
	/**
	 * Fills up a JTable with 0's to signify and empty (new) plane.
	 * @param t The table to fill up.
	 */
	private void initTable(JTable t){
		for(int col = 0; col < this.COLUMNS; col++){
			for(int row = 0; row < this.ROWS; row++){
				t.setValueAt(seats[row][col], row, col);
			}
		}
	}
	
	/**
	 * Modify a seat on the plane to contain the ID of the thing that selects it. 
	 * @param row
	 * @param column
	 */
	public synchronized void toggleSeat(int row, int column){
		
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
