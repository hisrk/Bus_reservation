package com.reservation.controller;
import com.reservation.model.User;
import com.reservation.payload.UserDto;
import com.reservation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {


     @Autowired
    private UserService userService;

     @PostMapping
    public String saveUser(@RequestParam("name") String name,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,


             @RequestParam("profilePicture")MultipartFile profilePicture) throws IOException {

            User user= new User();

            user.setName(name);
            user.setEmail(email);

            user.setPassword(password);

            user.setProfilePicture(profilePicture.getBytes());

            user.setProfilePictureName(profilePicture.getOriginalFilename());




         userService.saveUser(user);

           return "user saved ";

     }

     @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){

      UserDto userDto=userService.getUserByid(id);



    return ResponseEntity.ok(userDto);

    }





}
