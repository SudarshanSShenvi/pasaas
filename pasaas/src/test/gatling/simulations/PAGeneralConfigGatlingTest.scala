import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the PAGeneralConfig entity.
 */
class PAGeneralConfigGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://127.0.0.1:8080"""

    val httpConf = http
        .baseURL(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "X-XSRF-TOKEN" -> "${xsrf_token}"
    )

    val scn = scenario("Test the PAGeneralConfig entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))
        .check(headerRegex("Set-Cookie", "XSRF-TOKEN=(.*);[\\s]").saveAs("xsrf_token"))).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authentication")
        .headers(headers_http_authenticated)
        .formParam("j_username", "admin")
        .formParam("j_password", "admin")
        .formParam("remember-me", "true")
        .formParam("submit", "Login")
        .check(headerRegex("Set-Cookie", "XSRF-TOKEN=(.*);[\\s]").saveAs("xsrf_token"))).exitHereIfFailed
        .pause(1)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all pAGeneralConfigs")
            .get("/api/p-a-general-configs")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new pAGeneralConfig")
            .post("/api/p-a-general-configs")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "hdfsurl":"SAMPLE_TEXT", "sparkurl":"SAMPLE_TEXT", "dr_script":"SAMPLE_TEXT", "dr_inseriesformat":"SAMPLE_TEXT", "dr_outseriesformat":"SAMPLE_TEXT", "dr_inputfile":"SAMPLE_TEXT", "dr_expressionfile":"SAMPLE_TEXT", "dr_outputfile":"SAMPLE_TEXT", "dr_seriesgcolindex":"SAMPLE_TEXT", "dr_seriesstart":"0", "dr_seriesend":"0", "dr_seriesnxt":"0", "df_inputfile":"SAMPLE_TEXT", "df_outputfile":"SAMPLE_TEXT", "df_entityfld":"SAMPLE_TEXT", "df_seriesfld":"SAMPLE_TEXT", "df_inseriesformat":"SAMPLE_TEXT", "df_outseriesformat":"SAMPLE_TEXT", "df_isheader":"SAMPLE_TEXT", "df_script":"SAMPLE_TEXT", "df_skipfldindexes":"SAMPLE_TEXT", "ss_inputfile":"SAMPLE_TEXT", "ss_outputfile":"SAMPLE_TEXT", "ss_saxcodefldindex":"0", "ss_subseqinterval":"0", "ss_subseqintervalthreshhold":"0", "ss_script":"SAMPLE_TEXT", "ss_tempopfile":"SAMPLE_TEXT", "ss_inputdirname":"SAMPLE_TEXT", "sq_ipaddr":"SAMPLE_TEXT", "sq_mysqlpwd":"SAMPLE_TEXT", "sq_mysqldb":"SAMPLE_TEXT", "sq_loadlocalinfile":"SAMPLE_TEXT", "sq_daydumppath":"SAMPLE_TEXT", "sq_updquery":"SAMPLE_TEXT", "sq_insertquery":"SAMPLE_TEXT", "sq_script":"SAMPLE_TEXT", "sq_command":"SAMPLE_TEXT", "sq_localinfiile":"SAMPLE_TEXT", "pastatus":null}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_pAGeneralConfig_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created pAGeneralConfig")
                .get("${new_pAGeneralConfig_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created pAGeneralConfig")
            .delete("${new_pAGeneralConfig_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(100) over (1 minutes))
    ).protocols(httpConf)
}
