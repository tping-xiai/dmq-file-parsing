package com.jfinteck.dmq.model.project.dao;

import com.jfinteck.dmq.model.project.dto.ManagerProjectDTO;
import org.apache.ibatis.annotations.Param;

public interface ManagerProjectDao {

    /**
     * 保存数据
     *
     * @param mangerProject
     */
    void insertInfo(@Param("project")ManagerProjectDTO mangerProject);
}
