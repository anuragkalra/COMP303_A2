/**
 * 
 * @author Anurag Kalra
 * Selects a specified seat in the airline
 */
public class GUISelector implements Runnable{
	private final int id;
	private Airplane airplane;
	
	public GUISelector(int aId, Airplane aAirplane){
		id = aId;
		airplane = aAirplane;
	}
	
	public void run() {
		try{
			while(!this.airplane.isFull()){	//airplane is not full
				this.airplane.toggleSeat(1, 1, this.id);	//modify corresponding airplane with generated seat 2-tuple
				Thread.sleep(Booking.DELAY);
			}
		}catch(InterruptedException e){
			Thread.currentThread().interrupt();
		}
	}
}
