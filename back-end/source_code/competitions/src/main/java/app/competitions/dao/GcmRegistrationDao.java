package app.competitions.dao;

import java.util.List;

import app.competitions.model.GcmRegistration;

public interface GcmRegistrationDao {
	  void saveGcmRegistration(GcmRegistration gcmRegistration);
	  public List<GcmRegistration> findGcmRegistrationByUser(long userId);
}
