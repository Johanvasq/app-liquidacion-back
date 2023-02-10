package co.com.ias.appback.domain.usecase.employee;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.gateway.employee.IFindEmployeeByIdGateway;


import java.util.Optional;

public class FindEmployeeByIdUseCase {

    private final IFindEmployeeByIdGateway iFindEmployeeByIdGateway;

    public FindEmployeeByIdUseCase(IFindEmployeeByIdGateway iFindEmployeeByIdGateway) {
        this.iFindEmployeeByIdGateway = iFindEmployeeByIdGateway;
    }

    public Employee findEmployeeById(String id) {
        Optional<Employee> rta = Optional.ofNullable(iFindEmployeeByIdGateway.findById(id));
        if (rta.isPresent()) {
            return rta.get();
        }else {
            throw new IllegalStateException("The employee doesn't exists");
        }

    }
}
