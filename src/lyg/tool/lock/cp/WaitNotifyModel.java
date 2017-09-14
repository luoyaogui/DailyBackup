package lyg.tool.lock.cp;
/*
wait() / nofity()方法是基类Object的两个方法，也就意味着所有Java类都会拥有这两个方法，这样，我们就可以为任何对象实现同步机制。

wait()方法：当缓冲区已满/空时，生产者/消费者线程停止自己的执行，放弃锁，使自己处于等等状态，让其他线程执行。

notify()方法：当生产者/消费者向缓冲区放入/取出一个产品时，向其他等待的线程发出可执行的通知，同时放弃锁，使自己处于等待状态。
 */
public class WaitNotifyModel {
	private static Integer count = 0; 
	private final Integer FULL = 10; 
	private static String LOCK = "LOCK"; 

	class Producer implements Runnable { 
		@Override public void run() { 
			for (int i = 0; i < 10; i++) { 
				try { 
					Thread.sleep(3000); 
				} catch (Exception e) { 
//					e.printStackTrace(); 
				} 
				synchronized (LOCK) { 
					while (count == FULL) { 
						try { LOCK.wait(); 
						} catch (Exception e) { 
//							e.printStackTrace(); 
						} 
					}
					count++; 
					System.out.println(Thread.currentThread().getName() + "生产者生产，目前总共有" + count); 
					LOCK.notifyAll(); 
				}
			} 
		}
	}

	class Consumer implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				try { 
					Thread.sleep(3000);
				} catch (InterruptedException e1) { 
//					e1.printStackTrace(); 
				}
				synchronized (LOCK) {
					while (count == 0) { 
						try { 
							LOCK.wait(); 
						} catch (Exception e) {
							// TODO: handle exception e.printStackTrace(); 
						}
					}
					count--;
					System.out.println(Thread.currentThread().getName() + "消费者消费，目前总共有" + count); 
					LOCK.notifyAll();
				}
			} 
		}
	}
	public static void main(String[] args) throws Exception { 
		WaitNotifyModel hosee = new WaitNotifyModel(); 
		new Thread(hosee.new Producer()).start(); 
		new Thread(hosee.new Consumer()).start(); 
		new Thread(hosee.new Producer()).start(); 
		new Thread(hosee.new Consumer()).start(); 
		new Thread(hosee.new Producer()).start(); 
		new Thread(hosee.new Consumer()).start(); 
		new Thread(hosee.new Producer()).start(); 
	}
}

