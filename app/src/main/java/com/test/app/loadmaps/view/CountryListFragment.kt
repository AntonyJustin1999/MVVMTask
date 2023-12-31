package com.test.app.loadmaps.view

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.test.app.R
import com.test.app.databinding.FragmentCountryListBinding
import com.test.app.loadmaps.view.adapter.CountriesListAdapter
import com.test.app.loadmaps.viewmodel.CountryDetailsViewModelFactory
import com.test.app.loadmaps.viewmodel.CountryListViewModelFactory
import com.test.app.loadmaps.viewmodel.CountryListViewModelImpl

class CountryListFragment : Fragment {
    private var mCountryListView: View? = null
    var mContext: Context? = null
    var activity: Activity? = null
    //private var rv_country_list: RecyclerView? = null
    private lateinit var viewModel: CountryListViewModelImpl
    private lateinit var databinding: FragmentCountryListBinding

    constructor() {
        // Required empty public constructor
    }

    constructor(context: Context?) {
        this.mContext = context
    }

    // Override function when the view is being created
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflates the custom fragment layout
        val bundle = arguments
        val message = bundle?.getString("mText")

        databinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_country_list, container, false)
        mCountryListView = databinding.getRoot()
        val factory = CountryListViewModelFactory(mContext!!)
        viewModel = ViewModelProvider(this, factory).get(CountryListViewModelImpl::class.java)
        databinding.countrylistViewModel = viewModel
        databinding.lifecycleOwner = viewLifecycleOwner
        lifecycle.addObserver(viewModel)

        //rv_country_list = mCountryListView?.findViewById<RecyclerView>(R.id.rv_restaurant_list)

//        viewModel?.errorMessage?.observe(viewLifecycleOwner) {
//                errMsg -> showAlertDialogBox("", errMsg)
//        }
        viewModel?.mutableCountryList?.observe(viewLifecycleOwner) { countrylist ->
            val countryListRecyclerViewAdapter = CountriesListAdapter(countrylist, context)
            databinding.setMyAdapter(countryListRecyclerViewAdapter)
        }
//        viewModel?.countryListErrorMessage?.observe(viewLifecycleOwner) { msg ->
//            onFailureCountryList(msg) }
//        viewModel?.isProgressShow?.observe(viewLifecycleOwner) { isShow ->
//            if (isShow!!) {
//                databinding.progressShow = true
//            } else {
//                databinding.progressShow = false
//            }
//        }
//        viewModel?.onNetwork?.observe(viewLifecycleOwner) { isNetwork ->
//            if (isNetwork!!) {
//                showError("NetworkNotAvailable")
//                onFailureCountryList("Network Not Available")
//            }
//        }

        viewModel?.loadCountryList()

        return mCountryListView
    }

    private fun showAlertDialogBox(title: String, msg: String?) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton(
            android.R.string.yes
        ) { dialog, which -> dialog.dismiss() }
        builder.show()
    }

}