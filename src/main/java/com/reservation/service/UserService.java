package com.reservation.service;

import com.reservation.exception.ResourceNotFoundException;
import com.reservation.model.User;
import com.reservation.payload.UserDto;
import com.reservation.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {

          return  userRepository.save(user);

    }

    public UserDto  getUserByid(Long id) {

           User user= userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("id not Found"));

           UserDto userDto= new UserDto();

             userDto.setName(user.getName()) ;

                   userDto.setEmail(user.getEmail());

                   userDto.setProfilePictureName(user.getProfilePictureName());


return userDto;

    }
}
