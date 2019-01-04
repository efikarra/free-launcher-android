package app.competitions.service;

import java.util.List;

import app.competitions.model.GcmRegistration;

public interface GcmRegistrationService {
	 public void saveGcmRegistration(GcmRegistration gcmRegistration) ;
	 public List<GcmRegistration> findGcmRegistrationByUser(long userId);
}
