package HealthCare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckupDTO {
    private long id;
    private String result;
    private List<PrescriptionDTO> prescriptions = new ArrayList<>();
}
