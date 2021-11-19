package elena.krunic.elastic.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import elena.krunic.elastic.search.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
