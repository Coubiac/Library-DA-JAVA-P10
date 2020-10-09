package com.library.users.controller;



import com.library.users.error.UserServiceException;
import com.library.users.model.request.UpdateUserDetailsRequestModel;
import com.library.users.model.request.UserDetailsRequestModel;
import com.library.users.model.response.UserResponseModel;
import com.library.users.service.UserService;
import com.library.users.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private Environment env;

    @GetMapping("/status/check")
    public String Status()
    {
        return "Working on port " + env.getProperty("local.server.port");
    }

    @GetMapping
    public String getUsers(@RequestParam(value="page", defaultValue = "1") int page,
                           @RequestParam(value="limit", defaultValue = "50") int limit,
                            @RequestParam(value="sort", defaultValue = "desc", required = false) String sort )
    {
        return "get users was called with page = " + page + " and limit = " + limit + "and sort " + sort;
    }

    @GetMapping(path="/{userId}",
            produces = {
                MediaType.APPLICATION_XML_VALUE,
                MediaType.APPLICATION_JSON_VALUE
            } )
    public ResponseEntity<UserResponseModel> getUser(@PathVariable String userId){
        return userService.getUser(userId);
    }

    @PostMapping(
            consumes = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    } )
    public ResponseEntity createUser(@Valid @RequestBody UserDetailsRequestModel userDetails)
    {
        if (userService.userEmailExist(userDetails.getEmail())){
            throw  new UserServiceException(userDetails.getEmail()+" already exists");
        }


        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserResponseModel userResponseModel = modelMapper.map(userService.createUser(userDto), UserResponseModel.class);
        return new ResponseEntity(userResponseModel, HttpStatus.CREATED);
    }

    @PutMapping(path="/{userId}",
            consumes = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE },
            produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserResponseModel> updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserDetailsRequestModel userDetails){
        return userService.updateUser(userId, userDetails);
    }

    @DeleteMapping(path="/{userId}")
    public ResponseEntity deleteUser(@PathVariable String userId){

        if (userService.deleteUser(userId)){
            return ResponseEntity.noContent().build();
        }
        else return ResponseEntity.notFound().build();
    }
}
