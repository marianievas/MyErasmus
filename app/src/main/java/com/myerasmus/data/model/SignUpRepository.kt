package com.myerasmus.data.model



import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.*

/**
 * Class *SignUpRepository*
 *
 * Data Repository for the signUp screen. Encapsulates the access to data from the ViewModel.
 * Is the Model for this screen's MVVM
 * @author Ferran
 * @param dataSource is the reference to the DataSource object, which accesses Firebase and the backend
 * @property _result private mutable live data of the result of the SignUp (success/error and further info)
 * @property result public live data for the result of the SignUp
 * */
class SignUpRepository(val dataSource: SignUpDataSource) {

    private val _result = MutableLiveData<ResultSignUp>()
    val result: LiveData<ResultSignUp> = _result

    /**
     * Propagates the Sign-up process to the Data Source
     *
     * makes the call to the Data Source and observes its live data for the result
     * Sets the Repository's live data according to that of the Data Source when it is ready
     * @param email user's email
     * @param username user's username
     * @param password
     * @param activity pointer to the activity, used by the observers
     */
    fun signUp(email: String, username: String, password: String?, activity: AppCompatActivity) {
        dataSource.result.observe(
            activity,
            Observer {
                val resultDS = it ?: return@Observer

                // _result.value = resultDS  //potser es pot substituir per això
                if (resultDS.error != null) {
                    _result.value = ResultSignUp(error = resultDS.error)
                }
                if (resultDS.success != null) {
                    _result.value = ResultSignUp(success = resultDS.success)
                }
                // aqui la activity fa mes coses q suposo q aqui no calen
                dataSource.result.removeObservers(activity)
            }
        )
        if (password != null) dataSource.signUp(email, username, password, activity)
    }

    fun signUpBack(email: String, username: String, uid: String, token: String, activity: AppCompatActivity) {
        dataSource.result.observe(
            activity,
            Observer {
                val resultDS = it ?: return@Observer

                // _result.value = resultDS  //potser es pot substituir per això
                if (resultDS.error != null) {
                    _result.value = ResultSignUp(error = resultDS.error)
                }
                if (resultDS.success != null) {
                    _result.value = ResultSignUp(success = resultDS.success)
                }
                // aqui la activity fa mes coses q suposo q aqui no calen
                dataSource.result.removeObservers(activity)
            }
        )
    }
}
