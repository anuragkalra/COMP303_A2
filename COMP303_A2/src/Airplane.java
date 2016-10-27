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
	private final static int ROWS = 50;	//NUMBER OF ROWS IN THE AIRPLANE
	private final static int COLUMNS = 4;	//NUMBER OF SEATS PER ROW IN AIRPLANE
	
	private static int[][] seats = new int[ROWS][COLUMNS];	//DATA STRUCTURE TO REPRESENT THE SEATS
	private JTable table = new JTable(ROWS, COLUMNS){
		public boolean isCellEditable(int row, int column){
			return false;
		}
	};
	
	private boolean isClicked = false;	//TOGGLED WHEN THE CONFIRM BUTTON HAS BEEN CLICKED 
	
	private int rowMan;	//ROW SELECTED MANUALLY
	private int colMan;	//COLUMN SELECTED MANUALLY
	
	/**
	 * Constructor for the Airplane class and builds GUI.
	 */
	public Airplane(){
		
		//FRAME BODY
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setTitle("Anurag Airlines");
		
		//TABLE PANEL
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new FlowLayout());
		
		//TABLE GRID COLOR SETTING
		table.setGridColor(Color.BLACK);
		
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
		
		//SELECTION MESSAGE PANEL
		JPanel confirmMessagePanel = new JPanel();
		confirmMessagePanel.setLayout(new FlowLayout());
		
		//SELECTION MESSAGE
		JLabel confirmMessage = new JLabel("", SwingConstants.CENTER);
		
		//BUILD SELECTION MESSAGE PANEL
		confirmMessagePanel.add(confirmMessage);
		
		//CONFIRMATION BUTTON + action listener implementation
		JButton confirmButton = new JButton("Confirm seat");
		confirmButton.addActionListener(new
			ActionListener(){
				public void actionPerformed(ActionEvent event){
					isClicked = true;
					String rowText = rowSelect.getText();
					String colText = colSelect.getText();
					
					if(!isNumeric(rowText) || !isNumeric(colText)){
						System.out.println(">Improperly formatted");
						confirmMessage.setText("Improperly formatted");
						rowMan = -1;
						colMan = -1;
						return;
					}
					
					int rowSelection = Integer.parseInt(rowText);
					int colSelection = Integer.parseInt(colText);
					
					
					if(rowSelection >= ROWS || colSelection >= COLUMNS){	//OUT OF BOUNDS
						System.out.println(">Illegal Seat");
						confirmMessage.setText("Illegal Seat");
						rowMan = -1;	//ERROR CASE: put in out of bounds
						colMan = -1;
						return;
					}
					if(!seatAvailable(rowSelection, colSelection)){	//IF THE SEAT IS TAKEN	
						System.out.println(">Seat Taken");
						confirmMessage.setText("Seat Taken");
						rowMan = -1;	//ERROR CASE: put in out of bounds
						colMan = -1;
						return;
					}
					else{	//OKAY
						System.out.println(">Valid");
						confirmMessage.setText("Confirmed");
						rowMan = rowSelection;
						colMan = colSelection;
						return;
					}
					
				}
			});
		
		
		//BUILD SELECTION PANEL
		selectionPanel.add(rowPanel);
		selectionPanel.add(columnPanel);
		selectionPanel.add(confirmButton);
		selectionPanel.add(confirmMessage);
		
		//BUILD ENTIRE FRAME (tablePanel + selectionPanel)
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
	
	/**
	 * Getter for rowMan field
	 * @return rowMan value
	 */
	public int getRowMan(){
		return rowMan;
	}
	
	/**
	 * getter for colMan field.
	 * @return colMan value
	 */
	public int getColMan(){
		return colMan;
	}
	
	/**
	 * getter for isClicked variable
	 * @return true if isClicked is true, false otherwise
	 */
	public boolean getIsClicked(){
		return isClicked;
	}
	

	/**
	 * Notifies whether the airplane is full or not.
	 * @return true if it finds a 0 inside the seating chart (empty seat).
	 */
	public synchronized boolean isFull(){
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
	private synchronized void updateTable(int row, int col, int id, JTable t){
		t.setValueAt(id, row, col);
	}
	
	/**
	 * Modify a seat on the plane to contain the ID of the thing that selects it. 
	 * @param row
	 * @param column
	 */
	public synchronized void toggleSeat(int row, int column, int threadID) throws InterruptedException{
		if(!(row >= 0 && row < ROWS) || !(column >= 0 && column < COLUMNS)){	//IF ILLEGAL
			return;
		}
		if(seatAvailable(row, column)){	//if the seat is available, toggle it
			seats[row][column] = threadID;
			updateTable(row, column, threadID, table);
		}
	}
	
	/**
	 * Helper method to validate whether user input is numeric or not
	 * @param str the String to validate.
	 * @return true if is numeric, false otherwise.
	 */
	private static boolean isNumeric(String str){
		try{  
			int d = Integer.parseInt(str);  
		}
		catch(NumberFormatException nfe){  
			return false;  
		}
		return true;  
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
