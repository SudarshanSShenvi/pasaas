package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.User;

import java.time.ZonedDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(ZonedDateTime dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByLogin(String login);
    
    @Query("select distinct user from User user where user.login = ?1")
    User findOneByLoginName(String login);

    /*@Query(value = "select distinct user from User user left join fetch user.authorities",
        countQuery = "select count(user) from User user")
    Page<User> findAllWithAuthorities(Pageable pageable);
    
    @Query(value = "select distinct user from User user left join fetch user.organizations",
     countQuery = "select count(user) from User user")
     Page<User> findAllWithOrganizations(Pageable pageable);*/

    @Query(value = "select distinct user from User user left join fetch user.organizations left join fetch user.authorities left join fetch user.projects",
    countQuery = "select count(user) from User user")
    Page<User> findAllWithAuthoritiesProjectsAndOrganizations(Pageable pageable);
    
    Page<User> findByOrganizationsIn(Set organizations, Pageable pageable);


}
