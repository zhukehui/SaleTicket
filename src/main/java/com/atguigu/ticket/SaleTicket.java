package com.atguigu.ticket;

//在高内聚低耦合的前提下，线程      操作		资源类
public class SaleTicket {
	public static void main(String[] args) {
		Ticket ticket = new Ticket();
		
		new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sale(); },"售票员一").start();
		new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sale(); },"售票员二").start();
		new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sale(); },"售票员三").start();

	
		
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
	private int ticket = 30;

	public synchronized void sale() {
		if (ticket > 0) {
			System.out.println(Thread.currentThread().getName() + "正在卖第" + (ticket--) + "张票，还剩" + ticket + "张票");
		}
	}
}
