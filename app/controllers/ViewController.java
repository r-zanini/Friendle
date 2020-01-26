package controllers;

import enums.UserType;
import models.Community;
import models.Event;
import models.User;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

@Singleton
public class ViewController extends Controller {
	private final FormFactory formFactory;

	@Inject
	public ViewController(FormFactory formFactory, MessagesApi messagesApi) {this.formFactory = formFactory;}

	private void initialize(){
		User admin = new User("q","q","Master","Admin", "Master@mail.com", "7776664422","nowhere land", UserType.ADMIN);
		if (User.find.byId("q")==null) admin.save();
		else admin.update();
		admin.refresh();
		Community community = new Community("testComm");
		System.out.println(community.toString());
		community.save();
		community.save();
		if (Community.find.byId("testComm")==null) community.save();
		else community.update();
//		HackConcordia.refresh();
		System.out.println(Community.find.byId("HackConcordia"));
//		Event conUhacks = new Event("ConUHacks 2020", "The best Hackathon ever!", LocalDateTime.of(2020, Month.JANUARY, 25, 9,30,00), LocalDateTime.of(2020, Month.JANUARY, 26, 17,30,00));
//		System.out.println(conUhacks.toString());
//		if (Event.find.byId(conUhacks.getEventId())==null) conUhacks.save();
//		else conUhacks.update();
//		conUhacks.refresh();
		System.out.println(Event.find.byId(";2020-01-26T09:57:30.554").toString());
	}



	@Security.Authenticated(UserAuthenticator.class)
	public Result index(Http.Request request) {
		return ok(views.html.index.render(request));
	}

	public Result loginPage(Http.Request request) {
		initialize();return ok(views.html.logIn.render(formFactory.form().bindFromRequest(request), request));}
	public Result loginAction(Http.Request request) {
		DynamicForm loginForm = formFactory.form().bindFromRequest(request);
		System.out.println(loginForm.get("userName"));
		System.out.println(loginForm.get("userPassword"));
		User toLogin = User.logIn(loginForm.get("userName"), loginForm.get("userPassword"));
		System.out.println(toLogin == null);
		if (toLogin == null) return redirect(routes.ViewController.loginPage()).flashing("error", "Invalid Credentials");
		TreeMap<String, String> session = new TreeMap<>();
		session.put("user", toLogin.getUserName());
		return redirect(routes.ViewController.index()).withSession(session);
	}
	public Result signupPage(Http.Request request) {return ok(views.html.signup.render(formFactory.form().bindFromRequest(request), request));}
	public Result signupAction(Http.Request request) {
		DynamicForm signupForm = formFactory.form().bindFromRequest(request);
		if (signupForm.hasErrors()) return badRequest(views.html.signup.render(signupForm, request));
		User toCreate = UserController.newUser(signupForm);
		if (!toCreate.availableUserName()) return badRequest(views.html.signup.render(signupForm.withError("userName", "That UserName is already in use."), request));
		if (!toCreate.availableUserEmail()) return badRequest(views.html.signup.render(signupForm.withError("userEmail", "That Email is already in use."), request));
		if (!toCreate.availablePhoneNumber()) return badRequest(views.html.signup.render(signupForm.withError("phoneNumber", "That Phone Number is already in use."), request));
		toCreate.save();
		return redirect(routes.ViewController.loginPage()).flashing("success", "Account successfully created. Please Log In now.");
	}
	@Security.Authenticated(UserAuthenticator.class)
	public Result newEventPage(Http.Request request) {return ok(views.html.eventPage.render(formFactory.form().bindFromRequest(request), request));}
	@Security.Authenticated(UserAuthenticator.class)
	public Result newEventAction(Http.Request request) {
		DynamicForm signupForm = formFactory.form().bindFromRequest(request);
		if (signupForm.hasErrors()) return badRequest(views.html.eventPage.render(signupForm, request));
		Event toCreate = EventController.newEvent(signupForm);
		toCreate.save();
		return redirect(routes.ViewController.index()).flashing("success", "Event successfully created.");
	}
	@Security.Authenticated(UserAuthenticator.class)
	public Result viewEventPage(Http.Request request, String eventId) {
		Map<String, Object> answer = new TreeMap<>();
		Event event = Event.find.byId(eventId);
		answer.put("eventId", event.getEventId());
		answer.put("eventName", event.getEventName());
		answer.put("eventDescription", event.getEventDescription());
		answer.put("eventStart", event.getEventStart());
		answer.put("eventEnd", event.getEventEnd());
		return ok(views.html.eventPage.render(formFactory.form().fill(answer), request));}
	@Security.Authenticated(UserAuthenticator.class)
	public Result viewSchedule(Http.Request request) {
		return ok(views.html.schedule.render(request));
	}

	public Result logout(Http.Request request) {return redirect(routes.ViewController.loginPage()).withNewSession();}
}
