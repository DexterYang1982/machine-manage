package net.gridtech.machine.manage.controller.entity

import net.gridtech.core.data.INode
import net.gridtech.core.util.cast
import net.gridtech.core.util.generateId
import net.gridtech.machine.manage.controller.result
import net.gridtech.machine.model.Trigger
import net.gridtech.machine.model.entity.Group
import net.gridtech.machine.model.entityClass.GroupClass
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/Group"], produces = [MediaType.APPLICATION_JSON_VALUE])
class GroupController : EntityController() {
    override fun addEntity(entityClassId: String, parentId: String, name: String, alias: String): INode? =
            bootService.dataHolder.entityClassHolder[entityClassId]?.let { entityClass ->
                if (entityClass is GroupClass)
                    Group(generateId(), entityClass).addNew(parentId, name, alias)
                else
                    null
            }

    @RequestMapping(value = ["/addTrigger"], method = [RequestMethod.POST])
    fun addTrigger(@RequestParam("id")
                   id: String,
                   @RequestBody
                   trigger: Trigger): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Group>(it) }?.description?.addTrigger(trigger))


    @RequestMapping(value = ["/updateTrigger"], method = [RequestMethod.PUT])
    fun updateTrigger(@RequestParam("id")
                      id: String,
                      @RequestBody
                      trigger: Trigger): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Group>(it) }?.description?.updateTrigger(trigger))


    @RequestMapping(value = ["/deleteTrigger"], method = [RequestMethod.DELETE])
    fun deleteTrigger(@RequestParam("id")
                      id: String,
                      @RequestParam("triggerId")
                      triggerId: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Group>(it) }?.description?.deleteTrigger(triggerId))
}