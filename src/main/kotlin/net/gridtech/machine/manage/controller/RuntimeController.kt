package net.gridtech.machine.manage.controller

import net.gridtech.core.util.cast
import net.gridtech.machine.manage.domain.BootService
import net.gridtech.machine.model.entity.Device
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/Runtime"], produces = [MediaType.APPLICATION_JSON_VALUE])
class RuntimeController {
    @Autowired
    lateinit var bootService: BootService

    @RequestMapping(value = ["/ExecuteDeviceCommand"], method = [RequestMethod.POST])
    fun addStatus(@RequestParam("deviceId")
                  deviceId: String,
                  @RequestParam("commandId")
                  commandId: String,
                  @RequestParam("valueDescriptionId")
                  valueDescriptionId: String,
                  @RequestParam("session")
                  session: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[deviceId]?.let { cast<Device>(it) }?.executeCommand(commandId, valueDescriptionId, session))
}