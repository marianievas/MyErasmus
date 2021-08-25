package com.myerasmus.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myerasmus.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DiaryListFragment: Fragment() {

        companion object {
            fun newInstance() =
                    DiaryListFragment()
        }

        //private lateinit var activitiesListAdapter: ActivitiesListRecyclerViewAdapter
        private var diaryList: MutableList<DiaryListFragment> = ArrayList()
       // private val spinnerDialog = view?.findViewById<Spinner>(R.id.spinnerCategories)

        /**
         * Called to initialize the fragment and has the observers, returns the view inflated
         * @param inflater is the Layout inflater to inflate the view
         * @param container is the part which contains the view
         * @param savedInstanceState is the last saved instance of the view
         */
       /* override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View {
            return
        //    return inflater.inflate(R.layout.activities_list_fragment, container, false)
        }

        /**
         * Called once the view is inflated and here is where we display the information and we initizalize other views
         * @param view is the view initialized by the onCreateView function
         * @param savedInstanceState is the last saved instance of the view
         */
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
        }


        }*/
}