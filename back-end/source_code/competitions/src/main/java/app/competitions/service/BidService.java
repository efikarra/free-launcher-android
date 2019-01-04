package app.competitions.service;

import java.util.List;

import app.competitions.model.Bid;

public interface BidService {
	 void save(Bid bid);
	 int delete(long bidId);
	 List<Bid> findBidByExpert(long expId, boolean isClosed);
}
