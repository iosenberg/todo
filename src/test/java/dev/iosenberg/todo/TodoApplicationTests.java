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

import dev.iosenberg.todo.models.Todo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
	}
	
	@Test
	void getsAllTodos() {
		ResponseEntity<String> response = restTemplate.getForEntity("/todos", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int todoCount = documentContext.read("$.length()");
		assertThat(todoCount).isEqualTo(0);
	}

	@Test
	void getsTodoWithKnownId() {
		ResponseEntity<String> response = restTemplate.getForEntity("/todos/0", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");
		String name = documentContext.read("$.name");
		assertThat(id).isEqualTo(0);
		assertThat(name).isEqualTo("Todo");
	}

	@Test
	void doesNotGetTodoWithUnknownId() {
		ResponseEntity<String> response = restTemplate.getForEntity("/todos/666", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
	}

	@Test
	@DirtiesContext
	void createsNewTodo() {
		Todo newTodo = new Todo(null, 0L, "Todo", "Description", false);
		ResponseEntity<Void> createResponse = restTemplate.postForEntity("/todos", newTodo, Void.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfNewTodo = createResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewTodo, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		String name = documentContext.read("$.name");

		assertThat(id).isNotNull();
		assertThat(name).isEqualTo(newTodo.name());
	}

	@Test
	@DirtiesContext
	void updatesExistingTodo() {
		Todo newTodo = new Todo(0L, 0L, "New name", "New description", false);
		HttpEntity<Todo> request = new HttpEntity<>(newTodo);
		ResponseEntity<Void> updateResponse = restTemplate.exchange("/todos/0", HttpMethod.PUT, request, Void.class);
		assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

		ResponseEntity<String> getResponse = restTemplate.getForEntity("/todos/0", String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		String name = documentContext.read("$.name");
		assertThat(id).isEqualTo(0);
		assertThat(name).isEqualTo(newTodo.name());
	}

	@Test
	@DirtiesContext
	void deletesTodoWithKnownId() {
		ResponseEntity<Void> deleteResponse = restTemplate.exchange("/todos/0", HttpMethod.DELETE, null, Void.class);
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

		ResponseEntity<String> getResponse = restTemplate.getForEntity("/todos/0", String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void doesNotDeleteTodoWithUnknownId() {
		ResponseEntity<Void> deleteResponse = restTemplate.exchange("/todos/666", HttpMethod.DELETE, null, Void.class);
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
}
