package com.hazalyarimdunya.auth_jwt_app.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazalyarimdunya.auth_jwt_app.controller.interfaces.ITodoController;
import com.hazalyarimdunya.auth_jwt_app.dto.TodoDto;
import com.hazalyarimdunya.auth_jwt_app.error.ApiResult;
import com.hazalyarimdunya.auth_jwt_app.repository.TodoRepository;
import com.hazalyarimdunya.auth_jwt_app.services.impl.TodoServicesImpl;
import com.hazalyarimdunya.auth_jwt_app.services.interfaces.ITodoServices;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor

@RestController
//@CrossOrigin(origins = FrontEnd.REACT_URL) // http://localhost:3000
@RequestMapping("/todo")
public class TodoControllerImpl implements ITodoController<TodoDto> {

    @Autowired
    private final TodoServicesImpl todoServices;
    private final ObjectMapper  objectMapper;

    @Override
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<?>> objectApiCreate(@Valid @RequestBody TodoDto todoDto) {

        try {
            TodoDto createdDto = (TodoDto) todoServices.objectServiceCreate(todoDto);
            return ResponseEntity.ok(ApiResult.success(createdDto));
        }
        catch (Exception e) {
            return ResponseEntity.ok(ApiResult.error("Server error",e.getMessage(),"/todo/create"));
        }
    }

    @Override
    @GetMapping(value = "/list")
    public ResponseEntity<ApiResult<List<TodoDto>>> objectApiList() {
        try {
            List<TodoDto> list = todoServices.objectServiceList();
            return ResponseEntity.ok(ApiResult.success(list));
        }
        catch (Exception e) {
            return ResponseEntity.ok(ApiResult.error("Server error",e.getMessage(),"/todo/list"));
        }

    }


    @Override
    public ResponseEntity<ApiResult<?>> objectApiFindById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResult<?>> objectApiUpdate(Long id, TodoDto todoDto) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResult<?>> objectApiDelete(Long id) {
        return null;
    }
}
