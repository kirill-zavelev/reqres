package by.teachmeskills.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserResponse {

    private String name;
    private String job;
    private long id;
    private Date createdAt;
}
