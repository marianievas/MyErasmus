package com.myerasmus.data.model

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

/**
 * Class *SignUpDataSource*
 *
 * @author Ferran and Pau
 * @property _result private mutable live data of the result of the SignUp (success/error and further info)
 * @property result public live data for the result of the SignUp
 * @property firebaseAuth : firebase Authentication
 * @property retrofit: retrofit client to access the back-end
 * @property signUpService : implementation of the SignUpService Interface, used to access the back-end
 * */
class SignUpDataSource {

    private var _result = MutableLiveData<ResultSignUp>()
    val result: LiveData<ResultSignUp> = _result // aquest es observat per Repository

    private lateinit var firebaseAuth: FirebaseAuth

    /**
     * performs the Sign-Up on Firebase and the Back-end
     *
     * creates a user on firebase using the e-mail and password
     * sends a Firebase confirmation e-mail
     * creates a user on our database with all the data
     *
     * Observes the result of both user creations and sets the live data accordingly (success/error, and further info)
     * is supposed to put the appropriate error messages in the live data, but for now puts some error messages on screen
     *
     * @param email user's email
     * @param username user's username
     * @param password
     */
    fun signUp(email: String, username: String, password: String, activity: AppCompatActivity) { // TODO treure activity quan arregli observers (no passarĂ  mai)

        try {
            firebaseAuth = Firebase.auth
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) { // Sign in success
                    Log.d("Sign-up", "createUserWithEmail:success")

                    val user = firebaseAuth.currentUser
                    user!!.sendEmailVerification().addOnCompleteListener { task2 ->
                        if (task2.isSuccessful) {
                            Log.d("Verification email", "Email sent.")
                        }
                    }

                    // parlar amb el nostre client -- token
                  /*  FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                            return@OnCompleteListener
                        }

                        // Get new FCM registration token
                        val token = task.result

                        //val signedUpUser = SignUpUserData(email, user.uid, token)
                        //signUpBack(username, signedUpUser)
                    })*/
                } else { // error a Firebase
                    Log.w("Sign-up", "createUserWithEmail:failure", task.exception)

                    _result.value = ResultSignUp(error = task.exception)
                }
            }
        } catch (e: Throwable) {
            _result.value = ResultSignUp(error = e as Exception) // cast!
        }
    }
}
