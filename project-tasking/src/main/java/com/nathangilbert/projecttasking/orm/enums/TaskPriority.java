package com.nathangilbert.projecttasking.orm.enums;

public enum TaskPriority {
    HIGH(1),
    MEDIUM(2),
    LOW(3);
    
    private final Integer priority;

    TaskPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getPriority() {
        return priority;
    }
}