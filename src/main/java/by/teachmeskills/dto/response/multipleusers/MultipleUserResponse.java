package by.teachmeskills.dto.response.multipleusers;

import by.teachmeskills.dto.response.singleuser.Data;
import lombok.AllArgsConstructor;

import java.util.List;

@lombok.Data
@AllArgsConstructor
public class MultipleUserResponse {

    private List<Data> data;
}
