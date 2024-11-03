package vn.edu.usth.dropboxui.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import vn.edu.usth.dropboxui.R;

public class HomeFragment extends Fragment {

    Button musicButton, moviesButton, picturesButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        musicButton = root.findViewById(R.id.musicButton);
        moviesButton = root.findViewById(R.id.moviesButton);
        picturesButton = root.findViewById(R.id.picturesButton);

        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.musicFragment);
            }
        });

        moviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.movieFragment);
            }
        });

        picturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.picturesFragment);
            }
        });

        return root;
    }
}