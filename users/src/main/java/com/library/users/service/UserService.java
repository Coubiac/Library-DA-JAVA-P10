package com.library.users.service;


import com.library.users.model.request.UpdateUserDetailsRequestModel;
import com.library.users.model.response.UserResponseModel;
import com.library.users.shared.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
UserDto createUser(UserDto userDetails);
Boolean deleteUser(String userId);
ResponseEntity<UserResponseModel> updateUser(String userId, UpdateUserDetailsRequestModel userDetails);
ResponseEntity<UserResponseModel> getUser(String userId);
UserDto getUserDetailsByEmail(String email);
Boolean userEmailExist(String email);

}
