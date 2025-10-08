package CIS3334.restaurantsinduluthproject;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RestaurantsViewModel extends ViewModel {
    public final String instanceId = UUID.randomUUID().toString();
    private final FirebaseClass firebaseService = new FirebaseClass();
    // Initialize with an empty list to prevent null issues.
    private final MutableLiveData<List<Restaurant>> _restaurants = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Restaurant>> getRestaurants() {
        return _restaurants;
    }

    private final MutableLiveData<Restaurant> _selectedRestaurant = new MutableLiveData<>(null);

    public LiveData<Restaurant> getSelectedRestaurant() {
        return _selectedRestaurant;
    }

    public RestaurantsViewModel() {
        Log.d("RestaurantViewModel", "Instance created: " + instanceId);
        loadRestaurants();
    }

    public void loadRestaurants() {
        firebaseService.retrieveRest(restaurantsList -> {
            _restaurants.setValue(restaurantsList); // Update LiveData with the result from Firebase
            // If the Firebase list is empty, add three sample Restaurants to seed the database.
            Log.d("RestaurantViewModel", "loadingRestaurant");
            if (restaurantsList.isEmpty()) {
                Log.d("RestaurantViewModel", "EmptyList");
                // Sara's Table Menu
                ArrayList<String> STmenu = new ArrayList<>();
                STmenu.add("Pumpkin Pancakes");
                STmenu.add("Koren Barbecue Sandwich");
                STmenu.add("GBLT");
                // Bridgeman's Menu
                ArrayList<String> BMmenu = new ArrayList<>();
                BMmenu.add("Buffalo Chicken Wrap");
                BMmenu.add("Mizzle Skizzle");
                BMmenu.add("Turtle Sunday");
                // Duluth Grill's Menu
                ArrayList<String> DGmenu = new ArrayList<>();
                DGmenu.add("The Bear");
                DGmenu.add("Mediterranean Omelet");
                DGmenu.add("Maple Bacon Salad");

                // taken from ChatGPT
                List<Restaurant> defaultRestaurants = new ArrayList<>();
                defaultRestaurants.add(new Restaurant(null, "Sara's Table Chester Creek Cafe", "1902 E 8th St Duluth MN 55812", STmenu, "Cafe", 4.5));
                defaultRestaurants.add(new Restaurant(null, "Bridgemans", "2202 Mountain Shadow Dr. Duluth, MN ", BMmenu, "Diner", 4.7));
                defaultRestaurants.add(new Restaurant(null, "Duluth Grill", "118 S 27th Ave W, Duluth, MN 55806", DGmenu, "Grill", 4.7));

                // Save them to Firebase (optional)
                for (Restaurant r : defaultRestaurants) {
                    addRestaurant(r);
                }

                // 🚀 Immediately update LiveData so RecyclerView can show them
                _restaurants.setValue(defaultRestaurants);
            }
        });
    }

    public void selectRestaurant(String restaurantId) {
        if (restaurantId == null) {
            _selectedRestaurant.setValue(null);
            return;
        }
        Restaurant foundRestaurant = null;
        List<Restaurant> currentRestaurant = _restaurants.getValue(); // Get the list from LiveData
        for (Restaurant restaurant : currentRestaurant) { // Loop over the list
            if (restaurant.getId() != null && restaurant.getId().equals(restaurantId)) {
                foundRestaurant = restaurant;
                break;
            }
        }
        _selectedRestaurant.setValue(foundRestaurant);
    }

    public void addRestaurant(Restaurant restaurant) {
        Log.d("RestaurantViewModel", "Adding restaurant: " + restaurant.getName());

        firebaseService.addRest(restaurant);

        List<Restaurant> currentRestaurants = _restaurants.getValue();
        currentRestaurants.add(restaurant);
        _restaurants.setValue(currentRestaurants);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("RestaurantViewModel", "Instance cleared: " + instanceId);
    }

    private void loadSampleRestaurants() {
        ArrayList<Restaurant> sampleList = new ArrayList<>();

        ArrayList<String> stMenu = new ArrayList<>();
        stMenu.add("Pumpkin Pancakes");
        stMenu.add("Korean Barbecue Sandwich");
        stMenu.add("GBLT");

        ArrayList<String> bmMenu = new ArrayList<>();
        bmMenu.add("Buffalo Chicken Wrap");
        bmMenu.add("Mizzle Skizzle");
        bmMenu.add("Turtle Sundae");

        ArrayList<String> dgMenu = new ArrayList<>();
        dgMenu.add("The Bear");
        dgMenu.add("Mediterranean Omelet");
        dgMenu.add("Maple Bacon Salad");

        sampleList.add(new Restaurant("1", "Sara's Table Chester Creek Cafe", "1902 E 8th St, Duluth, MN 55812", stMenu, "Cafe", 4.5));
        sampleList.add(new Restaurant("2", "Bridgeman's", "2202 Mountain Shadow Dr, Duluth, MN", bmMenu, "Diner", 4.7));
        sampleList.add(new Restaurant("3", "Duluth Grill", "118 S 27th Ave W, Duluth, MN 55806", dgMenu, "Grill", 4.7));

        _restaurants.setValue(sampleList);
    }

}

