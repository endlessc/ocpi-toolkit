package tests.validation

import common.OcpiStatus
import common.SearchResult
import ocpi.locations.domain.Location
import ocpi.locations.validation.LocationsCpoValidationService
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import tests.mock.locationsCpoService
import java.time.Instant
import java.util.*

class LocationsCpoValidationServiceTest {
    private lateinit var service: LocationsCpoValidationService
    private val from = Instant.parse("2022-04-28T08:00:00.000Z")
    private val to = Instant.parse("2022-04-28T09:00:00.000Z")

    @Test
    fun getLocationsParamsValidationTest() {
        service = LocationsCpoValidationService(service = locationsCpoService(emptyList()))

        expectThat(service.getLocations(dateFrom = from, dateTo = from, offset = 0, limit = null)) {
            get { status_code }
                .isEqualTo(OcpiStatus.SUCCESS.code)

            get { data }
                .isNotNull()
                .get(SearchResult<Location>::offset)
                .isEqualTo(0)
        }

        expectThat(service.getLocations(dateFrom = to, dateTo = from, offset = 0, limit = null)) {
            get { status_code }
                .isEqualTo(OcpiStatus.CLIENT_INVALID_PARAMETERS.code)

            get { data }
                .isNull()
        }

        expectThat(service.getLocations(dateFrom = from, dateTo = to, offset = 0, limit = null)) {
            get { status_code }
                .isEqualTo(OcpiStatus.SUCCESS.code)

            get { data }
                .isNotNull()
                .get(SearchResult<Location>::offset)
                .isEqualTo(0)
        }

        expectThat(service.getLocations(dateFrom = null, dateTo = to, offset = 0, limit = null)) {
            get { status_code }
                .isEqualTo(OcpiStatus.SUCCESS.code)

            get { data }
                .isNotNull()
                .get(SearchResult<Location>::offset)
                .isEqualTo(0)
        }

        expectThat(service.getLocations(dateFrom = from, dateTo = null, offset = 0, limit = null)) {
            get { status_code }
                .isEqualTo(OcpiStatus.SUCCESS.code)

            get { data }
                .isNotNull()
                .get(SearchResult<Location>::offset)
                .isEqualTo(0)
        }

        expectThat(service.getLocations(dateFrom = null, dateTo = null, offset = 0, limit = null)) {
            get { status_code }
                .isEqualTo(OcpiStatus.SUCCESS.code)

            get { data }
                .isNotNull()
                .get(SearchResult<Location>::offset)
                .isEqualTo(0)
        }

        expectThat(service.getLocations(dateFrom = null, dateTo = null, offset = -10, limit = null)) {
            get { status_code }
                .isEqualTo(OcpiStatus.CLIENT_INVALID_PARAMETERS.code)

            get { data }
                .isNull()
        }

        expectThat(service.getLocations(dateFrom = null, dateTo = null, offset = 0, limit = -10)) {
            get { status_code }
                .isEqualTo(OcpiStatus.CLIENT_INVALID_PARAMETERS.code)

            get { data }
                .isNull()
        }

        expectThat(service.getLocations(dateFrom = null, dateTo = null, offset = 0, limit = 100)) {
            get { status_code }
                .isEqualTo(OcpiStatus.SUCCESS.code)

            get { data }
                .isNotNull()
                .get(SearchResult<Location>::offset)
                .isEqualTo(0)

            get { data }
                .isNotNull()
                .get(SearchResult<Location>::limit)
                .isEqualTo(100)
        }

        expectThat(service.getLocations(dateFrom = null, dateTo = null, offset = 100, limit = 100)) {
            get { status_code }
                .isEqualTo(OcpiStatus.SUCCESS.code)

            get { data }
                .isNotNull()
                .get(SearchResult<Location>::offset)
                .isEqualTo(100)

            get { data }
                .isNotNull()
                .get(SearchResult<Location>::limit)
                .isEqualTo(100)
        }

        expectThat(service.getLocations(dateFrom = null, dateTo = null, offset = 0, limit = 0)) {
            get { status_code }
                .isEqualTo(OcpiStatus.SUCCESS.code)

            get { data }
                .isNotNull()
                .get(SearchResult<Location>::offset)
                .isEqualTo(0)

            get { data }
                .isNotNull()
                .get(SearchResult<Location>::limit)
                .isEqualTo(0)
        }
    }

    @Test
    fun getLocationParamsValidationTest() {
        service =
            LocationsCpoValidationService(service = locationsCpoService(emptyList()))

        val str3chars = "abc"
        val str39chars = "abababababababababababababababababababa"
        val str40chars = "abababababababababababababababababababab"

        expectThat(service.getLocation(locationId = str3chars)) {
            get { status_code }
                .isEqualTo(OcpiStatus.SUCCESS.code)
        }

        expectThat(service.getLocation(locationId = str39chars)) {
            get { status_code }
                .isEqualTo(OcpiStatus.SUCCESS.code)
        }

        expectThat(service.getLocation(locationId = str40chars)) {
            get { status_code }
                .isEqualTo(OcpiStatus.CLIENT_INVALID_PARAMETERS.code)
        }
    }

    @Test
    fun getEvseParamsValidationTest() {
        service = LocationsCpoValidationService(service = locationsCpoService(emptyList()))

        val str3chars = "abc"
        val str39chars = "abababababababababababababababababababa"
        val str40chars = "abababababababababababababababababababab"

        expectThat(service.getEvse(locationId = str3chars, evseUid = str3chars)) {
            get { status_code }
                .isEqualTo(OcpiStatus.SUCCESS.code)
        }

        expectThat(service.getEvse(locationId = str39chars, evseUid = str3chars)) {
            get { status_code }
                .isEqualTo(OcpiStatus.SUCCESS.code)
        }

        expectThat(service.getEvse(locationId = str40chars, evseUid = str3chars)) {
            get { status_code }
                .isEqualTo(OcpiStatus.CLIENT_INVALID_PARAMETERS.code)
        }

        expectThat(service.getEvse(locationId = str3chars, evseUid = str3chars)) {
            get { status_code }
                .isEqualTo(OcpiStatus.SUCCESS.code)
        }

        expectThat(service.getEvse(locationId = str3chars, evseUid = str39chars)) {
            get { status_code }
                .isEqualTo(OcpiStatus.SUCCESS.code)
        }

        expectThat(service.getEvse(locationId = str3chars, evseUid = str40chars)) {
            get { status_code }
                .isEqualTo(OcpiStatus.CLIENT_INVALID_PARAMETERS.code)
        }

        expectThat(service.getEvse(locationId = str40chars, evseUid = str40chars)) {
            get { status_code }
                .isEqualTo(OcpiStatus.CLIENT_INVALID_PARAMETERS.code)
        }
    }

    @Test
    fun getConnectorParamsValidationTest() {
        service =
            LocationsCpoValidationService(service = locationsCpoService(emptyList()))

        val str3chars = "abc"
        val str37chars = "ababababababababababababababababababa"
        val str39chars = "abababababababababababababababababababa"
        val str40chars = "abababababababababababababababababababab"

        expectThat(
            service.getConnector(
                locationId = str3chars,
                evseUid = str3chars,
                connectorId = str3chars
            )
        ) {
            get { status_code }
                .isEqualTo(OcpiStatus.SUCCESS.code)
        }

        expectThat(
            service.getConnector(
                locationId = str39chars,
                evseUid = str3chars,
                connectorId = str3chars
            )
        ) {
            get { status_code }
                .isEqualTo(OcpiStatus.SUCCESS.code)
        }

        expectThat(
            service.getConnector(
                locationId = str40chars,
                evseUid = str3chars,
                connectorId = str3chars
            )
        ) {
            get { status_code }
                .isEqualTo(OcpiStatus.CLIENT_INVALID_PARAMETERS.code)
        }

        expectThat(
            service.getConnector(
                locationId = str3chars,
                evseUid = str3chars,
                connectorId = str3chars
            )
        ) {
            get { status_code }
                .isEqualTo(OcpiStatus.SUCCESS.code)
        }

        expectThat(
            service.getConnector(
                locationId = str3chars,
                evseUid = str39chars,
                connectorId = str3chars
            )
        ) {
            get { status_code }
                .isEqualTo(OcpiStatus.SUCCESS.code)
        }

        expectThat(
            service.getConnector(
                locationId = str3chars,
                evseUid = str40chars,
                connectorId = str3chars
            )
        ) {
            get { status_code }
                .isEqualTo(OcpiStatus.CLIENT_INVALID_PARAMETERS.code)
        }

        expectThat(
            service.getConnector(
                locationId = str40chars,
                evseUid = str40chars,
                connectorId = str3chars
            )
        ) {
            get { status_code }
                .isEqualTo(OcpiStatus.CLIENT_INVALID_PARAMETERS.code)
        }

        expectThat(
            service.getConnector(
                locationId = str3chars,
                evseUid = str3chars,
                connectorId = str37chars
            )
        ) {
            get { status_code }
                .isEqualTo(OcpiStatus.CLIENT_INVALID_PARAMETERS.code)
        }

        expectThat(
            service.getConnector(
                locationId = str40chars,
                evseUid = str40chars,
                connectorId = str37chars
            )
        ) {
            get { status_code }
                .isEqualTo(OcpiStatus.CLIENT_INVALID_PARAMETERS.code)
        }
    }
}
