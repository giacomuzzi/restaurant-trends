package com.giac.restauranttrends.api.response

import com.giac.restauranttrends.model.entity.Collection

data class CollectionsResult (
    val collections: List<CollectionWrapper>
)

data class CollectionWrapper (
    val collection : Collection
)