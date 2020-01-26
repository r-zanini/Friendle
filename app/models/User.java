package models;

import enums.UserType;
import io.ebean.Expr;
import io.ebean.Finder;
import io.ebean.Model;
import models.relationships.User_Community;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class User extends Model {
	@Id
	private String userName;
	@Constraints.Required
	private String userPassword;
	@Constraints.Required
	private String firstName;
	@Constraints.Required
	private String lastName;
	@Constraints.Required
	@Column(unique = true)
	private String userEmail;
	@Constraints.Required
	@Column(unique = true)
	private String phoneNumber;
	@Constraints.Required
	private String userAddress;
	private UserType userType;
	@OneToMany(mappedBy = "relatedUser")
	private List<User_Community> relatedCommunityList;

	public User(String userName, @Constraints.Required String userPassword, @Constraints.Required String firstName, @Constraints.Required String lastName, @Constraints.Required String userEmail, @Constraints.Required String phoneNumber, @Constraints.Required String userAddress, UserType userType) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userEmail = userEmail;
		this.phoneNumber = phoneNumber;
		this.userAddress = userAddress;
		this.userType = userType;
	}

	public static Finder<String, User> find = new Finder<>(User.class);
	public static User findByEmail(String email) {return find.query().where().eq("userEmail", email).findOne();}

	public String getUserName() {return userName;}
	public void setUserName(String userName) {this.userName = userName;}
	public String getUserPassword() {return userPassword;}
	public void setUserPassword(String userPassword) {this.userPassword = userPassword;}
	public String getFirstName() {return firstName;}
	public void setFirstName(String firstName) {this.firstName = firstName;}
	public String getLastName() {return lastName;}
	public void setLastName(String lastName) {this.lastName = lastName;}
	public String getUserEmail() {return userEmail;}
	public void setUserEmail(String userEmail) {this.userEmail = userEmail;}
	public String getPhoneNumber() {return phoneNumber;}
	public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
	public String getUserAddress() {return userAddress;}
	public void setUserAddress(String userAddress) {this.userAddress = userAddress;}
	public UserType getUserType() {return userType;}
	public void setUserType(UserType userType) {this.userType = userType;}
	public List<User_Community> getRelatedCommunityList() {return relatedCommunityList;}
	public void setRelatedCommunityList(List<User_Community> relatedCommunityList) {this.relatedCommunityList = relatedCommunityList;}

	public static User logIn(String userName, String password) {return find.query().where().and(Expr.eq("userName", userName), Expr.eq("userPassword", password)).findOne();}
	public boolean logIn(String password) {return password.equals(this.userPassword);}
	public boolean availableUserName(){return find.byId(userName)==null;}
	public boolean availableUserEmail(){return find.query().where().eq("userEmail", userEmail).findOne()==null;}
	public boolean availablePhoneNumber(){return find.query().where().eq("phoneNumber", phoneNumber).findOne()==null;}

}
