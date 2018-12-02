package com.baron.learning

import com.baron.learning.dto.BaseResponse
import com.baron.learning.dto.UserDTO
import com.baron.learning.repository.UserJpaRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserRegistrationRestController @Autowired constructor(private val userJpaRepository: UserJpaRepository) {

    companion object {
        val logger = LoggerFactory.getLogger(UserRegistrationRestController::class.java)
    }

    /**
     * ResponseEntity: This class extends HttpEntity and is used in the controller method
     * to add the HTTP status to the response. It can contain HTTP status codes, headers, and the body.
     */
    @GetMapping(value = ["/"])
    fun listAllUsers(): ResponseEntity<List<UserDTO>> {
        val users = userJpaRepository.findAll()
        return ResponseEntity(users, HttpStatus.OK)
    }

    /**
     * The createUser method takes a parameter of type UsersDTO annotated with the @RequestBody annotation,
     * which requests Spring to convert the entire request body to an instance of UserDTO.
     *
     * consumes = MediaType.APPLICATION_JSON_VALUE, which indicates this method will accept only
     * JSON data from the request body. You can eliminate this.
     */
    @PostMapping(value = ["/"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createUser(@RequestBody user: UserDTO): ResponseEntity<UserDTO> {
        userJpaRepository.save(user)
        return ResponseEntity(user, HttpStatus.CREATED)
    }

    @GetMapping(value = ["/{id}"])
    fun getUserById(@PathVariable("id") id: Long): ResponseEntity<UserDTO> {
        val user = userJpaRepository.findById(id).get()
        return ResponseEntity(user, HttpStatus.OK)
    }

    @PutMapping(value = ["/{id}"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateUser(@PathVariable("id") id: Long, @RequestBody user: UserDTO): ResponseEntity<Any> {
        if (!userJpaRepository.existsById(id))
            return ResponseEntity(BaseResponse(false, "User with id $id not found."), HttpStatus.NOT_FOUND)
        val curUser = userJpaRepository.findById(id).get()
        if (curUser.name == user.name &&
                curUser.address == user.address &&
                curUser.email == user.email) {
            return ResponseEntity(HttpStatus.NOT_MODIFIED)
        }
        curUser.name = user.name
        curUser.address = user.address
        curUser.email = user.email
        userJpaRepository.saveAndFlush(curUser)
        return ResponseEntity(curUser, HttpStatus.OK)
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteUser(@PathVariable("id") id: Long): ResponseEntity<BaseResponse> {
        val isExist = userJpaRepository.existsById(id)
        return if (isExist) {
            userJpaRepository.deleteById(id)
            ResponseEntity(BaseResponse(true, "Delete succeed"), HttpStatus.OK)
        } else {
            ResponseEntity(BaseResponse(false, "User with id $id not found"), HttpStatus.NOT_FOUND)
        }
    }
}