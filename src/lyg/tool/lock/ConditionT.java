package lyg.tool.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*await()方法会使当前线程等待，同时释放当前锁，当其他线程中使用signal()时或者signalAll()方法时，线 程会重新获得锁并继续执行。或者当线程被中断时，也能跳出等待。这和Object.wait()方法很相似。
awaitUninterruptibly()方法与await()方法基本相同，但是它并不会再等待过程中响应中断。 singal()方法用于唤醒一个在等待中的线程。相对的singalAll()方法会唤醒所有在等待中的线程。这和Obejct.notify()方法很类似。*/
public class ConditionT implements Runnable {
	public static ReentrantLock lock = new ReentrantLock();
	public static Condition condition = lock.newCondition();

	@Override
	public void run() {
		try {
			lock.lock();
			condition.await();
			System.out.println("Thread is going on");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ConditionT t = new ConditionT();
		Thread thread = new Thread(t);
		thread.start();
		Thread.sleep(1000);
		
		lock.lock();
		condition.signal();
		lock.unlock();
	}

}