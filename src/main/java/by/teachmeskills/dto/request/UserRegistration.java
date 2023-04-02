package by.teachmeskills.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegistration {

    private String email;
    private String password;
}
