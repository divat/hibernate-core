package de.laliluna.example.service;

import java.util.List;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.OptimisticLockingFailureException;

import com.sun.java.swing.plaf.windows.resources.windows;

import de.laliluna.example.domain.Hedgehog;
import de.laliluna.example.domain.WinterAddress;

public class HedgehogServiceTest implements Runnable {

	private static BeanFactory beanFactory;

	private Integer optimisticLockingId;

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HedgehogServiceTest.class);

	public void setOptimisticLockingId(Integer optimisticLockingId) {
		this.optimisticLockingId = optimisticLockingId;
	}

	/**
     * @param args
     */
	public static void main(String[] args) {
		
		beanFactory = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });

		createHedgehog();

		listHedgehogs();

		testOptimisticLocking();

	}

	private static void testOptimisticLocking() {
		HedgehogService hedgehogService = (HedgehogService) beanFactory
				.getBean("hedgehogService");

		Hedgehog hedgehog = new Hedgehog("Kaspar");
		hedgehogService.createNewHedgehog(hedgehog);

		HedgehogServiceTest t1 = new HedgehogServiceTest();
		t1.setOptimisticLockingId(hedgehog.getId());
		Thread thread = new Thread(t1);
		thread.start();

		HedgehogServiceTest t2 = new HedgehogServiceTest();
		t2.setOptimisticLockingId(hedgehog.getId());
		Thread thread2 = new Thread(t2);
		thread2.start();

	}

	private static void listHedgehogs() {

		HedgehogService hedgehogService = (HedgehogService) beanFactory
				.getBean("hedgehogService");

		List<Hedgehog> list = hedgehogService.findAll();
		for (Hedgehog hedgehog : list) {
			System.out.println(hedgehog);
		}
		
		list = hedgehogService.findByAddressName("Emilios Reisighaufen");
		for (Hedgehog hedgehog : list) {
			System.out.println(hedgehog);
		}
		
		list = hedgehogService.findByAddressName("Addresse unbekannt");
		for (Hedgehog hedgehog : list) {
			System.out.println(hedgehog);
		}

	}

	private static void createHedgehog() {

		HedgehogService hedgehogService = (HedgehogService) beanFactory
				.getBean("hedgehogService");

		Hedgehog hedgehog = new Hedgehog("Kaspar");
		WinterAddress address = new WinterAddress("Emilios Reisighaufen");
		hedgehog.getAddresses().add(address);
		address.setHedgehog(hedgehog);
		hedgehogService.createNewHedgehog(hedgehog);

	}

	/**
     * test optimistic locking
     */
	public void run() {
		HedgehogService hedgehogService = (HedgehogService) beanFactory
				.getBean("hedgehogService");

		Hedgehog hedgehog = hedgehogService.findById(optimisticLockingId);
		
		// we wait to ensure that both threads have loaded the hedgehog
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		hedgehog.setName("Pete" + Thread.currentThread());
		try {
			hedgehogService.update(hedgehog);
		} catch (OptimisticLockingFailureException e) {
			log.info("The row was updated by another user. Show a nice dialog here");
		}

	}

}
