package lyg.tool.jdkcheck;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;


public class PhaserTest1 {
	public static void main(String[] args) {  
		CountDownLatch latch = new CountDownLatch(1);
//        Phaser phaser = new Phaser(1); //此处可使用CountDownLatch(1)  
        for(int i=0; i<1; i++) {  
            new MyThread((char)(97+i), latch).start();  
        }  
        try {  
            TimeUnit.SECONDS.sleep(3);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
        latch.countDown();
//        phaser.arrive();  //此处可使用latch.countDown()  
    } 
	
	static class MyThread extends Thread {  
		private char c;  
		private CountDownLatch latch;  
		
		public MyThread(char c, CountDownLatch latch) {  
			this.c = c;  
			this.latch = latch;  
		}  
		
		@Override  
		public void run() {  
			try {
				this.latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//        phaser.awaitAdvance(phaser.getPhase()); //此处可使用latch.await()  
			for(int i=0; i<100; i++) {  
				System.out.print(c+" ");  
				if(i % 10 == 9) {  
					System.out.println();  
				}  
			}  
		}  
	}  
}
