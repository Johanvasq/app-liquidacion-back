package co.com.ias.appback.domain.model.pageresponse;

import co.com.ias.appback.domain.model.pageresponse.attributes.PageResponsePaging;
import co.com.ias.appback.domain.model.pageresponse.attributes.PageResponseRemainingResults;
import co.com.ias.appback.domain.model.pageresponse.attributes.PageResponseResults;
import co.com.ias.appback.domain.model.employee.Employee;

import java.util.List;

public class PageResponse {
    private final PageResponsePaging pageResponsePaging;

    private final PageResponseResults pageResponseResults;
    private final PageResponseRemainingResults pageResponseRemainingResults;
    private final List<Employee> employee;

    public PageResponse(PageResponsePaging pageResponsePaging, PageResponseResults pageResponseResults, PageResponseRemainingResults pageResponseRemainingResults, List<Employee> employee) {
        this.pageResponsePaging = pageResponsePaging;
        this.pageResponseResults = pageResponseResults;
        this.pageResponseRemainingResults = pageResponseRemainingResults;
        this.employee = employee;
    }

    public PageResponsePaging getPageResponsePaging() {
        return pageResponsePaging;
    }

    public PageResponseResults getPageResponseResults() {
        return pageResponseResults;
    }

    public PageResponseRemainingResults getPageResponseRemainingResults() {
        return pageResponseRemainingResults;
    }

    public List<Employee> getEmployee() {
        return employee;
    }

}
