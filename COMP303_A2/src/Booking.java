import javax.swing.*;
import java.util.*;
/**
 * 
 * @author Anurag Kalra
 *
 */
public class Booking {
	public static void main(String[] args){
		Airplane a = new Airplane();
		
		Runnable r1 = new GUISelector();
		Runnable r2 = new RandomSelector();
		Runnable r3 = new RandomSelector();
		
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		Thread t3 = new Thread(r3);
		
		t1.start();
		t2.start();
		t3.start();
	}
}
