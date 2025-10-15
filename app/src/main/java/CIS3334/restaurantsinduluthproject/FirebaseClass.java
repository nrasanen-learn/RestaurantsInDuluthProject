package CIS3334.restaurantsinduluthproject;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FirebaseClass {
    private static final String TAG = "FirebaseService";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * this goes through the collection in the database and creates a listener and a arrayList
     * then for every document there is it will add it to that list
     * if its empty it will get the 3 default restaurants to add by calling the addDefaultRestaurants() method
     * @param callback this is the method in the RestaurantsViewModel
     */
    public void retrieveRest(Consumer<List<Restaurant>> callback) {
        db.collection("restaurants")
                .orderBy("name") // or "category" or "rating"
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Restaurant> restaurantsList = new ArrayList<>();
                        Log.d("FirebaseService", "first if statement");

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Restaurant restaurant = document.toObject(Restaurant.class);
                            restaurant.setId(document.getId());
                            restaurantsList.add(restaurant);
                            Log.d("FirebaseService", "for loop" + restaurant.getName());
                        }

                        if (restaurantsList.isEmpty()) {
                            Log.d("FirebaseService", "Database is empty. Adding default restaurants.");
                            addDefaultRestaurants();

                            callback.accept(new ArrayList<>());
                        } else {
                            callback.accept(restaurantsList);
                        }
                    } else {
                        Log.w("FirebaseService", "Error getting documents.", task.getException());
                        callback.accept(new ArrayList<>());
                    }
                });
    }

    /**
     * makes 3 restaurants that can be called as default restaurants if the user's list is empty
     * it also makes the ArrayList's for the menuItems since that is it's data type
     */
    public void addDefaultRestaurants() {
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
                addRest(new Restaurant(null, "Sara's Table Chester Creek Cafe", "1902 E 8th St Duluth MN 55812", STmenu, "Cafe", 4.5));
                addRest(new Restaurant(null, "Bridgemans", "2202 Mountain Shadow Dr. Duluth, MN ", BMmenu, "Diner", 4.6));
                addRest(new Restaurant(null, "Duluth Grill", "118 S 27th Ave W, Duluth, MN 55806", DGmenu, "Grill", 4.7));
            }

    /**
     * this adds any restaurant object you want to the database and the recyclerView
     * @param restaurant this is any restaurant object that you want to add to your database and the recyclerView
     */
    public void addRest(Restaurant restaurant) {
        db.collection("restaurants")
                .add(restaurant);
    }


}
