package nl.mycompany.core.domain.repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends
		Repository<T, ID> {

	void delete(T deleted);
	
	void deleteAll();

	List<T> findAll();

	Optional<T> findOne(ID id);

	T save(T persisted);
}