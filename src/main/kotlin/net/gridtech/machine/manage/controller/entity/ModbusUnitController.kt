package net.gridtech.machine.manage.controller.entity

import net.gridtech.core.data.INode
import net.gridtech.core.util.generateId
import net.gridtech.machine.model.entity.ModbusUnit
import net.gridtech.machine.model.entityClass.ModbusUnitClass
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/ModbusUnit"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ModbusUnitController : EntityController() {
    override fun addEntity(entityClassId: String, parentId: String, name: String, alias: String): INode? =
            bootService.dataHolder.entityClassHolder[entityClassId]?.let { entityClass ->
                if (entityClass is ModbusUnitClass)
                    ModbusUnit(generateId(), entityClass).addNew(parentId, name, alias)
                else
                    null
            }
}