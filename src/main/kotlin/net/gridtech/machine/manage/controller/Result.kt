package net.gridtech.machine.manage.controller

import org.springframework.http.ResponseEntity

fun result(any: Any?): ResponseEntity<*> =
        if (any == null || any == false)
            ResponseEntity.badRequest().body(mapOf("result" to "failed"))
        else
            ResponseEntity.ok(mapOf("result" to "success"))