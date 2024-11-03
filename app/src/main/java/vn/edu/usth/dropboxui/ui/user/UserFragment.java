package vn.edu.usth.dropboxui.ui.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import vn.edu.usth.dropboxui.LoginActivity;
import vn.edu.usth.dropboxui.databinding.FragmentUserBinding;

public class UserFragment extends Fragment {

    private FragmentUserBinding binding;
    private GoogleSignInClient gsc;
    private SharedPreferences sharedPreferences;
    private String email, apiKey;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize shared preferences
        sharedPreferences = getActivity().getSharedPreferences("DropBox", getActivity().MODE_PRIVATE);

        // Bind name and email
        binding.userName.setText(sharedPreferences.getString("name", "Name not found"));
        binding.userEmail.setText(sharedPreferences.getString("email", "Email not found"));

        // Store email and API key
        email = sharedPreferences.getString("email", "");
        apiKey = sharedPreferences.getString("apiKey", "");

        // Initialize GoogleSignInClient
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        gsc = GoogleSignIn.getClient(getActivity(), gso);

        // Set up sign-out button click listener
        binding.signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        return root;
    }

    void signOut() {
        // Step 1: Invalidate the API key in your backend (sign out request)
        String url = "http://192.168.0.102/DropBoxUI/logout.php"; // Update with the actual URL to your logout script
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if (status.equals("success")) {
                                // Step 2: Google sign-out
                                googleSignOut();
                            } else {
                                Toast.makeText(getActivity(), "Logout failed: " + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Response parsing error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LogoutError", error.toString());
                Toast.makeText(getActivity(), "Logout error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("apiKey", apiKey);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    void googleSignOut() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // Step 3: Clear shared preferences and redirect to LoginActivity
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear(); // Clear all user data
                editor.apply();

                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // To avoid memory leaks
    }
}
