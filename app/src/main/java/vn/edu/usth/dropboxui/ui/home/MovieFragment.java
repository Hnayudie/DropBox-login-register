package vn.edu.usth.dropboxui.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import vn.edu.usth.dropboxui.R;

public class MovieFragment extends Fragment {
    @Override
    public void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie, container, false);

        FloatingActionButton fab = root.findViewById(R.id.float_Bton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Add movie", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}