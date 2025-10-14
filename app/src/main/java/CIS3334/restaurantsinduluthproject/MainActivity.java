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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

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