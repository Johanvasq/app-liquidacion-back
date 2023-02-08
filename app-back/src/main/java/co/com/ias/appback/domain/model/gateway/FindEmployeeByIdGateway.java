package co.com.ias.appback.domain.model.gateway;

import co.com.ias.appback.domain.model.employee.Employee;

public interface FindEmployeeByIdGateway {

    Employee findById(String id);
}
