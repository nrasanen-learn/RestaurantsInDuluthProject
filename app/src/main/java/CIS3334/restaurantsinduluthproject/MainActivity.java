package CIS3334.restaurantsinduluthproject;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import CIS3334.restaurantsinduluthproject.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    RestaurantsViewModel restaurantsViewModel;
    Button NextButton;
    Button confirmation;
    EditText restaurantName;
    EditText restaurantAddress;
    EditText restaurantMenuItems;
    EditText restaurantCategory;
    EditText restaurantRating;
    RecyclerView RestaurantRecyclerView;
    RestaurantAdapter restaurantAdapter;

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        restaurantsViewModel = new ViewModelProvider(this).get(RestaurantsViewModel.class);

        confirmation = findViewById(R.id.confirmation);
        restaurantName = findViewById(R.id.restaurantName);
        restaurantAddress = findViewById(R.id.restaurantAddress);
        restaurantMenuItems = findViewById(R.id.restaurantMenuItems);
        restaurantCategory = findViewById(R.id.restaurantCatagory);
        restaurantRating = findViewById(R.id.restaurantRating);

        setupAddRestaurantButton();

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(view -> {
            restaurantsViewModel.sortByRating();
            Snackbar.make(view, "Sorted by rating!", Snackbar.LENGTH_SHORT).show();
        });
    }

    /**
     *this starts up the options menu
     * @param menu The options menu in which you place your items.
     *
     * @return true which means it started up
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     *
     * @param item The menu item that was selected.
     *
     * @return returns that item that you select
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * this initialises the navController in this method
     * @return the navigateUp with parameters
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * this method sets up the confirmation button on the first fragment
     * it sets the appropiate data type to the variables and formats them
     * then calls the addRestaurant in the restaurantViewModel
     */
    public void setupAddRestaurantButton()
    {
        confirmation.setOnClickListener(v -> {
            String name = restaurantName.getText().toString();
            String address = restaurantAddress.getText().toString();
            String menuItems = restaurantMenuItems.getText().toString();
            ArrayList<String> menuList = new ArrayList<>(Arrays.asList(menuItems.split("\\s*,\\s*")));
            String category = restaurantCategory.getText().toString();
            String ratingText = restaurantRating.getText().toString();
            double rating = Double.parseDouble(ratingText);
            Restaurant newRestaurant = new Restaurant(null, name, address, menuList, category, rating);

            Log.d("setupAddRestaurantButton", "Called the add method ");

            // Add to Firebase and update ViewModel
            restaurantsViewModel.addRestaurant(newRestaurant);
    });

    }


}