package com.giac.restauranttrends.view.ui.collection

import com.giac.restauranttrends.model.entity.Collection

interface CollectionItemCallback {
    fun onClick(cityId : String, collection: Collection)
}