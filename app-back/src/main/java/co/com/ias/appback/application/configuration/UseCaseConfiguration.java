package co.com.ias.appback.application.configuration;

import co.com.ias.appback.domain.model.gateway.employee.IFindEmployeeByIdGateway;
import co.com.ias.appback.domain.model.gateway.employee.IPaginationEmployeeGateway;
import co.com.ias.appback.domain.model.gateway.employee.ISaveEmployeeGateway;
import co.com.ias.appback.domain.model.gateway.liquidation.IFindAllLiquidationGateway;
import co.com.ias.appback.domain.model.gateway.liquidation.IFindLiquidationByIdGateway;
import co.com.ias.appback.domain.model.gateway.liquidation.ISaveLiquidationGateway;
import co.com.ias.appback.domain.model.gateway.salary.IFindAllSalaryHistoryGateway;
import co.com.ias.appback.domain.model.gateway.salary.ISaveSalaryHistoryGateway;
import co.com.ias.appback.domain.model.gateway.employee.IUpdateEmployeeGateway;
import co.com.ias.appback.domain.usecase.employee.FindEmployeeByIdUseCase;
import co.com.ias.appback.domain.usecase.employee.PaginationEmployeeUseCase;
import co.com.ias.appback.domain.usecase.employee.SaveEmployeeUseCase;
import co.com.ias.appback.domain.usecase.liquidation.FindAllLiquidationUseCase;
import co.com.ias.appback.domain.usecase.liquidation.FindLiquidationByIdUseCase;
import co.com.ias.appback.domain.usecase.liquidation.SaveLiquidationUseCase;
import co.com.ias.appback.domain.usecase.salary.FindSalaryEmployeeUseCase;
import co.com.ias.appback.domain.usecase.salary.SaveSalaryHistoryUseCase;
import co.com.ias.appback.domain.usecase.employee.UpdateEmployeeUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {


    // Employee use cases
    @Bean
    public SaveEmployeeUseCase saveEmployeeUseCase(ISaveEmployeeGateway iSaveEmployeeGateway,
                                                   IFindEmployeeByIdGateway iFindEmployeeByIdGateway,
                                                   SaveSalaryHistoryUseCase saveSalaryHistoryUseCase){
        return new SaveEmployeeUseCase(iSaveEmployeeGateway, iFindEmployeeByIdGateway, saveSalaryHistoryUseCase);
    }

    @Bean
    public FindEmployeeByIdUseCase findEmployeeByIdUseCase(IFindEmployeeByIdGateway iFindEmployeeByIdGateway){
        return new FindEmployeeByIdUseCase(iFindEmployeeByIdGateway);
    }

    @Bean
    public UpdateEmployeeUseCase updateEmployeeUseCase(
            IUpdateEmployeeGateway iUpdateEmployeeGateway,
            IFindEmployeeByIdGateway iFindEmployeeByIdGateway,
            ISaveSalaryHistoryGateway iSaveSalaryHistoryGateway
    ){
        return new UpdateEmployeeUseCase(iUpdateEmployeeGateway, iFindEmployeeByIdGateway, iSaveSalaryHistoryGateway);
    }

    // Salary use Cases
    @Bean
    public SaveSalaryHistoryUseCase saveSalaryHistoryUseCase(ISaveSalaryHistoryGateway iSaveSalaryHistoryGateway){
        return new SaveSalaryHistoryUseCase(iSaveSalaryHistoryGateway);
    }

    @Bean
    public PaginationEmployeeUseCase paginationEmployeeUseCase(IPaginationEmployeeGateway iPaginationEmployeeGateway){
        return new PaginationEmployeeUseCase(iPaginationEmployeeGateway);
    }

    @Bean
    public FindSalaryEmployeeUseCase findSalaryEmployeeUseCase(
            IFindAllSalaryHistoryGateway iFindAllSalaryHistoryGateway,
            IFindEmployeeByIdGateway iFindEmployeeByIdGateway){
        return new FindSalaryEmployeeUseCase(iFindAllSalaryHistoryGateway, iFindEmployeeByIdGateway);
    }

    @Bean
    public FindAllLiquidationUseCase findAllLiquidationUseCase(IFindAllLiquidationGateway iFindAllLiquidationGateway){
        return new FindAllLiquidationUseCase(iFindAllLiquidationGateway);
    }

    @Bean
    public FindLiquidationByIdUseCase findLiquidationByIdUseCase(IFindLiquidationByIdGateway iFindLiquidationByIdGateway,
                                                                 IFindEmployeeByIdGateway iFindEmployeeByIdGateway){
        return new FindLiquidationByIdUseCase(iFindLiquidationByIdGateway, iFindEmployeeByIdGateway);
    }

    @Bean
    public SaveLiquidationUseCase saveLiquidationUseCase(
            ISaveLiquidationGateway iSaveLiquidationGateway,
            IFindEmployeeByIdGateway iFindEmployeeByIdGateway,
            IFindAllSalaryHistoryGateway iFindAllSalaryHistoryGateway,
            IUpdateEmployeeGateway iUpdateEmployeeGateway){
        return new SaveLiquidationUseCase(
                iSaveLiquidationGateway,
                iFindEmployeeByIdGateway,
                iFindAllSalaryHistoryGateway,
                iUpdateEmployeeGateway);
    }
}
