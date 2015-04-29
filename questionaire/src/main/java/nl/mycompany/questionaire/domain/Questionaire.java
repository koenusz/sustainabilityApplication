package nl.mycompany.questionaire.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class Questionaire {
	
	@GeneratedValue
	@Id
	private long id;
	
	@ManyToMany(fetch=FetchType.EAGER , cascade=CascadeType.ALL)
	@JoinTable(
		      name="Questionaire_question",
		      joinColumns={@JoinColumn(name="QUESTIONAIRE_ID", referencedColumnName="ID")},
		      inverseJoinColumns={@JoinColumn(name="QUESTION_ID", referencedColumnName="ID")})
	private List<Question> questions;
	
	private String name;
	
	private LocalDateTime creationDate;
	
	@ManyToOne
	@PrimaryKeyJoinColumn
	private Client client;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestion(List<Question> questions) {
		this.questions = questions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
