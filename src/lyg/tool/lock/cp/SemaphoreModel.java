package lyg.tool.lock.cp;
/*
Semaphore 信号量，就是一个允许实现设置好的令牌。也许有1个，也许有10个或更多。  
谁拿到令牌(acquire)就可以去执行了，如果没有令牌则需要等待。  
执行完毕，一定要归还(release)令牌，否则令牌会被很快用光，别的线程就无法获得令牌而执行下去了。

注意notFull.acquire()与mutex.acquire()的位置不能互换，如果先得到互斥锁再发生等待，会造成死锁。
 */
import java.util.concurrent.Semaphore;

public class SemaphoreModel {
	
	int count = 0;
	final Semaphore notFull = new Semaphore(10);
	final Semaphore notEmpty = new Semaphore(0);
	final Semaphore mutex = new Semaphore(1);

	class Producer implements Runnable
	{
		@Override
		public void run()
		{
			for (int i = 0; i < 10; i++)
			{
				try
				{
					Thread.sleep(3000);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				try
				{
					notFull.acquire();//顺序不能颠倒，否则会造成死锁。
					mutex.acquire();
					count++;
					System.out.println(Thread.currentThread().getName()
							+ "生产者生产，目前总共有" + count);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					mutex.release();
					notEmpty.release();
				}

			}
		}
	}

	class Consumer implements Runnable
	{

		@Override
		public void run()
		{
			for (int i = 0; i < 10; i++)
			{
				try
				{
					Thread.sleep(3000);
				}
				catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
				try
				{
					notEmpty.acquire();//顺序不能颠倒，否则会造成死锁。
					mutex.acquire();
					count--;
					System.out.println(Thread.currentThread().getName()
							+ "消费者消费，目前总共有" + count);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					mutex.release();
					notFull.release();
				}

			}

		}

	}

	public static void main(String[] args) throws Exception
	{
		SemaphoreModel hosee = new SemaphoreModel();
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