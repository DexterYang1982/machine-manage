package net.gridtech.machine.manage.config

import net.gridtech.machine.manage.domain.DomainInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class WebSecurityConfig : WebMvcConfigurer {
    @Autowired
    lateinit var domainInfoService: DomainInfoService

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(object : HandlerInterceptor {
            override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
                val nodeId = request.getHeader("nodeId")
                val nodeSecret = request.getHeader("nodeSecret")
                val result = domainInfoService.domainNodeSecret == nodeSecret && domainInfoService.domainNodeId == nodeId
                if (!result) {
                    response.status = HttpServletResponse.SC_FORBIDDEN
                }
                return result
            }
        }).addPathPatterns("/api/**")
        super.addInterceptors(registry)
    }
}