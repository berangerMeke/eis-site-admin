import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the PageAPropos entity.
 */
class PageAProposGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://localhost:8080"""

    val httpConf = http
        .baseUrl(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")
        .silentResources // Silence all resources like css or css so they don't clutter the results

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authentication = Map(
        "Content-Type" -> """application/json""",
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "Authorization" -> "${access_token}"
    )

    val scn = scenario("Test the PageAPropos entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))
        ).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authenticate")
        .headers(headers_http_authentication)
        .body(StringBody("""{"username":"admin", "password":"admin"}""")).asJson
        .check(header("Authorization").saveAs("access_token"))).exitHereIfFailed
        .pause(2)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all pageAPropos")
            .get("/api/page-a-propos")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new pageAPropos")
            .post("/api/page-a-propos")
            .headers(headers_http_authenticated)
            .body(StringBody("""{
                "sec1Img1":null
                , "sec1Desc1":"SAMPLE_TEXT"
                , "sec1Logo":null
                , "sec1Img2":null
                , "sec1Desc2":"SAMPLE_TEXT"
                , "sec1Img3":null
                , "sec1Desc3":"SAMPLE_TEXT"
                , "sec2Titre":"SAMPLE_TEXT"
                , "sec2Img":null
                , "sec2SousTitre":"SAMPLE_TEXT"
                , "sec2Text":"SAMPLE_TEXT"
                , "sec3Titre":"SAMPLE_TEXT"
                , "sec3Img":null
                , "sec3SousTitre":"SAMPLE_TEXT"
                , "sec3Text":"SAMPLE_TEXT"
                , "sec4Img":null
                , "sec4Desc1":"SAMPLE_TEXT"
                , "sec4Desc2":"SAMPLE_TEXT"
                , "sec4Desc3":"SAMPLE_TEXT"
                , "sec4Desc4":"SAMPLE_TEXT"
                , "sec5Titre":"SAMPLE_TEXT"
                }""")).asJson
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_pageAPropos_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created pageAPropos")
                .get("${new_pageAPropos_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created pageAPropos")
            .delete("${new_pageAPropos_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(Integer.getInteger("users", 100)) during (Integer.getInteger("ramp", 1) minutes))
    ).protocols(httpConf)
}
