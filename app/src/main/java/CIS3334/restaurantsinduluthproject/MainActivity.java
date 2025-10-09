package CIS3334.restaurantsinduluthproject;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

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

        Button confirmation = findViewById(R.id.confirmation);
        EditText restaurantName = findViewById(R.id.restaurantName);
        EditText restaurantAddress = findViewById(R.id.restaurantAddress);
        EditText restaurantMenuItems = findViewById(R.id.restaurantMenuItems);
        EditText restaurantCategory = findViewById(R.id.restaurantCatagory);
        EditText restaurantRating = findViewById(R.id.restaurantRating);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        restaurantsViewModel = new ViewModelProvider(this).get(RestaurantsViewModel.class);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

            // Add to Firebase and update ViewModel
            restaurantsViewModel.addRestaurant(newRestaurant);
    });

    }


}