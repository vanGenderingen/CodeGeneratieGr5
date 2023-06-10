package io.swagger.api.repository;

import io.swagger.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE (:email IS NULL OR LOWER(u.email) LIKE CONCAT('%', LOWER(:email), '%')) AND (:searchStrings IS NULL OR LOWER(u.firstName) LIKE CONCAT('%', LOWER(:searchStrings), '%') OR LOWER(u.lastName) LIKE CONCAT('%', LOWER(:searchStrings), '%') OR LOWER(u.email) LIKE CONCAT('%', LOWER(:searchStrings), '%'))")
    List<User> getAll(String email, String searchStrings, Pageable pageable);
    User getUserByUserID(UUID userID);
    User getUserByEmail(String email);
}
