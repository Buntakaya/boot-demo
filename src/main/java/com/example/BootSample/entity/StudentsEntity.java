package com.example.BootSample.entity;

import lombok.Data;

/**
 * StudentsテーブルのEntityクラスです
 * DataとはLombokのアノテーションで、Getter・Setterの自動生成を行います。
 * フィールドにfinalをつけると、2つのフィールドを初期化するコンストラクタが生成されます。
 * その場合デフォルトコンストラクタは生成されませんのでご注意ください。
 */
@Data
public class StudentsEntity {
    private int id;
    private String name;
    private String prefecture;
    private int gender;
}
