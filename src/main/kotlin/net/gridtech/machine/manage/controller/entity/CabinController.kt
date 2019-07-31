package net.gridtech.machine.manage.controller.entity

import net.gridtech.core.data.INode
import net.gridtech.core.util.cast
import net.gridtech.core.util.generateId
import net.gridtech.machine.manage.controller.result
import net.gridtech.machine.model.ReadCondition
import net.gridtech.machine.model.entity.Cabin
import net.gridtech.machine.model.entityClass.CabinClass
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/Cabin"], produces = [MediaType.APPLICATION_JSON_VALUE])
class CabinController : EntityController() {
    override fun addEntity(entityClassId: String, parentId: String, name: String, alias: String): INode? =
            bootService.dataHolder.entityClassHolder[entityClassId]?.let { entityClass ->
                if (entityClass is CabinClass)
                    Cabin(generateId(), entityClass).addNew(parentId, name, alias)
                else
                    null
            }

    @RequestMapping(value = ["/updateEmptyCondition"], method = [RequestMethod.PUT])
    fun updateEmptyCondition(
            @RequestParam("id")
            id: String,
            @RequestBody
            readCondition: ReadCondition): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Cabin>(it) }?.description?.updateEmptyCondition(readCondition))


    @RequestMapping(value = ["/updateExportSingle"], method = [RequestMethod.PUT])
    fun updateExportSingle(
            @RequestParam("id")
            id: String,
            @RequestParam("exportSingle")
            exportSingle: Boolean): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Cabin>(it) }?.description?.updateExportSingle(exportSingle))
}