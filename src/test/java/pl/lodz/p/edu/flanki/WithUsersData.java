package pl.lodz.p.edu.flanki;

import pl.lodz.p.edu.flanki.entities.User;
import java.util.Collections;
import java.util.UUID;

public interface WithUsersData {

    default User getExampleUser(){
        final UUID DUMMY_ID = UUID.randomUUID();
        final String DUMMY_EMAIL = "DUMMY_EMAIL@DUMMY.COM";
        final String DUMMY_PASSWORD = "DUMMY_PASSWORD";
        final String DUMMY_USERNAME = "DUMMY_PASSWORD";

        return User.builder()
                .id(DUMMY_ID)
                .email(DUMMY_EMAIL)
                .password(DUMMY_PASSWORD)
                .username(DUMMY_USERNAME)
                .build();
    }
}
