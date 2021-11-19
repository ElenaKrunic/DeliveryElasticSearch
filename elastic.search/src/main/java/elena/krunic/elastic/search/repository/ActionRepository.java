package elena.krunic.elastic.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import elena.krunic.elastic.search.model.Action;

public interface ActionRepository extends JpaRepository<Action, Long> {

}
