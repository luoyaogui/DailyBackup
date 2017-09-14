package lyg.tool.lock;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;

import sun.misc.Unsafe;

public class UsingUnsafe {

	public static void main(String[] args) throws Throwable {
		CompareSynAndUnsafe();
	}
	public static void CompareSynAndUnsafe() throws InterruptedException{
		final UsingUnsafe us = new UsingUnsafe();
		
		System.out.println(us.enq("test-head---11--"));
		
		final int size = 1000;
		final CountDownLatch cd = new CountDownLatch(size );
		long start = System.currentTimeMillis();
		for(int index=0;index<size;index++){
			new Thread(new Runnable(){
				public void run() {
					for(int index=0;index<10000;index++)
						us.enq("test-head");
					cd.countDown();
				}
			}).start();
		}
		cd.await();
		/*for(int index=0;index<size;index++){
			node.enq("test-head");
		}*/
		System.out.println("cost: "+(System.currentTimeMillis() - start));
	}
	private transient volatile String head;
	private Object lock = new Object();

	public UsingUnsafe(){
		compareAndSetHead("test-head");
	}

	private void enq1(final String update){
		synchronized (lock) {
			//System.out.println("before: "+head);
			head = update;
			//System.out.println("after: "+head);
		}
	}

	private boolean enq(final String update) {
		return compareAndSetHead(update);
		/*for (;;) {
            String t = head;
//            System.out.println("before: "+t);
            if (t == null) { // Must initialize
            	if(compareAndSetHead(update)){
//            		System.out.println("after: "+head);
            		return;
            	}
            }
        }*/
	}
	private static final Unsafe unsafe = getUnsafe();//
	private static final long headOffset;
	static {
		try {
			headOffset = unsafe.objectFieldOffset(UsingUnsafe.class.getDeclaredField("head"));
		} catch (Exception ex) { throw new Error(ex); }
	}
	private final boolean compareAndSetHead(String update) {
		return unsafe.compareAndSwapObject(this, headOffset, "test-head", update);
	}
	public static Unsafe getUnsafe() {//利用反射
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			return (Unsafe)field.get(null);
		} catch (Exception e) {
		}
		return null;
	}
}
