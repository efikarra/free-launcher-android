package app.competitions.dao;

import java.util.List;

import app.competitions.model.Bid;

public interface BidDao {
 void save(Bid bid);
 int delete(long bidId);
 List<Bid> findBidByExpert(long expId, boolean isClosed);
}
