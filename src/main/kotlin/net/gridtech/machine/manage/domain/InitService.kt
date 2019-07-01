package net.gridtech.machine.manage.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InitService {
    @Autowired
    lateinit var domainInfoService: DomainInfoService
    @Autowired
    lateinit var rootManageService: RootManageService
}