package de.blackforestsolutions.dravelopsconfigbackend.service.supportservice;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import de.blackforestsolutions.dravelopsdatamodel.GraphQlTab;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.Map;

import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.GraphQLApiConfigObjectMother.getGraphQLApiConfigWithNoEmptyFields;
import static de.blackforestsolutions.dravelopsdatamodel.objectmothers.ApiTokenObjectMother.getTestSoftwareApiTokens;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestSoftwareHttpCallBuilderServiceTest {

    private final TestSoftwareHttpCallBuilderService classUnderTest = new TestSoftwareHttpCallBuilderServiceImpl();

    @Test
    void test_buildTestSoftwareHttpEntityWith_graphqlApiConfig_returns_correctly_mapped_httpEntity() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();

        HttpEntity<Map<GraphQlTab, ApiToken>> result = classUnderTest.buildTestSoftwareHttpEntityWith(testData);

        assertThat(result.getHeaders()).isEqualTo(HttpHeaders.EMPTY);
        assertThat(result.getBody().size()).isEqualTo(getTestSoftwareApiTokens().size());
        assertThat(result.getBody().get(GraphQlTab.JOURNEY_QUERY)).isEqualToComparingFieldByFieldRecursively(getTestSoftwareApiTokens().get(GraphQlTab.JOURNEY_QUERY));
        assertThat(result.getBody().get(GraphQlTab.ADDRESS_AUTOCOMPLETION)).isEqualToComparingFieldByFieldRecursively(getTestSoftwareApiTokens().get(GraphQlTab.ADDRESS_AUTOCOMPLETION));
        assertThat(result.getBody().get(GraphQlTab.NEAREST_ADDRESSES)).isEqualToComparingFieldByFieldRecursively(getTestSoftwareApiTokens().get(GraphQlTab.NEAREST_ADDRESSES));
        assertThat(result.getBody().get(GraphQlTab.NEAREST_STATIONS)).isEqualToComparingFieldByFieldRecursively(getTestSoftwareApiTokens().get(GraphQlTab.NEAREST_STATIONS));
    }

    @Test
    void test_buildTestSoftwareHttpEntityWith_graphqlApiConfig_and_tabs_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().setTabs(null);

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

    @Test
    void test_buildTestSoftwareHttpEntityWith_graphqlApiConfig_and_journey_tab_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().getTabs().remove(GraphQlTab.JOURNEY_QUERY.toString());

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

    @Test
    void test_buildTestSoftwareHttpEntityWith_graphqlApiConfig_and_address_autocompletion_tab_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().getTabs().remove(GraphQlTab.ADDRESS_AUTOCOMPLETION.toString());

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

    @Test
    void test_buildTestSoftwareHttpEntityWith_graphqlApiConfig_and_nearest_address_tab_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().getTabs().remove(GraphQlTab.NEAREST_ADDRESSES.toString());

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

    @Test
    void test_buildTestSoftwareHttpEntityWith_graphqlApiConfig_and_nearest_stations_tab_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().getTabs().remove(GraphQlTab.NEAREST_STATIONS.toString());

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

    @Test
    void test_buildJourneyApiTokenWith_graphqlApiConfig_and_journey_query_departure_longitude_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().getTabs().get(GraphQlTab.JOURNEY_QUERY.toString()).getVariables().setDepartureLongitude(null);

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

    @Test
    void test_buildJourneyApiTokenWith_graphqlApiConfig_and_journey_query_arrival_longitude_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().getTabs().get(GraphQlTab.JOURNEY_QUERY.toString()).getVariables().setArrivalLongitude(null);

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

    @Test
    void test_buildJourneyApiTokenWith_graphqlApiConfig_and_journey_query_date_time_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().getTabs().get(GraphQlTab.JOURNEY_QUERY.toString()).getVariables().setDateTime(null);

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

    @Test
    void test_buildJourneyApiTokenWith_graphqlApiConfig_and_journey_query_is_arrival_date_time_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().getTabs().get(GraphQlTab.JOURNEY_QUERY.toString()).getVariables().setIsArrivalDateTime(null);

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

    @Test
    void test_buildJourneyApiTokenWith_graphqlApiConfig_and_journey_query_language_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().getTabs().get(GraphQlTab.JOURNEY_QUERY.toString()).getVariables().setLanguage(null);

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

    @Test
    void test_buildAddressAutocompletionApiTokenWith_graphqlApiConfig_and_address_autocompletion_text_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().getTabs().get(GraphQlTab.ADDRESS_AUTOCOMPLETION.toString()).getVariables().setText(null);

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

    @Test
    void test_buildAddressAutocompletionApiTokenWith_graphqlApiConfig_and_address_autocompletion_language_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().getTabs().get(GraphQlTab.ADDRESS_AUTOCOMPLETION.toString()).getVariables().setLanguage(null);

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

    @Test
    void test_buildNearestApiTokenWith_graphqlApiConfig_and_nearest_addresses_longitude_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().getTabs().get(GraphQlTab.NEAREST_ADDRESSES.toString()).getVariables().setLongitude(null);

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

    @Test
    void test_buildNearestApiTokenWith_graphqlApiConfig_and_nearest_addresses_latitude_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().getTabs().get(GraphQlTab.NEAREST_ADDRESSES.toString()).getVariables().setLatitude(null);

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

    @Test
    void test_buildNearestApiTokenWith_graphqlApiConfig_and_nearest_addresses_radius_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().getTabs().get(GraphQlTab.NEAREST_ADDRESSES.toString()).getVariables().setRadiusInKilometers(null);

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

    @Test
    void test_buildNearestApiTokenWith_graphqlApiConfig_and_nearest_addresses_language_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().getTabs().get(GraphQlTab.NEAREST_ADDRESSES.toString()).getVariables().setLanguage(null);

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

    @Test
    void test_buildNearestApiTokenWith_graphqlApiConfig_and_nearest_stations_longitude_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().getTabs().get(GraphQlTab.NEAREST_STATIONS.toString()).getVariables().setLongitude(null);

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

    @Test
    void test_buildNearestApiTokenWith_graphqlApiConfig_and_nearest_stations_latitude_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().getTabs().get(GraphQlTab.NEAREST_STATIONS.toString()).getVariables().setLatitude(null);

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

    @Test
    void test_buildNearestApiTokenWith_graphqlApiConfig_and_nearest_stations_radius_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().getTabs().get(GraphQlTab.NEAREST_STATIONS.toString()).getVariables().setRadiusInKilometers(null);

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

    @Test
    void test_buildNearestApiTokenWith_graphqlApiConfig_and_nearest_stations_language_as_null_throws_exception() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        testData.getGraphql().getPlayground().getTabs().get(GraphQlTab.NEAREST_STATIONS.toString()).getVariables().setLanguage(null);

        assertThrows(NullPointerException.class, () -> classUnderTest.buildTestSoftwareHttpEntityWith(testData));
    }

}
