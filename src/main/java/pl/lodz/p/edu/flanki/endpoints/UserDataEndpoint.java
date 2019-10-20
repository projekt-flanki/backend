package pl.lodz.p.edu.flanki.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.edu.flanki.dtos.UserInfoDto;
import pl.lodz.p.edu.flanki.mappers.UserInfoMapper;
import pl.lodz.p.edu.flanki.services.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserDataEndpoint {

    private final UserService userService;

    private final UserInfoMapper userInfoMapper;

    @Autowired
    public UserDataEndpoint(final UserService userService, final UserInfoMapper userInfoMapper) {
        this.userService = userService;
        this.userInfoMapper = userInfoMapper;
    }

    @GetMapping("info")
    public ResponseEntity<UserInfoDto> getUserInfo() {
        final UserInfoDto userInfo = userInfoMapper.toDto(userService.getUser());
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }
}
