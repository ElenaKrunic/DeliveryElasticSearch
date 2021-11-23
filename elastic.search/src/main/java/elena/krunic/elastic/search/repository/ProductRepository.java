package elena.krunic.elastic.search.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import elena.krunic.elastic.search.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findProductsBySellerId(Long id);

}
