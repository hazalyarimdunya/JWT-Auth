package com.hazalyarimdunya.auth_jwt_app.services.impl;

import com.hazalyarimdunya.auth_jwt_app.bean.ModelMapperBean;
import com.hazalyarimdunya.auth_jwt_app.dto.TodoDto;
import com.hazalyarimdunya.auth_jwt_app.entity.TodoEntity;
import com.hazalyarimdunya.auth_jwt_app.entity.UserEntity;
import com.hazalyarimdunya.auth_jwt_app.repository.TodoRepository;
import com.hazalyarimdunya.auth_jwt_app.repository.UserRepository;
import com.hazalyarimdunya.auth_jwt_app.services.interfaces.ITodoServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
// LOMBOK
@RequiredArgsConstructor
@Log4j2

@Service
public class TodoServicesImpl implements ITodoServices<TodoDto, TodoEntity> {

    //injection
    private final TodoRepository todoRepository;
    private final ModelMapperBean modelMapperBean;
    private final UserRepository userRepository;


    @Override
    public TodoDto entityToDto(TodoEntity todoEntity) {
        return modelMapperBean.modelMapperMethod().map(todoEntity, TodoDto.class);
    }

    @Override
    public TodoEntity dtoToEntity(TodoDto todoDto) {
        return modelMapperBean.modelMapperMethod().map(todoDto, TodoEntity.class);
    }

    @Override
    public TodoDto objectServiceCreate(TodoDto todoDto) {

        if (todoDto == null) {
            throw new NullPointerException("TodoDto is null");
        }
        // Şu an login olan kullanıcıyı al
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // DTO → Entity çevir
        TodoEntity todoEntity = dtoToEntity(todoDto);

        // Token’dan gelen user’ı ilişkilendir
        todoEntity.setUserEntity(user);

        // Kaydet
        todoRepository.save(todoEntity);

        // Entity → DTO güncelle
        todoDto.setId(todoEntity.getId());
        todoDto.setTitle(todoEntity.getTitle());
        todoDto.setDescription(todoEntity.getDescription());
        return todoDto;
    }

    @Override
    public List<TodoDto> objectServiceList() {
        //repodan verileri entity olarak cektik.
        Iterable<TodoEntity> todoEntities = todoRepository.findAll();
        List<TodoDto> todoDtoList = new ArrayList<>(); //dto verilerinin tutulacagi bos array

        //verielri dtoya cevirdik tek tek. Ve bos listemize ekledik
        for(TodoEntity entity : todoEntities){
            TodoDto todoDto = entityToDto(entity);
            todoDtoList.add(todoDto);
        }
        return todoDtoList;
    }

    @Override
    public TodoDto objectServiceFindById(Long id) {
        return null;
    }

    @Override
    public TodoDto objectServiceUpdate(Long id, TodoDto todoDto) {
        return null;
    }

    @Override
    public TodoDto objectServiceDelete(Long id) {
        return null;
    }

}
