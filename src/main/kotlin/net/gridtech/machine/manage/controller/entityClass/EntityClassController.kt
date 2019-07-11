package net.gridtech.machine.manage.controller.entityClass

import net.gridtech.core.data.INodeClass
import net.gridtech.machine.manage.domain.BootService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

abstract class EntityClassController {
    @Autowired
    lateinit var bootService: BootService

    abstract fun addEntityClass(name: String, alias: String): INodeClass?

    @RequestMapping(value = ["/add"], method = [RequestMethod.GET])
    fun add(@RequestParam("name")
            name: String,
            @RequestParam("alias")
            alias: String): ResponseEntity<*> =
            ResponseEntity.ok(addEntityClass(name, alias) ?: "error")

    @RequestMapping(value = ["/updateNameAndAlias"], method = [RequestMethod.PUT])
    fun updateNameAndAlias(
            @RequestParam("id")
            id: String,
            @RequestParam("name")
            name: String,
            @RequestParam("alias")
            alias: String): ResponseEntity<*> {
        bootService.dataHolder.entityClassHolder[id]?.updateNameAndAlias(name, alias)
        return ResponseEntity.ok("ok")
    }

    @RequestMapping(value = ["/delete"], method = [RequestMethod.DELETE])
    fun delete(@RequestParam("id")
               id: String): ResponseEntity<*> {
        bootService.dataHolder.entityClassHolder[id]?.tryToDelete()
        return ResponseEntity.ok("ok")
    }
}