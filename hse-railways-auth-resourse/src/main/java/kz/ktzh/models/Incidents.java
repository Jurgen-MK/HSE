package kz.ktzh.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Incidents {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer inc_id;
	@Column(name = "user_id")
	Integer userid;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	Date dated;
	String discription;
	Integer content_type;
	String content_path;

	protected Incidents() {
	}

	public Incidents(Integer user_id, Date dated, String discription, Integer content_type, String content_path) {
		this.userid = user_id;
		this.dated = new Date(dated.getTime());
		this.discription = discription;
		this.content_type = content_type;
		this.content_path = content_path;
	}

	public Integer getInc_id() {
		return inc_id;
	}

	public void setInc_id(Integer inc_id) {
		this.inc_id = inc_id;
	}

	public Integer getUser_id() {
		return userid;
	}

	public void setUser_id(Integer user_id) {
		this.userid = user_id;
	}

	public Date getDated() {
		return new Date(dated.getTime());
	}

	public void setDated(Date dated) {
		this.dated = new Date(dated.getTime());
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public Integer getContent_type() {
		return content_type;
	}

	public void setContent_type(Integer content_type) {
		this.content_type = content_type;
	}

	public String getContent_path() {
		return content_path;
	}

	public void setContent_path(String content_path) {
		this.content_path = content_path;
	}

}
