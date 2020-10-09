package com.library.users.data;


import org.springframework.data.repository.CrudRepository;

public interface UserRespository extends CrudRepository<UserEntity, Long> {
    public UserEntity findUserEntitiesByUserId(String userId);
    public UserEntity findByEmail(String email);
}
