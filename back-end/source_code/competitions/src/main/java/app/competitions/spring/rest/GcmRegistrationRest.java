package app.competitions.spring.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.competitions.dao.GcmRegistrationDao;
import app.competitions.dto.GcmRegistrationDTO;
import app.competitions.dto.utils.HibernateMapperFactory;
import app.competitions.model.GcmRegistration;
import app.competitions.model.Project;
import app.competitions.service.GcmRegistrationService;

@RestController
public class GcmRegistrationRest {
	@Autowired
	GcmRegistrationService gcmRegistrationService;
	@Autowired
	HibernateMapperFactory mapperFactory;

	@RequestMapping(value = "/gcmRegistrations", method = RequestMethod.POST, produces = "application/json")
	public HttpEntity<String> registerToGcm(
			@RequestBody GcmRegistrationDTO gcmRegistrationDTO) {
		boolean exists = false;
		GcmRegistration gcmRegistration = mapperFactory.getMapperFacade().map(
				gcmRegistrationDTO, GcmRegistration.class);
		System.out.println(gcmRegistration.getUser().getUserId());
		List<GcmRegistration> l = gcmRegistrationService
				.findGcmRegistrationByUser(gcmRegistration.getUser()
						.getUserId());
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).getRegistrationId() == gcmRegistrationDTO
					.getRegistrationId()) {
				exists = true;
			}

		}
		if (!exists)
			gcmRegistrationService.saveGcmRegistration(gcmRegistration);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
