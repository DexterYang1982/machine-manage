package net.gridtech.machine.manage.controller.entityClass

import net.gridtech.core.data.INodeClass
import net.gridtech.machine.manage.controller.result
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

    @RequestMapping(value = ["/add"], method = [RequestMethod.POST])
    fun add(@RequestParam("name")
            name: String,
            @RequestParam("alias")
            alias: String): ResponseEntity<*> =
            result(addEntityClass(name, alias))

    @RequestMapping(value = ["/update"], method = [RequestMethod.PUT])
    fun update(
            @RequestParam("id")
            id: String,
            @RequestParam("name")
            name: String,
            @RequestParam("alias")
            alias: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityClassHolder[id]?.updateNameAndAlias(name, alias))

    @RequestMapping(value = ["/delete"], method = [RequestMethod.DELETE])
    fun delete(@RequestParam("id")
               id: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityClassHolder[id]?.tryToDelete())
}