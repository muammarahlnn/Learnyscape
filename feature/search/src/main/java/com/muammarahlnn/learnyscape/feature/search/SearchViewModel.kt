package com.muammarahlnn.learnyscape.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file SearchViewModel, 13/11/2023 17.16 by Muammar Ahlan Abimanyu
 */
class SearchViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")

    fun onSearchQueryChanged(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
    }
}

private const val SEARCH_QUERY = "search_query"