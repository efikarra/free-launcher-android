package app.competitions.service;

import java.util.List;

import app.competitions.model.Notification;

public interface NotificationService {
	public void pushNotificationToGCM(Notification notification,List<String> validDeviceIds);
}
