package netologyru.dto.response;

import lombok.Data;

@Data
public class ExceptionResponse {

    private String message;
    private Integer id;

    public ExceptionResponse(String message, Integer id){
        this.message = message;
        this.id = id;
    }
}