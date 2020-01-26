package models;

import io.ebean.Expr;
import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Event extends Model {
	@Id
	private String eventId;
	@Constraints.Required
	private String eventName;
	@Constraints.Required
	private String eventDescription;
	@Constraints.Required
	private LocalDateTime eventStart;
	@Constraints.Required
	private LocalDateTime eventEnd;
//	@ManyToOne
//	private Community eventOwner;

	public Event(@Constraints.Required String eventName, @Constraints.Required String eventDescription, @Constraints.Required LocalDateTime eventStart, @Constraints.Required LocalDateTime eventEnd) {
		this.eventId = ";" + LocalDateTime.now();
		this.eventName = eventName;
		this.eventDescription = eventDescription;
		this.eventStart = eventStart;
		this.eventEnd = eventEnd;
//		this.eventOwner = eventOwner;
	}

	public static Finder<String, Event> find = new Finder<>(Event.class);
	public static List<Event> findInDateRange(LocalDateTime start, LocalDateTime end) {
		return find.query().where().and(
				Expr.le("eventStart", end),
				Expr.ge("eventEnd", start)
		).findList();
	}

	public String getEventId() {return eventId;}
	public void setEventId(String eventId) {this.eventId = eventId;}
	public String getEventName() {return eventName;}
	public void setEventName(String eventName) {this.eventName = eventName;}
	public String getEventDescription() {return eventDescription;}
	public void setEventDescription(String eventDescription) {this.eventDescription = eventDescription;}
	public LocalDateTime getEventStart() {return eventStart;}
	public void setEventStart(LocalDateTime eventStart) {this.eventStart = eventStart;}
	public LocalDateTime getEventEnd() {return eventEnd;}
	public void setEventEnd(LocalDateTime eventEnd) {this.eventEnd = eventEnd;}

	@Override
	public String toString() {
		return "Event{" +
				"eventId='" + eventId + '\'' +
				", eventName='" + eventName + '\'' +
				", eventDescription='" + eventDescription + '\'' +
				", eventStart=" + eventStart +
				", eventEnd=" + eventEnd +
				'}';
	}
}
