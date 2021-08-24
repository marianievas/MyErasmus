import androidx.lifecycle.LiveData
import com.myerasmus.data.model.LoggedInUser

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val data: LiveData<LoggedInUser>
    // ... other data fields that may be accessible to the UI
)
