package com.example.BootSample.mapper;

import com.example.BootSample.entity.StudentsEntity;
import com.example.BootSample.form.SearchFrom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapperアノテーションで、Mapperインターフェースとして定義をし、管理をDIに任せます
 */
@Mapper
public interface StudentsMapper {
    /**
     * 検索条件によってStudentsテーブルから検索します
     *
     * @param searchFrom 検索条件フォーム
     * @return 検索結果のList
     */
    List<StudentsEntity> selectByConditions(SearchFrom searchFrom);
}
