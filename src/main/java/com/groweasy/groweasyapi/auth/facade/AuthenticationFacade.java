package com.groweasy.groweasyapi.auth.facade;

import com.groweasy.groweasyapi.auth.model.entities.UserEntity;

public interface AuthenticationFacade {

    UserEntity getCurrentUser();
}
