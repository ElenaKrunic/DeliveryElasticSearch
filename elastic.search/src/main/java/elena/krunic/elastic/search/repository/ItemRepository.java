package elena.krunic.elastic.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import elena.krunic.elastic.search.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
