package com.library.users.service.impl;

import com.library.users.data.UserEntity;
import com.library.users.data.UserRespository;
import com.library.users.model.request.UpdateUserDetailsRequestModel;
import com.library.users.model.response.UserResponseModel;
import com.library.users.service.UserService;
import com.library.users.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRespository userRespository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        userRespository.save(userEntity);

        return userDetails;
    }


    @Override
    public Boolean deleteUser(String userId) {
        UserEntity theUser = userRespository.findUserEntitiesByUserId(userId);
        if(theUser != null){
            userRespository.delete(theUser);
            return true;
        }
        return false;
    }

    @Override
    public ResponseEntity<UserResponseModel> updateUser(String userId, UpdateUserDetailsRequestModel userDetails) {
        UserEntity theUser = userRespository.findUserEntitiesByUserId(userId);
        if(theUser == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        theUser.setFirstName(userDetails.getFirstName());
        theUser.setLastName(userDetails.getLastName());
        theUser.setAddress(userDetails.getAddress());
        theUser.setPostalCode(userDetails.getPostalCode());
        theUser.setCity(userDetails.getCity());
        userRespository.save(theUser);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserResponseModel responseBody = modelMapper.map(theUser, UserResponseModel.class);
        return new ResponseEntity<>(responseBody, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<UserResponseModel> getUser(String userId) {
        UserEntity theUser = userRespository.findUserEntitiesByUserId(userId);
        if(theUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            UserResponseModel responseBody = modelMapper.map(theUser, UserResponseModel.class);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRespository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);


        return new User(userEntity.getEmail(),
                userEntity.getEncryptedPassword(),
                true,
                true,
                true,
                true, new ArrayList<>());
    }

    public UserDto getUserDetailsByEmail(String email){
        UserEntity userEntity = userRespository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);


        return new ModelMapper().map(userEntity, UserDto.class);
    }

    public Boolean userEmailExist(String email){
        return userRespository.findByEmail(email) != null;
    }
}
