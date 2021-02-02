package co.com.csanvel.model.balancemovements;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class RsStatus {
    private String code;
    private String title;
    private String detail;
    private String severity;
}
