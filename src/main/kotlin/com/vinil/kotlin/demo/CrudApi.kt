package com.vinil.kotlin.demo

import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageResource(val service: MessageService) {
    //http://localhost:8080/
   /* @GetMapping
    fun index(): List<Message> = listOf(
        Message("1", "Hello"),
        Message("2", "World"),
        Message("3", "Kotlin SpringBoot"),
    )*/

    @GetMapping
    fun index(): List<Message> = service.findMessages()

    @PostMapping
    fun post(@RequestBody message: Message) {
        service.post(message)
    }
}
//id cant be null
@Table("MESSAGES")
data class Message(@Id val id: String?, val text: String)

interface MessageRepository : CrudRepository<Message, String> {
    @Query("select * from messages")
    fun findMessages(): List<Message>
}

@Service
class MessageService(val db: MessageRepository) {
    fun findMessages(): List<Message> = db.findMessages()
    fun post(message: Message){
        db.save(message)
    }
}
