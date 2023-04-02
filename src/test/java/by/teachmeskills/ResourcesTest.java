package by.teachmeskills;

import by.teachmeskills.client.ResourceApiClient;
import by.teachmeskills.dto.response.resource.Data;
import by.teachmeskills.dto.response.resource.MultipleResources;
import by.teachmeskills.dto.response.resource.SingleResource;
import by.teachmeskills.dto.response.resource.Support;
import com.github.javafaker.Faker;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ResourcesTest {

    private Data resourceData;

    @BeforeClass
    public void setUpData() {
        resourceData = by.teachmeskills.dto.response.resource.Data.builder()
                .id(2)
                .name("fuchsia rose")
                .year(2001)
                .color("#C74375")
                .pantone_value("17-2031")
                .build();
    }

    @Test
    public void checkGetSingleResource() {
        Support resourceSupport = new Support("https://reqres.in/#support-heading",
                "To keep ReqRes free, contributions towards server costs are appreciated!");
        SingleResource expSingleResource = new SingleResource(resourceData, resourceSupport);
        SingleResource actSingleResource = new ResourceApiClient().getResource(2, HttpStatus.SC_OK);
        Assertions.assertThat(actSingleResource).as("User's data is not equal").isEqualTo(expSingleResource);
    }

    @Test
    public void checkGetAllResources() {
        MultipleResources actResources = new ResourceApiClient().getResources();
        Assertions.assertThat(actResources.getData())
                .as("List should contain " + resourceData)
                .contains(resourceData);
    }

    @Test
    public void checkGetSingleResourceNegative() {
        new ResourceApiClient().getResource(new Faker().number().numberBetween(500, 1000), HttpStatus.SC_NOT_FOUND);
    }
}
