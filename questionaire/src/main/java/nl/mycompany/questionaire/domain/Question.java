package nl.mycompany.questionaire.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity
public class Question {
	
	@GeneratedValue
	@Id
	private long id;
	
	private String questionText;
	
	private String domain;
	
	private String auditResult;
	
	private String answer;
	
	@ManyToMany(mappedBy="questions", fetch=FetchType.EAGER)
	private List<Questionaire> questionaires;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public List<Questionaire> getQuestionaires() {
		return questionaires;
	}

	public void setQuestionaire(List<Questionaire> questionaires) {
		this.questionaires = questionaires;
	}

	
}
