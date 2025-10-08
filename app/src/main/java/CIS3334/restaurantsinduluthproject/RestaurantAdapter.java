package CIS3334.restaurantsinduluthproject;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantViewHolder>{
    Context context;
    RestaurantsViewModel restaurantsViewModel;
    private List<Restaurant> restaurants;

    RestaurantAdapter(Context context, RestaurantsViewModel restaurantsViewModel) {
        this.context = context;
        this.restaurantsViewModel = restaurantsViewModel;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_row, parent, false);
        RestaurantViewHolder restaurantViewHolder = new RestaurantViewHolder(view);
        return restaurantViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        LiveData<List<Restaurant>> orderLiveData = restaurantsViewModel.getRestaurants();
        Restaurant restaurant = orderLiveData.getValue().get(position);
        holder.textViewRestaurantDescription.setText(restaurant.toString());
    }

    @Override
    public int getItemCount() {
        int numberOfRestaurants = 0;
        LiveData<List<Restaurant>> orderLiveData = restaurantsViewModel.getRestaurants(); // Get the LiveData object

        if (orderLiveData != null && orderLiveData.getValue() != null) {
            // Only try to get the size if getValue() is not null
            numberOfRestaurants = orderLiveData.getValue().size();
        } else {
            // Handle the case where LiveData or its value is null
            // You could log this, or simply return 0 as you are.
            Log.d("CIS 3334", "RestaurantAdapter -- loadRestaurants().getValue() is null");
        }

        Log.d("CIS 3334", "OrderAdapter -- Number of Pizzas = " + numberOfRestaurants);
        return numberOfRestaurants;
    }

    public RestaurantAdapter(Context context, List<Restaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants; // store it internally
    }

    public void setRestaurants(List<Restaurant> newRestaurants) {
        this.restaurants = newRestaurants;
        notifyDataSetChanged(); // Tells RecyclerView to refresh the list
    }
}

