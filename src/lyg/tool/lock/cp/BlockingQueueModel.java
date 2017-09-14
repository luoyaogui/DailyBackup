package lyg.tool.lock.cp;
/*
BlockingQueue是JDK5.0的新增内容，它是一个已经在内部实现了同步的队列，实现方式采用的是我们第2种await() / signal()方法。它可以在生成对象时指定容量大小。它用于阻塞操作的是put()和take()方法。

put()方法：类似于我们上面的生产者线程，容量达到最大时，自动阻塞。

take()方法：类似于我们上面的消费者线程，容量为0时，自动阻塞。

其实这个BlockingQueue比较难用代码来演示，因为put()与take()方法无法与输出语句保证同步，当然你可以自己去实现 BlockingQueue（BlockingQueue是用await()/signal() 实现的)。所以在输出结果上你会发现不匹配。
例如：当缓冲区已满，生产者在put()操作时，put()内部调用了await()方法，放弃了线程的执行，然后消费者线程执行，调用take()方法，take()内部调用了signal()方法，通知生产者线程可以执行，致使在消费者的println()还没运行的情况下生产者的println()先被执行，所以有了输出不匹配的情况。

对于BlockingQueue大家可以放心使用，这可不是它的问题，只是在它和别的对象之间的同步有问题。
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueModel {
	private static Integer count = 0;
	final BlockingQueue<Integer> bq = new ArrayBlockingQueue<Integer>(10);
	class Producer implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					bq.put(1);
					count++;
					System.out.println(Thread.currentThread().getName()
							+ "生产者生产，目前总共有" + count);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
					e1.printStackTrace();
				}
				try {
					bq.take();
					count--;
					System.out.println(Thread.currentThread().getName()
							+ "消费者消费，目前总共有" + count);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) throws Exception {
		BlockingQueueModel hosee = new BlockingQueueModel();
		new Thread(hosee.new Producer()).start();
		new Thread(hosee.new Consumer()).start();
		new Thread(hosee.new Producer()).start();
		new Thread(hosee.new Consumer()).start();

		new Thread(hosee.new Producer()).start();
		new Thread(hosee.new Consumer()).start();
		new Thread(hosee.new Producer()).start();
		new Thread(hosee.new Consumer()).start();
	}

}