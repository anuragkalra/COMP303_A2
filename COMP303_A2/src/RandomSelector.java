import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
/**
 * Selects a random seat in the airplane and updates with ID.
 * @author Anurag Kalra
 */
public class RandomSelector implements Runnable{
	private final int id;
	private Airplane airplane;
	private final ReentrantLock lock = new ReentrantLock();
	
	/**
	 * Constructor for RandomSelector
	 * @param aId the ID that is saved with the seat reservation.
	 * @param aAirplane the Airplane object that the seat corresponds to.
	 */
	public RandomSelector(int aId, Airplane aAirplane){
		id = aId;
		airplane = aAirplane;
	}
	
	/**
	 * Updates seats in the plan in a random manner, placing ID in seat position.
	 */
	public void run() {
		lock.lock();	
		try{
			while(!airplane.isFull()){	//airplane is not full
				Thread.sleep(Booking.DELAY);
				
				//selecting seat randomly
				int[] seat = generateSeat();	//random 2-tuple of row, column
				
				int row = seat[0];
				int col = seat[1];
				//System.out.println("row = " + row);
				//System.out.println("col = " + col);
				//
				
				airplane.toggleSeat(row, col, this.id);	//modify corresponding airplane with generated seat 2-tuple
			}
		}
		catch(InterruptedException e){
			Thread.currentThread().interrupt();
		}
		finally{
			lock.unlock();
		}
		
	}
	
	
	private int[] generateSeat(){
		Random rand = new Random();
		int row = rand.nextInt(Airplane.getRows());
		int col = rand.nextInt(Airplane.getColumns());
		int[] seat = {row, col};
		return seat;
	}
	
}
