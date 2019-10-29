package pl.lodz.p.edu.flanki.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.edu.flanki.dtos.UserLoginDto;
import pl.lodz.p.edu.flanki.dtos.UserRegisterDto;
import pl.lodz.p.edu.flanki.services.AuthorizationService;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class AuthUserEndpoint {

    private final AuthorizationService userService;

    @Autowired
    public AuthUserEndpoint(final AuthorizationService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody final UserRegisterDto userRegisterDto) {
        userService.register(userRegisterDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody final UserLoginDto userLoginDto) {
        final String responseData = userService.login(userLoginDto);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}