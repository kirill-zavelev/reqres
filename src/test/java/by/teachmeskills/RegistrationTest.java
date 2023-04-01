package by.teachmeskills;

import by.teachmeskills.client.UserApiClient;
import by.teachmeskills.dto.request.UserRegistration;
import by.teachmeskills.dto.response.registration.RegistrationResponse;
import com.github.javafaker.Faker;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RegistrationTest {

    private Faker faker;
    private static final String EXISTING_USER_EMAIL = "eve.holt@reqres.in";

    @BeforeClass
    public void setUpData() {
        faker = new Faker();
    }

    @Test
    public void checkSuccessfullRegistration() {
        UserRegistration expUserRegistration = new UserRegistration(EXISTING_USER_EMAIL, faker.internet().password());
        RegistrationResponse registrationResponse = new UserApiClient()
                .postRegisterUser(expUserRegistration, HttpStatus.SC_OK);

        Assertions.assertThat(registrationResponse.getId()).as("Id should not be null").asString().isNotNull();
        Assertions.assertThat(registrationResponse.getToken()).as("Token should not be null").isNotNull();
    }

    @Test
    public void checkUnsuccessfulRegistration() {
        UserRegistration expUserRegistration = new UserRegistration(faker.internet().emailAddress(), faker.internet().password());
        RegistrationResponse registrationResponse = new UserApiClient()
                .postRegisterUser(expUserRegistration, HttpStatus.SC_BAD_REQUEST);
        final String expError = "Note: Only defined users succeed registration";

        Assertions.assertThat(registrationResponse.getError()).as("Error should be " + expError).isEqualTo(expError);
    }
}
