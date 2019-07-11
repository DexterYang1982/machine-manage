package net.gridtech.machine.manage.controller.entity

import net.gridtech.core.data.INode
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

    @RequestMapping(value = ["/add"], method = [RequestMethod.GET])
    fun add(@RequestParam("entityClassId")
            entityClassId: String,
            @RequestParam("parentId")
            parentId: String,
            @RequestParam("name")
            name: String,
            @RequestParam("alias")
            alias: String): ResponseEntity<*> =
            ResponseEntity.ok(addEntity(entityClassId, parentId, name, alias) ?: "error")

    @RequestMapping(value = ["/updateNameAndAlias"], method = [RequestMethod.PUT])
    fun updateNameAndAlias(
            @RequestParam("id")
            id: String,
            @RequestParam("name")
            name: String,
            @RequestParam("alias")
            alias: String): ResponseEntity<*> {
        bootService.dataHolder.entityHolder[id]?.updateNameAndAlias(name, alias)
        return ResponseEntity.ok("ok")
    }

    @RequestMapping(value = ["/delete"], method = [RequestMethod.DELETE])
    fun delete(@RequestParam("id")
               id: String): ResponseEntity<*> {
        bootService.dataHolder.entityHolder[id]?.tryToDelete()
        return ResponseEntity.ok("ok")
    }
}