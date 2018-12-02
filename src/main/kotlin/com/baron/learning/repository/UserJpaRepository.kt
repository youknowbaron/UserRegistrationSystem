package com.baron.learning.repository

import com.baron.learning.dto.UserDTO
import org.springframework.data.jpa.repository.JpaRepository

/**
 * The benefit of Spring Data JPA is that developers do not have to
 * write implementations of the repository interface. Spring Data JPA creates
 * an implementation at runtime when you run the application.
 * The UserJpaRepository interface extends Spring Data’s JpaRepository,
 * which takes the type of domain object that it can manipulate and
 * the type of UserDTO domain object’s identifier field, UserDTO and Long,
 * as its generic parameters, T and ID. UserJpaRepository inherits
 * all of JpaRepository’s CRUD methods for working with UserDTO persistence.
 */
interface UserJpaRepository : JpaRepository<UserDTO, Long> {

    fun findByName(name: String): UserDTO
}