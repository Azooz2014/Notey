package io.blacketron.notey.Database;

public class NoteDbSchema {

    public static final class NoteTable{

        public static final String NAME = "notes";

        public static final class Columns{

            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String NOTES = "notes";
        }
    }
}
