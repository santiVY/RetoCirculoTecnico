package co.com.csanvel.model.balancemovements;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RsStatus {
    private String code;
    private String title;
    private String detail;
    private String severity;
}
