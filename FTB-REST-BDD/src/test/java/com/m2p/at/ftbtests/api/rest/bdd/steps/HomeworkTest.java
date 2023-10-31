package com.m2p.at.ftbtests.api.rest.bdd.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.m2p.at.ftbtests.api.rest.bdd.model.api.AircraftDto;
import com.m2p.at.ftbtests.api.rest.bdd.model.api.CreateAircraftDto;
import com.m2p.at.ftbtests.api.rest.bdd.utils.ApiCalls;
import com.m2p.at.ftbtests.api.rest.bdd.utils.ExchangeStorage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.AllArgsConstructor;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.assertj.core.api.Assertions.assertThat;

@AllArgsConstructor
public class HomeworkTest {

    private final ApiCalls apiCalls;

    private final ExchangeStorage<AircraftDto> storage;

    private final static String PATH = "aircraft";

    @When("client tries to create an Aircraft having manufacturer={string} and model={string} and number of seats=null")
    public void create(String manufacturer, String model) throws JsonProcessingException {
        var data = CreateAircraftDto.of()
                .manufacturer(manufacturer)
                .model(model)
                .numberOfSeats(null)
                .build();

        var response = apiCalls.doPost(SC_CREATED, AircraftDto.class, data, PATH);
        storage.setLastApiCallSingleItemResponse(response);
    }

    @Then("aircraft data to be manufacturer={string} and model={string} and number of seats=null")
    public void verifyAircraftData() {
        var lastResponse = storage.getLastApiCallSingleItemResponse();
        assertThat(lastResponse.getNumberOfSeats())
                .as("Seems Aircraft response contained unexpected manufacturer value.")
                .isEqualTo(null);
    }
}
