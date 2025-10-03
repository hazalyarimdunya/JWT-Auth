package com.hazalyarimdunya.auth_jwt_app.repository;

import com.hazalyarimdunya.auth_jwt_app.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

    // user entity üzerinden username e göre todos listesi çek
    List<TodoEntity> findByUserEntityUsername(String username);
}
