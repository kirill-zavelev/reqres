package by.teachmeskills.dto.response.singleuser;

import lombok.AllArgsConstructor;
import lombok.Builder;

@lombok.Data
@AllArgsConstructor
@Builder
public class Data {
    private long id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;
}
