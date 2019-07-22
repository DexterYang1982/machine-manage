package net.gridtech.machine.manage.controller.entity

import net.gridtech.core.data.INode
import net.gridtech.core.util.cast
import net.gridtech.core.util.generateId
import net.gridtech.machine.manage.controller.result
import net.gridtech.machine.model.entity.Tunnel
import net.gridtech.machine.model.entityClass.TunnelClass
import net.gridtech.machine.model.property.entity.TunnelTransaction
import net.gridtech.machine.model.property.entity.TunnelTransactionPhase
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/Tunnel"], produces = [MediaType.APPLICATION_JSON_VALUE])
class TunnelController : EntityController() {
    override fun addEntity(entityClassId: String, parentId: String, name: String, alias: String): INode? =
            bootService.dataHolder.entityClassHolder[entityClassId]?.let { entityClass ->
                if (entityClass is TunnelClass)
                    Tunnel(generateId(), entityClass).addNew(parentId, name, alias)
                else
                    null
            }

    @RequestMapping(value = ["/updateMainCabin"], method = [RequestMethod.PUT])
    fun updateMainCabin(@RequestParam("id")
                        id: String,
                        @RequestParam("mainCabinId")
                        mainCabinId: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Tunnel>(it) }?.description?.updateMainCabin(mainCabinId))

    @RequestMapping(value = ["/addTransaction"], method = [RequestMethod.POST])
    fun addTransaction(@RequestParam("id")
                       id: String,
                       @RequestBody
                       transaction: TunnelTransaction): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Tunnel>(it) }?.description?.addTransaction(transaction))


    @RequestMapping(value = ["/updateTransaction"], method = [RequestMethod.PUT])
    fun updateTransaction(@RequestParam("id")
                          id: String,
                          @RequestBody
                          transaction: TunnelTransaction): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Tunnel>(it) }?.description?.updateTransaction(transaction))


    @RequestMapping(value = ["/deleteTransaction"], method = [RequestMethod.DELETE])
    fun deleteTransaction(@RequestParam("id")
                          id: String,
                          @RequestParam("transactionId")
                          transactionId: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Tunnel>(it) }?.description?.deleteTransaction(transactionId))


    @RequestMapping(value = ["/addTransactionPhase"], method = [RequestMethod.POST])
    fun addTransactionPhase(@RequestParam("id")
                            id: String,
                            @RequestParam("transactionId")
                            transactionId: String,
                            @RequestBody
                            phase: TunnelTransactionPhase): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Tunnel>(it) }?.description?.addTransactionPhase(transactionId, phase))


    @RequestMapping(value = ["/updateTransactionPhase"], method = [RequestMethod.PUT])
    fun updateTransactionPhase(@RequestParam("id")
                               id: String,
                               @RequestParam("transactionId")
                               transactionId: String,
                               @RequestBody
                               phase: TunnelTransactionPhase): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Tunnel>(it) }?.description?.updateTransactionPhase(transactionId, phase))


    @RequestMapping(value = ["/deleteTransactionPhase"], method = [RequestMethod.DELETE])
    fun deleteTransactionPhase(@RequestParam("id")
                               id: String,
                               @RequestParam("transactionId")
                               transactionId: String,
                               @RequestParam("phaseId")
                               phaseId: String): ResponseEntity<*> =
            result(bootService.dataHolder.entityHolder[id]?.let { cast<Tunnel>(it) }?.description?.deleteTransactionPhase(transactionId, phaseId))
}