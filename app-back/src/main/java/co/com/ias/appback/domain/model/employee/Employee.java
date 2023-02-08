package co.com.ias.appback.domain.model.employee;

import co.com.ias.appback.domain.model.employee.attributes.*;

/**
 * Value object of employee
 */
public class Employee {
    private final EmployeeId employeeId;
    private final EmployeeName employeeName;
    private final EmployeeContractStart employeeContractStart;
    private final EmployeePosition employeePosition;
    private final EmployeeState employeeState;
    private final EmployeeCurrentSalary employeeCurrentSalary;

    /**
     * Constructor of value object
     * @param employeeId employee identification, not null, String
     * @param employeeName employee name, not null, String
     * @param employeeContractStart employee contract start, yyyy/dd/mm, not null, Local Date
     * @param employeePosition employee position, not null, String
     * @param employeeState employee state, not null, Boolean
     * @param employeeCurrentSalary employee current salary, not null, Double
     */
    public Employee(EmployeeId employeeId, EmployeeName employeeName, EmployeeContractStart employeeContractStart, EmployeePosition employeePosition, EmployeeState employeeState, EmployeeCurrentSalary employeeCurrentSalary) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeContractStart = employeeContractStart;
        this.employeePosition = employeePosition;
        this.employeeState = employeeState;
        this.employeeCurrentSalary = employeeCurrentSalary;
    }

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public EmployeeName getEmployeeName() {
        return employeeName;
    }

    public EmployeeContractStart getEmployeeContractStart() {
        return employeeContractStart;
    }

    public EmployeePosition getEmployeePosition() {
        return employeePosition;
    }

    public EmployeeState getEmployeeState() {
        return employeeState;
    }

    public EmployeeCurrentSalary getEmployeeCurrentSalary() {
        return employeeCurrentSalary;
    }
}
