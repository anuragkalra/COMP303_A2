/**
 * A class to facilitate all operations of the airplane and seat selection.
 * @author Anurag Kalra
 */
public class Booking {
	public final static int DELAY = 1000;	//THE AMOUNT OF SLEEP TIME FOR ALL THREADS (1000 MS BY DEFAULT).
	
	public static void main(String[] args){
		Airplane a = new Airplane();	//UI startup
		
		Runnable r1 = new RandomSelector(1, a);	//random1
		Runnable r2 = new RandomSelector(2, a);	//random2
		Runnable r3 = new GUISelector(3, a);	//manual1
		
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		Thread t3 = new Thread(r3);
		
		t1.start();	//start random1
		t2.start();	//start random2
		t3.start();	//start manual1
	}
}
