package net.gridtech.machine.manage.controller.entityField

import net.gridtech.machine.manage.domain.BootService
import net.gridtech.machine.model.entityField.CustomField
import net.gridtech.machine.model.property.field.ValueDescription
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(value = ["/api/customField"], produces = [MediaType.APPLICATION_JSON_VALUE])
class CustomFieldController {
    @Autowired
    lateinit var bootService: BootService

    @RequestMapping(value = ["/add"], method = [RequestMethod.GET])
    fun add(@RequestParam("nodeClassId")
            nodeClassId: String,
            @RequestParam("name")
            name: String,
            @RequestParam("alias")
            alias: String): ResponseEntity<*> =
            ResponseEntity.ok(CustomField.addNew(nodeClassId, name, alias) ?: "failed")


    @RequestMapping(value = ["/delete"], method = [RequestMethod.DELETE])
    fun delete(@RequestParam("id")
               id: String): ResponseEntity<*> {
        bootService.dataHolder.entityFieldHolder[id]?.tryToDelete()
        return ResponseEntity.ok("ok")
    }

    @RequestMapping(value = ["/addValueDescription"], method = [RequestMethod.POST])
    fun addValueDescription(@RequestParam("id")
                            id: String,
                            @RequestBody
                            valueDescription: ValueDescription): ResponseEntity<*> {
        bootService.dataHolder.entityFieldHolder[id]?.let { if (it is CustomField) it else null }?.apply {
            description.addValueDescription(valueDescription)
        }
        return ResponseEntity.ok("ok")
    }

    @RequestMapping(value = ["/updateValueDescription"], method = [RequestMethod.PUT])
    fun updateValueDescription(@RequestParam("id")
                               id: String,
                               @RequestBody
                               valueDescription: ValueDescription): ResponseEntity<*> {
        bootService.dataHolder.entityFieldHolder[id]?.let { if (it is CustomField) it else null }?.apply {
            description.updateValueDescription(valueDescription)
        }
        return ResponseEntity.ok("ok")
    }

    @RequestMapping(value = ["/deleteValueDescription"], method = [RequestMethod.DELETE])
    fun deleteValueDescription(@RequestParam("id")
                               id: String,
                               @RequestParam("valueDescriptionId")
                               valueDescriptionId: String): ResponseEntity<*> {
        bootService.dataHolder.entityFieldHolder[id]?.let { if (it is CustomField) it else null }?.apply {
            description.deleteValueDescription(valueDescriptionId)
        }
        return ResponseEntity.ok("ok")
    }
}