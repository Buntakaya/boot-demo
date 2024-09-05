package com.example.BootSample.form;

import com.example.BootSample.GenderEnum;
import com.example.BootSample.PrefectureEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateForm {

    private int id;

    @NotEmpty
    @Size(max = 30)
    private String name;

    @NotNull
    private PrefectureEnum prefecture;

    @NotNull
    private GenderEnum gender;
}
