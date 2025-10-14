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
        List<Restaurant> orderLiveData = restaurantsViewModel.getRestaurants();
        Restaurant restaurant = orderLiveData.get(position);
        holder.textViewRestaurantDescription.setText(restaurant.getName());
        Log.d("CIS 3334", "RestaurantAdapter  restaurant");
    }

    @Override
    public int getItemCount() {
        int numberOfRestaurants = 0;
        List<Restaurant> orderLiveData = restaurantsViewModel.getRestaurants(); // Get the LiveData object

        if (orderLiveData != null && orderLiveData != null) {
            numberOfRestaurants = orderLiveData.size();
        } else {
            Log.d("CIS 3334", "RestaurantAdapter -- loadRestaurants().getValue() is null");
        }
        Log.d("CIS 3334", "RestaurantAdapter -- Number of Restaurants = " + numberOfRestaurants);
        return numberOfRestaurants;
    }

    public RestaurantAdapter(Context context, List<Restaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants; // store it internally
    }

    public void setRestaurants(List<Restaurant> newRestaurants) {
        this.restaurants = newRestaurants;
        notifyDataSetChanged();
    }
}

