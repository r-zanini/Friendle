package controllers;

import models.Community;
import models.Event;
import models.User;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Singleton;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class EventController extends Controller {

	public static Event newEvent(DynamicForm eventForm) {return new Event(eventForm.get("eventName"), eventForm.get("eventDescription"), LocalDateTime.parse(eventForm.get("eventStart")), LocalDateTime.parse(eventForm.get("eventEnd")));}

	private Map<Object, Serializable> remapEvent(Event event) {
		Map<Object, Serializable> eventRemapped = new HashMap<>();
		eventRemapped.put("id", event.getEventId());
		eventRemapped.put("title", event.getEventName());
		eventRemapped.put("description", event.getEventDescription());
		eventRemapped.put("start", event.getEventStart().format(DateTimeFormatter.ISO_DATE_TIME).replace("T"," "));
		eventRemapped.put("end", event.getEventEnd().format(DateTimeFormatter.ISO_DATE_TIME).replace("T"," "));
		eventRemapped.put("url", routes.ViewController.viewEventPage(event.getEventId()).toString());
		return eventRemapped;
	}

	public Result eventJSON(Http.Request request){
		User user = User.find.byId(request.session().get("user").orElse(""));
		LocalDateTime startDate = LocalDateTime.parse(request.queryString("start").orElse("").substring(0,19));
		LocalDateTime endDate = LocalDateTime.parse(request.queryString("end").orElse("").substring(0, 19));
		List<Event> eventList = Event.findInDateRange(startDate, endDate);
		System.out.println("size of list: " + eventList.size());
		ArrayList<Map<Object, Serializable>> filteredEvents = new ArrayList<>();
		for (Event event : eventList){
			System.out.println("event: " + event);
					filteredEvents.add(remapEvent(event));
			}
		System.out.println(play.libs.Json.toJson(filteredEvents));
		return ok(play.libs.Json.toJson(filteredEvents));
	}
}
