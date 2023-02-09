package co.com.ias.appback.application.configuration;

import co.com.ias.appback.domain.model.gateway.IFindEmployeeByIdGateway;
import co.com.ias.appback.domain.model.gateway.ISaveEmployeeGateway;
import co.com.ias.appback.domain.usecase.SaveEmployeeUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public SaveEmployeeUseCase saveEmployeeUseCase(ISaveEmployeeGateway iSaveEmployeeGateway, IFindEmployeeByIdGateway iFindEmployeeByIdGateway){
        return new SaveEmployeeUseCase(iSaveEmployeeGateway, iFindEmployeeByIdGateway);
    }
}
