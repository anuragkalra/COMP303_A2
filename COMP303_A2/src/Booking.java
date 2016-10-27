import javax.swing.*;
import java.util.*;
/**
 * 
 * @author Anurag Kalra
 *
 */
public class Booking {
	public final static int DELAY = 1000;
	
	public static void main(String[] args){
		Airplane a = new Airplane();	//keeps GUI
		
		Runnable r1 = new RandomSelector(1, a);
		Runnable r2 = new RandomSelector(2, a);
		//Runnable r3 = new GUISelector(3, a);
		
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		//Thread t3 = new Thread(r3);
		
		t1.start();	//start random1
		t2.start();	//start random2
		//t3.start();	//start manual1
	}
}
