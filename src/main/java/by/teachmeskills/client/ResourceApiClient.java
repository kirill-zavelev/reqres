package by.teachmeskills.client;

import by.teachmeskills.dto.response.resource.MultipleResources;
import by.teachmeskills.dto.response.resource.SingleResource;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;

import java.util.Map;

@Log4j2
public class ResourceApiClient extends BaseApiClient {

    private static final String RESOURCE_PATH = "/api/unknown";
    private static final String RESOURCE_ID = "resourceId";
    private static final String RESOURCE_ID_PATH = "/api/unknown/{resourceId}";

    public SingleResource getResource(long id, int statusCode) {
        log.info("GET " + RESOURCE_ID_PATH);
        return get(RESOURCE_ID_PATH, Map.of(RESOURCE_ID, id))
                .then()
                .statusCode(statusCode)
                .extract()
                .body()
                .as(SingleResource.class);
    }

    public MultipleResources getResources() {
        log.info("GET all resources");
        return get(RESOURCE_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(MultipleResources.class);
    }
}
