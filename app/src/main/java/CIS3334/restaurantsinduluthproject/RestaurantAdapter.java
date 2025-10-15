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

    /**
     * this is just a default constructer
     * @param context
     * @param restaurantsViewModel the view model instance in this class
     */
    RestaurantAdapter(Context context, RestaurantsViewModel restaurantsViewModel) {
        this.context = context;
        this.restaurantsViewModel = restaurantsViewModel;
    }

    /**
     * this is the from the viewholder dictating the format and layout of the app
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return the restaurantViewHolder instance
     */
    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_row, parent, false);
        RestaurantViewHolder restaurantViewHolder = new RestaurantViewHolder(view);
        return restaurantViewHolder;
    }

    /**
     * this gets the live data from the viewModel and sets it and displays it
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        List<Restaurant> orderLiveData = restaurantsViewModel.getRestaurants();
        Restaurant restaurant = orderLiveData.get(position);
        holder.textViewRestaurantDescription.setText(restaurant.getName() + restaurant.getMenuItems());
        Log.d("CIS 3334", "RestaurantAdapter  restaurant");
    }

    /**
     * this gets the number of currents restaurants in the viewmodel and by assaosiation the number in the Firebase Database
     * @return the number of restaurants in the app currently
     */
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

    /**
     *  this is the "default constructor" for the setRestaurants it sets it to the input list and notifies that it was changed
     * @param newRestaurants this sets it to the current restaurants in the list
     */
    public void setRestaurants(List<Restaurant> newRestaurants) {
        this.restaurants = newRestaurants;
        notifyDataSetChanged();
    }
}

