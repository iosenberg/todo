package dev.iosenberg.todo;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import dev.iosenberg.todo.model.Todo;

@SpringBootTest
class TodoApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	@DirtiesContext
	void performsTodoCRUDFunctions() {
		//Does not return Todos with unknown ID
		ResponseEntity<String> response = restTemplate.getForEntity("/quests/0", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();

		//Creates new Todo
		Todo newTodo = new Todo(0L, 0L, "Todo", "Description", false);
		ResponseEntity<Void> createResponse = restTemplate.postForEntity("/todos", newTodo, Void.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfNewQuest = createResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewQuest, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		String name = documentContext.read("$.name");

		assertThat(id).isNotNull();
		assertThat(name).isEqualTo(newTodo.name());

		//Update existing todo
		newTodo = new Todo(0L, 0L, "New name", "New description", false);
		HttpEntity<Todo> request = new HttpEntity<>(newTodo);
		ResponseEntity<Void> updateResponse = restTemplate.exchange("/todos/0", HttpMethod.PUT, request, Void.class);
		assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

		getResponse = restTemplate.getForEntity("/todos/0", String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		documentContext = JsonPath.parse(getResponse.getBody());
		id = documentContext.read("$.id");
		name = documentContext.read("$.name");
		assertThat(id).isEqualTo(0);
		assertThat(name).isEqualTo(newTodo.name());

		//Delete todo
	}
}
