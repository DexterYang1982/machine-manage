package net.gridtech.machine.manage.controller.websocket

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import net.gridtech.core.util.stringfy
import net.gridtech.machine.manage.domain.BootService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.CopyOnWriteArraySet


@Service
class MachineRuntimeData : TextWebSocketHandler() {
    @Autowired
    lateinit var bootService: BootService
    private val webClientSet = CopyOnWriteArraySet<WebSocketSession>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        webClientSet.add(session)
        val machineNodeId = session.attributes["machineNodeId"] as String
        session.attributes["disposable"] =
                bootService.dataHolder
                        .getEntityByConditionObservable { entity ->
                            entity.source?.path?.contains(machineNodeId) == true || entity.source?.id == machineNodeId
                        }
                        .flatMap { entity ->
                            Observable.concat(
                                    Observable.fromIterable(entity.entityClass.embeddedFields),
                                    Observable.fromIterable(entity.entityClass.getCustomFields())
                            ).flatMap { entityField ->
                                val entityFieldValue = entityField.getFieldValue(entity)
                                entityFieldValue.observable
                                        .map { value ->
                                            RuntimeValue(
                                                    id = entityFieldValue.source?.id ?: "",
                                                    entityId = entity.id,
                                                    entityName = entity.name.value ?: "",
                                                    dataName = entity.dataName(),
                                                    fieldId = entityField.id,
                                                    fieldKey = entityField.getFieldKey(),
                                                    fieldName = entityField.name.value ?: "",
                                                    value = value?.let { stringfy(it) } ?: "",
                                                    session = entityFieldValue.session,
                                                    updateTime = entityFieldValue.updateTime
                                            )
                                        }
                            }
                        }
                        .subscribe { runtimeValue ->
                            session.sendMessage(TextMessage(stringfy(runtimeValue)))
                        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        (session.attributes["disposable"] as Disposable).dispose()
        webClientSet.remove(session)
    }

    data class RuntimeValue(
            val id: String,
            val entityId: String,
            val entityName: String,
            val dataName: String,
            val fieldId: String,
            val fieldKey: String,
            val fieldName: String,
            val value: String,
            val session: String,
            val updateTime: Long
    )
}
