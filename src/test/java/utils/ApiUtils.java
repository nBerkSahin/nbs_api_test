package utils;

import endpoints.PetEndPoints;
import io.restassured.response.Response;
import models.Pet;

import static io.restassured.RestAssured.given;

public class ApiUtils {

    public static Response postPet(Pet pet) {
        return given()
                .baseUri(PetEndPoints.BASE_URL)
                .contentType("application/json")
                .body(pet)
                .post(PetEndPoints.PET);
    }

    public static Response getPetById(long petId) {
        return given()
                .baseUri(PetEndPoints.BASE_URL)
                .pathParam("petId", petId)
                .accept("application/json")
                .get(PetEndPoints.PET_BY_ID);
    }

    public static Response getPetsByStatus(String status) {
        return given()
                .baseUri(PetEndPoints.BASE_URL)
                .accept("application/json")
                .queryParam("status", status)
                .get(PetEndPoints.PET_BY_STATUS);
    }

    public static Response deletePet(long petId) {
        return given()
                .baseUri(PetEndPoints.BASE_URL)
                .pathParam("petId", petId)
                .delete(PetEndPoints.PET_BY_ID);
    }

    public static Response updatePet(Pet pet) {
        return given()
                .baseUri(PetEndPoints.BASE_URL)
                .contentType("application/json")
                .body(pet)
                .put(PetEndPoints.PET);
    }

    public static Response updatePetWithFormData(long petId, String name, String status) {
        return given()
                .baseUri(PetEndPoints.BASE_URL)
                .contentType("application/x-www-form-urlencoded")
                .accept("application/json")
                .pathParam("petId", petId)
                .formParam("name", name)
                .formParam("status", status)
                .post(PetEndPoints.PET_UPDATE_FORM);
    }
}
