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
    private List<Restaurant> originalRestaurants = new ArrayList<>();

    public LiveData<List<Restaurant>> getLiveRestaurants() {
        return _restaurants;
    }
    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    private final MutableLiveData<Restaurant> _selectedRestaurant = new MutableLiveData<>(null);

    public LiveData<Restaurant> getSelectedRestaurant() {
        return _selectedRestaurant;
    }

    public List<Restaurant> restaurants;

    public RestaurantsViewModel() {
        Log.d("RestaurantViewModel", "Instance created: loadRestaurants " + instanceId);
        firebaseService.retrieveRest(this::callback);
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

    public void callback(List<Restaurant> normalRestaurants)
    {
            Log.d("RestaurantViewModel", "Callback received " + normalRestaurants.size() + " restaurants.");
            _restaurants.setValue(normalRestaurants);
            restaurants = normalRestaurants;
    }

    public void sortByRating() {
        List<Restaurant> currentList = _restaurants.getValue();
        if (currentList == null || currentList.isEmpty()) return;

        currentList.sort((r1, r2) -> Double.compare(r2.getRating(), r1.getRating()));
        _restaurants.setValue(currentList);
    }

    public void loadRestaurants() {
        Log.d("RestaurantsViewModel", "Loading restaurants from Firebase...");

        firebaseService.retrieveRest(restaurantsFromFirebase -> {

            _restaurants.postValue(restaurantsFromFirebase);
            Log.d("RestaurantsViewModel", "Loaded " + restaurantsFromFirebase.size() + " restaurants from Firebase.");
        });
    }

    public void restoreOriginalOrder() {
        _restaurants.setValue(new ArrayList<>(originalRestaurants));
    }

}

