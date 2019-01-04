package app.competitions.model;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Notification {
	private Integer badge;

	private String title;

	private String message;

	private List<Long> userIdsToSend;

	public Integer getBadge() {
		return badge;
	}

	public void setBadge(Integer badge) {
		this.badge = badge;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Notification)) {
			return false;
		}

		final Notification other = (Notification) o;
		return new EqualsBuilder().append(this.badge, other.getBadge())
				.append(this.title, other.getTitle())
				.append(this.message, other.getMessage())
				.append(this.userIdsToSend, this.getUserIdsToSend()).isEquals();

	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.badge).append(this.title)
				.append(this.message).append(this.userIdsToSend).hashCode();
	}

	@Override
	public String toString() {
		return "Notification{" + "badge='" + badge + '\'' + ", title='" + title
				+ '\'' + ", message='" + message + '\''
				+ ", registrationIdsToSend="
				+ (userIdsToSend == null ? null : userIdsToSend) + '}';
	}

	public List<Long> getUserIdsToSend() {
		return userIdsToSend;
	}

	public void setUserIdsToSend(List<Long> userIdsToSend) {
		this.userIdsToSend = userIdsToSend;
	}
}
