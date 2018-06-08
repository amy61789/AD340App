package com.example.amyfunk.ad340app

class TrafficCam(val description: String, val image: String, var type: String, var coords: DoubleArray ) {

    val baseUrl: Map<String, String> = mapOf(
            "sdot" to "http://www.seattle.gov/trafficcams/images/",
            "wsdot" to "http://images.wsdot.wa.gov/nw/"
    )

    fun imageUrl() : String {
        return baseUrl[this.type] + this.image
    }

    fun coords() : DoubleArray {
        return coords
    }

    fun getDesc() : String {
        return description
    }
}