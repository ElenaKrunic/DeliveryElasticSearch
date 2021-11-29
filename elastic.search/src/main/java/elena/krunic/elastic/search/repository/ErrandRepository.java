package elena.krunic.elastic.search.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import elena.krunic.elastic.search.model.Errand;

public interface ErrandRepository extends JpaRepository<Errand, Long> {

	List<Errand> findAllByBuyerId(Long id);

}
