package net.gridtech.machine.manage.domain

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.gridtech.core.util.okHttpClient
import net.gridtech.exception.APIException
import net.gridtech.exception.APIExceptionEnum
import okhttp3.HttpUrl
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Service
import java.net.URL

@Service
@ConfigurationProperties(prefix = "manage")
class RootManageService {
    var rootNodeId: String? = null
    var rootNodeSecret: String? = null
    var rootApiUrl: String? = null


    private fun httpPost(method: String, body: String, vararg queries: Pair<String, Any?>): String {
        val url = getHttpUrl(rootApiUrl!!, method, *queries)
        val request = Request.Builder().url(url).header("nodeId", rootNodeId!!).header("nodeSecret", rootNodeSecret!!).post(
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body)
        ).build()
        return responseContent(request)
    }

    private fun httpPut(method: String, body: String, vararg queries: Pair<String, Any?>): String {
        val url = getHttpUrl(rootApiUrl!!, method, *queries)
        val request = Request.Builder().url(url).header("nodeId", rootNodeId!!).header("nodeSecret", rootNodeSecret!!).put(
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body)
        ).build()
        return responseContent(request)
    }

    private fun httpGet(method: String, vararg queries: Pair<String, Any?>): String {
        val url = getHttpUrl(rootApiUrl!!, method, *queries)
        val request = Request.Builder().url(url).header("nodeId", rootNodeId!!).header("nodeSecret", rootNodeSecret!!).get().build()
        return responseContent(request)
    }

    private fun httpDelete(method: String, vararg queries: Pair<String, Any?>): String {
        val url = getHttpUrl(rootApiUrl!!, method, *queries)
        val request = Request.Builder().url(url).header("nodeId", rootNodeId!!).header("nodeSecret", rootNodeSecret!!).delete().build()
        return responseContent(request)
    }

    fun getHttpUrl(rootApiUrl: String, method: String, vararg queries: Pair<String, Any?>): URL {
        val builder = HttpUrl.parse(rootApiUrl)!!.newBuilder()
        builder.addPathSegment(method)
        queries.forEach { (key, values) ->
            values?.apply {
                if (values is String)
                    builder.addQueryParameter(key, values)
                else if (values is List<*>) {
                    values.filterNotNull().forEach {
                        builder.addQueryParameter(key, it.toString())
                    }
                }
            }
        }
        return builder.build().url()
    }

    fun responseContent(request: Request): String {
        val responseCode: Int?
        val responseContent: String?
        try {
            val response = okHttpClient.newCall(request).execute()
            responseContent = response.body()?.string()
            responseCode = response.code()

        } catch (e: Throwable) {
            System.err.println(e.message)
            throw APIExceptionEnum.ERR50_EXCHANGE_ERROR.toException(e.message)
        }
        if (responseCode == 200 && responseContent != null) {
            return responseContent
        }
        if (responseCode == 406 && responseContent != null) {
            val message = jacksonObjectMapper().readValue<Map<String, String>>(responseContent)
            throw APIException(
                    message["code"] ?: "unknown",
                    message["info"] ?: "",
                    message["ext"] ?: "",
                    message["entity"] ?: ""
            )
        } else {
            throw APIExceptionEnum.ERR50_EXCHANGE_ERROR.toException("$responseCode $responseContent")
        }

    }
}