package com.jianastrero.yelpr.model

import com.google.gson.annotations.SerializedName

data class Location(
    var address1: String,
    var address2: String?,
    var address3: String?,
    var city: String,
    var country: String,
    @SerializedName("cross_streets")
    var crossStreets: String?,
    @SerializedName("display_address")
    var displayAddress: List<String>?,
    var state: String,
    @SerializedName("zip_code")
    var zipCode: String?
) {

    override fun toString(): String {
        return displayAddress?.let {
            it.joinToString() + ", $zipCode"
        } ?: address1 +
        if (address2 == null) "" else ", $address2" +
            if (address3 == null) "" else ", $address3" +
                ", $city, $country, $zipCode"
    }
}