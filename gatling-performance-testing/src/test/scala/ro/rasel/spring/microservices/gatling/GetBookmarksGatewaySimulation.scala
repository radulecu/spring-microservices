package ro.rasel.spring.microservices.gatling

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class GetBookmarksGatewaySimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:9025/passport-service/v1/users/jlong/passport")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scn = scenario(this.getClass.toString)
    .exec(http("request_1")
      .get("/"))
    .pause(5)

  setUp(
    scn.inject(
      rampUsersPerSec (1) to 150 during (30 seconds),
      constantUsersPerSec(150) during(15 seconds),
      constantUsersPerSec(175) during(15 seconds),
      constantUsersPerSec(200) during(15 seconds),
      constantUsersPerSec(225) during(15 seconds),
      constantUsersPerSec(250) during(15 seconds),
      constantUsersPerSec(275) during(15 seconds),
      constantUsersPerSec(300) during(15 seconds)
    )
  ).protocols(httpProtocol)
}