package lyg.tool.lock;

import java.util.concurrent.locks.LockSupport;
/*
 * LockSupport的思想呢，和 Semaphore有点相似，内部有一个许可，park的时候拿掉这个许可，unpark的时候申请这个许可。所以如果unpark在park之前，是不会发生线程冻结的。
 * 
 * park()能够响应中断，但不抛出异常。中断响应的结果是，park()函数的返回，可以从Thread.interrupted()得到中断标志。
 * 
 * 在使用suspend时会发生死锁。而使用 LockSupport则不会发生死锁。
 */
public class LockSupportT {
    static Object u = new Object();
    static TestSuspendThread t1 = new TestSuspendThread("t1");
    static TestSuspendThread t2 = new TestSuspendThread("t2");
 
    public static class TestSuspendThread extends Thread {
        public TestSuspendThread(String name) {
            setName(name);
        }
 
        @Override
        public void run() {
            synchronized (u)
            {
                System.out.println("in " + getName());
//                Thread.currentThread().suspend();
                LockSupport.park();
            }
        }
    }
 
    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
//        t1.resume();
//        t2.resume();
        LockSupport.unpark(t1);
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }
}