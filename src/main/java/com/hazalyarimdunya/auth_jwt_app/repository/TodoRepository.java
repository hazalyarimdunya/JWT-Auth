package com.hazalyarimdunya.auth_jwt_app.repository;

import com.hazalyarimdunya.auth_jwt_app.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
