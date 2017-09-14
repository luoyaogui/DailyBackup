package lyg.tool.lock.cp.wr;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/*
读者—写者问题（Readers-Writers problem）也是一个经典的并发程序设计问题，是经常出现的一种同步问题。计算机系统中的数据（文件、记录）常被多个进程共享，但其中某些进程可能只要求读数据（称为读者Reader）；另一些进程则要求修改数据（称为写者Writer）。就共享数据而言，Reader和Writer是两组并发进程共享一组数据区，要求：

（1）允许多个读者同时执行读操作；

（2）不允许读者、写者同时操作；

（3）不允许多个写者同时操作。

Reader和Writer的同步问题分为读者优先、弱写者优先（公平竞争）和强写者优先三种情况，它们的处理方式不同。

首先我们都只考虑公平竞争的情况下，看看Java有哪些方法可以实现读者写者问题

2.1 读写锁

ReentrantReadWriteLock会使用两把锁来解决问题，一个读锁，一个写锁
线程进入读锁的前提条件：
    没有其他线程的写锁，
    没有写请求或者有写请求，但调用线程和持有锁的线程是同一个
线程进入写锁的前提条件：
    没有其他线程的读锁
    没有其他线程的写锁

到ReentrantReadWriteLock，首先要做的是与ReentrantLock划清界限。它和后者都是单独的实现，彼此之间没有继承或实现的关系。然后就是总结这个锁机制的特性了： 

重入（在上文ReentrantLock处已经介绍了）方面其内部的WriteLock可以获取ReadLock，但是反过来ReadLock想要获得WriteLock则永远都不要想。 
WriteLock可以降级为ReadLock，顺序是：先获得WriteLock再获得ReadLock，然后释放WriteLock，这时候线程将保持Readlock的持有。反过来ReadLock想要升级为WriteLock则不可能，为什么？参看(1)，呵呵. 
ReadLock可以被多个线程持有并且在作用时排斥任何的WriteLock，而WriteLock则是完全的互斥。这一特性最为重要，因为对于高读取频率而相对较低写入的数据结构，使用此类锁同步机制则可以提高并发量。 
不管是ReadLock还是WriteLock都支持Interrupt，语义与ReentrantLock一致。 
WriteLock支持Condition并且与ReentrantLock语义一致，而ReadLock则不能使用Condition，否则抛出UnsupportedOperationException异常。 
看下ReentrantReadWriteLock这个类的两个构造函数

public ReentrantReadWriteLock() {
        this(false);
    }

    public ReentrantReadWriteLock(boolean fair) {
        sync = (fair)? new FairSync() : new NonfairSync();
        readerLock = new ReadLock(this);
        writerLock = new WriteLock(this);
    }
fair这个参数表示是否是创建一个公平的读写锁，还是非公平的读写锁。也就是抢占式还是非抢占式。

公平和非公平：公平表示获取的锁的顺序是按照线程加锁的顺序来分配获取到锁的线程时最先加锁的线程，是按照FIFO的顺序来分配锁的；非公平表示获取锁的顺序是无需的，后来加锁的线程可能先获得锁，这种情况就导致某些线程可能一直没获取到锁。

公平锁为啥会影响性能，从code上来看看公平锁仅仅是多了一项检查是否在队首会影响性能，如不是，那么又是在什么地方影响的？假如是闯入的线程，会排在队尾并睡觉(parking)等待前任节点唤醒，这样势必会比非公平锁添加很多paking和unparking的操作

一般的应用场景是： 如果有多个读线程，一个写线程，而且写线程在操作的时候需要阻塞读线程，那么此时就需要使用公平锁，要不然可能写线程一直获取不到锁，导致线程饿死。

再简单说下锁降级

重入还允许从写入锁降级为读取锁，其实现方式是：先获取写入锁，然后获取读取锁，最后释放写入锁。但是，从读取锁升级到写入锁是不可能的。
rwl.readLock().lock();
      if (!cacheValid) {
         // Must release read lock before acquiring write lock
         rwl.readLock().unlock();
         rwl.writeLock().lock();
        
         if (!cacheValid) {
           data = ...
           cacheValid = true;
         }
       
         rwl.readLock().lock();
         rwl.writeLock().unlock(); // 降级：先获取读锁再释放写锁
      }
下面我们用读写锁来实现读者写者问题
 */
public class WRReentrantReadWriteLockT {
	public static void main(String[] args) {
		final Queue3 q3 = new Queue3();
		for (int i = 0; i < 3; i++) {
			new Thread() {
				public void run() {
					while (true) {
						q3.get();
					}
				}
			}.start();
		}
		for (int i = 0; i < 3; i++) {
			new Thread() {
				public void run() {
					while (true) {
						q3.put(new Random().nextInt(10000));
					}
				}
			}.start();
		}
	}
	static class Queue3 {
		private Object data = null;// 共享数据，只能有一个线程能写该数据，但可以有多个线程同时读该数据。
		private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

		public void get() {
			rwl.readLock().lock();// 上读锁，其他线程只能读不能写
			System.out.println(Thread.currentThread().getName()
					+ " be ready to read data!");
			try {
				Thread.sleep((long) (Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()
					+ "have read data :" + data);
			rwl.readLock().unlock(); // 释放读锁，最好放在finnaly里面
		}

		public void put(Object data) {
			rwl.writeLock().lock();// 上写锁，不允许其他线程读也不允许写
			System.out.println(Thread.currentThread().getName()
					+ " be ready to write data!");
			try {
				Thread.sleep((long) (Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.data = data;
			System.out.println(Thread.currentThread().getName()
					+ " have write data: " + data);
			rwl.writeLock().unlock();// 释放写锁
		}
	}
}