package app.competitions.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;

import app.competitions.dao.GcmRegistrationDao;
import app.competitions.model.Notification;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class NotificationServiceImpl implements NotificationService {
	private static final int MAX_RETRIES = 3;

	private static final int MAX_MULTICAST_SIZE = 1000;

	private static final Executor THREAD_POOL = Executors.newFixedThreadPool(5);
	@Autowired
	private GcmRegistrationDao GcmRegistrationDao;
	@Autowired
	private Sender gcmSender;

	@Override
	public void pushNotificationToGCM(Notification notification,
			List<String> validDeviceIds) {
		if (validDeviceIds.size() == 1) {
			sendSyncNotification(notification, validDeviceIds.get(0));
		} else {
			sendAsyncNotification(notification, validDeviceIds);
		}

	}

	private void sendSyncNotification(Notification notification, String deviceId) {
		final Message message = createMessage(notification);
		System.out.println(deviceId);
		System.out.println(message.getData().get("message") + " "
				+ message.getData().get("title"));
		try {
			Result result = gcmSender.send(message, deviceId, MAX_RETRIES);
			final String messageId = result.getMessageId();
			if (messageId == null) {
				System.out.println("Unable to send message {} . {}");
				throw new UnableToSendNotificationException(result.toString());
			}
			System.out.println("Message send successfully {}");
		} catch (IOException e) {
			System.out.println("Unable to send message to device id: {}");
			throw new UnableToSendNotificationException(
					"Internal comunication error");
		}
	}

	private void sendAsyncNotification(Notification notification,
			List<String> validDeviceIds) {
		final Message message = createMessage(notification);
		System.out.println(validDeviceIds.get(0));
		System.out.println(message.getData().get("message") + " "
				+ message.getData().get("title"));
		if (validDeviceIds.size() > MAX_MULTICAST_SIZE) {
			final List<String> deviceIdsToSend = validDeviceIds.subList(0,
					MAX_MULTICAST_SIZE);
			THREAD_POOL.execute(new NotificationSenderThread(message,
					deviceIdsToSend));
			sendAsyncNotification(
					notification,
					validDeviceIds.subList(MAX_MULTICAST_SIZE,
							validDeviceIds.size()));
		} else {
			THREAD_POOL.execute(new NotificationSenderThread(message,
					validDeviceIds));
		}
	}

	private Message createMessage(Notification notification) {
		final Message.Builder messageBuilder = new Message.Builder();
		messageBuilder.addData("title", notification.getTitle());
		messageBuilder.addData("message", notification.getMessage());
		messageBuilder.addData("msgcnt", notification.getBadge().toString());
		return messageBuilder.build();
	}

	private class NotificationSenderThread implements Runnable {

		private final Message message;

		private final List<String> deviceIdsToSend;

		NotificationSenderThread(Message message, List<String> deviceIdsToSend) {
			this.message = message;
			this.deviceIdsToSend = deviceIdsToSend;
		}

		public void run() {
			MulticastResult multicastResult;
			try {
				multicastResult = gcmSender.send(message, deviceIdsToSend,
						MAX_RETRIES);
			} catch (IOException e) {
				System.out.println("Error sending messages to GCM");
				return;
			}

		}

	}
}
