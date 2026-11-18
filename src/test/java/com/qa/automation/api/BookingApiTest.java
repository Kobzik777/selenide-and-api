package com.qa.automation.api;

import com.qa.automation.api.models.Booking;
import com.qa.automation.api.models.BookingDates;
import com.qa.automation.api.models.CreateBookingResponse;
import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class BookingApiTest {

    private final Dotenv env = Dotenv.configure().ignoreIfMissing().load();

    private RequestSpecification spec;

    @BeforeClass
    public void setUp() {
        spec = new RequestSpecBuilder()
                .setBaseUri(env.get("API_BASE_URL"))
                .setContentType(ContentType.JSON)
                .setAccept("application/json")
                .build();
    }

    @Test(groups = "api")
    public void createdBookingCanBeRetrievedWithSameData() {
        Booking newBooking = new Booking(
                "Jim",
                "Brown",
                111,
                true,
                new BookingDates("2024-01-01", "2024-01-10"),
                "Breakfast"
        );

        CreateBookingResponse created = given()
                .spec(spec)
                .body(newBooking)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("bookingid", notNullValue())
                .extract()
                .as(CreateBookingResponse.class);

        int bookingId = created.bookingid();
        assertThat(created.booking())
                .as("Booking echoed back by POST should match the request")
                .isEqualTo(newBooking);

        Booking fetched = given()
                .spec(spec)
                .when()
                .get("/booking/{id}", bookingId)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .as(Booking.class);

        assertThat(fetched.firstname()).isEqualTo(newBooking.firstname());
        assertThat(fetched.lastname()).isEqualTo(newBooking.lastname());
        assertThat(fetched.totalprice()).isEqualTo(newBooking.totalprice());
        assertThat(fetched.depositpaid()).isEqualTo(newBooking.depositpaid());
        assertThat(fetched.bookingdates()).isEqualTo(newBooking.bookingdates());
        assertThat(fetched.additionalneeds()).isEqualTo(newBooking.additionalneeds());
    }
}
