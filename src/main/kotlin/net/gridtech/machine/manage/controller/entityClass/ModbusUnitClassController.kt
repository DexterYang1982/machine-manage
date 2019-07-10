package net.gridtech.machine.manage.controller.entityClass

import net.gridtech.core.data.INodeClass
import net.gridtech.core.util.generateId
import net.gridtech.machine.model.entityClass.MachineClass
import net.gridtech.machine.model.entityClass.ModbusUnitClass
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(value = ["/api/modbusUnitClass"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ModbusUnitClassController : EntityClassController() {
    override fun addEntityClass(name: String, alias: String): INodeClass? =
            ModbusUnitClass(generateId()).let {
                it.initialize(null)
                it.addNew(name, alias)
            }
}