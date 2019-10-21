package com.giac.restauranttrends.view.ui.collection

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.giac.restauranttrends.R
import com.giac.restauranttrends.databinding.CollectionListFragmentBinding
import com.giac.restauranttrends.model.entity.City
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
    // TODO hardcode, usar geolocation
    private var cityId = "83"

    fun setCollectionItemCallback(collectionItemCallback: CollectionItemCallback) {
        this.collectionItemCallback = collectionItemCallback
    }

    override fun createContentFragmentLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.collection_list_fragment, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        collectionListAdapter = CollectionListAdapter(collectionItemCallback)
        binding.collectionList.layoutManager = LinearLayoutManager(context)
        binding.collectionList.adapter = collectionListAdapter

        collectionListViewModel = ViewModelProviders.of(this).get(CollectionListViewModel::class.java)
        collectionListViewModel.getCollections(cityId).observe(this, CollectionListResponseHandler())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.collection_list_title)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.collection_list_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.queryHint = getString(R.string.action_search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                query?.let {
                    collectionListViewModel.findCities(query).observe(this@CollectionListFragment, CitiesResponseHandler())
                }

                // hide edit text
                searchView.setQuery("", false)
                searchView.isIconified = true

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    private inner class CollectionListResponseHandler : DefaultResponseHandler<Resource<List<Collection>>?>() {

        override fun getFragment(): AbstractBaseFragment = this@CollectionListFragment

        override fun onSuccess(resource: Resource<List<Collection>>?) {
            collectionListAdapter.setData(cityId, resource!!.requireData())
        }

        override fun onEmptyResult() {
            collectionListAdapter.clearAdapter()
        }
    }

    // TODO manejar casos de error
    // TODO sugerir ciudades para que seleccione el usuario
    private inner class CitiesResponseHandler : DefaultResponseHandler<Resource<List<City>>?>() {

        override fun getFragment(): AbstractBaseFragment = this@CollectionListFragment

        override fun onSuccess(resource: Resource<List<City>>?) {
            val cities = resource!!.requireData()
            val bestCity = cities.first()
            this@CollectionListFragment.cityId = bestCity.id.toString()
            collectionListViewModel.getCollections(this@CollectionListFragment.cityId).observe(this@CollectionListFragment, CollectionListResponseHandler())
        }
    }

    companion object {
        fun newInstance() : CollectionListFragment =
            CollectionListFragment()
    }
}