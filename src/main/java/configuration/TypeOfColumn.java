package configuration;

public enum TypeOfColumn {

    BOOLEAN,
    INTEGER,
    BIGINTEGER,
    FLOAT,
    DOUBLE,
    VARCHAR,
    CHAR,
    TEXT,
    OTHER;

    @Override
    public String toString() {
        switch (this){
            case BOOLEAN:
                return Constants.STRING_BOOLEAN;
            case INTEGER:
                return Constants.STRING_INT;
            case BIGINTEGER:
                return Constants.STRING_BIGINTEGER;
            case FLOAT:
                return Constants.STRING_FLOAT;
            case DOUBLE:
                return Constants.STRING_DOUBLE;
            case VARCHAR:
                return Constants.STRING_VARCHAR;
            case CHAR:
                return Constants.STRING_CHAR;
            case TEXT:
                return Constants.STRING_TEXT;
        }
        return "OTHER";
    }
}