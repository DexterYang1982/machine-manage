package net.gridtech.machine.manage.controller.entityClass

import net.gridtech.core.data.INodeClass
import net.gridtech.core.util.cast
import net.gridtech.core.util.generateId
import net.gridtech.machine.manage.controller.result
import net.gridtech.machine.model.entityClass.DisplayClass
import net.gridtech.machine.model.property.entityClass.DisplayClientVersion
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(value = ["/api/DisplayClass"], produces = [MediaType.APPLICATION_JSON_VALUE])
class DisplayClassController : EntityClassController() {
    override fun addEntityClass(name: String, alias: String): INodeClass? =
            DisplayClass(generateId()).let {
                it.initialize(null)
                it.addNew(name, alias)
            }

    @RequestMapping(value = ["/updateDisplayClientVersion"], method = [RequestMethod.PUT])
    fun updateDisplayClientVersion(@RequestParam("id")
                                   id: String,
                                   @RequestBody
                                   displayClientVersion: DisplayClientVersion): ResponseEntity<*> =
            result(bootService.dataHolder.entityClassHolder[id]?.let { cast<DisplayClass>(it) }?.description?.updateDisplayClientVersion(displayClientVersion))
}