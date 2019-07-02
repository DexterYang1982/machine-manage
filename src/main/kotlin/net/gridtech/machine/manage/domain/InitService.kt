package net.gridtech.machine.manage.domain

import net.gridtech.core.util.ID_NODE_ROOT
import net.gridtech.core.util.KEY_FIELD_SECRET
import net.gridtech.exception.APIException
import net.gridtech.exception.APIExceptionEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InitService {
    @Autowired
    lateinit var domainInfoService: DomainInfoService
    @Autowired
    lateinit var rootManageService: RootManageService

    fun initialize(): Boolean {
        val domainNodeClass = try {
            rootManageService.nodeClassGetById(domainInfoService.domainNodeClassId)
        } catch (e: APIException) {
            if (e.code == APIExceptionEnum.ERR01_ID_NOT_EXIST.name) {
                try {
                    rootManageService.nodeClassAdd(
                            domainInfoService.domainNodeClassId,
                            "${domainInfoService.domainName} node class",
                            "",
                            true,
                            emptyList())
                } catch (e: Throwable) {
                    e.printStackTrace()
                    null
                }
            } else null
        } catch (e: Throwable) {
            e.printStackTrace()
            null
        }
        val domainNode = domainNodeClass?.let {
            try {
                rootManageService.nodeGetById(domainInfoService.domainNodeId)
            } catch (e: APIException) {
                if (e.code == APIExceptionEnum.ERR01_ID_NOT_EXIST.name) {
                    try {
                        rootManageService.nodeAdd(
                                domainInfoService.domainNodeId,
                                domainInfoService.domainName,
                                "",
                                it.id,
                                ID_NODE_ROOT,
                                emptyList(),
                                emptyList())
                    } catch (e: Throwable) {
                        e.printStackTrace()
                        null
                    }
                } else null
            } catch (e: Throwable) {
                e.printStackTrace()
                null
            }
        }
        val secret = domainNode?.let {
            try {
                rootManageService.fieldValueGetByFieldKey(KEY_FIELD_SECRET, it.id)
            } catch (e: Throwable) {
                e.printStackTrace()
                null
            }
        }
        secret?.takeIf { it.value != domainInfoService.domainNodeSecret }?.apply {
            try {
                rootManageService.fieldValueSetByFieldKey(KEY_FIELD_SECRET, domainNode.id, "", domainInfoService.domainNodeSecret)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        return secret != null
    }
}