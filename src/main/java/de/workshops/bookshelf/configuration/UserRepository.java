package de.workshops.bookshelf.configuration;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
}
