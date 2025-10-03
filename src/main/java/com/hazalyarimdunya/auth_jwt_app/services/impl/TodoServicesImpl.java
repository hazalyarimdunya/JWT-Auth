package com.hazalyarimdunya.auth_jwt_app.services.impl;

import com.hazalyarimdunya.auth_jwt_app.bean.ModelMapperBean;
import com.hazalyarimdunya.auth_jwt_app.dto.TodoDto;
import com.hazalyarimdunya.auth_jwt_app.entity.TodoEntity;
import com.hazalyarimdunya.auth_jwt_app.entity.UserEntity;
import com.hazalyarimdunya.auth_jwt_app.repository.TodoRepository;
import com.hazalyarimdunya.auth_jwt_app.repository.UserRepository;
import com.hazalyarimdunya.auth_jwt_app.services.interfaces.ITodoServices;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Transactional
    @CacheEvict(value = "todos" , key = "#root.target.getCurrentUsername()")//Hazelcast tarafında da todos isimli bir map oluşuyor.
    public TodoDto objectServiceCreate(TodoDto todoDto) {

        if (todoDto == null) {
            throw new NullPointerException("TodoDto is null");
        }
        // Şu an login olan kullanıcıyı al
        String username = getCurrentUsername();
        log.info("DB'den todolar çekiliyor -> " + username);  // <-- bu satır sadece DB çağrıldığında çalışacak

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
    @Cacheable(value = "todos",key = "#root.target.getCurrentUsername()")
    public List<TodoDto> objectServiceList() {
        String username = getCurrentUsername();
        //repodan verileri entity olarak cektik.
        Iterable<TodoEntity> todoEntities = todoRepository.findByUserEntityUsername(username);
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
        if (id == null) {
            throw new NullPointerException("Id is null");
        }
        else {
            TodoEntity entity = todoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Id not found"));
            return entityToDto(entity);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "todos" , key = "#root.target.getCurrentUsername()")
    public TodoDto objectServiceUpdate(Long id, TodoDto todoDto) {

        String username = getCurrentUsername();

        TodoEntity findEntity = todoRepository.findById(id) //bul
                .orElseThrow(()-> new RuntimeException("Todo not found"));
        if (findEntity != null) {

            //alanlari setle
            findEntity.setTitle(todoDto.getTitle());
            findEntity.setDescription(todoDto.getDescription());
            //repoya kaydet
            todoRepository.save(findEntity);
        }

        return entityToDto(findEntity);
    }

    @Override
    @Transactional
    @CacheEvict(value = "todos" , key = "#root.target.getCurrentUsername()")
    public TodoDto objectServiceDelete( Long id) {
        String username = getCurrentUsername();

        TodoDto findDto = objectServiceFindById(id);//bul
        if (findDto != null) {
            todoRepository.deleteById(id);
        }
        return findDto;
    }


    // Helper method
    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
