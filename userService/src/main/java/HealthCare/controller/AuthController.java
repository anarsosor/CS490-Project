package HealthCare.controller;

import HealthCare.dto.AuthDTO;
import HealthCare.dto.UserDTO;
import HealthCare.service.JwtService;
import HealthCare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/register")
    ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<UserDTO>(userService.addUser(userDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody AuthDTO authDTO) {
        if(authDTO.getUsername() == null || authDTO.getPassword() == null) {
            throw new RuntimeException("UserName or Password is Empty");
        }
        UserDTO userDTO = userService.checkCredential(authDTO);
        return new ResponseEntity<Map<String, String>>(jwtService.generateToken(userDTO), HttpStatus.OK);
    }
}
