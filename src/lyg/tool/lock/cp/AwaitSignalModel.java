package lyg.tool.lock.cp;
/*
首先，我们先来看看await()/signal()与wait()/notify()的区别：

wait()和notify()必须在synchronized的代码块中使用 因为只有在获取当前对象的锁时才能进行这两个操作 否则会报异常 而await()和signal()一般与Lock()配合使用。
wait是Object的方法，而await只有部分类有，如Condition。
await()/signal()和新引入的锁定机制Lock直接挂钩，具有更大的灵活性。
那么为什么有了synchronized还要提出Lock呢？

1.2.1 对synchronized的改进

    synchronized并不完美，它有一些功能性的限制 —— 它无法中断一个正在等候获得锁的线程，也无法通过投票得到锁，如果不想等下去，也就没法得到锁。同步还要求锁的释放只能在与获得锁所在的堆栈帧相同的堆栈帧中进行，多数情况下，这没问题（而且与异常处理交互得很好），但是，确实存在一些非块结构的锁定更合适的情况。

1.2.2 ReentrantLock 类

java.util.concurrent.lock 中的 Lock 框架是锁定的一个抽象，它允许把锁定的实现作为 Java 类，而不是作为语言的特性来实现(更加面向对象)。这就为 Lock 的多种实现留下了空间，各种实现可能有不同的调度算法、性能特性或者锁定语义。 ReentrantLock 类实现了 Lock ，它拥有与 synchronized 相同的并发性和内存语义，但是添加了类似锁投票、定时锁等候和可中断锁等候的一些特性。此外，它还提供了在激烈争用情况下更佳的性能。（换句话说，当许多线程都想访问共享资源时，JVM 可以花更少的时候来调度线程，把更多时间用在执行线程上。）

reentrant 锁意味着什么呢？简单来说，它有一个与锁相关的获取计数器，如果拥有锁的某个线程再次得到锁，那么获取计数器就加1，然后锁需要被释放两次才能获得真正释放(重入锁)。这模仿了 synchronized 的语义；如果线程进入由线程已经拥有的监控器保护的 synchronized 块，就允许线程继续进行，当线程退出第二个（或者后续） synchronized 块的时候，不释放锁，只有线程退出它进入的监控器保护的第一个synchronized 块时，才释放锁。

简单解释下重入锁：


public class Child extends Father implements Runnable{
	final static Child child = new Child();//为了保证锁唯一
	public static void main(String[] args) {
		for (int i = 0; i < 50; i++) {
			new Thread(child).start();
		}
	}

	public synchronized void doSomething() {
		System.out.println("1child.doSomething()");
		doAnotherThing(); // 调用自己类中其他的synchronized方法
	}

	private synchronized void doAnotherThing() {
		super.doSomething(); // 调用父类的synchronized方法
		System.out.println("3child.doAnotherThing()");
	}

	@Override
	public void run() {
		child.doSomething();
	}
}
class Father {
	public synchronized void doSomething() {
		System.out.println("2father.doSomething()");
	}
}
上述代码的锁都是child对象，当执行child.doSomething时，该线程获得child对象的锁，在doSomething方法内执行doAnotherThing时再次请求child对象的锁，因为synchronized是重入锁，所以可以得到该锁，继续在doAnotherThing里执行父类的doSomething方法时第三次请求child对象的锁，同理可得到，如果不是重入锁的话，那这后面这两次请求锁将会被一直阻塞，从而导致死锁。
在查看下面代码示例时，可以看到 Lock 和 synchronized 有一点明显的区别 —— lock 必须在 finally 块中释放。否则，如果受保护的代码将抛出异常，锁就有可能永远得不到释放！这一点区别看起来可能没什么，但是实际上，它极为重要。忘记在 finally 块中释放锁，可能会在程序中留下一个定时炸弹，当有一天炸弹爆炸时，您要花费很大力气才有找到源头在哪。而使用同步，JVM 将确保锁会获得自动释放。

Lock lock = new ReentrantLock();
lock.lock();
try { 
  // update object state
}
finally {
  lock.unlock(); 
}
除此之外，与目前的 synchronized 实现相比，争用下的 ReentrantLock 实现更具可伸缩性。（在未来的 JVM 版本中，synchronized 的争用性能很有可能会获得提高。）这意味着当许多线程都在争用同一个锁时，使用 ReentrantLock 的总体开支通常要比 synchronized 少得多。

1.2.3 什么时候选择用 ReentrantLock 代替 synchronized

在 Java1.5 中，synchronized 是性能低效的。因为这是一个重量级操作，需要调用操作接口，导致有可能加锁消耗的系统时间比加锁以外的操作还多。相比之下使用 Java 提供的 Lock 对象，性能更高一些。但是到了 Java1.6，发生了变化。synchronized 在语义上很清晰，可以进行很多优化，有适应自旋，锁消除，锁粗化，轻量级锁，偏向锁等等。导致在 Java1.6 上 synchronized 的性能并不比 Lock 差。官方也表示，他们也更支持 synchronized，在未来的版本中还有优化余地。

所以在确实需要一些 synchronized 所没有的特性的时候，比如时间锁等候、可中断锁等候、无块结构锁、多个条件变量或者锁投票使用ReentrantLock。ReentrantLock 还具有可伸缩性的好处，应当在高度争用的情况下使用它，但是请记住，大多数 synchronized 块几乎从来没有出现过争用，所以可以把高度争用放在一边。我建议用 synchronized 开发，直到确实证明 synchronized 不合适，而不要仅仅是假设如果使用 ReentrantLock “性能会更好”。请记住，这些是供高级用户使用的高级工具。（而且，真正的高级用户喜欢选择能够找到的最简单工具，直到他们认为简单的工具不适用为止。）。一如既往，首先要把事情做好，然后再考虑是不是有必要做得更快。
 */
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitSignalModel {
	private static Integer count = 0;
	private final Integer FULL = 10;
	final Lock lock = new ReentrantLock();
	final Condition NotFull = lock.newCondition();
	final Condition NotEmpty = lock.newCondition();

	class Producer implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				lock.lock();
				try {
					while (count == FULL) {
						try {
							NotFull.await();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					count++;
					System.out.println(Thread.currentThread().getName() + "生产者生产，目前总共有" + count);
					NotEmpty.signal();
				} finally {
					lock.unlock();
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
				lock.lock();
				try {
					while (count == 0) {
						try {
							NotEmpty.await();
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
					count--;
					System.out.println(Thread.currentThread().getName() + "消费者消费，目前总共有" + count);
					NotFull.signal();
				} finally {
					lock.unlock();
				}

			}

		}

	}

	public static void main(String[] args) throws Exception {
		AwaitSignalModel hosee = new AwaitSignalModel();
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