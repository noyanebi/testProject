package com.test.data.model

import com.google.gson.annotations.SerializedName
import com.test.data.remote.DefaultResponseStatusMessage

data class ResponseErrorModel constructor(

    @SerializedName("detail")
    var errorMessage: String = DefaultResponseStatusMessage.FAILED_TRY_AGAIN

)