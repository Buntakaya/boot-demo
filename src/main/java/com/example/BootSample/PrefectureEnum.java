package com.example.BootSample;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;

@RequiredArgsConstructor
@Getter
public enum PrefectureEnum {
    HOKKAIDO(0, "北海道"),
    MIYAGI(1, "宮城"),
    TOKYO(2, "東京"),
    OSAKA(3, "大阪"),
    NAGASAKI(4, "長崎"),
    OKINAWA(5, "沖縄");

    private final Integer code;
    private final String description;

    public static LinkedHashMap<Integer, PrefectureEnum> getMap() {

        LinkedHashMap<Integer, PrefectureEnum> enumMap = new LinkedHashMap<>();

        for (PrefectureEnum result : PrefectureEnum.values()) {
            enumMap.put(result.getCode(), result);
        }

        return enumMap;
    }
}
