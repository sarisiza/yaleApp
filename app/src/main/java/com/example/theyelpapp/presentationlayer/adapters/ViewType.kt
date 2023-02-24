package com.example.theyelpapp.presentationlayer.adapters

import com.example.theyelpapp.datalayer.domain.Rating
import com.example.theyelpapp.datalayer.domain.Restaurant

sealed class ViewType{

    data class RATING(val rating: Rating): ViewType()

    data class USER(val rating: Rating): ViewType()

}
