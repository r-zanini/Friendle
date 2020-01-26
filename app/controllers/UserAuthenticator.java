package controllers;

import models.User;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.util.Optional;

public class UserAuthenticator extends Security.Authenticator {

	@Override
	public Optional<String> getUsername(Http.Request request) {
		Optional<String> userName = request.session().get("user");
		User user = User.find.byId(userName.orElse(""));
		if (user == null) return Optional.empty();      // user with specified userName does not exist or is inactive
		return userName;                                   // session is valid
	}

	@Override
	public Result onUnauthorized(Http.Request request) {
		return redirect(routes.ViewController.loginPage());
	}
}
