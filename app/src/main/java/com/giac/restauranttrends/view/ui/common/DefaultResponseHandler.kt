package com.giac.restauranttrends.view.ui.common

import android.view.View
import androidx.lifecycle.Observer
import com.giac.restauranttrends.view.ui.AbstractBaseFragment
import com.giac.restauranttrends.vo.Resource
import com.giac.restauranttrends.vo.Status



abstract class DefaultResponseHandler<T : Resource<*>?> : Observer<T> {

    abstract fun getFragment() : AbstractBaseFragment

    override fun onChanged(resource: T) {
        if (resource != null) {
            when (resource.status) {
                Status.ERROR -> {
                    onError(resource)
                }
                Status.LOADING -> {
                    onLoading()
                }
                Status.SUCCESS -> {
                    getFragment().progressbar.visibility = View.GONE
                    getFragment().errorMessage.visibility = View.GONE
                    if (resource.data == null || isEmptyList(resource)) {
                        getFragment().errorMessage.text = getFragment().resources.getString(com.giac.restauranttrends.R.string.empty_collection_list_result)
                        getFragment().errorMessage.visibility = View.VISIBLE
                        onEmptyResult()
                    } else {
                        onSuccess(resource)
                    }
                }
            }
        }
    }

    open fun onLoading() {
        getFragment().progressbar.visibility = View.VISIBLE
        getFragment().errorMessage.visibility = View.GONE
    }

    open fun onError(resource: T) {
        // TODO agregar retry
        getFragment().progressbar.visibility = View.GONE
        getFragment().errorMessage.visibility = View.VISIBLE
        getFragment().errorMessage.text = resource!!.message
    }

    abstract fun onSuccess(resource: T)

    open fun onEmptyResult(){
        // Do nothing
    }

    private fun isEmptyList(resource: T) : Boolean {
        val list = resource?.data as? List<*>
        return list?.isEmpty() ?: false
    }
}
