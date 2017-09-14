package lyg.tool.lock;

import java.util.concurrent.CyclicBarrier;
/*
 * 和CountDownLatch相似，也是等待某些线程都做完以后再执行。与CountDownLatch区别在于这个计数器可以反复使用。比如，假设我们将计数器设置为10。那么凑齐第一批1 0个线程后，计数器就会归零，然后接着凑齐下一批10个线程
 */
public class CyclicBarrierT implements Runnable
{
	private String soldier;
	private final CyclicBarrier cyclic;

	public CyclicBarrierT(String soldier, CyclicBarrier cyclic)
	{
		this.soldier = soldier;
		this.cyclic = cyclic;
	}

	@Override
	public void run() {
		try {
			//等待所有士兵到齐
			cyclic.await();
			dowork();
			//等待所有士兵完成工作
			cyclic.await();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void dowork() {
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(soldier + ": done");
	}

	public static class BarrierRun implements Runnable {

		boolean flag;
		int n;

		public BarrierRun(boolean flag, int n) {
			super();
			this.flag = flag;
			this.n = n;
		}

		@Override
		public void run() {
			if (flag) {
				System.out.println(n + "个任务完成");
			} else {
				System.out.println(n + "个集合完成");
				flag = true;
			}
		}

	}

	public static void main(String[] args) {
		final int n = 10;
		Thread[] threads = new Thread[n];
		boolean flag = false;
		CyclicBarrier barrier = new CyclicBarrier(n, new BarrierRun(flag, n));
		System.out.println("集合");
		for (int i = 0; i < n; i++) {
			System.out.println(i + "报道");
			threads[i] = new Thread(new CyclicBarrierT("士兵" + i, barrier));
			threads[i].start();
		}
	}

}