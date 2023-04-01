package by.teachmeskills.client;

import by.teachmeskills.dto.request.User;
import by.teachmeskills.dto.request.UserRegistration;
import by.teachmeskills.dto.response.login.LoginResponse;
import by.teachmeskills.dto.response.registration.RegistrationResponse;
import by.teachmeskills.dto.response.user.UserResponse;
import by.teachmeskills.dto.response.multipleusers.MultipleUserResponse;
import by.teachmeskills.dto.response.singleuser.SingleUser;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;

import java.util.Map;

@Log4j2
public class UserApiClient extends BaseApiClient {

    private static final String REGISTRATION_PATH = "/api/register";
    private static final String LOGIN_PATH = "/api/login";
    private static final String CREATION_PATH = "/api/users";
    private static final String USER_ID = "userId";
    private static final String USER_ID_PATH = "/api/users/{userId}";

    public RegistrationResponse postRegisterUser(UserRegistration body, int statusCode) {
        log.info("POST " + REGISTRATION_PATH);
        return post(REGISTRATION_PATH, body)
                .then()
                .statusCode(statusCode)
                .extract()
                .body()
                .as(RegistrationResponse.class);
    }

    public User postCreateUser(User body) {
        log.info("POST " + CREATION_PATH);
        return post(CREATION_PATH, body)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .body()
                .as(User.class);
    }

    public UserResponse getResponseOfUserPost(User body) {
        log.info("Response of POST " + CREATION_PATH);
        return post(CREATION_PATH, body)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .body()
                .as(UserResponse.class);
    }

    public User putUpdateUser(long id, User body) {
        log.info("PUT " + USER_ID_PATH);
        return put(USER_ID_PATH, Map.of(USER_ID, id), body)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(User.class);
    }

    public Response deleteUser(long id) {
        log.info("DELETE " + USER_ID_PATH);
        return delete(USER_ID_PATH, Map.of(USER_ID, id));
    }

    public LoginResponse postLoginUser(UserRegistration body, int statusCode) {
        log.info("POST " + LOGIN_PATH);
        return post(LOGIN_PATH, body)
                .then()
                .statusCode(statusCode)
                .extract()
                .body()
                .as(LoginResponse.class);
    }

    public LoginResponse postLoginUser(Map<String, String> map, int statusCode) {
        log.info("POST " + LOGIN_PATH);
        return post(LOGIN_PATH, map)
                .then()
                .statusCode(statusCode)
                .extract()
                .body()
                .as(LoginResponse.class);
    }

    public UserResponse patchUpdateUser(long id, Map<String, String> map) {
        log.info("PATCH " + USER_ID_PATH);
        return patch(USER_ID_PATH, Map.of(USER_ID, id), map)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(UserResponse.class);
    }

    public SingleUser getUser(long id, int statusCode) {
        log.info("GET " + USER_ID_PATH);
        return get(USER_ID_PATH, Map.of(USER_ID, id))
                .then()
                .statusCode(statusCode)
                .extract()
                .body()
                .as(SingleUser.class);
    }

    public Response getUsersWithDelay(int delay) {
        log.info("GET " + USER_ID_PATH);
        return getWithDelay(CREATION_PATH, delay);
    }

    public MultipleUserResponse getUsers() {
        log.info("GET all users");
        return get(CREATION_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(MultipleUserResponse.class);
    }
}
