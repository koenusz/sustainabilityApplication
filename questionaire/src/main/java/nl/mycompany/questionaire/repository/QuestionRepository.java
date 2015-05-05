package nl.mycompany.questionaire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nl.mycompany.core.domain.repositories.BaseRepository;
import nl.mycompany.questionaire.domain.Question;
import nl.mycompany.questionaire.domain.Questionaire;

public interface QuestionRepository extends BaseRepository<Question, Long> {
	
	public final static String FIND_BY_QUESTION_QUERY = "SELECT q FROM Question q LEFT JOIN q.questionaires qq "
            + "WHERE :questionaire NOT IN qq  OR qq = null";
	
	
	@Query(FIND_BY_QUESTION_QUERY)
	public List<Question> findNotInQuestionaire(@Param("questionaire") Questionaire questionaire);
	
	

}
