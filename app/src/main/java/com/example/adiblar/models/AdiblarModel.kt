package com.example.adiblar.models

import java.io.Serializable

class AdiblarModel:Serializable {
    var imgUri:String? = null
    var fio:String? = null
    var birthday:String? = null
    var diedYear:String? = null
    var poetTip:String? = null
    var aboutAdib:String? = null

    constructor(
        imgUri: String?,
        fio: String?,
        birthday: String?,
        diedYear: String?,
        poetTip: String?,
        aboutAdib: String?
    ) {
        this.imgUri = imgUri
        this.fio = fio
        this.birthday = birthday
        this.diedYear = diedYear
        this.poetTip = poetTip
        this.aboutAdib = aboutAdib
    }

    constructor()


}