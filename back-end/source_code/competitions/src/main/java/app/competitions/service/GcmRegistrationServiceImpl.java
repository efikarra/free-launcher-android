package app.competitions.service;

import java.util.List;

import javax.transaction.Transactional;

import app.competitions.dao.GcmRegistrationDao;
import app.competitions.model.GcmRegistration;

public class GcmRegistrationServiceImpl implements GcmRegistrationService {
	private GcmRegistrationDao gcmRegistrationDao;

	public void setGcmRegistrationDao(GcmRegistrationDao gcmRegistrationDao) {
		this.gcmRegistrationDao = gcmRegistrationDao;
	}

	@Transactional
	@Override
	public void saveGcmRegistration(GcmRegistration gcmRegistration) {
		gcmRegistrationDao.saveGcmRegistration(gcmRegistration);

	}

	@Transactional
	@Override
	public List<GcmRegistration> findGcmRegistrationByUser(long userId) {

		return gcmRegistrationDao.findGcmRegistrationByUser(userId);
	}

}
