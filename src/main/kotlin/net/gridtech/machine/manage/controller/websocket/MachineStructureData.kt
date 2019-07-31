package net.gridtech.machine.manage.controller.websocket

import io.reactivex.Observable
import net.gridtech.core.util.stringfy
import net.gridtech.machine.manage.domain.BootService
import net.gridtech.machine.model.StructureDataChangedType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.CopyOnWriteArraySet
import javax.annotation.PostConstruct


@Service
class MachineStructureData : TextWebSocketHandler() {
    @Autowired
    lateinit var bootService: BootService
    private val webClientSet = CopyOnWriteArraySet<WebSocketSession>()

    @PostConstruct
    fun listenToUpdate() {
        bootService.dataHolder.structureDataChangedPublisher.subscribe { (type, structure) ->
            webClientSet.filter { it.isOpen }.forEach { session ->
                session.sendMessage(TextMessage(stringfy(structure.capsule().apply {
                    if (type == StructureDataChangedType.DELETE)
                        updateType = "delete"
                })))
            }
        }
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        webClientSet.add(session)
        val existed = Observable.concat(
                Observable.fromIterable(bootService.dataHolder.entityClassHolder.values),
                Observable.fromIterable(bootService.dataHolder.entityFieldHolder.values),
                Observable.fromIterable(bootService.dataHolder.entityHolder.values)
        )

        existed.subscribe({
            session.sendMessage(TextMessage(stringfy(it.capsule())))
        }, {}, {
            session.sendMessage(TextMessage("{}"))
        })
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        webClientSet.remove(session)
    }
}