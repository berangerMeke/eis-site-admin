import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the PageAccueil entity.
 */
class PageAccueilGatlingTest extends Simulation {

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

    val scn = scenario("Test the PageAccueil entity")
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
            exec(http("Get all pageAccueils")
            .get("/api/page-accueils")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new pageAccueil")
            .post("/api/page-accueils")
            .headers(headers_http_authenticated)
            .body(StringBody("""{
                "sec1Titre":"SAMPLE_TEXT"
                , "sec1Texte":"SAMPLE_TEXT"
                , "secImage":null
                , "sec1Bouton":"SAMPLE_TEXT"
                , "sec2Titre":"SAMPLE_TEXT"
                , "sec2Text":"SAMPLE_TEXT"
                , "sec2Bouton":"SAMPLE_TEXT"
                , "sec3Titre":"SAMPLE_TEXT"
                , "sec3Blog1Img":null
                , "sec3Blog2Img":null
                , "sec3Blog3Img":null
                , "sec3Blog1SousTitre":"SAMPLE_TEXT"
                , "sec3Blog2SousTitre":"SAMPLE_TEXT"
                , "sec3Blog3SousTitre":"SAMPLE_TEXT"
                , "sec3Blog1Text":"SAMPLE_TEXT"
                , "sec3Blog2Text":"SAMPLE_TEXT"
                , "sec3Blog3Text":"SAMPLE_TEXT"
                , "sec4Titre":"SAMPLE_TEXT"
                , "sec4Text":"SAMPLE_TEXT"
                , "sec4Blog1Img":null
                , "sec4Blog1SousTitre":"SAMPLE_TEXT"
                , "sec4Blog2SousTitre":"SAMPLE_TEXT"
                , "sec4Blog1Text":"SAMPLE_TEXT"
                , "sec4Blog2Text":"SAMPLE_TEXT"
                , "sec5Titre":"SAMPLE_TEXT"
                , "sec5Blog1Img":null
                , "sec5Blog2Img":null
                , "sec5Blog3Img":null
                , "sec5Blog4Img":null
                , "sec5Blog5Img":null
                , "sec5Blog6Img":null
                , "sec5Blog7Img":null
                , "sec5Blog1SousTitre":null
                , "sec5Blog2SousTitre":null
                , "sec5Blog3SousTitre":null
                , "sec5Blog4SousTitre":null
                , "sec5Blog5SousTitre":null
                , "sec5Blog6SousTitre":null
                , "sec5Blog7SousTitre":null
                , "sec5Blog1Text":"SAMPLE_TEXT"
                , "sec5Blog2Text":"SAMPLE_TEXT"
                , "sec5Blog3Text":"SAMPLE_TEXT"
                , "sec5Blog4Text":"SAMPLE_TEXT"
                , "sec5Blog5Text":"SAMPLE_TEXT"
                , "sec5Blog6Text":"SAMPLE_TEXT"
                , "sec5Blog7Text":"SAMPLE_TEXT"
                , "sec6Titre":"SAMPLE_TEXT"
                , "sec6Img":null
                , "sec6Text":"SAMPLE_TEXT"
                , "sec7Titre":"SAMPLE_TEXT"
                , "sec7Img":null
                , "sec7Text":"SAMPLE_TEXT"
                , "sec8Titre":"SAMPLE_TEXT"
                , "sec8Img":null
                , "sec8Text":"SAMPLE_TEXT"
                , "sec9Titre":"SAMPLE_TEXT"
                , "sec9Img":null
                , "sec9Text":"SAMPLE_TEXT"
                }""")).asJson
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_pageAccueil_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created pageAccueil")
                .get("${new_pageAccueil_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created pageAccueil")
            .delete("${new_pageAccueil_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(Integer.getInteger("users", 100)) during (Integer.getInteger("ramp", 1) minutes))
    ).protocols(httpConf)
}
