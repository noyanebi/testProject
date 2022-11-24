package com.test.data.remote.errorhandle

/**
 * This class designed to show different types of errors through error status & message
 *
 * */
data class ErrorModel(
    val message: String?,
    val code: Int?,
    var errorStatus: ErrorStatus?
) {
    constructor(errorStatus: ErrorStatus) : this(null, null, errorStatus)
    constructor(message: String?, errorStatus: ErrorStatus) : this(message, null, errorStatus)
    constructor(message: String?, code: Int?) : this(message, code, null)
    constructor(message: String?) : this(message, -1, null)

    /**
     * various error status to know what happened if something goes wrong with a repository call
     */
    enum class ErrorStatus {
        /**
         * error in connecting to repository (Server or Database)
         */
        NO_CONNECTION,
        /**
         * error in getting value (Json Error, Server Error, etc)
         */
        BAD_REQUEST,
        /**
         * Time out  error
         */
        TIMEOUT,
        /**
         * no data available in repository
         */
        EMPTY_RESPONSE,
        /**
         * an unexpected error
         */
        NOT_DEFINED,
        /**
         * bad credential
         */
        UNAUTHORIZED,
        /**
         * Database Errors
         */
        DATABASE,

        /**
         * Bazaar Or Charkhune error
         */
        USED_BY_OTHER_USER
    }
}