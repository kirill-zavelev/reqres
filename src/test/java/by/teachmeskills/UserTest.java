package by.teachmeskills;

import by.teachmeskills.client.UserApiClient;
import by.teachmeskills.dto.request.User;
import by.teachmeskills.dto.response.multipleusers.MultipleUserResponse;
import by.teachmeskills.dto.response.singleuser.Data;
import by.teachmeskills.dto.response.singleuser.SingleUser;
import by.teachmeskills.dto.response.singleuser.Support;
import by.teachmeskills.dto.response.user.UserResponse;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UserTest {

    private Faker faker;
    private Data userData;
    private User user;

    @BeforeClass
    public void setUpData() {
        faker = new Faker();
        userData = Data.builder()
                .id(2)
                .email("janet.weaver@reqres.in")
                .first_name("Janet")
                .last_name("Weaver")
                .avatar("https://reqres.in/img/faces/2-image.jpg")
                .build();
        user = new User(faker.name().firstName(), faker.job().position());
    }

    @Test
    public void checkUserCreation() {
        User expUser = new User(faker.name().firstName(), faker.job().position());
        User createdUser = new UserApiClient().postCreateUser(expUser);

        Assertions.assertThat(createdUser).usingRecursiveComparison().ignoringFields("id", "createdAt")
                .isEqualTo(expUser);
    }

    @Test
    public void checkUserUpdate() {
        UserResponse createdUserResponse = new UserApiClient().getResponseOfUserPost(createUser());
        User expUserUpdated = new User(faker.name().firstName(), faker.job().position());
        User userToUpdate = new UserApiClient().putUpdateUser(createdUserResponse.getId(), expUserUpdated);
        Assertions.assertThat(userToUpdate).isEqualTo(expUserUpdated);
    }

    @Test
    public void checkUserDelete() {
        UserResponse createdUserResponse = new UserApiClient().getResponseOfUserPost(createUser());
        Response actualUserResponse = new UserApiClient().deleteUser(createdUserResponse.getId());
        Assertions.assertThat(actualUserResponse.getStatusCode()).isEqualTo(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void checkUserUpdateWithOneParameter() {
        final String updatedUserName = faker.name().firstName();
        UserResponse createdUserResponse = new UserApiClient().getResponseOfUserPost(createUser());
        UserResponse actUserResponse = new UserApiClient()
                .patchUpdateUser(createdUserResponse.getId(), Map.of("name", updatedUserName));
        Assertions.assertThat(actUserResponse.getName()).isEqualTo(updatedUserName);
    }

    @Test
    public void checkGetSingleUser() {
        Support expSupport = new Support("https://reqres.in/#support-heading",
                "To keep ReqRes free, contributions towards server costs are appreciated!");
        SingleUser expUser = new SingleUser(userData, expSupport);
        SingleUser actUser = new UserApiClient().getUser(2, HttpStatus.SC_OK);
        Assertions.assertThat(actUser).as("User's data is not equal").isEqualTo(expUser);
    }

    @Test
    public void checkGetSingleUserNegative() {
        new UserApiClient().getUser(faker.number().numberBetween(500, 1000), HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void checkGetAllUsers() {
        MultipleUserResponse actUsers = new UserApiClient().getUsers();
        Assertions.assertThat(actUsers.getData())
                .as("List should contain " + userData)
                .contains(userData);
    }

    @Test
    public void checkGetAllUsersWithDelay() {
        Awaitility.await()
                .atLeast(3, TimeUnit.SECONDS).until(() ->
                        new UserApiClient().getUsersWithDelay(3).getStatusCode() == HttpStatus.SC_OK);
    }

    private User createUser() {
        return new UserApiClient().postCreateUser(user);
    }
}
