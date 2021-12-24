package com.ews.ews.model.event;

import java.util.Date;

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
	
	//NA: private String showAs;
	
	//NA: private String webLink;
	
	private Date start;
	
	private Date end;
	
	//NA: private int reminderMinutesBeforeStart;
	
	private String location;
	
	private EventResponseStatus responseStatus;
	
	private Attendee[] attendees;
	
	private Attendee organizer;

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

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
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
	
}
