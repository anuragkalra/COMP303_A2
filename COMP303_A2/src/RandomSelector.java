import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 
 * @author Anurag Kalra
 * Selects a random seat in the airline.
 * Modifies airplane data structure.
 */
public class RandomSelector implements Runnable{
	private final int id;
	private Airplane airplane;
	private final ReentrantLock lock = new ReentrantLock();
	
	public RandomSelector(int aId, Airplane aAirplane){
		id = aId;
		airplane = aAirplane;
	}
	
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
