package br.com.testsusecase.tests.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.testsusecase.tests.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findUserById(Long id);

  @Query(value = "SELECT obj FROM User obj where obj.email=:email")
  Optional<User> findByEmail(String email);
}
