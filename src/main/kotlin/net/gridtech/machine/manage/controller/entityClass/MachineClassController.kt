package net.gridtech.machine.manage.controller.entityClass

import net.gridtech.core.data.INodeClass
import net.gridtech.machine.model.entityClass.MachineClass
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(value = ["/api/machineClass"], produces = [MediaType.APPLICATION_JSON_VALUE])
class MachineClassController : EntityClassController() {
    override fun addEntityClass(name: String, alias: String): INodeClass? =
            MachineClass.add(name, alias)
}