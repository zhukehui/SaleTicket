package com.atguigu.ticket;

import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*什么是进程？什么是线程？
	进程：后台运行的软件，像  QQ.exe		QQMusic.exe
	线程：线程是进程的一个实体,是CPU调度和分派的基本单位,它是比进程更小的能独立运行的基本单位.
		   就像QQ中的各个窗口  
		   一个进程中至少有一个线程。一个进程中是可以有多个线程的
	
*/

/*什么是并发？什么是并行？
	并发： 一个CPU(采用时间片)同时执行多个任务。比如：秒杀、多个人做同一件事。 
	 	   同一时间点多个线程共同抢夺同一份资源   例如：12306抢票
	并行：多个CPU同时执行多个任务。比如：多个人同时做不同的事。 就像变烧水边泡方便面  
		 你吃饭吃到一半，电话来了，你一边打电话一边吃饭，这说明你支持并行。

*/

//在高内聚低耦合的前提下，线程      操作		资源类
public class SaleTicket {
	public static void main(String[] args) {
		Ticket ticket = new Ticket();
		//ExecutorService executor = Executors.newFixedThreadPool(3);//一池3线程
		ExecutorService executor = Executors.newCachedThreadPool();//一池N线程
		/*
		 * new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sale();
		 * },"售票员一").start(); new Thread(() -> {for (int i = 0; i < 40; i++)
		 * ticket.sale(); },"售票员二").start(); new Thread(() -> {for (int i = 0; i < 40;
		 * i++) ticket.sale(); },"售票员三").start();
		 */
	try {
		for (int i = 0; i < 30; i++) {
			executor.submit(() -> {
				ticket.sale();
			});
		}
	} catch (Exception e) {
	}finally {
		executor.shutdown();
	}
		
		/*
		 * new Thread(new Runnable() {
		 * 
		 * @Override public void run() { for (int i = 0; i < 40; i++) { ticket.sale(); }
		 * } }, "售票员一").start();
		 *  
		 * new Thread(new Runnable() {
		 * 
		 * @Override public void run() { for (int i = 0; i < 40; i++) { ticket.sale(); }
		 * } }, "售票员二").start();
		 * 
		 * new Thread(new Runnable() {
		 * 
		 * @Override public void run() { for (int i = 0; i < 40; i++) { ticket.sale(); }
		 * } }, "售票员三").start();
		 */

	}
}

//三名售票员  卖票   30张

class Ticket {// 资源类
	
	/*
	 * private int ticket = 30;
	 * 
	 * public synchronized void sale() { if (ticket > 0) {
	 * System.out.println(Thread.currentThread().getName() + "正在卖第" + (ticket--) +
	 * "张票，还剩" + ticket + "张票"); } }
	 */
	
	private int ticket = 30;
	
    Lock lock = new ReentrantLock();
    
    public void sale() {
    	lock.lock();//添加锁
    	try {
    		if (ticket>0) {
    			System.out.println(Thread.currentThread().getName()+"正在卖第"+ (ticket--)+"张票，还剩"+ticket+"张票");
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();//释放锁
		}
    	
    }
}

/**
 * 
 *
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行。
 * 但是
 * 如果有一个线程想去写共享资源来，就不应该再有其它线程可以对该资源进行读或写
 * 小总结：
 *          读-读能共存
 *          读-写不能共存
 *          写-写不能共存
 */