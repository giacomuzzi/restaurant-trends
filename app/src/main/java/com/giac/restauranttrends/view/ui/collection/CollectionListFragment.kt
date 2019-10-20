package com.giac.restauranttrends.view.ui.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.giac.restauranttrends.R
import com.giac.restauranttrends.databinding.CollectionListFragmentBinding
import com.giac.restauranttrends.model.entity.Collection
import com.giac.restauranttrends.viewmodel.CollectionListViewModel
import com.giac.restauranttrends.vo.Resource
import com.giac.restauranttrends.vo.Status

// TODO probar reconstruccion
// TODO probar sin conexion
// TODO probar kill process
class CollectionListFragment : Fragment() {

    private lateinit var binding : CollectionListFragmentBinding

    private lateinit var collectionListViewModel : CollectionListViewModel

    private lateinit var collectionListAdapter: CollectionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.collection_list_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        collectionListAdapter = CollectionListAdapter()
        binding.collectionList.layoutManager = LinearLayoutManager(context)
        binding.collectionList.adapter = collectionListAdapter

        collectionListViewModel = ViewModelProviders.of(this).get(CollectionListViewModel::class.java)
        // TODO hardcode
        collectionListViewModel.getCollections("83").observe(this, Observer { listResource ->
            handleResponse(listResource)
        })

    }

    private fun handleResponse(collectionListResource: Resource<List<Collection>>?) {
        if (collectionListResource != null) {
            when (collectionListResource.status) {
                Status.ERROR -> {
                    // TODO agregar retry
                    binding.progressbar.visibility = View.GONE
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.errorMessage.text = collectionListResource.message
                }
                Status.LOADING -> {
                    binding.progressbar.visibility = View.VISIBLE
                    binding.errorMessage.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.progressbar.visibility = View.GONE
                    binding.errorMessage.visibility = View.GONE
                    if (collectionListResource.data?.isNotEmpty() == true) {
                        collectionListAdapter.setData(collectionListResource.data)
                        collectionListAdapter.notifyDataSetChanged()
                    } else {
                        binding.errorMessage.text = resources.getString(R.string.empty_collection_list_result)
                        binding.errorMessage.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() : CollectionListFragment =
            CollectionListFragment()
    }
}