package by.teachmeskills;

import by.teachmeskills.client.UserApiClient;
import by.teachmeskills.dto.request.UserRegistration;
import by.teachmeskills.dto.response.login.LoginResponse;
import com.github.javafaker.Faker;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class LoginTest {

    private static final String EXISTING_USER_EMAIL = "eve.holt@reqres.in";
    private Faker faker;

    @BeforeClass
    public void setUpData() {
        faker = new Faker();
    }

    @Test
    public void checkSuccessfulLogin() {
        UserRegistration expUserForLogin = new UserRegistration(EXISTING_USER_EMAIL, faker.internet().password());
        LoginResponse expLoginResponse = new UserApiClient().postLoginUser(expUserForLogin, HttpStatus.SC_OK);
        Assertions.assertThat(expLoginResponse.getToken())
                .as("Token should not be null")
                .isNotNull();
    }

    @Test
    public void checkUnsuccessfulLogin() {
        final String expectedError = "Missing password";
        UserRegistration expUserForLogin = new UserRegistration(faker.internet().emailAddress(), faker.internet().password());
        LoginResponse expLoginResponse = new UserApiClient().postLoginUser(Map.of("email", expUserForLogin.getEmail()), HttpStatus.SC_BAD_REQUEST);
        Assertions.assertThat(expLoginResponse.getError())
                .as("Error should be " + expectedError)
                .isEqualTo(expectedError);
    }
}
