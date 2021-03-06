package com.offhome.app.ui.signup



import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myerasmus.data.model.SignUpDataSource
import com.myerasmus.data.model.SignUpRepository
import com.myerasmus.ui.signup.SignUpViewModel

/**
 * Class *SignUpViewModelFactory*
 *
 * ViewModel provider factory to instantiate SignUpViewModel.
 * Required given SignUpViewModel has a non-empty constructor
 *
 * @author Ferran
 *
 */
class SignUpViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(
                signUpRepository = SignUpRepository( // renamed l'atribut de loginRepository a signUpRepositori
                    dataSource = SignUpDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
