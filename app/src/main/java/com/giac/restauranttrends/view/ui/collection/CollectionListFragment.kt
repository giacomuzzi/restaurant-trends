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
import com.giac.restauranttrends.util.requireData
import com.giac.restauranttrends.view.ui.AbstractBaseFragment
import com.giac.restauranttrends.view.ui.common.DefaultResponseHandler
import com.giac.restauranttrends.viewmodel.CollectionListViewModel
import com.giac.restauranttrends.vo.Resource

// TODO probar sin conexion
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

        // TODO hardcode
        val cityId = "83"

        collectionListAdapter = CollectionListAdapter(cityId, collectionItemCallback)
        binding.collectionList.layoutManager = LinearLayoutManager(context)
        binding.collectionList.adapter = collectionListAdapter

        collectionListViewModel = ViewModelProviders.of(this).get(CollectionListViewModel::class.java)
        collectionListViewModel.getCollections(cityId).observe(this, CollectionListResponseHandler())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.collection_list_title)
    }

    private inner class CollectionListResponseHandler : DefaultResponseHandler<Resource<List<Collection>>?>() {

        override fun getFragment(): AbstractBaseFragment = this@CollectionListFragment

        override fun onSuccess(resource: Resource<List<Collection>>?) {
            collectionListAdapter.setData(resource!!.requireData())
        }
    }

    companion object {
        fun newInstance() : CollectionListFragment =
            CollectionListFragment()
    }
}