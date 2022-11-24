package com.test.data.remote.errorhandle

import android.database.sqlite.SQLiteException
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.test.data.model.ResponseErrorModel
import com.test.data.remote.DefaultResponseStatusMessage
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * This class trace exceptions(api call or parse data or connection errors) &
 * depending on exception returns a [ErrorModel]
 *
 * */
class ErrorHandler {

    fun traceErrorException(throwable: Throwable?): ErrorModel {
        return when (throwable) {
            // if throwable is an instance of HttpException
            // then attempt to parse error data from response body
            is HttpException -> {
                // handle UNAUTHORIZED situation (when token expired)
                if (throwable.code() == 401) {
                    ErrorModel(
                        throwable.message(),
                        throwable.code(),
                        ErrorModel.ErrorStatus.UNAUTHORIZED
                    )
                } else {
                    getHttpError(throwable.response()?.errorBody())
                }
            }

            // handle api call timeout error
            is SocketTimeoutException -> {
                ErrorModel(
                    DefaultResponseStatusMessage.FAILED_TRY_AGAIN,
                    ErrorModel.ErrorStatus.TIMEOUT
                )
            }

            // handle connection error
            is IOException -> {
                ErrorModel(
                    DefaultResponseStatusMessage.NO_CONNECTION,
                    ErrorModel.ErrorStatus.NO_CONNECTION
                )
            }

            // handle database error
            is SQLiteException -> {
                ErrorModel(
                    throwable.message,
                    ErrorModel.ErrorStatus.DATABASE
                )
            }
            else -> ErrorModel(
                DefaultResponseStatusMessage.FAILED_TRY_AGAIN,
                ErrorModel.ErrorStatus.BAD_REQUEST
            )
        }
    }

    /**
     * attempts to parse http response body and get error data from it
     *
     * @param body retrofit response body
     * @return returns an instance of [ErrorModel] with parsed data or NOT_DEFINED status
     */
    private fun getHttpError(body: ResponseBody?): ErrorModel {
        return try {
            // use response body to get error detail
            val result = body?.string()
            Log.d("getHttpError", "getErrorMessage() called with: errorBody = [$result]")
            val json = Gson().fromJson(result, JsonObject::class.java)
            ErrorModel(json.toString(), 400, ErrorModel.ErrorStatus.BAD_REQUEST)
        } catch (e: Throwable) {
            e.printStackTrace()
            ErrorModel(
                message = e.message,
                errorStatus = ErrorModel.ErrorStatus.NOT_DEFINED
            )
        }

    }

    fun parseError(response: Response<*>): ErrorModel {
        if (response.code() >= 500)
            return ErrorModel(
                DefaultResponseStatusMessage.FAILED_TRY_AGAIN,
                ErrorModel.ErrorStatus.BAD_REQUEST
            )

        Log.d("parseError", "parseError() called with: errorResponse = [${response.errorBody()}]")

        val errorResponse: ResponseErrorModel? = try {
            Gson().fromJson(response.errorBody()?.charStream(), ResponseErrorModel::class.java)
        } catch (e: Exception) {
            null
        }


        if (errorResponse == null) {

            try {
                if (response.errorBody() != null) {
                    val error = JSONObject(response.errorBody()!!.string())
                    if ("{\"message\":\"Token already used by other user\"}" == error.toString()) {
                        return ErrorModel(
                            response.errorBody().toString(),
                            response.code(),
                            ErrorModel.ErrorStatus.USED_BY_OTHER_USER
                        )
                    }
                }
            } catch (e: Exception) {
                return ErrorModel(
                    DefaultResponseStatusMessage.FAILED_TRY_AGAIN,
                    response.code()
                )
            }

            return ErrorModel(
                DefaultResponseStatusMessage.FAILED_TRY_AGAIN,
                ErrorModel.ErrorStatus.BAD_REQUEST
            )
        } else
            return ErrorModel(
                errorResponse.errorMessage,
                response.code()
            )
    }
}