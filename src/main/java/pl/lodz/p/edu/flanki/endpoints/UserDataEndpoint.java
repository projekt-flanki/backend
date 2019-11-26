package pl.lodz.p.edu.flanki.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.edu.flanki.dtos.UserInfoDto;
import pl.lodz.p.edu.flanki.mappers.UserInfoMapper;
import pl.lodz.p.edu.flanki.services.UserService;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("get/{uuid}")
    public ResponseEntity<UserInfoDto> getUserInfoByUuid(@PathVariable UUID uuid) {
        final UserInfoDto userInfo = userInfoMapper.toDto(userService.getUserByUuid(uuid));
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @PostMapping("set-image")
    public ResponseEntity<UserInfoDto> setProfileImage(@RequestBody final UserInfoDto userInfoDto) {
        userService.setProfileImage(userInfoDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("edit")
    public ResponseEntity<UserInfoDto> editUser(@RequestBody final UserInfoDto userInfoDto) {
        userService.editUser(userInfoDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("points-rank")
    public ResponseEntity<List<UserInfoDto>> getPointsRanking() {
        return new ResponseEntity<>(userService.getUsersByPoints(), HttpStatus.OK);
    }

    @GetMapping("rating-rank")
    public ResponseEntity<List<UserInfoDto>> getRatingRanking() {
        return new ResponseEntity<>(userService.getRatingRanking(), HttpStatus.OK);
    }
}
