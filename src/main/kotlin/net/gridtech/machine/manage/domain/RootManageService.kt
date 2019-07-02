package net.gridtech.machine.manage.domain

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.gridtech.core.data.FieldStub
import net.gridtech.core.data.FieldValueStub
import net.gridtech.core.data.NodeClassStub
import net.gridtech.core.data.NodeStub
import net.gridtech.core.util.parse
import net.gridtech.core.util.stringfy
import net.gridtech.exception.APIException
import net.gridtech.exception.APIExceptionEnum
import okhttp3.*
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Service
import java.net.URL

@Service
@ConfigurationProperties(prefix = "manage")
class RootManageService {
    lateinit var rootNodeId: String
    lateinit var rootNodeSecret: String
    lateinit var rootApiUrl: String


    fun nodeClassGetById(nodeClassId: String): NodeClassStub =
            parse(httpGet("nodeClassGetById", "id" to nodeClassId))

    fun fieldGetById(fieldId: String): FieldStub =
            parse(httpGet("fieldGetById", "id" to fieldId))

    fun nodeGetById(nodeId: String): NodeStub =
            parse(httpGet("nodeGetById", "id" to nodeId))

    fun nodeClassAdd(id: String,
                     name: String,
                     alias: String,
                     connectable: Boolean,
                     tags: List<String>,
                     description: Any? = null): NodeClassStub =
            parse(httpPost("nodeClassAdd", description,
                    "id" to id,
                    "name" to name,
                    "alias" to alias,
                    "connectable" to connectable,
                    "tags" to tags))

    fun fieldAdd(key: String,
                 name: String,
                 alias: String,
                 nodeClassId: String,
                 through: Boolean,
                 tags: List<String>,
                 description: Any? = null): FieldStub =
            parse(httpPost("fieldAdd", description,
                    "key" to key,
                    "name" to name,
                    "alias" to alias,
                    "nodeClassId" to nodeClassId,
                    "through" to through,
                    "tags" to tags))

    fun nodeAdd(id: String,
                name: String,
                alias: String,
                nodeClassId: String,
                parentId: String,
                externalScope: List<String>,
                tags: List<String>,
                description: Any? = null): NodeStub =
            parse(httpPost("nodeAdd", description,
                    "id" to id,
                    "name" to name,
                    "alias" to alias,
                    "nodeClassId" to nodeClassId,
                    "parentId" to parentId,
                    "externalScope" to externalScope,
                    "tags" to tags))

    fun nodeClassUpdate(id: String,
                        name: String,
                        alias: String,
                        description: Any? = null): NodeClassStub =
            parse(httpPut("nodeClassUpdate", description,
                    "id" to id,
                    "name" to name,
                    "alias" to alias))

    fun fieldUpdate(id: String,
                    name: String,
                    alias: String,
                    description: Any? = null): FieldStub =
            parse(httpPut("fieldUpdate", description,
                    "id" to id,
                    "name" to name,
                    "alias" to alias))

    fun nodeUpdate(id: String,
                   name: String,
                   alias: String,
                   description: Any? = null): NodeStub =
            parse(httpPut("nodeUpdate", description,
                    "id" to id,
                    "name" to name,
                    "alias" to alias))


    fun nodeClassDelete(id: String) =
            httpDelete("nodeClassDelete",
                    "id" to id)

    fun fieldDelete(id: String) =
            httpDelete("fieldDelete",
                    "id" to id)

    fun nodeDelete(id: String) =
            httpDelete("nodeDelete",
                    "id" to id)

    fun fieldValueGetByFieldKey(fieldKey: String, nodeId: String): FieldValueStub =
            parse(httpGet("fieldValueGetByFieldKey", "fieldKey" to fieldKey, "nodeId" to nodeId))

    fun fieldValueSetByFieldKey(fieldKey: String,
                                nodeId: String,
                                session: String,
                                value: String) =
            httpPost("fieldValueSetByFieldKey", value,
                    "nodeId" to nodeId,
                    "fieldKey" to fieldKey,
                    "session" to session)


    private fun httpPost(method: String, body: Any?, vararg queries: Pair<String, Any?>): String {
        val url = getHttpUrl(rootApiUrl, method, *queries)
        val request = Request.Builder().url(url).header("nodeId", rootNodeId).header("nodeSecret", rootNodeSecret).post(
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body?.let { if (it is String) it else stringfy(body) }
                        ?: "{}")
        ).build()
        return responseContent(request)
    }

    private fun httpPut(method: String, body: Any?, vararg queries: Pair<String, Any?>): String {
        val url = getHttpUrl(rootApiUrl, method, *queries)
        val request = Request.Builder().url(url).header("nodeId", rootNodeId).header("nodeSecret", rootNodeSecret).put(
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body?.let { stringfy(body) }
                        ?: "{}")
        ).build()
        return responseContent(request)
    }

    private fun httpGet(method: String, vararg queries: Pair<String, Any?>): String {
        val url = getHttpUrl(rootApiUrl, method, *queries)
        val request = Request.Builder().url(url).header("nodeId", rootNodeId).header("nodeSecret", rootNodeSecret).get().build()
        return responseContent(request)
    }

    private fun httpDelete(method: String, vararg queries: Pair<String, Any?>): String {
        val url = getHttpUrl(rootApiUrl, method, *queries)
        val request = Request.Builder().url(url).header("nodeId", rootNodeId).header("nodeSecret", rootNodeSecret).delete().build()
        return responseContent(request)
    }

    private fun getHttpUrl(rootApiUrl: String, method: String, vararg queries: Pair<String, Any?>): URL {
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
                } else
                    builder.addQueryParameter(key, values.toString())
            }
        }
        return builder.build().url()
    }

    private fun responseContent(request: Request): String {
        val responseCode: Int?
        val responseContent: String?
        try {
            val response = OkHttpClient().newCall(request).execute()
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
            System.err.println(message)
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