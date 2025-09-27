package com.hazalyarimdunya.auth_jwt_app.controller;

import com.hazalyarimdunya.auth_jwt_app.error.ApiResult;
import org.springframework.http.ResponseEntity;

import java.util.List;

// D: Dto
public interface ICrudApi<D> {

    // CREATE
    public ResponseEntity<ApiResult<?>> objectApiCreate(D d);

    // LIST
    public ResponseEntity<ApiResult<List<D>>> objectApiList();

    // FIND BY ID
    public ResponseEntity<ApiResult<?>> objectApiFindById(Long id);

    // UPDATE
    public ResponseEntity<ApiResult<?>> objectApiUpdate(Long id, D d);

    // DELETE
    public ResponseEntity<ApiResult<?>> objectApiDelete(Long id);

}
