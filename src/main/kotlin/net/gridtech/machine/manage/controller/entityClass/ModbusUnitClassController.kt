package net.gridtech.machine.manage.controller.entityClass

import net.gridtech.core.data.INodeClass
import net.gridtech.core.util.cast
import net.gridtech.core.util.generateId
import net.gridtech.machine.manage.controller.result
import net.gridtech.machine.model.entityClass.ModbusUnitClass
import net.gridtech.machine.model.property.entityClass.ReadPoint
import net.gridtech.machine.model.property.entityClass.WritePoint
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(value = ["/api/ModbusUnitClass"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ModbusUnitClassController : EntityClassController() {
    override fun addEntityClass(name: String, alias: String): INodeClass? =
            ModbusUnitClass(generateId()).let {
                it.initialize(null)
                it.addNew(name, alias)
            }

    @RequestMapping(value = ["/addReadPoint"], method = [RequestMethod.POST])
    fun addReadPoint(@RequestParam("id")
                     id: String,
                     @RequestBody
                     readPoint: ReadPoint): ResponseEntity<*> =
            result(bootService.dataHolder.entityClassHolder[id]?.let { cast<ModbusUnitClass>(it) }?.description?.addReadPoint(readPoint))

    @RequestMapping(value = ["/addWritePoint"], method = [RequestMethod.POST])
    fun addWritePoint(@RequestParam("id")
                      id: String,
                      @RequestBody
                      writePoint: WritePoint): ResponseEntity<*> =
            result(bootService.dataHolder.entityClassHolder[id]?.let { cast<ModbusUnitClass>(it) }?.description?.addWritePoint(writePoint))


    @RequestMapping(value = ["/updateReadPoint"], method = [RequestMethod.PUT])
    fun updateReadPoint(@RequestParam("id")
                        id: String,
                        @RequestBody
                        readPoint: ReadPoint): ResponseEntity<*> =
            result(bootService.dataHolder.entityClassHolder[id]?.let { cast<ModbusUnitClass>(it) }?.description?.updateReadPoint(readPoint))

    @RequestMapping(value = ["/updateWritePoint"], method = [RequestMethod.PUT])
    fun updateWritePoint(@RequestParam("id")
                         id: String,
                         @RequestBody
                         writePoint: WritePoint): ResponseEntity<*> =
            result(bootService.dataHolder.entityClassHolder[id]?.let { cast<ModbusUnitClass>(it) }?.description?.updateWritePoint(writePoint))


    @RequestMapping(value = ["/deleteReadPoint"], method = [RequestMethod.DELETE])
    fun deleteReadPoint(@RequestParam("id")
                        id: String,
                        @RequestParam("readPointId")
                        readPointId: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityClassHolder[id]?.let { cast<ModbusUnitClass>(it) }?.description?.deleteReadPoint(readPointId))

    @RequestMapping(value = ["/deleteWritePoint"], method = [RequestMethod.DELETE])
    fun deleteWritePoint(@RequestParam("id")
                         id: String,
                         @RequestParam("writePointId")
                         writePointId: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityClassHolder[id]?.let { cast<ModbusUnitClass>(it) }?.description?.deleteWritePoint(writePointId))

}