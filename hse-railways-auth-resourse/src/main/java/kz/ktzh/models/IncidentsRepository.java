package kz.ktzh.models;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface IncidentsRepository extends CrudRepository<Incidents, Long> {
	List<Incidents> findByUserid(Integer user_id);

}
