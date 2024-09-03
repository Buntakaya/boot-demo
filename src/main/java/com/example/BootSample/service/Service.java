package com.example.BootSample.service;

import com.example.BootSample.entity.StudentsEntity;
import com.example.BootSample.form.SearchFrom;
import com.example.BootSample.mapper.StudentsMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Serviceアノテーションで、Serviceクラスとして定義をし、管理をDIに任せます
 * RequiredArgsConstructorアノテーションは、final修飾子がついたフィールドを、
 * コンストラクタで初期化します
 */
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {
    private final StudentsMapper studentsMapper;

    /**
     * 検索条件によってStudentsテーブルから検索します
     *
     * @param searchFrom 　検索条件フォーム
     * @return 検索結果のList
     */
    public List<StudentsEntity> findStudent(SearchFrom searchFrom) {
        return studentsMapper.selectByConditions(searchFrom);
    }
}
