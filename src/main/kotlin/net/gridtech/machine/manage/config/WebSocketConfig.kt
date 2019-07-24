package net.gridtech.machine.manage.config

import net.gridtech.machine.manage.controller.websocket.MachineRuntimeData
import net.gridtech.machine.manage.controller.websocket.MachineStructureData
import net.gridtech.machine.manage.domain.DomainInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.socket.server.HandshakeInterceptor

@Configuration
@EnableWebSocket
class WebSocketConfig : WebSocketConfigurer {
    @Autowired
    lateinit var machineStructureData: MachineStructureData
    @Autowired
    lateinit var machineRuntimeData: MachineRuntimeData
    @Autowired
    lateinit var domainInfo: DomainInfoService


    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(machineRuntimeData, "/machineRuntimeData").setAllowedOrigins("*")
                .addInterceptors(object : HandshakeInterceptor {
                    override fun beforeHandshake(request: ServerHttpRequest, response: ServerHttpResponse, wsHandler: WebSocketHandler, attributes: MutableMap<String, Any>): Boolean {
                        val req = request as ServletServerHttpRequest
                        val resp = response as ServletServerHttpResponse
                        val nodeId = req.servletRequest.getParameter("nodeId")
                        val nodeSecret = req.servletRequest.getParameter("nodeSecret")
                        val machineNodeId = req.servletRequest.getParameter("machineNodeId")
                        attributes["machineNodeId"] = machineNodeId
                        return if (domainInfo.hostInfo?.nodeId == nodeId && domainInfo.hostInfo?.nodeSecret == nodeSecret) {
                            true
                        } else {
                            resp.setStatusCode(HttpStatus.FORBIDDEN)
                            false
                        }
                    }

                    override fun afterHandshake(request: ServerHttpRequest, response: ServerHttpResponse, wsHandler: WebSocketHandler, exception: Exception?) {
                    }
                })
        registry.addHandler(machineStructureData, "/machineStructureData").setAllowedOrigins("*")
                .addInterceptors(object : HandshakeInterceptor {
                    override fun beforeHandshake(request: ServerHttpRequest, response: ServerHttpResponse, wsHandler: WebSocketHandler, attributes: MutableMap<String, Any>): Boolean {
                        val req = request as ServletServerHttpRequest
                        val resp = response as ServletServerHttpResponse
                        val nodeId = req.servletRequest.getParameter("nodeId")
                        val nodeSecret = req.servletRequest.getParameter("nodeSecret")
                        return if (domainInfo.hostInfo?.nodeId == nodeId && domainInfo.hostInfo?.nodeSecret == nodeSecret) {
                            true
                        } else {
                            resp.setStatusCode(HttpStatus.FORBIDDEN)
                            false
                        }
                    }

                    override fun afterHandshake(request: ServerHttpRequest, response: ServerHttpResponse, wsHandler: WebSocketHandler, exception: Exception?) {
                    }
                })
    }
}