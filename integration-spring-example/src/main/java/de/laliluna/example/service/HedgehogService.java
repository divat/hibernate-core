package de.laliluna.example.service;

import java.util.List;

import de.laliluna.example.domain.Hedgehog;
import de.laliluna.example.domain.WinterAddress;

public interface HedgehogService {

	/**
	 * creates a new hedgehog
	 * @param hedgehog
	 */
	public void createNewHedgehog(Hedgehog hedgehog);

	/**
	 * find all hedgehogs and returns them.
	 * @return
	 */
	public List<Hedgehog> findAll();
	
	/**
	 * find all hedgehogs having a winteraddress with the given name
	 * @param addressName
	 * @return
	 */
	public List<Hedgehog> findByAddressName(String addressName);
	
	/**
	 * find a hedgehog for the given address
	 * @param address
	 * @return
	 */
	public Hedgehog findByAddress(WinterAddress address);
	
	/**
	 * find a hedgehog by primary key
	 * @param id
	 * @return the hedgehog or null if not found
	 */
	public Hedgehog findById(Integer id);

	/**
	 * updates a hedgehog
	 * @param hedgehog
	 */
	public void update(Hedgehog hedgehog);
	
	/**
	 * hibernates a hedgehog at a specific address
	 * @param hedgehog
	 * @param winterAddress
	 */
	public void hibernate(Hedgehog hedgehog, WinterAddress winterAddress);
	
	

}