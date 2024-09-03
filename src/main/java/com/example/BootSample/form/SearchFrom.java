package com.example.BootSample.form;

import lombok.Data;

/**
 * 検索条件を格納するFormクラスです
 * DataとはLombokのアノテーションで、Getter・Setterの自動生成を行います。
 * フィールドにfinalをつけると、2つのフィールドを初期化するコンストラクタが生成されます。
 * その場合デフォルトコンストラクタは生成されませんのでご注意ください。
 */
@Data
public class SearchFrom {
    private Integer id;
    private String name;
}