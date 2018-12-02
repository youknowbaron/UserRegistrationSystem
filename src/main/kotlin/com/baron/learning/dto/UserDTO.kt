package com.baron.learning.dto

import javax.persistence.*

/**
 * The DTO (data transfer object) object contains just data and access modifiers and no logic;
 * it is used to transfer data between different layers of the application
 * when there is a separation of concerns.
 * You can annotate this class with Java Persistence API (JPA) annotations,
 * which allows the Users class to be easily persisted and retrieved using the JPA technology
 */
@Entity
@Table(name = "Users")
data class UserDTO(
        /**
         * The UserDTO’s id property has been annotated with the
         * @Id annotation to make it the primary key. The id attribute has been annotated with the
         * @GeneratedValue annotation to indicate that the id value should be generated automatically
         */
        @Id
        @GeneratedValue
        @Column(name = "USER_ID")
        val id: Long?,

        @Column(name = "NAME")
        var name: String?,

        @Column(name = "ADDRESS")
        var address: String?,

        @Column(name = "EMAIL")
        var email: String?
)