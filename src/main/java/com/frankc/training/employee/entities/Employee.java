package com.frankc.training.employee.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="employees")
public class Employee {

    @Id
    private String id;

    @Indexed
    private String fullName;

    private String departmentId;

    private Task currentTask;

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", fullName=" + fullName
               + ", departmentId=" + departmentId + ", currentTask="
               + currentTask + "]";
    }
}
