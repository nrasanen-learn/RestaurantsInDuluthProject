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

    public void retrieveRest(Consumer<List<Restaurant>> callback) {
        db.collection("restaurants")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Restaurant> restaurantsList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Restaurant restaurant = document.toObject(Restaurant.class);
                            restaurant.setId(document.getId()); // Set the document ID on the park object
                            restaurantsList.add(restaurant);
                        }
                        callback.accept(restaurantsList); // Pass the completed list to the callback
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                        // On failure, you could pass back an empty list
                        callback.accept(new ArrayList<>());
                    }
                });
    }

    public void addRest(Restaurant restaurant) {
        db.collection("restaurants")
                .add(restaurant);
    }


}
