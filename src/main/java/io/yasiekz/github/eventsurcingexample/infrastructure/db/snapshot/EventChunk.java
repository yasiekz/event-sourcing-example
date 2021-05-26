package io.yasiekz.github.eventsurcingexample.infrastructure.db.snapshot;

public enum EventChunk {

    CHUNK_A("a"),
    CHUNK_B("b"),
    CHUNK_C("c"),
    CHUNK_D("d"),
    CHUNK_E("e"),
    CHUNK_F("f"),
    CHUNK_0("0"),
    CHUNK_1("1"),
    CHUNK_2("2"),
    CHUNK_3("3"),
    CHUNK_4("4"),
    CHUNK_5("5"),
    CHUNK_6("6"),
    CHUNK_7("7"),
    CHUNK_8("8"),
    CHUNK_9("9");

    private String value;

    EventChunk(final String name) {
        this.value = name;
    }

    public String getValue() {
        return value;
    }
}
