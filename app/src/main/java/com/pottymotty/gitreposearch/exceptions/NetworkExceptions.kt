package com.pottymotty.gitreposearch.exceptions

import com.pottymotty.gitreposearch.data.util.HttpResponseCode


class ApiCallFailedException(body: String, httpResponseCode: HttpResponseCode) : Exception(
    "API call failed. CODE: $httpResponseCode.\n Error body: $body", null)