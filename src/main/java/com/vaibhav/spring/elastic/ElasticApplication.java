package com.vaibhav.spring.elastic;

import com.vaibhav.spring.elastic.Data.Book;
import com.vaibhav.spring.elastic.services.BookService;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.Map;
import java.util.UUID;

@SpringBootApplication
public class ElasticApplication implements CommandLineRunner {

	@Autowired
	private ElasticsearchOperations es;

	@Autowired
	private BookService bookService;

	public static void main(String[] args) {
		SpringApplication.run(ElasticApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		printElasticSearchInfo();

		Book book = new Book();
		book.setId(UUID.randomUUID().toString());
		book.setAuthor("Vaibhav Srivastava");
		book.setTitle("Elasticsearch Basics");
		book.setReleaseDate("23-FEB-2017");

        bookService.save(book);

        //bookService.save(new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017"));
		//bookService.save(new Book("1002", "Apache Lucene Basics", "Rambabu Posa", "13-MAR-2017"));
		//bookService.save(new Book("1003", "Apache Solr Basics", "Rambabu Posa", "21-MAR-2017"));

		Page<Book> books = bookService.findByAuthor("Vaibhav Srivastava", new PageRequest(0, 10));

		books.forEach(x -> System.out.println(x));
	}

	private void printElasticSearchInfo() {

		System.out.println("--ElasticSearch--");
		Client client = es.getClient();
		Map<String, String> asMap = client.settings().getAsMap();

		asMap.forEach((k, v) -> {
			System.out.println(k + " = " + v);
		});
		System.out.println("--ElasticSearch--");
	}
}
