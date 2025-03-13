package com.groweasy.groweasyapi.loginregister.facade;

import com.groweasy.groweasyapi.loginregister.model.entities.UserEntity;

public interface AuthenticationFacade {

    UserEntity getCurrentUser();
}
