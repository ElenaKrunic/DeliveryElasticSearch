package elena.krunic.elastic.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import elena.krunic.elastic.search.model.Buyer;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {

	Buyer findByAddress(String address);

	Buyer findByUsername(String name);

}
