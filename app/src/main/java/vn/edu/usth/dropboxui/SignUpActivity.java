package vn.edu.usth.dropboxui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity{

    ImageButton imageButton;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    FrameLayout googleBtn3;

    TextInputEditText textInputEditTextName, textInputEditTextEmail, textInputEditTextPassword;
    Button signUpBtn;
    String name, email, password;
    TextView textViewError;
    ProgressBar progressBar;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_signup);

        signUpBtn = findViewById(R.id.sign_up);
        checkBox = findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            signUpBtn.setEnabled(isChecked);
        }));

        textInputEditTextName = findViewById(R.id.textFieldName);
        textInputEditTextEmail = findViewById(R.id.textFieldEmail);
        textInputEditTextPassword = findViewById(R.id.textFieldPassword);
        textViewError = findViewById(R.id.error);
        progressBar = findViewById(R.id.loading);


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkBox.isChecked()) {
                    Toast.makeText(SignUpActivity.this, "Please agree with the terms", Toast.LENGTH_SHORT).show();
                } else {
                    textViewError.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    name = String.valueOf(textInputEditTextName.getText());
                    email = String.valueOf(textInputEditTextEmail.getText());
                    password = String.valueOf(textInputEditTextPassword.getText());
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = "http://192.168.0.102/DropBoxUI/register.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressBar.setVisibility(View.GONE);
                                    if (response.equals("success")) {
                                        Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        textViewError.setText(response);
                                        textViewError.setVisibility(View.VISIBLE);
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar.setVisibility(View.GONE);
                            textViewError.setText(error.getLocalizedMessage());
                            textViewError.setVisibility(View.VISIBLE);

                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("name", name);
                            paramV.put("email", email);
                            paramV.put("password", password);
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
                }
            }
        });

        googleBtn3 = findViewById(R.id.signup_with_google);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        googleBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        LinearLayout rootLayout = findViewById(R.id.root_layout);

        imageButton = (ImageButton) findViewById(R.id.header_image_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboardAndClearfocus();
            }
        });
    }

    void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Not gud", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void navigateToSecondActivity() {
        finish();
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void hideKeyboardAndClearfocus() {
        View view = getCurrentFocus();
        if(view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            view.clearFocus();
        }
    }
}
