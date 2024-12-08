package com.example.movieapp.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilterBadgeCache @Inject constructor() {
    var isFilterApplied: Boolean = false
}