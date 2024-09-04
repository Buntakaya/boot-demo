package com.example.BootSample;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum GenderEnum {
    MALE(0, "男"), FEMALE(1, "女");
    private final Integer code;
    private final String description;

    public static LinkedHashMap<Integer, GenderEnum> getMap() {

        LinkedHashMap<Integer, GenderEnum> enumMap = new LinkedHashMap<>();

        for (GenderEnum result : GenderEnum.values()) {
            enumMap.put(result.getCode(), result);
        }

        return enumMap;
    }
}
