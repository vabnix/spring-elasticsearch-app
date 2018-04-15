package com.vaibhav.spring.elastic.services;

import com.vaibhav.spring.elastic.Data.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Before
    public void before() {
        esTemplate.deleteIndex(Book.class);
        esTemplate.createIndex(Book.class);
        esTemplate.putMapping(Book.class);
        esTemplate.refresh(Book.class);
    }

    @Test
    public void testSave() {

        Book book = new Book("1001", "Elasticsearch Basics", "Vaibhav Srivastava", "23-FEB-2017");
        Book testBook = bookService.save(book);

        /*assertNotNull(testBook.getId());
        assertEquals(testBook.getTitle(), book.getTitle());
        assertEquals(testBook.getAuthor(), book.getAuthor());
        assertEquals(testBook.getReleaseDate(), book.getReleaseDate());*/

    }

    @Test
    public void testFindByTitle() {

        Book book = new Book("1001", "Elasticsearch Basics", "Vaibhav Srivastava", "23-FEB-2017");
        bookService.save(book);

//        List<Book> byTitle = bookService.findByTitle(book.getTitle());
    }

    @Test
    public void testFindByAuthor() {

        List<Book> bookList = new ArrayList<>();

        bookList.add(new Book("1001", "Elasticsearch Basics", "Vaibhav Srivastava", "23-FEB-2017"));
        bookList.add(new Book("1002", "Apache Lucene Basics", "Vaibhav Srivastava", "13-MAR-2017"));
        bookList.add(new Book("1003", "Apache Solr Basics", "Vaibhav Srivastava", "21-MAR-2017"));
        bookList.add(new Book("1007", "Spring Data + ElasticSearch", "Vaibhav Srivastava", "01-APR-2017"));
        bookList.add(new Book("1008", "Spring Boot + MongoDB", "Dallas", "25-FEB-2017"));

        for (Book book : bookList) {
            bookService.save(book);
        }

        Page<Book> byAuthor = bookService.findByAuthor("Vaibhav Srivastava", new PageRequest(0, 10));

        Page<Book> byAuthor2 = bookService.findByAuthor("Dallas", new PageRequest(0, 10));

    }

    @Test
    public void testDelete() {

        Book book = new Book("1001", "Elasticsearch Basics", "Vaibhav Srivastava", "23-FEB-2017");
        bookService.save(book);
        bookService.delete(book);
       // Book testBook = bookService.findOne(book.getId());
       // assertNull(testBook);
    }
}
