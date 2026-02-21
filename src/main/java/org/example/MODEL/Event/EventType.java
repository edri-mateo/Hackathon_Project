package org.example.MODEL.Event;

public enum EventType {
    ASSIGNMENT,
    LAB,
    QUIZ,
    TEST,
    EXAM,
    OTHER;

    public String toString() {
        return this.name();
    }
}
