package com.trongdev.bookservice.command.aggregate;

import com.trongdev.bookservice.command.command.CreateBookCommand;
import com.trongdev.bookservice.command.command.DeleteBookCommand;
import com.trongdev.bookservice.command.command.UpdateBookCommand;
import com.trongdev.bookservice.command.events.BookCreatedEvent;
import com.trongdev.bookservice.command.events.BookDeletedEvent;
import com.trongdev.bookservice.command.events.BookUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class BookAggregate {
    @AggregateIdentifier
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;

    public BookAggregate(){}

    @CommandHandler
    public BookAggregate(CreateBookCommand createBookCommand){
        BookCreatedEvent bookCreatedEvent = new BookCreatedEvent();
        BeanUtils.copyProperties(createBookCommand, bookCreatedEvent);
        AggregateLifecycle.apply(bookCreatedEvent);
    }
    @CommandHandler
    public void handle(UpdateBookCommand updateBookCommand){
        BookUpdatedEvent bookUpdatedEvent = new BookUpdatedEvent();
        BeanUtils.copyProperties(updateBookCommand, bookUpdatedEvent);
        AggregateLifecycle.apply(bookUpdatedEvent);
    }
    @CommandHandler
    public void handle(DeleteBookCommand deleteBookCommand){
        BookDeletedEvent bookDeletedEvent = new BookDeletedEvent();
        BeanUtils.copyProperties(deleteBookCommand, bookDeletedEvent);
        AggregateLifecycle.apply(bookDeletedEvent);
    }

    @EventSourcingHandler
    public void on(BookCreatedEvent event){
        this.bookId = event.getBookId();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
        this.name = event.getName();
    }
    @EventSourcingHandler
    public void on(BookUpdatedEvent event){
        this.bookId = event.getBookId();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
        this.name = event.getName();
    }
    @EventSourcingHandler
    public void on(BookDeletedEvent event){
        this.bookId = event.getBookId();
    }
}
