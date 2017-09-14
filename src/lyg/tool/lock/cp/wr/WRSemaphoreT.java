package lyg.tool.lock.cp.wr;

import java.util.Random;
import java.util.concurrent.Semaphore;
/*
单纯使用信号量不能解决读者与写者问题，必须引入计数器count（可以用CountDownLatch代替 ）对读进程计数； count与wmutex结合使用，使读读能同时进行，读写排斥。count为0时表示读进程开始，此时写进程阻塞（wmutex被读进程获取），当count不为0时，表示有多个读进程，就不用操作 wmutex了，因为第一个读进程已经获得了 wmutex。count表示有多少个读进程在读，每次有一个就+1，读完了-1，当count==0时，表示读进程都结束了。此时 wmutex释放，写进程才有机会获得wmutex。为了使读进程不要一直占有 wmutex，最好让读进程sleep一下，让写进程有机会获得wmutex，使效果更明显。
 */
public class WRSemaphoreT
{
	public static void main(String[] args)
	{
		final Queue3 q3 = new Queue3();
		for (int i = 0; i < 3; i++)
		{
			new Thread()
			{
				public void run()
				{
					while (true)
					{
						try
						{
							Thread.sleep((long) (Math.random() * 1000));
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
						q3.get();
					}
				}
			}.start();
			
		}
		for (int i = 0; i < 3; i++)
		{
			new Thread()
			{
				public void run()
				{
					while (true)
					{
						try
						{
							Thread.sleep((long) (Math.random() * 1000));
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
						q3.put(new Random().nextInt(10000));
					}
				}
			}.start();
		}
	}
	static class Queue3 {
		private Object data = null;// 共享数据，只能有一个线程能写该数据，但可以有多个线程同时读该数据。
		private Semaphore wmutex = new Semaphore(1);
		private Semaphore rmutex = new Semaphore(2);
		private int count = 0;

		public void get()
		{
			try
			{
				rmutex.acquire();
				if (count == 0)
					wmutex.acquire();// 当第一读进程欲读数据库时，阻止写进程写
				count++;
				System.out.println(Thread.currentThread().getName()
						+ " be ready to read data!");
				try
				{
					Thread.sleep((long) (Math.random() * 1000));
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()
						+ "have read data :" + data);
				count--;
				if (count == 0)
					wmutex.release();
				rmutex.release();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		public void put(Object data)
		{
			try
			{
				wmutex.acquire();
				System.out.println(Thread.currentThread().getName()
						+ " be ready to write data!");
				try
				{
					Thread.sleep((long) (Math.random() * 1000));
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				this.data = data;
				System.out.println(Thread.currentThread().getName()
						+ " have write data: " + data);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				wmutex.release();
			}
		}
	}
}