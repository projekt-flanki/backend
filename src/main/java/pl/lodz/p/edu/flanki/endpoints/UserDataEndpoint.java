package pl.lodz.p.edu.flanki.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.edu.flanki.dtos.UserInfoDto;
import pl.lodz.p.edu.flanki.mappers.UserInfoMapper;
import pl.lodz.p.edu.flanki.services.AuthorizationService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserDataEndpoint {

    private final AuthorizationService authorizationService;
    private final UserInfoMapper userInfoMapper;

    @Autowired
    public UserDataEndpoint(final AuthorizationService authorizationService, final UserInfoMapper userInfoMapper) {
        this.authorizationService = authorizationService;
        this.userInfoMapper = userInfoMapper;
    }

    @GetMapping("info")
    public ResponseEntity<UserInfoDto> getUserInfo() {
        final UserInfoDto userInfo = userInfoMapper.toDto(authorizationService.getUser());
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }
}
