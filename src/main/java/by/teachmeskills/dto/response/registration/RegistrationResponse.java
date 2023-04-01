package by.teachmeskills.dto.response.registration;

import lombok.Data;

@Data
public class RegistrationResponse {

    private long id;
    private String token;
    private String error;
}
