package de.laliluna.example.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.laliluna.example.domain.Hedgehog;
import de.laliluna.example.domain.HedgehogDao;
import de.laliluna.example.domain.WinterAddress;

public class HedgehogServiceImp implements HedgehogService {

	private HedgehogDao hedgehogDao;
	/* (non-Javadoc)
	 * @see de.laliluna.example.service.HedgehogService#createNewHedgehog(de.laliluna.example.domain.Hedgehog)
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void createNewHedgehog(Hedgehog hedgehog){
		hedgehogDao.save(hedgehog);
	}
	
	/* (non-Javadoc)
	 * @see de.laliluna.example.service.HedgehogService#findAll()
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<Hedgehog> findAll(){
		return hedgehogDao.findAll();
	}
	
	/* (non-Javadoc)
	 * @see de.laliluna.example.service.HedgehogService#findByAddress(de.laliluna.example.domain.WinterAddress)
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public Hedgehog findByAddress(WinterAddress address) {
		return hedgehogDao.findByWinterAddress(address);
	}

	/* (non-Javadoc)
	 * @see de.laliluna.example.service.HedgehogService#findByAddressName(java.lang.String)
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<Hedgehog> findByAddressName(String addressName) {
		return hedgehogDao.findByWinterAddressName(addressName);
	}
	
	/* (non-Javadoc)
	 * @see de.laliluna.example.service.HedgehogService#findById(Integer id)
	 */
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public Hedgehog findById(Integer id) {
		return hedgehogDao.findById(id);
	}
	
	/* (non-Javadoc)
	 * @see de.laliluna.example.service.HedgehogService#update(Hedgehog hedgehog)
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void update(Hedgehog hedgehog) {
		hedgehogDao.update(hedgehog);
	}
	
	

	/* (non-Javadoc)
	 * @see de.laliluna.example.service.HedgehogService#hibernate(de.laliluna.example.domain.Hedgehog, de.laliluna.example.domain.WinterAddress)
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void hibernate(Hedgehog hedgehog, WinterAddress winterAddress){
		hedgehog.getAddresses().add(winterAddress);
		winterAddress.setHedgehog(hedgehog);
		hedgehogDao.update(hedgehog);
	}

	public void setHedgehogDao(HedgehogDao hedgehogDao) {
		this.hedgehogDao = hedgehogDao;
	}
	
	
}
