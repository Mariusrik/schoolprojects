package no.westerdals.eksamen.app2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import no.westerdals.eksamen.app2.Model.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    EditText editTextUser;
    EditText editTextPass;
    Button loginButton;
    TextView signUpLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUser = (EditText) findViewById(R.id.email_address);
        editTextPass = (EditText) findViewById(R.id.password_edit_text);
        loginButton = (Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(this);

        signUpLink = (TextView) findViewById(R.id.link_signup);
        signUpLink.setOnClickListener(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        //opens main activity if already signed in
        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            LoginActivity.this.startActivity(intent);
            finish();
        }
    }


    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    /**
     * Signup is only with username and password.
     * Room number will be connected to user id in the reception.
     */

    private void createAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "User: " + user.getEmail() + " created",
                                    Toast.LENGTH_SHORT).show();
                            registerUserDetails(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }


    private void registerUserDetails(FirebaseUser user) {
        writeNewUser(user.getUid(), user.getEmail(), "");
    }

    private void writeNewUser(String userId, String email, String roomNumber) {
        User user = new User(email, roomNumber);

        mDatabase.child("users").child(userId).setValue(user);
    }


    //TODO: make better validation on email etc
    private boolean validateForm() {
        boolean validLogin = true;
        String email = editTextUser.getText().toString();
        String password = editTextPass.getText().toString();

        if (TextUtils.isEmpty(email)) {
            editTextUser.setError("Email address is required");
            validLogin = false;
        } else
            editTextUser.setError(null);

        if (TextUtils.isEmpty(password)) {
            editTextPass.setError(" Password is required");
            validLogin = false;
        } else
            editTextPass.setError(null);

        return validLogin;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            signIn(editTextUser.getText().toString(), editTextPass.getText().toString());
        } else if (v.getId() == R.id.link_signup) {
            //createAccount(editTextUser.getText().toString(), editTextPass.getText().toString());
            signupForm();
        }
    }


    public void signupForm() {

        final AlertDialog.Builder signUpDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.sign_up_form,(ViewGroup) findViewById(R.id.sign_up_form));
        signUpDialog.setView(layout);

        final EditText emailAddress = (EditText) layout.findViewById(R.id.email_address);
        final EditText repeatEmailAddress = (EditText) layout.findViewById(R.id.repeat_email_address);

        final EditText password = (EditText) layout.findViewById(R.id.password_edit_text);
        final EditText repeatPassword = (EditText) layout.findViewById(R.id.repeat_password_edit_text);

        Button button = (Button) layout.findViewById(R.id.btn_sign_up);

        signUpDialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

            }

        });

        signUpDialog.create();
        final AlertDialog d = signUpDialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateSignUpForm(password, repeatPassword, emailAddress, repeatEmailAddress)) {
                    createAccount(emailAddress.getText().toString(), password.getText().toString());
                            d.dismiss();
                }
            }
        });
    }

    private boolean validateSignUpForm(EditText password, EditText repeatPassword, EditText emailAdress, EditText repeatEmailAdress){
            boolean validated = true;

        if (!TextUtils.equals(password.getText(), repeatPassword.getText())){
            password.setError("Passwords not matching");
            repeatPassword.setError("Passwords not matching");
            validated = false;
        } else if(TextUtils.isEmpty(password.getText())){
            password.setError("Cannot be empty");
            validated = false;
        }else if(TextUtils.isEmpty(repeatPassword.getText())){
            repeatPassword.setError("Cannot be empty");
            validated = false;
        } else {
            password.setError(null);
            repeatPassword.setError(null);
        }

        if (!TextUtils.equals(emailAdress.getText(), repeatEmailAdress.getText())){
            emailAdress.setError("Email not matching");
            repeatEmailAdress.setError("Email not matching");
            validated = false;
        } else if(TextUtils.isEmpty(emailAdress.getText())){
            emailAdress.setError("Cannot be empty");
            validated = false;
        }else if(TextUtils.isEmpty(repeatEmailAdress.getText())){
            repeatEmailAdress.setError("Cannot be empty");
            validated = false;
        } else {
            emailAdress.setError(null);
            repeatEmailAdress.setError(null);
        }


        return validated;
    }

}