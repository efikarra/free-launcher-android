package app.competitions.spring.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.competitions.model.GcmRegistration;
import app.competitions.model.Notification;
import app.competitions.service.GcmRegistrationService;
import app.competitions.service.NotificationService;


@RestController
public class NotificationRest {

	@Autowired
	NotificationService notificationService;
	@Autowired
	GcmRegistrationService gcmRegistrationService;
	
	@RequestMapping(value = "/notifications", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpEntity<Notification> pushNotification(@RequestBody Notification notification) {
		
		List<Long> userIdsToSend=notification.getUserIdsToSend();
		List<GcmRegistration> registrationIdsToSend =new ArrayList<GcmRegistration>();
		for(int i=0;i<userIdsToSend.size();i++)
		{
			registrationIdsToSend.addAll(gcmRegistrationService
				.findGcmRegistrationByUser(userIdsToSend.get(i)));
		}
		if (!registrationIdsToSend.isEmpty()) {
			List<String> ids= (List<String>) registrationIdsToSend.stream().map(sc -> sc.getRegistrationId())
					.collect(Collectors.toList());
			System.out.println(ids.size());
			notificationService.pushNotificationToGCM(notification,ids);

		} else
			System.out.println("no ids!!!!!!!!!!");
		
		 return new ResponseEntity<>(notification, HttpStatus.CREATED);
	}
}
