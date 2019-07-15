package net.gridtech.machine.manage.controller.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.CopyOnWriteArraySet


@Service
class MachineRuntime : TextWebSocketHandler() {
    @Autowired
    lateinit var objectMapper: ObjectMapper
    private val webClientSet = CopyOnWriteArraySet<WebSocketSession>()

}