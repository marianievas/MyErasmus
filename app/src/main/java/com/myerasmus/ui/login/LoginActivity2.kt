package com.myerasmus.ui.login.login


import LoggedInUserView
import LoginViewModel
import LoginViewModelFactory
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.myerasmus.MainActivity
import com.myerasmus.R
import com.myerasmus.ui.signup.SignUpActivity
import com.myerasmus.common.Constants
import com.myerasmus.common.SharedPreferenceManager
import com.myerasmus.ui.signup.SignUpViewModel
import com.offhome.app.ui.signup.SignUpViewModelFactory

//import com.offhome.app.ui.onboarding.OnboardingActivity
//import com.offhome.app.ui.recoverPassword.RecoverPasswordActivity
/*import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.messaging.FirebaseMessaging*/

/**
 * Class *LoginActicity*
 *
 * This class is the one that interacts with the User
 * @author Pau Cuesta Arcos
 * @property loginViewModel references the ViewModel class for this Activity
 * @property loading references the ProgressBar shown while doing the login in background until receiving a response
 * @property editTextEmail references the EditText to input the email of the user
 * @property editTextPassword references the EditText to input the password of the user
 * @property btnLogin references the Button to do the login to the app
 * @property btnToSignUp references the TextView that allows the user to navigate to another activity to SignUp
 * @property btnLoginGoogle references the Button to login to the app through Google Sign In
 */
class LoginActivity2 : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loading: ProgressBar
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var btnShowPassword: ImageView
    private lateinit var btnLogin: Button
    private lateinit var btnToSignUp: TextView
    private lateinit var btnLoginGoogle: Button
    //   private lateinit var btnRecoverPassword: TextView

    //  private val GOOGLE_SIGN_IN = 100

    /**
     * It is executed when the activity is launched for first time or created again following
     * activities lifecycle.
     * @param savedInstanceState is the instance of the saved State of the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //  setTheme(R.style.theme_My_Erasmus)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Log.d("first time", "is_first_time = " + SharedPreferenceManager.getBooleanValue(Constants().PREF_IS_NOT_FIRST_TIME_OPENING_APP))

        if (!SharedPreferenceManager.getBooleanValue(Constants().PREF_IS_NOT_FIRST_TIME_OPENING_APP)) {
            Log.d("first time2", "2 is_first_time = " + SharedPreferenceManager.getBooleanValue(Constants().PREF_IS_NOT_FIRST_TIME_OPENING_APP))
            //val intent = Intent(this, OnboardingActivity::class.java)  //aix?? es el splash screen
            startActivity(intent)
            finish()
        }
        if (SharedPreferenceManager.getStringValue(Constants().PREF_EMAIL) != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        setUp()
        startObservers()
        editTextsChanges()
        btnShowPassword.setOnClickListener {
            editTextPassword.inputType = if (editTextPassword.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            editTextPassword.setSelection(editTextPassword.text.length)
        }

        btnToSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        /*      btnRecoverPassword.setOnClickListener {
            val intent = Intent(this, RecoverPasswordActivity::class.java)
            startActivity(intent)
        }

        btnLoginGoogle.setOnClickListener {
            loading.visibility = View.VISIBLE
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this, gso)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }*/
    }

    /**
     * Gets the result of the intent and signs in or signs up.
     * It also calls the view model to send info to the backend.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        signUp()
        /* if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("LOGIN", "signInWithEmail:success")
                        SharedPreferenceManager.setStringValue(
                                Constants().PREF_EMAIL,
                                account.email.toString()
                        )
                        SharedPreferenceManager.setStringValue(
                                Constants().PREF_PROVIDER,
                                Constants().PREF_PROVIDER_GOOGLE
                        )
                        SharedPreferenceManager.setStringValue(
                                Constants().PREF_UID,
                                FirebaseAuth.getInstance().currentUser!!.uid
                        )
                        SharedPreferenceManager.setStringValue(
                                Constants().PREF_USERNAME,
                                it.data.username
                        )
                        val welcome = getString(R.string.welcome)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(
                                applicationContext,
                                "$welcome ${account.email}",
                                Toast.LENGTH_LONG
                        ).show()
                        finish()
                    } else signUp()
                }
          //  else {
                        Log.w("LOGIN", "signInWithEmail:failure", it.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: ApiException) {
                Log.w("LOGIN", "signInWithGoogle:failure", e.cause)
         /*       Toast.makeText(
                    baseContext, "Authentication google failed.",
                    Toast.LENGTH_SHORT
                ).show()*/
            }
        }*/
    }

    /**
     * It set's the title of the activity and it binds all the components from the .xml file
     * to the activity to be able to deal with them
     */
    private fun setUp() {
        title = "My Erasmus"
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        btnShowPassword = findViewById(R.id.imageViewShowPassword)
        btnLogin = findViewById(R.id.buttonLogin)
        btnToSignUp = findViewById(R.id.textViewHere)
        btnLoginGoogle = findViewById(R.id.buttonGoogleLogin)
        loading = findViewById(R.id.loading)
        //       btnRecoverPassword = findViewById(R.id.textViewHereRecover)

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
                .get(LoginViewModel::class.java)
    }

    /**
     * It starts the observers of the state of the login and the login result. In case of changes,
     * it takes the necessary actions
     */
    private fun startObservers() {
        loginViewModel.loginFormState.observe(
                this@LoginActivity2,
                Observer {
                    val loginState = it ?: return@Observer

                    // disable login button unless both username / password is valid
                    btnLogin.isEnabled = loginState.isDataValid

                    if (editTextEmail.text.isNotEmpty() && loginState.emailError != null) {
                        editTextEmail.setBackgroundResource(R.drawable.background_edit_text_wrong)
                        Toast.makeText(this, getString(loginState.emailError), Toast.LENGTH_LONG).show()
                    }
                }
        )

        loginViewModel.loginResult.observe(
                this@LoginActivity2,
                Observer {
                    val loginResult = it ?: return@Observer

                    if (loginResult.error != null) {
                        loading.visibility = View.GONE
                        showLoginFailed(loginResult.error)
                    }
                    if (loginResult.success != null) {
                        updateUiWithUser(loginResult.success)
                    }
                    setResult(Activity.RESULT_OK)
                    // Complete and destroy login activity once successful
                    // finish()
                }
        )
    }

    /**
     * Treats with the editTexts in case the user input some text in them
     */
    private fun editTextsChanges() {
        editTextEmail.afterTextChanged {
            editTextEmail.setBackgroundResource(R.drawable.background_edit_text)
        }

        editTextEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                loginViewModel.loginDataChanged(
                        editTextEmail.text.toString(),
                        editTextPassword.text.toString()
                )
        }

        editTextPassword.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus)
                    loginViewModel.loginDataChanged(
                            editTextEmail.text.toString(),
                            editTextPassword.text.toString()
                    )
            }

            afterTextChanged {
                setBackgroundResource(R.drawable.background_edit_text)
                loginViewModel.loginDataChanged(
                        editTextEmail.text.toString(),
                        editTextPassword.text.toString()
                )
            }

           /* setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                                editTextEmail.text.toString(),
                                editTextPassword.text.toString()
                        )
                }
                false
            }

            btnLogin.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(
                        editTextEmail.text.toString(),
                        editTextPassword.text.toString()
                )
            }*/
        }
    }

    /**
     * It updates the UI in the case the login is successful. Actually changes to the main activity
     * of the app
     * @param model is the data of the logged user that may be shown later
     */
    private fun updateUiWithUser(model: LoggedInUserView) {
        model.data.observe(
                this@LoginActivity2,
                {
                    loading.visibility = View.GONE
                    val welcome = getString(R.string.welcome)
                    when {
                        it.errorLogin != null && it.errorLogin == R.string.login_failed_email -> Toast.makeText(applicationContext, getString(R.string.login_failed_email), Toast.LENGTH_LONG).show()
                        it.errorLogin != null && it.errorLogin == R.string.login_failed_login -> Toast.makeText(applicationContext, getString(R.string.login_failed_login), Toast.LENGTH_LONG).show()
                        else -> {
                            //val username = it.data.username
                            val username = "maria"
                            SharedPreferenceManager.setStringValue(Constants().PREF_USERNAME, username)
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(
                                    applicationContext,
                                    "$welcome $username",
                                    Toast.LENGTH_LONG
                            ).show()
                            finish()
                        }
                    }
                }
        )
    }

    /**
     * In case the login is unsuccessful, it shows a error Toast to the user
     * @param errorString is the string that will be shown to the user
     */
    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    private fun signUp() {
        val usernameDialog = AlertDialog.Builder(this@LoginActivity2)
        val view = layoutInflater.inflate(R.layout.dialog_username, null)
        usernameDialog.setTitle(R.string.set_username)
        usernameDialog.setMessage(R.string.set_username_description)
        usernameDialog.setPositiveButton(R.string.ok) { dialog, id ->
            loading.visibility = View.VISIBLE
            val user = FirebaseAuth.getInstance().currentUser
            val signUpViewModel = ViewModelProvider(this, SignUpViewModelFactory())
                    .get(SignUpViewModel::class.java)
            /*  signUpViewModel.signUpResult.observe(
                this,
                Observer {
                    val signUpResultVM = it ?: return@Observer

                    loading.visibility = View.GONE
                    if (signUpResultVM.success != null) {
                        SharedPreferenceManager.setStringValue(Constants().PREF_EMAIL, FirebaseAuth.getInstance().currentUser.email)
                        SharedPreferenceManager.setStringValue(Constants().PREF_PROVIDER, Constants().PREF_PROVIDER_GOOGLE)
                        SharedPreferenceManager.setStringValue(
                            Constants().PREF_UID,
                            FirebaseAuth.getInstance().currentUser!!.uid
                        )
                        SharedPreferenceManager.setStringValue(Constants().PREF_USERNAME, view.findViewById<EditText>(R.id.editTextUsername).text.toString())
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        dialog.dismiss()
                    } else {
                        Toast.makeText(this, getString(R.string.error_username), Toast.LENGTH_LONG).show()
                    }
                    setResult(Activity.RESULT_OK)
                }
            )*/
           /* FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                if (user != null) {
                    user.email?.let {
                        signUpViewModel.signUpBack(
                                it,
                                view.findViewById<EditText>(R.id.editTextUsername).text.toString(),
                                user.uid,
                                token,
                                this@LoginActivity
                        )
                    }
                }
            })*/

        }
        usernameDialog.setNegativeButton(R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }
        usernameDialog.setView(view)
        usernameDialog.show()
    }

    /**
     * Extension function to simplify setting an afterTextChanged action to EditText components.
     */
    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}
