package net.gridtech.machine.manage.controller

import net.gridtech.core.util.cast
import net.gridtech.machine.manage.domain.BootService
import net.gridtech.machine.model.entity.Device
import net.gridtech.machine.model.entity.Tunnel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/Runtime"], produces = [MediaType.APPLICATION_JSON_VALUE])
class RuntimeController {
    @Autowired
    lateinit var bootService: BootService

    @RequestMapping(value = ["/updateEntityInput"], method = [RequestMethod.POST])
    fun updateEntityInput(@RequestParam("entityId")
                          entityId: String,
                          @RequestParam("inputFieldId")
                          inputFieldId: String,
                          @RequestParam("session")
                          session: String,
                          @RequestBody
                          value: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[entityId]?.getCustomFieldValue(inputFieldId)?.update(value, session))

    @RequestMapping(value = ["/executeDeviceCommand"], method = [RequestMethod.POST])
    fun executeDeviceCommand(@RequestParam("deviceId")
                             deviceId: String,
                             @RequestParam("commandId")
                             commandId: String,
                             @RequestParam("valueDescriptionId")
                             valueDescriptionId: String,
                             @RequestParam("session")
                             session: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[deviceId]?.let { cast<Device>(it) }?.executeCommand(commandId, valueDescriptionId, session))


    @RequestMapping(value = ["/executeDeviceProcess"], method = [RequestMethod.POST])
    fun executeDeviceProcess(@RequestParam("deviceId")
                             deviceId: String,
                             @RequestParam("processId")
                             processId: String,
                             @RequestParam("session")
                             session: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[deviceId]?.let { cast<Device>(it) }?.executeProcess(processId, session))


    @RequestMapping(value = ["/executeTunnelTransaction"], method = [RequestMethod.POST])
    fun executeTunnelTransaction(@RequestParam("tunnelId")
                                 tunnelId: String,
                                 @RequestParam("transactionId")
                                 transactionId: String,
                                 @RequestParam("session")
                                 session: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[tunnelId]?.let { cast<Tunnel>(it) }?.executeTransaction(transactionId, session))
}