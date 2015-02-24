package com.pichangetheworld.moasample.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.pichangetheworld.moasample.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * MoaSample
 * Author: pchan
 * Date: 15/02/24.
 */
public class SigninActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {
    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 902;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    // We use mSignInProgress to track whether user has clicked sign in.
    // mSignInProgress can be one of three values:
    //
    //       STATE_DEFAULT: The default state of the application before the user
    //                      has clicked 'sign in', or after they have clicked
    //                      'sign out'.  In this state we will not attempt to
    //                      resolve sign in errors and so will display our
    //                      Activity in a signed out state.
    //       STATE_SIGN_IN: This state indicates that the user has clicked 'sign
    //                      in', so resolve successive errors preventing sign in
    //                      until the user has successfully authorized an account
    //                      for our app.
    //   STATE_IN_PROGRESS: This state indicates that we have started an intent to
    //                      resolve an error, and so we should not start further
    //                      intents until the current intent completes.
    private static final int STATE_DEFAULT = 0;
    private static final int STATE_SIGN_IN = 1;
    private static final int STATE_IN_PROGRESS = 2;

    private int mSignInProgress;

    // Used to store the PendingIntent most recently returned by Google Play
    // services until the user clicks 'sign in'.
    private PendingIntent mSignInIntent;

    private static final String CLIENT_ID =
            "477431187441-kcg0oomusv8d7bl1qqlp56ve6kb46tlq.apps.googleusercontent.com";

    // Scope for AuthUtil.getToken()
    private static final String SCOPE = "audience:server:client_id:" + CLIENT_ID;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

//        Button btn2 = (Button) findViewById(R.id.tutorial_button);
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TutorialFragment dialog = new TutorialFragment();
//                dialog.show(getSupportFragmentManager(), "Tutorial");
//            }
//        });

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);

        // Sign in with Google+ button
        findViewById(R.id.signin_google).setOnClickListener(this);

        mGoogleApiClient = buildGoogleApiClient();
    }

    private GoogleApiClient buildGoogleApiClient() {
        // When we build the GoogleApiClient we specify where connected and
        // connection failed callbacks should be returned, which Google APIs our
        // app uses and which OAuth 2.0 scopes our app requests.
        return new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
    }

    @Override
    protected void onStart() {
        Log.d("StartActivity", "onStart");
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    // Called if the application loses service connection
    @Override
    public void onConnectionSuspended(int i) {
        Log.d("StartActivity", "Activity suspended");
        mGoogleApiClient.connect();
    }

    // Clicking the signin button
    @Override
    public void onClick(View view) {
        Log.d("StartActivity", "Signed in: " + mGoogleApiClient.isConnected());
        if (view.getId() == R.id.signin_google
                && !mGoogleApiClient.isConnecting()) {
            Log.d("StartActivity", "Signing in: on clicked!");
            resolveSignInError();
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    // When unable to establish a connection, ConnectionResult can be used to resolve the error
    //  using ConnectionResult.getResolution(), which will send a request to Google Play Services
    //  to get any user interaction required to resolve sign in errors
    //  (e.g. choose an account, enable more permissions, etc)
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.d("StartActivity", "Connection failed ... " + result.getErrorCode());
        if (result.getErrorCode() == ConnectionResult.API_UNAVAILABLE) {
            // An API requested for GoogleApiClient is not available. The device's current
            // configuration might not be supported with the requested API or a required component
            // may not be installed, such as the Android Wear application. You may need to use a
            // second GoogleApiClient to manage the application's optional APIs.
            Log.e("StartActivity", "API is unavailable!");
        } else if (mSignInProgress != STATE_IN_PROGRESS) {
            // We do not have an intent in progress so we should store the latest
            // error resolution intent for use when the sign in button is clicked.
            mSignInIntent = result.getResolution();

            if (mSignInProgress == STATE_SIGN_IN) {
                // STATE_SIGN_IN indicates the user already clicked the sign in button
                // so we should continue processing errors until the user is signed in
                // or they click cancel.
                resolveSignInError();
            }
        }
    }

    /* A helper method to resolve the current ConnectionResult error. */
    private void resolveSignInError() {
        if (mSignInIntent != null) {
            // We have an intent which will allow our user to sign in or
            // resolve an error.  For example if the user needs to
            // select an account to sign in with, or if they need to consent
            // to the permissions your app is requesting.

            try {
                // Send the pending intent that we stored on the most recent
                // OnConnectionFailed callback.  This will allow the user to
                // resolve the error currently preventing our connection to
                // Google Play services.
                mSignInProgress = STATE_IN_PROGRESS;
                startIntentSenderForResult(mSignInIntent.getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                Log.i("StartActivity", "Sign in intent could not be sent: "
                        + e.getLocalizedMessage());
                // The intent was canceled before it was sent.  Attempt to connect to
                // get an updated ConnectionResult.
                mSignInProgress = STATE_SIGN_IN;
                mGoogleApiClient.connect();
            }
        }
    }

    // Capture the result for onConnectionFailed, above
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Log.d("StartActivity", "OnActivityResult result " + resultCode);
            if (resultCode == RESULT_OK) {
                // If the error resolution was successful we should continue
                // processing errors.
                mSignInProgress = STATE_SIGN_IN;
            } else {
                // If the error resolution was not successful or the user canceled,
                // we should stop processing errors.
                mSignInProgress = STATE_DEFAULT;
            }

            Log.d("StartActivity", "Connecting? " + mGoogleApiClient.isConnecting());
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    // User has successfully signed in
    @Override
    public void onConnected(Bundle bundle) {
        Log.d("StartActivity", "Successfully signed in! Getting me data");

        // Indicate that the sign in process is complete.
        mSignInProgress = STATE_DEFAULT;

        final Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        if (currentPerson != null) {
            new AsyncTask<Void, Void, String>() {
                @Override
                protected void onPostExecute(String token) {
                    String id = currentPerson.getId();
                    String personName = currentPerson.getDisplayName();
                    String imageUrl = currentPerson.getImage().getUrl();
                    signInToServer(id, token, personName, imageUrl);
                }

                @Override
                protected String doInBackground(Void... params) {
                    String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

                    try {
                        final String token = GoogleAuthUtil.getToken(
                                SigninActivity.this, email, SCOPE);

                        Log.d("StartActivity", "Got token " + token);

                        return token;
                    } catch (UserRecoverableAuthException userAuthEx) {
                        // Start the user recoverable action using the intent returned by
                        // getIntent()
                        SigninActivity.this.startActivityForResult(
                                userAuthEx.getIntent(),
                                RC_SIGN_IN);
                    } catch (IOException e) {
                        // network or server error, the call is expected to succeed if you try again later.
                        // Don't attempt to call again immediately - the request is likely to
                        // fail, you'll hit quotas or back-off.
                        e.printStackTrace();
                    } catch (GoogleAuthException e) {
                        Log.e("StartActivity", "Failed Authentication!");
                        e.printStackTrace();
                    }

                    return null;
                }
            }.execute((Void) null);
        } else {
            Log.e("StartActivity", "No user!");
        }
    }

    private void signInToServer(String id, String token, final String name, final String imageUrl) {
        Log.d("StartActivity", "Signing in to server " + id + " name:" + name + " image:" + imageUrl);

        JSONObject jData = new JSONObject();
        try {
            jData.put("google_id", id);
            jData.put("google_auth_token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(SigninActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
//        StringEntity entity;
//        try {
//            entity = new StringEntity(jData.toString());
//
//            mProgressBar.setVisibility(View.VISIBLE);

            // pass user info to server
//            ServerRestClient.post(StartActivity.this, "auth", entity, "application/json",
//                    new JsonHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
//                            // If the response is JSONObject instead of expected JSONArray
//                            Log.d("JsonHTTPResponse", "Response is " + response.toString());
//
//                            Thread thread = new Thread() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        Thread.sleep(4000);
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
//                                    STApplication.getInstance().init(name, imageUrl, response);
//                                    Intent startGameIntent = new Intent(StartActivity.this, TutorialMapActivity.class);
//                                    startActivity(startGameIntent);
//                                }
//                            };
//                            thread.start();
//                        }
//
//                        private void onFailResponse(int statusCode) {
//                            mProgressBar.setVisibility(View.GONE);
//                            switch (statusCode) {
//                                // TODO handle timeout error, invalid user error
//                                default:
//                                    Log.e("Signin", "Failed with error code: " + statusCode);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                            super.onFailure(statusCode, headers, throwable, errorResponse);
//                            onFailResponse(statusCode);
//                        }
//
//                        @Override
//                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                            super.onFailure(statusCode, headers, throwable, errorResponse);
//                            onFailResponse(statusCode);
//                        }
//
//                        @Override
//                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                            super.onFailure(statusCode, headers, responseString, throwable);
//                            onFailResponse(statusCode);
//                        }
//                    });
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
    }
}
