package com.example.theyelpapp.utils

class PermissionsRequiredException(message: String = "No permissions granted"): Exception(message)
class LocationRequiredException(message: String = "Location is off"): Exception(message)