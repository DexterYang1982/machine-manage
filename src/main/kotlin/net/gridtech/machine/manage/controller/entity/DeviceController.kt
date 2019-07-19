package net.gridtech.machine.manage.controller.entity

import net.gridtech.core.data.INode
import net.gridtech.core.util.cast
import net.gridtech.core.util.generateId
import net.gridtech.machine.manage.controller.result
import net.gridtech.machine.model.ReadCondition
import net.gridtech.machine.model.entity.Device
import net.gridtech.machine.model.entityClass.DeviceClass
import net.gridtech.machine.model.property.entity.DeviceProcess
import net.gridtech.machine.model.property.entity.ModbusRead
import net.gridtech.machine.model.property.entity.ModbusWrite
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/Device"], produces = [MediaType.APPLICATION_JSON_VALUE])
class DeviceController : EntityController() {
    override fun addEntity(entityClassId: String, parentId: String, name: String, alias: String): INode? =
            bootService.dataHolder.entityClassHolder[entityClassId]?.let { entityClass ->
                if (entityClass is DeviceClass)
                    Device(generateId(), entityClass).addNew(parentId, name, alias)
                else
                    null
            }

    @RequestMapping(value = ["/addStatus"], method = [RequestMethod.POST])
    fun addStatus(@RequestParam("id")
                  id: String,
                  @RequestBody
                  modbusRead: ModbusRead): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Device>(it) }?.description?.addStatus(modbusRead))


    @RequestMapping(value = ["/updateStatus"], method = [RequestMethod.PUT])
    fun updateStatus(@RequestParam("id")
                     id: String,
                     @RequestBody
                     modbusRead: ModbusRead): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Device>(it) }?.description?.updateStatus(modbusRead))


    @RequestMapping(value = ["/deleteStatus"], method = [RequestMethod.DELETE])
    fun deleteStatus(@RequestParam("id")
                     id: String,
                     @RequestParam("statusId")
                     statusId: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Device>(it) }?.description?.deleteStatus(statusId))


    @RequestMapping(value = ["/addCommand"], method = [RequestMethod.POST])
    fun addCommand(@RequestParam("id")
                   id: String,
                   @RequestBody
                   modbusWrite: ModbusWrite): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Device>(it) }?.description?.addCommand(modbusWrite))


    @RequestMapping(value = ["/updateCommand"], method = [RequestMethod.PUT])
    fun updateCommand(@RequestParam("id")
                      id: String,
                      @RequestBody
                      modbusWrite: ModbusWrite): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Device>(it) }?.description?.updateCommand(modbusWrite))


    @RequestMapping(value = ["/deleteCommand"], method = [RequestMethod.DELETE])
    fun deleteCommand(@RequestParam("id")
                      id: String,
                      @RequestParam("commandId")
                      commandId: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Device>(it) }?.description?.deleteCommand(commandId))

    @RequestMapping(value = ["/addProcess"], method = [RequestMethod.POST])
    fun addProcess(@RequestParam("id")
                   id: String,
                   @RequestBody
                   deviceProcess: DeviceProcess): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Device>(it) }?.description?.addProcess(deviceProcess))


    @RequestMapping(value = ["/updateProcess"], method = [RequestMethod.PUT])
    fun updateProcess(@RequestParam("id")
                      id: String,
                      @RequestBody
                      deviceProcess: DeviceProcess): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Device>(it) }?.description?.updateProcess(deviceProcess))


    @RequestMapping(value = ["/deleteProcess"], method = [RequestMethod.DELETE])
    fun deleteProcess(@RequestParam("id")
                      id: String,
                      @RequestParam("processId")
                      processId: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Device>(it) }?.description?.deleteProcess(processId))

    @RequestMapping(value = ["/updateErrorCondition"], method = [RequestMethod.PUT])
    fun updateErrorCondition(@RequestParam("id")
                             id: String,
                             @RequestBody
                             errorCondition: ReadCondition): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Device>(it) }?.description?.updateErrorCondition(errorCondition))

}