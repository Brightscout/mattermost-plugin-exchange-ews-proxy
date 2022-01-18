package com.ews.ews.model.event;

public class Event {

	private String id;

	private String iCalUID;

	private String subject;

	// NA: private String bodyPreview;

	private ItemBody body;

	private String importance;

	private boolean isAllDay; // IsAllDayEvent

	private boolean isCancelled;

	// NA: private boolean isOrganizer;

	private boolean responseRequested; // IsResponseRequested

	private String showAs;

	// NA: private String webLink;

	private DateTime start;

	private DateTime end;

	private int reminderMinutesBeforeStart;

	private String location;

	private EventResponseStatus responseStatus;

	private Attendee[] attendees;

	private Attendee organizer;

	private String timezone;

	private String webLink;

	private Boolean isOrganizer;

	public Event() {
	}

	public Event(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getiCalUID() {
		return iCalUID;
	}

	public void setiCalUID(String iCalUID) {
		this.iCalUID = iCalUID;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public ItemBody getBody() {
		return body;
	}

	public void setBody(ItemBody body) {
		this.body = body;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public boolean isAllDay() {
		return isAllDay;
	}

	public void setAllDay(boolean isAllDay) {
		this.isAllDay = isAllDay;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public boolean isResponseRequested() {
		return responseRequested;
	}

	public void setResponseRequested(boolean responseRequested) {
		this.responseRequested = responseRequested;
	}

	public DateTime getStart() {
		return start;
	}

	public void setStart(DateTime start) {
		this.start = start;
	}

	public DateTime getEnd() {
		return end;
	}

	public void setEnd(DateTime end) {
		this.end = end;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Attendee[] getAttendees() {
		return attendees;
	}

	public void setAttendees(Attendee[] attendees) {
		this.attendees = attendees;
	}

	public Attendee getOrganizer() {
		return organizer;
	}

	public void setOrganizer(Attendee organizer) {
		this.organizer = organizer;
	}

	public EventResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(EventResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public int getReminderMinutesBeforeStart() {
		return reminderMinutesBeforeStart;
	}

	public void setReminderMinutesBeforeStart(int reminderMinutesBeforeStart) {
		this.reminderMinutesBeforeStart = reminderMinutesBeforeStart;
	}
	
	public String getShowAs() {
		return showAs;
	}

	public void setShowAs(String showAs) {
		this.showAs = showAs;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setWebLink(String webLink) {
		this.webLink = webLink;
	}

	public String getWebLink() {
		return webLink;
	}

	public void setIsOrganizer(Boolean isOrganizer) {
		this.isOrganizer = isOrganizer;
	}

	public Boolean getIsOrganizer() {
		return isOrganizer;
	}
}
