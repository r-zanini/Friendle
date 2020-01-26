package controllers;

import enums.UserType;
import models.User;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserController extends Controller {
	private final FormFactory formFactory;

	@Inject
	public UserController(FormFactory formFactory, MessagesApi messagesApi) {this.formFactory = formFactory;}

	public static User newUser(DynamicForm signupForm) {return new User(signupForm.get("userName"), signupForm.get("userPassword"), signupForm.get("firstName"), signupForm.get("lastName"), signupForm.get("userEmail"), signupForm.get("phoneNumber"), signupForm.get("userAddress"), UserType.NORMAL);}
}
