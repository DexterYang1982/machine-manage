package net.gridtech.machine.manage.controller.entity

import net.gridtech.core.data.INode
import net.gridtech.core.util.generateId
import net.gridtech.machine.model.entity.Cabin
import net.gridtech.machine.model.entityClass.CabinClass
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/Cabin"], produces = [MediaType.APPLICATION_JSON_VALUE])
class CabinController : EntityController() {
    override fun addEntity(entityClassId: String, parentId: String, name: String, alias: String): INode? =
            bootService.dataHolder.entityClassHolder[entityClassId]?.let { entityClass ->
                if (entityClass is CabinClass)
                    Cabin(generateId(), entityClass).addNew(parentId, name, alias)
                else
                    null
            }
}