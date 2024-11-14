package com.trongdev.bookservice.command.events;

import com.trongdev.bookservice.command.data.Book;
import com.trongdev.bookservice.command.data.BookRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookEventsHandler {
    @Autowired
    BookRepository bookRepository;

    @EventHandler
    public void on(BookCreatedEvent event){
        Book book = new Book();
        BeanUtils.copyProperties(event,book);
        bookRepository.save(book);
    }
    @EventHandler
    public void on(BookUpdatedEvent event){
        Book book = bookRepository.findById(event.getBookId()).orElseThrow(
                () -> new RuntimeException("Book not exist!")
        );
        book.setAuthor(event.getAuthor());
        book.setName(event.getName());
        book.setIsReady(event.getIsReady());
        bookRepository.save(book);
    }
    @EventHandler
    public void on(BookDeletedEvent event){
        bookRepository.deleteById(event.getBookId());
    }
}
