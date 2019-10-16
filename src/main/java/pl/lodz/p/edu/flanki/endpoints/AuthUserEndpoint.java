package pl.lodz.p.edu.flanki.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.edu.flanki.dtos.UserLoginDto;
import pl.lodz.p.edu.flanki.dtos.UserRegisterDto;
import pl.lodz.p.edu.flanki.services.UserService;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class AuthUserEndpoint {

    private UserService userService;

    @Autowired
    public AuthUserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        userService.register(userRegisterDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginDto userLoginDto) {
        String responseData = userService.login(userLoginDto);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}