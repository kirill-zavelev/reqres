package by.teachmeskills.dto.response.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;

@lombok.Data
@AllArgsConstructor
@Builder
public class Data {

    private long id;
    private String name;
    private long year;
    private String color;
    private String pantone_value;
}
