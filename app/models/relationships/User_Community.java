package models.relationships;

import enums.UserType;
import io.ebean.Finder;
import io.ebean.Model;
import models.Community;
import models.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class User_Community extends Model {
	@Id
	private String relationshipId;
	@ManyToOne
	private User relatedUser;
	@ManyToOne
	private Community relatedCommunity;
	private UserType userType;

	public User_Community(User relatedUser, Community relatedCommunity, UserType userType) {
		this.relationshipId = relatedUser.getUserName() + ";" + relatedCommunity.getCommNameUnity();
		this.relatedUser = relatedUser;
		this.relatedCommunity = relatedCommunity;
		this.userType = userType;
	}

	public static Finder<String, User_Community> find = new Finder<>(User_Community.class);

	public String getRelationshipId() {return relationshipId;}
	public void setRelationshipId(String relationshipId) {this.relationshipId = relationshipId;}
	public User getRelatedUser() {return relatedUser;}
	public void setRelatedUser(User relatedUser) {this.relatedUser = relatedUser;}
	public Community getRelatedCommunity() {return relatedCommunity;}
	public void setRelatedCommunity(Community relatedCommunity) {this.relatedCommunity = relatedCommunity;}
	public UserType getUserType() {return userType;}
	public void setUserType(UserType userType) {this.userType = userType;}
}
