package CIS3334.restaurantsinduluthproject;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {
    TextView textViewRestaurantDescription;

    /**
     * this just sets the view to the view of the id that textview you enter as a parameter
     * @param itemView
     */
    public RestaurantViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewRestaurantDescription = itemView.findViewById(R.id.textViewRestaurantDescription);

    }
}
