import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author Anurag Kalra
 * Selects a specified seat in the airline
 */
public class GUISelector implements Runnable{
	private final int id;
	private Airplane airplane;
	private final ReentrantLock lock = new ReentrantLock();
	
	public GUISelector(int aId, Airplane aAirplane){
		id = aId;
		airplane = aAirplane;
	}
	
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
