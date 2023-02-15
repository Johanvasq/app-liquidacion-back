package co.com.ias.appback.infrastructure.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseModel {

    private Integer status;
    private String message;

}
