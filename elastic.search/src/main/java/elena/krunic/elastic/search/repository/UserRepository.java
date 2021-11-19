package elena.krunic.elastic.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import elena.krunic.elastic.search.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
