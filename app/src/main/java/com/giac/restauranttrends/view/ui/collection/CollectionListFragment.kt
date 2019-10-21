package com.giac.restauranttrends.view.ui.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.giac.restauranttrends.R
import com.giac.restauranttrends.databinding.CollectionListFragmentBinding
import com.giac.restauranttrends.model.entity.Collection
import com.giac.restauranttrends.view.ui.AbstractBaseFragment
import com.giac.restauranttrends.view.ui.common.DefaultResponseHandler
import com.giac.restauranttrends.viewmodel.CollectionListViewModel
import com.giac.restauranttrends.vo.Resource

// TODO probar reconstruccion
// TODO probar sin conexion
// TODO probar kill process
class CollectionListFragment : AbstractBaseFragment() {

    private lateinit var binding : CollectionListFragmentBinding
    private lateinit var collectionListViewModel : CollectionListViewModel
    private lateinit var collectionListAdapter: CollectionListAdapter

    private lateinit var collectionItemCallback: CollectionItemCallback

    fun setCollectionItemCallback(collectionItemCallback: CollectionItemCallback) {
        this.collectionItemCallback = collectionItemCallback
    }

    override fun createContentFragmentLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.collection_list_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        collectionListAdapter = CollectionListAdapter(object : CollectionItemCallback {
            override fun onClick(collection: Collection) {
                collectionItemCallback.onClick(collection)
            }
        })
        binding.collectionList.layoutManager = LinearLayoutManager(context)
        binding.collectionList.adapter = collectionListAdapter

        collectionListViewModel = ViewModelProviders.of(this).get(CollectionListViewModel::class.java)
        // TODO hardcode
        collectionListViewModel.getCollections("83").observe(this, CollectionListResponseHandler())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.collection_list_title)
    }

    private inner class CollectionListResponseHandler : DefaultResponseHandler<Resource<List<Collection>>?>() {

        override fun getFragment(): AbstractBaseFragment = this@CollectionListFragment

        override fun onSuccess(resource: Resource<List<Collection>>?) {
            super.onSuccess(resource)
            if (resource?.data?.isNotEmpty() == true) {
                collectionListAdapter.setData(resource.data)
                collectionListAdapter.notifyDataSetChanged()
            } else {
                errorMessage.text = resources.getString(R.string.empty_collection_list_result)
                errorMessage.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        fun newInstance() : CollectionListFragment =
            CollectionListFragment()
    }
}