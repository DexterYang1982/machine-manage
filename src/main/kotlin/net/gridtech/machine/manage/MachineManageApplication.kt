package net.gridtech.machine.manage

import net.gridtech.core.util.hostInfoPublisher
import net.gridtech.machine.manage.domain.DomainInfoService
import net.gridtech.machine.manage.domain.InitService
import org.springframework.beans.factory.getBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@ComponentScan("net.gridtech")
@EnableMongoRepositories("net.gridtech")
class MachineManageApplication

fun main(args: Array<String>) {
    runApplication<MachineManageApplication>(*args)
            .apply {
                val initService: InitService = getBean()
                if (initService.initialize()) {
                    val domainInfoService: DomainInfoService = getBean()
                    domainInfoService.hostInfo?.apply { hostInfoPublisher.onNext(this) }
                }
            }
}
