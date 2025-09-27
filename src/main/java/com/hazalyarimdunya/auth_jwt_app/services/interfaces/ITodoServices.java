package com.hazalyarimdunya.auth_jwt_app.services.interfaces;

import com.hazalyarimdunya.auth_jwt_app.services.ICrudService;
import com.hazalyarimdunya.auth_jwt_app.services.IModelMapperService;

public interface ITodoServices<D,E> extends IModelMapperService<D,E>, ICrudService<D,E> {
}
