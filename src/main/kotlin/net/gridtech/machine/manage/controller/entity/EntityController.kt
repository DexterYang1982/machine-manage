package net.gridtech.machine.manage.controller.entity

import net.gridtech.core.data.INode
import net.gridtech.machine.manage.controller.result
import net.gridtech.machine.manage.domain.BootService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

abstract class EntityController {
    @Autowired
    lateinit var bootService: BootService

    abstract fun addEntity(entityClassId: String, parentId: String, name: String, alias: String): INode?

    @RequestMapping(value = ["/add"], method = [RequestMethod.POST])
    fun add(@RequestParam("entityClassId")
            entityClassId: String,
            @RequestParam("parentId")
            parentId: String,
            @RequestParam("name")
            name: String,
            @RequestParam("alias")
            alias: String): ResponseEntity<*> =
            result(addEntity(entityClassId, parentId, name, alias))

    @RequestMapping(value = ["/update"], method = [RequestMethod.PUT])
    fun update(
            @RequestParam("id")
            id: String,
            @RequestParam("name")
            name: String,
            @RequestParam("alias")
            alias: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.updateNameAndAlias(name, alias))

    @RequestMapping(value = ["/delete"], method = [RequestMethod.DELETE])
    fun delete(@RequestParam("id")
               id: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.tryToDelete())
}