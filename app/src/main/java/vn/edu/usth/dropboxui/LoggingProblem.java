package vn.edu.usth.dropboxui;


import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;


public class LoggingProblem {

    private final Context context;
    private PopupWindow popupWindow;

    public LoggingProblem (Context context) {
        this.context = context;
    }

    public void showLoggingProblem(View anchorView) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.logging_problem, null);

        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.showAtLocation(anchorView, Gravity.TOP, 0, 0);
        dimBehind(popupWindow);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                clearDim();
            }
        });

        TextView forgotPassword = popupView.findViewById(R.id.forgot_password);
        TextView singleSignOn = popupView.findViewById(R.id.single_sign_on);
        TextView sendEmail = popupView.findViewById(R.id.send_email);
        TextView signInApple = popupView.findViewById(R.id.sign_in_apple);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Forgot Password clicked", Toast.LENGTH_SHORT).show();
            }
        });

        singleSignOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Single Sign-On clicked", Toast.LENGTH_SHORT).show();
            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Send Email clicked", Toast.LENGTH_SHORT).show();
            }
        });

        signInApple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Sign in with Apple clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void dimBehind(PopupWindow popupWindow) {
        WindowManager.LayoutParams layoutParams = ((SignInActivity) context).getWindow().getAttributes();
        layoutParams.alpha = 0.3f;
        ((SignInActivity) context).getWindow().setAttributes(layoutParams);
    }

    private void clearDim() {
        WindowManager.LayoutParams layoutParams = ((SignInActivity) context).getWindow().getAttributes();
        layoutParams.alpha = 1f;
        ((SignInActivity) context).getWindow().setAttributes(layoutParams);
    }
}
