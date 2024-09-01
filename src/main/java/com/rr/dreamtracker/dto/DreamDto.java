package com.rr.dreamtracker.dto;

import lombok.Data;
import java.util.Date;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Data
public class DreamDto {
    @NotBlank(message = "is mandatory")
    private String name;
    private String description;
    private Date deadline;
    @NotBlank(message = "is mandatory")
    @Range(min=1, max=5)
    private Integer priority;
    private Long parentDreamId;
}
