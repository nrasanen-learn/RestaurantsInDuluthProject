package CIS3334.restaurantsinduluthproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
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

        // sets up recycler view
        RestaurantRecyclerView = view.findViewById(R.id.RestaurantRecyclerView);
        restaurantAdapter = new RestaurantAdapter(requireContext(), restaurantsViewModel);
        RestaurantRecyclerView.setAdapter(restaurantAdapter);
        RestaurantRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        restaurantsViewModel.getLiveRestaurants().observe(getViewLifecycleOwner(), newRestaurants -> {
            restaurantAdapter.setRestaurants(newRestaurants);
        });

        // sets up switch
        Switch sortSwitch = view.findViewById(R.id.sortSwitch);

        sortSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                restaurantsViewModel.sortByRating();
            } else {
                restaurantsViewModel.restoreOriginalOrder();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}