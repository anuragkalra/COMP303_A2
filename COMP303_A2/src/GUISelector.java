import java.util.concurrent.locks.ReentrantLock;

/**
 * Selects a specified seat in the airline through the UI
 * @author Anurag Kalra
 */
public class GUISelector implements Runnable{
	private final int id;
	private Airplane airplane;
	private final ReentrantLock lock = new ReentrantLock();
	
	/**
	 * Constructor for the GUISelector.
	 * @param aId the ID that is saved with the seat reservation.
	 * @param aAirplane the Airplane object that the seat corresponds to.
	 */
	public GUISelector(int aId, Airplane aAirplane){
		id = aId;
		airplane = aAirplane;
	}
	
	/**
	 * Updates seats in plane given input from the UI
	 */
	public void run() {
		lock.lock();
		try{
			while(!airplane.isFull()){	//airplane is not full
				if(airplane.getIsClicked()){	//dont start this until the button is clicked
					Thread.sleep(Booking.DELAY);
					airplane.toggleSeat(airplane.getRowMan(), airplane.getColMan(), this.id);
				}
			}
		}catch(InterruptedException e){
			Thread.currentThread().interrupt();
		}finally{
			lock.unlock();
		}

	}
}
