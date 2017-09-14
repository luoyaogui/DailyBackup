package lyg.tool.lock.cp;
/*
这个类位于java.io包中，是解决同步问题的最简单的办法，一个线程将数据写入管道，另一个线程从管道读取数据，这样便构成了一种生产者/消费者的缓冲区编程模式。PipedInputStream/PipedOutputStream只能用于多线程模式，用于单线程下可能会引发死锁。

与阻塞队列一样，由于read()/write()方法与输出方法不一定同步，输出结果方面会发生不匹配现象，为了使结果更加明显，这里只有1个消费者和1个生产者。
*/
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedModel {
	final PipedInputStream pis = new PipedInputStream();
	final PipedOutputStream pos = new PipedOutputStream();
	{
		try {
			pis.connect(pos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class Producer implements Runnable {
		@Override
		public void run() {
			try{
                while(true){
                    int b = (int) (Math.random() * 255);
                    System.out.println("Producer: a byte, the value is " + b);
                    pos.write(b);
                    pos.flush();
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                try{
                    pos.close();
                    pis.close();
                }catch(IOException e){
                    System.out.println(e);
                }
            }
		}
	}

	class Consumer implements Runnable {

		@Override
		public void run() {
			try{
                while(true){
                    int b = pis.read();
                    System.out.println("Consumer: a byte, the value is " + String.valueOf(b));
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                try{
                    pos.close();
                    pis.close();
                }catch(IOException e){
                    System.out.println(e);
                }
            }
		}

	}

	public static void main(String[] args) throws Exception {
		PipedModel hosee = new PipedModel();
		new Thread(hosee.new Producer()).start();
		new Thread(hosee.new Consumer()).start();
	}

}