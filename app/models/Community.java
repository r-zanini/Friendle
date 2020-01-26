package models;

import io.ebean.Finder;
import io.ebean.Model;
import models.relationships.User_Community;

import javax.persistence.*;
import java.util.List;

@Entity
public class Community extends Model{
	@Id
	private String commNameUnity;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long random;
	@OneToMany(mappedBy = "relatedCommunity")
	private List<User_Community> relatedMemberList;

	public Community(String commNameUnity) {this.commNameUnity = commNameUnity;}

	public static Finder<String, Community> find = new Finder<>(Community.class);

	public String getCommNameUnity() {return commNameUnity;}
	public void setCommNameUnity(String commNameUnity) {this.commNameUnity = commNameUnity;}
	public List<User_Community> getRelatedMemberList() {return relatedMemberList;}
	public void setRelatedMemberList(List<User_Community> relatedMemberList) {this.relatedMemberList = relatedMemberList;}

	@Override
	public String toString() {
		return "Community{" +
				"commNameUnity='" + commNameUnity + '\'' +
				", random=" + random +
				'}';
	}
}
