package net.gridtech.machine.manage.controller.entity

import net.gridtech.core.data.INode
import net.gridtech.core.util.generateId
import net.gridtech.machine.model.entity.ModbusSlave
import net.gridtech.machine.model.entityClass.ModbusSlaveClass
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/ModbusSlave"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ModbusSlaveController : EntityController() {
    override fun addEntity(entityClassId: String, parentId: String, name: String, alias: String): INode? =
            bootService.dataHolder.entityClassHolder[entityClassId]?.let { entityClass ->
                if (entityClass is ModbusSlaveClass)
                    ModbusSlave(generateId(), entityClass).addNew(parentId, name, alias)
                else
                    null
            }
}