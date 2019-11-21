package kz.ktzh.hserailways;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import ca.mimic.oauth2library.OAuth2Client;
import ca.mimic.oauth2library.OAuthError;
import ca.mimic.oauth2library.OAuthResponse;
import kz.ktzh.hserailways.entity.UserCreationRequest;
import kz.ktzh.hserailways.entity.UserInfo;
import kz.ktzh.hserailways.entity.Users;
import kz.ktzh.hserailways.network.NetworkServiceAuth;
import kz.ktzh.hserailways.network.NetworkServiceResource;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public final static String URL_AUTH = "http://10.64.2.156:9000/oauth/token?";
    public final static String CLIENT_ID = "clientId";
    public final static String CLIENT_SECRET = "secret";

    private boolean mIsSignUp;

    EditText etLogin;
    EditText etPassword;
    EditText etPasswordConfirm;
    EditText etFullName;
    EditText etPhone;
    EditText etEmail;
    EditText etPosition;
    Button btnLogin;
    TextView tvOut;
//    String errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        etPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        etFullName = findViewById(R.id.etFullname);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etPosition = findViewById(R.id.etPosition);
        btnLogin = findViewById(R.id.btnLogin);
        tvOut = findViewById(R.id.tvOut);

        setupView(mIsSignUp);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void doLogin(){
        final String login = etLogin.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(login) || TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Введите логин и пароль!", Toast.LENGTH_SHORT).show();
            return;
        }
        OAuth2Client.Builder builder = new OAuth2Client.Builder(CLIENT_ID, CLIENT_SECRET, URL_AUTH)
                .grantType("password")
                .username(login)
                .password(password);
        OAuth2Client client = builder.build();
        /*client.requestAccessToken(new OAuthResponseCallback() {
            @Override
            public void onResponse(OAuthResponse response) {
                if (response.isSuccessful()) {
                    String accessToken = response.getAccessToken();
//                    onGetUserInfo(login, accessToken);
                    onShowSecondActivity(accessToken, login);
                } else {
                    OAuthError error = response.getOAuthError();
                    String errorMsg = response.getOAuthError().getError();
//                    response.getCode();
                    Log.i("LOGIN FAIL", ""+errorMsg);
                    tvOut.setText(""+errorMsg);
                }
            }
        });*/
        OAuthResponse response = null;
        try {
            response = client.requestAccessToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String accessToken = null;
//        String refreshToken;
        String errorMsg = null;

        if (response.isSuccessful()) {
            accessToken = response.getAccessToken();
//            refreshToken = response.getRefreshToken();
            onShowSecondActivity(accessToken, login);
        } else {
            OAuthError error = response.getOAuthError();
            errorMsg = error.getError();
            response.getCode();
            Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    }

    public void doSignUp(){
        final String login = etLogin.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String passwordConfirm = etPasswordConfirm.getText().toString().trim();
        String fullName = etFullName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String position = etPosition.getText().toString().trim();

        if (TextUtils.isEmpty(login) || TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Введите логин и пароль!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.equals(password, passwordConfirm)){
            Toast.makeText(LoginActivity.this, "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(position)){
            Toast.makeText(LoginActivity.this, "Введите ФИО и должность!", Toast.LENGTH_SHORT).show();
            return;
        }
        Users user = new Users(login, password, (byte) 1);
        UserInfo userInfo = new UserInfo(login, fullName, phone, email, position);
        NetworkServiceAuth.
            getInstance().
            getJSONAuthApi().
            doRegistration("application/json", new UserCreationRequest(user, userInfo)).
            enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String message = null;
                    try {
                        message = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    clearEditTexts();
                    setupView(false);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Ошибка при регистрации! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void clearEditTexts(){
        etLogin.getText().clear();
        etPassword.getText().clear();
        etPasswordConfirm.getText().clear();
        etFullName.getText().clear();
        etPhone.getText().clear();
        etEmail.getText().clear();
        etPosition.getText().clear();
    }

    public void onShowSecondActivity(String token,String username){
        NetworkServiceResource.getInstance().setAccessToken(token);
        NetworkServiceAuth.getInstance().setAccessToken(token);
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra(EXTRA_MESSAGE, username);
        startActivity(intent);
    }

    private void setupView(boolean isSignUp) {
        mIsSignUp = isSignUp;
        btnLogin.setText(isSignUp ? "Зарегистрироваться" : "Войти");
        tvOut.setText(isSignUp ? "Войти" : "Зарегистрироваться");
        etPasswordConfirm.setVisibility(isSignUp ? View.VISIBLE : View.GONE);
        etFullName.setVisibility(isSignUp ? View.VISIBLE : View.GONE);
        etPhone.setVisibility(isSignUp ? View.VISIBLE : View.GONE);
        etEmail.setVisibility(isSignUp ? View.VISIBLE : View.GONE);
        etPosition.setVisibility(isSignUp ? View.VISIBLE : View.GONE);
        btnLogin.setOnClickListener(isSignUp ? doSignUpClickListener : doLoginClickListener);
        tvOut.setOnClickListener(isSignUp ? showLoginFormClickListener : showSignUpFormClickListener);
    }

    private final View.OnClickListener showSignUpFormClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setupView(true);
        }
    };

    private final View.OnClickListener showLoginFormClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setupView(false);
        }
    };

    private final View.OnClickListener doLoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            doLogin();
        }
    };

    private final View.OnClickListener doSignUpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            doSignUp();
        }
    };
}
