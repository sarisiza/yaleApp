package com.example.theyelpapp.utils

enum class ApiSortedBy(val sorted_by: String) {
    NEWEST("newest"),
    RELEVANT("yelp_sort"),
    BEST_MATCH("best_match"),
    RATING("rating"),
    REVIEW_COUNT("review_count"),
    DISTANCE("distance")
}