package app.competitions.service;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import app.competitions.dao.BidDao;
import app.competitions.model.Bid;
import app.competitions.model.GcmRegistration;
import app.competitions.model.Notification;

public class BidServiceImpl implements BidService {

	private BidDao bidDao;
	
	

	public void setBidDao(BidDao bidDao) {
		this.bidDao = bidDao;
	}

	@Transactional
	@Override
	public void save(Bid bid) {
		bidDao.save(bid);
		
	}

	@Transactional
	@Override
	public List<Bid> findBidByExpert(long expId, boolean isClosed) {
		return bidDao.findBidByExpert(expId, isClosed);
	}

	@Transactional
	@Override
	public int delete(long bidId) {
		return bidDao.delete(bidId);
	}

}
