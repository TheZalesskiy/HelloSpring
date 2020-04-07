package io.zalesskiy.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
class TodoServlet {
    private final Logger logger = LoggerFactory.getLogger(TodoServlet.class);

    private TodoRepository todoRepository;

    TodoServlet(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    @GetMapping
    ResponseEntity<List<Todo>> findAllTodos() {
        return ResponseEntity.ok(todoRepository.findAll());

    }

    @PutMapping({"/{id}"})
    ResponseEntity<Todo> toggleTodo(@PathVariable Integer id) {
        Optional<Todo> todo = todoRepository.findById(id);
        todo.ifPresent(t -> {
            t.setDone(!t.isDone());
            todoRepository.save(t);
        });
        return todo.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    ResponseEntity<Todo> saveTodo(@RequestBody Todo todo) {

        return ResponseEntity.ok(todoRepository.save(todo));
    }
}
