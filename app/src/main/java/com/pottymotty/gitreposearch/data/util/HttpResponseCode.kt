package com.pottymotty.gitreposearch.data.util

//Generated by CHAT-GPT
enum class HttpResponseCode(val code: Int, val description: String) {
    // Informational 1xx
    CONTINUE(100, "Continue"),
    SWITCHING_PROTOCOLS(101, "Switching Protocols"),

    // Success 2xx
    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    // Add more success codes as needed

    // Redirection 3xx
    MOVED_PERMANENTLY(301, "Moved Permanently"),
    FOUND(302, "Found"),
    // Add more redirection codes as needed

    // Client Error 4xx
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    // Add more client error codes as needed

    // Server Error 5xx
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    BAD_GATEWAY(502, "Bad Gateway"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    // Add more server error codes as needed

    // Custom error codes
    CUSTOM_ERROR_1(600, "Custom Error 1"),
    CUSTOM_ERROR_2(601, "Custom Error 2"),
    // Add more custom error codes as needed

    UNKNOWN_ERROR(-1, "Unknown Error");

    companion object {
        fun fromCode(code: Int): HttpResponseCode {
            return entries.find { it.code == code } ?: UNKNOWN_ERROR
        }
    }
}