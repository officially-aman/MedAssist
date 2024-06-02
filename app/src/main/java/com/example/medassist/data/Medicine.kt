package com.example.medassist.data

import java.io.Serializable

class Medicine : Serializable {
    var symptoms: String? = null
    var medicines: String? = null
    var advice: String? = null

    constructor()

    constructor(Symptoms: String?, Medicines: String?, Advice: String?) {
        this.symptoms = Symptoms
        this.medicines = Medicines
        this.advice = Advice
    }
}
