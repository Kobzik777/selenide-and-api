# QA Automation — Selenide + REST Assured

Automated UI and API tests for two public systems:

- **UI:** [SauceDemo](https://www.saucedemo.com/) — login and product/cart flows (Selenide).
- **API:** [Restful Booker](https://restful-booker.herokuapp.com/) — booking create/retrieve (REST Assured).

Build tool: **Maven**. Test runner: **TestNG**. Language: **Java 17**.

---

## Prerequisites

To run locally you need:

- **JDK 17+**
- **Maven 3.9+**
- **Google Chrome** (for the UI tests; Selenium Manager resolves the matching driver automatically)
- Internet access (both systems under test are public)

To run via Docker you only need **Docker** — Java, Maven and Chrome are provided inside the image.

---

## Configuration

Credentials and the API base URL are supplied through environment variables, loaded from a local `.env` file (git-ignored). Create a `.env` in the project root:

```dotenv
UI_USERNAME=standard_user
UI_PASSWORD=secret_sauce
API_BASE_URL=https://restful-booker.herokuapp.com
```

- `UI_USERNAME` / `UI_PASSWORD` — SauceDemo login (these are public demo credentials, not real secrets).
- `API_BASE_URL` — base URL of the Restful Booker API.

If no `.env` is present, the tests fall back to real environment variables (useful in CI/Docker).

Other configuration lives in:

- `src/test/resources/selenide.properties` — browser, base URL, timeouts.
- `testng.xml` — the test suite and the `headless` parameter.

---

## Local execution

Create the `.env` file first (see above), then:

**All tests**
```bash
mvn test
```

**UI tests only**
```bash
mvn test -Dtest=LoginTest,ProductAndCartTest
```

**API tests only**
```bash
mvn test -Dtest=BookingApiTest
```

### Watching the browser

Tests run **headless** by default. To watch the UI tests in a real browser window, set the `headless` parameter to `false` in `testng.xml`:

```xml
<parameter name="headless" value="false"/>
```

---

## Docker execution

Build the image:

```bash
docker build -t qa-automation-tests .
```

Run the tests (passing the credentials via the `.env` file):

```bash
docker run --rm --env-file .env qa-automation-tests
```

The container runs Chrome headless and executes the full suite (`mvn test`). Results are printed to the console.

---

## Project structure

```
src/test/java/com/qa/automation/
├── ui/
│   ├── BaseTest.java            # browser config, login (@BeforeMethod), teardown, shared steps
│   ├── LoginTest.java           # Test 1 — login
│   ├── ProductAndCartTest.java  # Test 2 — sorting, add to cart
│   ├── pageobjects/             # locators + low-level element actions
│   │   ├── LoginPage.java
│   │   ├── ProductsPage.java
│   │   └── CartPage.java
│   └── steps/                   # business actions + assertions
│       ├── LoginPageSteps.java
│       ├── ProductPageSteps.java
│       └── CartPageSteps.java
└── api/
    ├── BookingApiTest.java      # Test 3 — create & retrieve a booking
    └── models/                  # request/response POJOs (Java records)
        ├── Booking.java
        ├── BookingDates.java
        └── CreateBookingResponse.java

src/test/resources/
├── selenide.properties          # browser / baseUrl / timeouts
└── simplelogger.properties      # log levels
testng.xml                       # suite definition + headless flag
Dockerfile
```

---

## Test approach

### Locators
- **Login fields** use stable element ids (`#user-name`, `#password`, `#login-button`).
- **Collections** (products, prices, cart items) use SauceDemo's semantic CSS classes (`.inventory_item`, `.inventory_item_price`, `.cart_item_label`).
- The **add-to-cart button** is located relative to its product name, so a test can act on a product by name rather than by position.

### Synchronization
- No hard waits (`Thread.sleep`). All verifications use Selenide conditions (`shouldBe`, `shouldHave`), which poll the element until it reaches the expected state or the configured timeout (`selenide.timeout`, 10s) elapses.

### Structure
- **Page Objects** hold only locators and basic element interactions — no assertions.
- **Steps** express user actions in business terms and contain the assertions (via Selenide conditions / AssertJ).
- **Tests** read as short, intention-revealing scenarios. `BaseTest` wires the step objects, logs in before every test (`@BeforeMethod`), and closes the browser afterwards (`@AfterMethod`) to keep tests isolated.
- **API** uses a shared `RequestSpecification` (base URI, JSON content/accept) and Java `record` POJOs with Jackson for clean request/response (de)serialization.

### Assumptions & limitations
- SauceDemo and Restful Booker are public environments; tests depend on their availability.
- SauceDemo credentials are public demo values and are treated as configuration, not secrets.
- Restful Booker is fronted by a CDN that rejects REST Assured's default multi-value `Accept` header with HTTP `418`; the API spec therefore sets `Accept: application/json` explicitly.
- UI tests each start a fresh browser session and log in, trading a little speed for full isolation.
