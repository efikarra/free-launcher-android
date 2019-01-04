package app.competitions.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import app.competitions.model.Bid;

public class BidDaoImpl implements BidDao {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(Bid bid) {
		sessionFactory.getCurrentSession().saveOrUpdate(bid);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bid> findBidByExpert(long expId, boolean isClosed) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"from Bid as b left join fetch b.expert as exp left join fetch b.project as proj where proj.isClosed= :isClosed and exp.userId= :userId ")
				.setParameter("isClosed", isClosed)
				.setParameter("userId", expId).list();
	}

	@Override
	public int delete(long bidId) {
		String hql = "delete from Bid where bidId= :bidId";
		int c = sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("bidId", bidId).executeUpdate();
		return c;
	}

}
