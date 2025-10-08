package CIS3334.restaurantsinduluthproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CIS3334.restaurantsinduluthproject.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    RestaurantsViewModel restaurantsViewModel;
    RecyclerView RestaurantRecyclerView;
    RestaurantAdapter restaurantAdapter;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(v ->
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment)
        );
        restaurantsViewModel = new ViewModelProvider(requireActivity()).get(RestaurantsViewModel.class);

        RestaurantRecyclerView = view.findViewById(R.id.RestaurantRecyclerView);
        restaurantAdapter = new RestaurantAdapter(requireContext(), restaurantsViewModel);
        RestaurantRecyclerView.setAdapter(restaurantAdapter);
        RestaurantRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        ArrayList<Restaurant> exampleRestaurants = new ArrayList<>();
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
        exampleRestaurants.add(new Restaurant(null, "Sara's Table Chester Creek Cafe", "1902 E 8th St Duluth MN 55812", STmenu, "Cafe", 4.5));
        exampleRestaurants.add(new Restaurant(null, "Bridgemans", "2202 Mountain Shadow Dr. Duluth, MN ", BMmenu, "Diner", 4.7));
        exampleRestaurants.add(new Restaurant(null, "Duluth Grill", "118 S 27th Ave W, Duluth, MN 55806", DGmenu, "Grill", 4.7));

        // 🚀 Update the adapter
        restaurantAdapter.setRestaurants(exampleRestaurants);

        restaurantsViewModel.loadRestaurants();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}