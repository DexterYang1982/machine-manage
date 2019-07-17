package net.gridtech.machine.manage.controller.entity

import net.gridtech.core.data.INode
import net.gridtech.core.util.generateId
import net.gridtech.machine.model.entity.Device
import net.gridtech.machine.model.entityClass.DeviceClass
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/Device"], produces = [MediaType.APPLICATION_JSON_VALUE])
class DeviceController : EntityController() {
    override fun addEntity(entityClassId: String, parentId: String, name: String, alias: String): INode? =
            bootService.dataHolder.entityClassHolder[entityClassId]?.let { entityClass ->
                if (entityClass is DeviceClass)
                    Device(generateId(), entityClass).addNew(parentId, name, alias)
                else
                    null
            }
}