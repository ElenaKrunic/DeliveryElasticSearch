package elena.krunic.elastic.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import elena.krunic.elastic.search.model.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long>{

	Seller findByName(String name);

}
