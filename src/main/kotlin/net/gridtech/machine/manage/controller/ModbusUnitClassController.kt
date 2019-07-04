package net.gridtech.machine.manage.controller

import net.gridtech.machine.manage.domain.BootService
import net.gridtech.machine.model.ModbusUnitClass
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(value = ["/api/modbusUnitClass"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ModbusUnitClassController {
    @Autowired
    lateinit var bootService: BootService

    @RequestMapping(value = ["/add"], method = [RequestMethod.GET])
    fun add(@RequestParam("name")
            name: String,
            @RequestParam("alias")
            alias: String): ResponseEntity<*> =
            ResponseEntity.ok(ModbusUnitClass.create(name, alias))


    @RequestMapping(value = ["/delete"], method = [RequestMethod.DELETE])
    fun delete(@RequestParam("id")
               id: String): ResponseEntity<*> {
        bootService.dataHolder.entityClassHolder[id]?.delete()
        return ResponseEntity.ok("ok")
    }
}