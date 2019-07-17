package net.gridtech.machine.manage.controller.entityField

import net.gridtech.machine.manage.controller.result
import net.gridtech.machine.manage.domain.BootService
import net.gridtech.machine.model.entityField.CustomField
import net.gridtech.machine.model.property.field.ValueDescription
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(value = ["/api/CustomField"], produces = [MediaType.APPLICATION_JSON_VALUE])
class CustomFieldController {
    @Autowired
    lateinit var bootService: BootService

    @RequestMapping(value = ["/add"], method = [RequestMethod.POST])
    fun add(@RequestParam("nodeClassId")
            nodeClassId: String,
            @RequestParam("name")
            name: String,
            @RequestParam("alias")
            alias: String,
            @RequestParam("output")
            output: Boolean): ResponseEntity<*> =
            result(CustomField.addNew(nodeClassId, name, alias, output))

    @RequestMapping(value = ["/update"], method = [RequestMethod.PUT])
    fun update(@RequestParam("id")
               id: String,
               @RequestParam("name")
               name: String,
               @RequestParam("alias")
               alias: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityFieldHolder[id]?.updateNameAndAlias(name, alias))

    @RequestMapping(value = ["/delete"], method = [RequestMethod.DELETE])
    fun delete(@RequestParam("id")
               id: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityFieldHolder[id]?.tryToDelete())

    @RequestMapping(value = ["/addValueDescription"], method = [RequestMethod.POST])
    fun addValueDescription(@RequestParam("id")
                            id: String,
                            @RequestBody
                            valueDescription: ValueDescription): ResponseEntity<*> =
            result(bootService.dataHolder.entityFieldHolder[id]?.let { if (it is CustomField) it else null }?.apply {
                description.addValueDescription(valueDescription)
            })

    @RequestMapping(value = ["/updateValueDescription"], method = [RequestMethod.PUT])
    fun updateValueDescription(@RequestParam("id")
                               id: String,
                               @RequestBody
                               valueDescription: ValueDescription): ResponseEntity<*> =
            result(bootService.dataHolder.entityFieldHolder[id]?.let { if (it is CustomField) it else null }?.apply {
                description.updateValueDescription(valueDescription)
            })

    @RequestMapping(value = ["/deleteValueDescription"], method = [RequestMethod.DELETE])
    fun deleteValueDescription(@RequestParam("id")
                               id: String,
                               @RequestParam("valueDescriptionId")
                               valueDescriptionId: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityFieldHolder[id]?.let { if (it is CustomField) it else null }?.apply {
                description.deleteValueDescription(valueDescriptionId)
            })
}