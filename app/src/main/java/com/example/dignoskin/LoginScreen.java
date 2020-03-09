package com.example.dignoskin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class LoginScreen extends AppCompatActivity {

    private static final int RC_SIGN_IN = 3;
    private FirebaseAuth mAuth;
    private GoogleApiClient googleApiClient;
    private Button signinBtn;
    ProgressDialog dialog;
//    private CoordinatorLayout coordinatorLayout;
    private TextView signtxt;
    private ImageView key;
    private ImageView lock;
    private Animation fadein,slideLeft,slideRight;
    private TextView regitxt;
    private EditText mailip;
    private EditText passip;
    private Button signinbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mAuth = FirebaseAuth.getInstance();

        fadein = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        slideLeft = AnimationUtils.loadAnimation(this,R.anim.slide_left);
        slideRight = AnimationUtils.loadAnimation(this,R.anim.slide_right);


        signinBtn = findViewById(R.id.signInBtn);
       // coordinatorLayout = findViewById(R.id.Coord2);

        signtxt = findViewById(R.id.signintxt);
        key = findViewById(R.id.key);
        lock = findViewById(R.id.lock);
        regitxt = findViewById(R.id.regtxt);
        mailip = findViewById(R.id.mail);
        passip = findViewById(R.id.pass);
        signinbtn = findViewById(R.id.loginbtn);

        signtxt.startAnimation(fadein);
        key.startAnimation(slideLeft);
        lock.startAnimation(slideRight);


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginScreen.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(LoginScreen.this,"Signin in","Just there..",true);
                dialog.show();
                signIn();
            }
        });

        regitxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this,RegisterUser.class));
            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mailip.getText().toString().isEmpty() || !passip.getText().toString().isEmpty()){
                    signInCustom();
                }else{
                    Toast.makeText(LoginScreen.this, "Empty fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void signInCustom(){

        String email = mailip.getText().toString();
        String password = passip.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(new Intent(LoginScreen.this, MainActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginScreen.this, "Authentication failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser!=null){
            startActivity(new Intent(LoginScreen.this,MainActivity.class));
            finish();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                dialog.cancel();
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                //Snackbar.make(coordinatorLayout,String.valueOf(e),Snackbar.LENGTH_SHORT).show();
                dialog.cancel();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {


        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                         //   Snackbar.make(coordinatorLayout,"Signed in!",Snackbar.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                          //  Snackbar.make(coordinatorLayout,"Error!",Snackbar.LENGTH_SHORT).show();
                            Toast.makeText(LoginScreen.this, "Error!", Toast.LENGTH_SHORT).show();

                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
