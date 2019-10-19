package pl.lodz.p.edu.flanki.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.edu.flanki.dtos.UserInfoDto;
import pl.lodz.p.edu.flanki.services.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserDataEndpoint {

    private final UserService userService;

    @Autowired
    public UserDataEndpoint(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("info")
    public ResponseEntity<UserInfoDto> getUserInfo() {
        final UserInfoDto userInfo = userService.getUserInfo();
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }
}
