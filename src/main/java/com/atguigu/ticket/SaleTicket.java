package com.atguigu.ticket;

//在高内聚低耦合的前提下，线程      操作		资源类
public class SaleTicket {
	public static void main(String[] args) {
		Ticket ticket = new Ticket();
		
		new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sale(); },"售票员一").start();
		new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sale(); },"售票员二").start();
		new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sale(); },"售票员三").start();

		/**
		 * 2    Lambda Express（前提是函数式接口，只有一个方法）
		 *
		 * 2.1 拷贝小括号，写死右箭头，落地大括号
		 * 2.2 注解@FunctionalInterface
		 * 2.3 default方法 可以有多个
		 * 2.4 static方法  可以有多个
		 *
		 */		
		
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
