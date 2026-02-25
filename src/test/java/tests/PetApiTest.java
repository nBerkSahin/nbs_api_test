package tests;

import io.restassured.response.Response;
import models.Category;
import models.Pet;
import models.PetStatus;
import models.Tag;
import org.junit.jupiter.api.Test;
import utils.ApiUtils;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PetApiTest {

    @Test
    public void testAddNewPetToStore(){
        Pet pet = new Pet();
        pet.id = 123456789;
        pet.category = new Category();
        pet.category.id = 1;
        pet.category.name = "Dogs";
        pet.name = "Rex";
        pet.photoUrls = List.of("https://example.com/photo1.jpg", "https://example.com/photo2.jpg");
        pet.tags = List.of(new Tag() {{ id = 1; name = "friendly";}});
        pet.status = PetStatus.available;

        Response postResponse = ApiUtils.postPet(pet);
        assertEquals(200, postResponse.getStatusCode());

        Pet returnedPet = postResponse.as(Pet.class);
        assertEquals(pet.id, returnedPet.id);
        assertEquals(pet.category.name, returnedPet.category.name);
        assertEquals(pet.category.id, returnedPet.category.id);
        assertEquals(pet.name, returnedPet.name);
        assertEquals(pet.photoUrls, returnedPet.photoUrls);
        assertEquals(pet.tags.size(), returnedPet.tags.size());
        assertEquals(pet.status, returnedPet.status);
    }

    @Test
    public void testFindPetByID(){
        // Create new Pet to find
        Pet pet = new Pet();
        pet.id = 234567891;
        pet.category = new Category();
        pet.category.id = 1;
        pet.category.name = "Dogs";
        pet.name = "Rex";
        pet.photoUrls = List.of("https://example.com/photo1.jpg", "https://example.com/photo2.jpg");
        pet.tags = List.of(new Tag() {{ id = 1; name = "friendly";}});
        pet.status = PetStatus.available;

        Response postResponse = ApiUtils.postPet(pet);
        assertEquals(200, postResponse.getStatusCode());

        Pet petResponse = postResponse.as(Pet.class);
        assertEquals(pet.id, petResponse.id);
        assertEquals(pet.category.name, petResponse.category.name);
        assertEquals(pet.category.id, petResponse.category.id);
        assertEquals(pet.name, petResponse.name);
        assertEquals(pet.photoUrls, petResponse.photoUrls);
        assertEquals(pet.tags.size(), petResponse.tags.size());
        assertEquals(pet.status, petResponse.status);

        // Now find the pet we just created
        long petID = pet.id;
        Response getResponse = ApiUtils.getPetById(petID);
        int statusCode = getResponse.getStatusCode();
        if (statusCode == 200) {
            Pet returnedPet = getResponse.as(Pet.class);
            assertEquals(petID, returnedPet.id);
            assertEquals("Rex", returnedPet.name, "Pet name should be Rex but was " + returnedPet.name);
        } else if (statusCode == 404) {
            String errorMessage = getResponse.path("message");
            assertEquals("Pet not found", errorMessage, "Expected 'Pet not found' message but got: " + errorMessage);
            System.out.println(errorMessage);
        } else {
            System.out.println("Unexpected status code: " + statusCode);
        }
    }

    @Test
    public void testFindPetByStatusAvailable(){
        Response response = ApiUtils.getPetsByStatus(PetStatus.available.name());
        assertEquals(200, response.statusCode(), "Expected status code 200");
        List<Map<String, Object>> pets = response.jsonPath().getList("$");
        assertNotNull(pets, "Response body should not be null");
        assertFalse(pets.isEmpty(), "Pet list should not be empty");
        // Assert all of the pets have status "available"
        for (Map<String, Object> pet : pets) {
            assertEquals(PetStatus.available.name(), pet.get("status"), "Pet status should be 'available'");
        }
    }

    @Test
    public void testFindPetByStatusPending(){
        Response response = ApiUtils.getPetsByStatus(PetStatus.pending.name());
        assertEquals(200, response.statusCode(), "Expected status code 200");
        List<Map<String, Object>> pets = response.jsonPath().getList("$");
        assertNotNull(pets, "Response body should not be null");
        assertFalse(pets.isEmpty(), "Pet list should not be empty");
        // Assert all of the pets have status "pending"
        for (Map<String, Object> pet : pets) {
            assertEquals(PetStatus.pending.name(), pet.get("status"), "Pet status should be 'pending'");
        }
    }

    @Test
    public void testFindPetByStatusSold(){
        Response response = ApiUtils.getPetsByStatus(PetStatus.sold.name());
        assertEquals(200, response.statusCode(), "Expected status code 200");
        List<Map<String, Object>> pets = response.jsonPath().getList("$");
        assertNotNull(pets, "Response body should not be null");
        assertFalse(pets.isEmpty(), "Pet list should not be empty");
        // Assert all of the pets have status "sold"
        for (Map<String, Object> pet : pets) {
            assertEquals(PetStatus.sold.name(), pet.get("status"), "Pet status should be 'sold'");
        }
    }

    @Test
    public void testUpdateExistingPet(){
        // Create new Pet to update
        Pet oldPet = new Pet();
        oldPet.id = 345678912;
        oldPet.category = new Category();
        oldPet.category.id = 1;
        oldPet.category.name = "Dogs";
        oldPet.name = "Rex";
        oldPet.photoUrls = List.of("https://example.com/photo1.jpg");
        oldPet.tags = List.of(new Tag() {{ id = 1; name = "friendly";}});
        oldPet.status = PetStatus.available;

        Response postResponse = ApiUtils.postPet(oldPet);
        assertEquals(200, postResponse.getStatusCode());

        Pet returnedOldPet = postResponse.as(Pet.class);
        assertEquals(oldPet.id, returnedOldPet.id);
        assertEquals(oldPet.category.name, returnedOldPet.category.name);
        assertEquals(oldPet.category.id, returnedOldPet.category.id);
        assertEquals(oldPet.name, returnedOldPet.name);
        assertEquals(oldPet.photoUrls, returnedOldPet.photoUrls);
        assertEquals(oldPet.tags.size(), returnedOldPet.tags.size());
        assertEquals(oldPet.status, returnedOldPet.status);

        // Update pet details
        Pet NewPet = new Pet();
        NewPet.id = oldPet.id;
        NewPet.category = new Category();
        NewPet.category.id = 2;
        NewPet.category.name = "Cats";
        NewPet.name = "Rexona";
        NewPet.photoUrls = List.of("https://example.com/photo2.jpg");
        NewPet.tags = List.of(new Tag() {{ id = 2; name = "warm";}});
        NewPet.status = PetStatus.pending;

        Response updateResponse = ApiUtils.updatePet(NewPet);
        assertEquals(200, updateResponse.getStatusCode());

        Pet returnedNewPet = updateResponse.as(Pet.class);
        assertEquals(NewPet.id, returnedNewPet.id);
        assertEquals(NewPet.category.name, returnedNewPet.category.name);
        assertEquals(NewPet.category.id, returnedNewPet.category.id);
        assertEquals(NewPet.name, returnedNewPet.name);
        assertEquals(NewPet.photoUrls, returnedNewPet.photoUrls);
        assertEquals(NewPet.tags.size(), returnedNewPet.tags.size());
        assertEquals(NewPet.status, returnedNewPet.status);
    }

    @Test
    public void testUpdateExistingPetWithFormData(){
        Pet oldPet = new Pet();
        oldPet.id = 456789123;
        oldPet.category = new Category();
        oldPet.category.id = 1;
        oldPet.category.name = "Dogs";
        oldPet.name = "Rex";
        oldPet.photoUrls = List.of("https://example.com/photo1.jpg");
        oldPet.tags = List.of(new Tag() {{ id = 1; name = "friendly";}});
        oldPet.status = PetStatus.available;

        Response postResponse = ApiUtils.postPet(oldPet);
        assertEquals(200, postResponse.getStatusCode());

        Pet returnedOldPet = postResponse.as(Pet.class);
        assertEquals(oldPet.id, returnedOldPet.id);
        assertEquals(oldPet.category.name, returnedOldPet.category.name);
        assertEquals(oldPet.category.id, returnedOldPet.category.id);
        assertEquals(oldPet.name, returnedOldPet.name);
        assertEquals(oldPet.photoUrls, returnedOldPet.photoUrls);
        assertEquals(oldPet.tags.size(), returnedOldPet.tags.size());
        assertEquals(oldPet.status, returnedOldPet.status);

        // Update pet details
        Pet newPet = new Pet();
        newPet.id = oldPet.id;
        newPet.name = "Rexona";
        newPet.status = PetStatus.pending;

        Response updateResponse = ApiUtils.updatePetWithFormData(newPet.id, newPet.name, newPet.status.name());
        assertEquals(200, updateResponse.getStatusCode());
        assertEquals(200, updateResponse.jsonPath().getInt("code"));
        assertEquals("unknown", updateResponse.jsonPath().getString("type"));
        assertEquals(String.valueOf(newPet.id), updateResponse.jsonPath().getString("message"));
    }

    @Test
    public void testDeletePet() {
        // Create new Pet to delete
        Pet pet = new Pet();
        pet.id = 567891234;
        pet.category = new Category();
        pet.category.id = 1;
        pet.category.name = "Dogs";
        pet.name = "Rex";
        pet.photoUrls = List.of("https://example.com/photo1.jpg", "https://example.com/photo2.jpg");
        pet.tags = List.of(new Tag() {{ id = 1; name = "friendly";}});
        pet.status = PetStatus.available;

        Response postResponse = ApiUtils.postPet(pet);
        assertEquals(200, postResponse.getStatusCode());

        Pet returnedPet = postResponse.as(Pet.class);
        assertEquals(pet.id, returnedPet.id);
        assertEquals(pet.category.name, returnedPet.category.name);
        assertEquals(pet.category.id, returnedPet.category.id);
        assertEquals(pet.name, returnedPet.name);
        assertEquals(pet.photoUrls, returnedPet.photoUrls);
        assertEquals(pet.tags.size(), returnedPet.tags.size());
        assertEquals(pet.status, returnedPet.status);

        // Assert the pet was created correctly
        Response getResponse = ApiUtils.getPetById(pet.id);
        assertEquals(200, getResponse.getStatusCode(), "Pet should exist after creation");

        // Now delete the pet we just created
        long petID = pet.id;
        Response deleteResponse = null;
        int maxRetries = 20;
        int attempt = 0;

        while (attempt < maxRetries) {
            deleteResponse = ApiUtils.deletePet(petID);
            int statusCode = deleteResponse.getStatusCode();

            if (statusCode == 200) {
                break;
            } else if (statusCode == 404) {
                attempt++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                fail("Unexpected status code: " + statusCode);
            }
        }
        assertNotNull(deleteResponse, "Delete response should not be null");
        assertEquals(200, deleteResponse.getStatusCode(), "Expected status code 200 on delete");
        String message = deleteResponse.path("message");
        assertEquals(String.valueOf(petID), message, "Delete message should contain the pet ID");
    }
}
