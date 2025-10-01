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
    private final ITodoServices iTodoServices;
    private final ObjectMapper  objectMapper;

    @Override
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<?>> objectApiCreate(@Valid @RequestBody TodoDto todoDto) {

        try {
            TodoDto createdDto = (TodoDto) iTodoServices.objectServiceCreate(todoDto);
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
            List<TodoDto> list = iTodoServices.objectServiceList();
            return ResponseEntity.ok(ApiResult.success(list));
        }
        catch (Exception e) {
            return ResponseEntity.ok(ApiResult.error("Server error",e.getMessage(),"/todo/list"));
        }
    }


    @Override
    @GetMapping(value = "/find/{id}")
    public ResponseEntity<ApiResult<?>> objectApiFindById(@PathVariable(name = "id")  Long id) {
        try {
            if (id == null) {
                return ResponseEntity.ok(ApiResult.notFound("null", "/todo/findById"));
            }
            TodoDto found = (TodoDto) iTodoServices.objectServiceFindById(id);
            return ResponseEntity.ok(ApiResult.success(found));
        } catch (Exception ex) {
            return ResponseEntity.ok(ApiResult.error("serverError", ex.getMessage(), "/todo/findById"));
        }
    }

    @Override
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ApiResult<?>> objectApiUpdate(@PathVariable(name = "id") Long id, @Valid @RequestBody TodoDto todoDto) {
        try {
            TodoDto updatedDto = (TodoDto) iTodoServices.objectServiceUpdate(id, todoDto);
            return ResponseEntity.ok(ApiResult.success(updatedDto));
        }catch (Exception ex) {
            return ResponseEntity.ok(ApiResult.error("Server Error", ex.getMessage(),"/update/id"));
        }

    }

    @Override
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<ApiResult<?>> objectApiDelete(@PathVariable(name = "id") Long id) {
        try {
            TodoDto deletedDto = (TodoDto) iTodoServices.objectServiceDelete(id);
            return ResponseEntity.ok(ApiResult.success(deletedDto));
        }catch (Exception ex) {
            return ResponseEntity.ok(ApiResult.error("Server Error", ex.getMessage(),"/delete/id"));
        }

    }
}
