package com.trongdev.bookservice.command.controller;

import com.trongdev.bookservice.command.command.CreateBookCommand;
import com.trongdev.bookservice.command.command.DeleteBookCommand;
import com.trongdev.bookservice.command.command.UpdateBookCommand;
import com.trongdev.bookservice.command.model.BookRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String addBook(@RequestBody BookRequestModel model){
        CreateBookCommand command = new CreateBookCommand(
                UUID.randomUUID().toString(),
                model.getName(),
                model.getAuthor(),
                true
        );
        commandGateway.sendAndWait(command);
        return "added Book";
    }

    @PutMapping
    public String updateBook(@RequestBody BookRequestModel model){
        UpdateBookCommand command = new UpdateBookCommand(
                UUID.randomUUID().toString(),
                model.getName(),
                model.getAuthor(),
                model.getIsReady()
        );
        commandGateway.sendAndWait(command);
        return "updated Book";
    }

    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable String bookId){
        DeleteBookCommand command = new DeleteBookCommand(bookId);
        commandGateway.sendAndWait(command);
        return "deleted Book";
    }
}
