package lyg.tool.lock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.ReentrantLock;

//普通的lock.lock()是不能响应中断的，lock.lockInterruptibly()能够响应中断。
public class Deadlock implements Runnable
{
	public static ReentrantLock lock1 = new ReentrantLock();
	public static ReentrantLock lock2 = new ReentrantLock();

	int lock;
	public Deadlock(int lock) {
		this.lock = lock;
	}

	public void run() {
		try {
			if (lock == 1) {
				lock1.lockInterruptibly();
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					// TODO: handle exception
				}
				lock2.lockInterruptibly();
			} else {
				lock2.lockInterruptibly();
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					// TODO: handle exception
				}
				lock1.lockInterruptibly();
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (lock1.isHeldByCurrentThread()) {
				lock1.unlock();
			}
			if (lock2.isHeldByCurrentThread()) {
				lock2.unlock();
			}
			System.out.println(Thread.currentThread().getId() + ":线程退出");
		}
	}

	public static void main(String[] args) throws InterruptedException
	{
		Deadlock t1 = new Deadlock(1);
		Deadlock t2 = new Deadlock(2);
		Thread thread1 = new Thread(t1);
		Thread thread2 = new Thread(t2);
		thread1.start();
		thread2.start();
		Thread.sleep(1000);
//		DLChecker.check();
	}

	static class DLChecker {
		private final static ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
		final static Runnable checker = new Runnable(){
			@Override
			public void run() {
				long[] deadlockedThreadIds = mbean.findDeadlockedThreads();
				if (deadlockedThreadIds != null) {
					ThreadInfo[] threadInfos = mbean.getThreadInfo(deadlockedThreadIds);
					for (Thread t : Thread.getAllStackTraces().keySet()) {
						for (int i = 0; i < threadInfos.length; i++) {
							if(t.getId() == threadInfos[i].getThreadId()) {
								t.interrupt();
							}
						}
					}
				}
				try{
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		};
		public static void check() {
			Thread t = new Thread(checker);
			t.setDaemon(true);
			t.start();
		}
	}
}
