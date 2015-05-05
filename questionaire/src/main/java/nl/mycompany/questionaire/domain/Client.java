package nl.mycompany.questionaire.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Client {

	@Id 
	@NotNull
	String name;
	
	@OneToMany
	List<Questionaire> questionaires;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Questionaire> getQuestionaires() {
		return questionaires;
	}

	public void setQuestionaires(List<Questionaire> questionaires) {
		this.questionaires = questionaires;
	}


	
}
