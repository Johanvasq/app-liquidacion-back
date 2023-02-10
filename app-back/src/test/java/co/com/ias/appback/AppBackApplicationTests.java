package co.com.ias.appback;

import co.com.ias.appback.domain.usecase.SaveEmployeeUseCase;
import co.com.ias.appback.infrastructure.entrypoint.EmployeeEntryPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AppBackApplicationTests {

    @Autowired
    private SaveEmployeeUseCase saveEmployeeUseCase;

    @Autowired
    private EmployeeEntryPoint employeeEntryPoint;

    @Test
    void contextLoads() {
        assertThat(saveEmployeeUseCase).isNotNull();
        assertThat(employeeEntryPoint).isNotNull();
    }

}
