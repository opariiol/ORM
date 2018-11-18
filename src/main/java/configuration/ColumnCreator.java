package configuration;

import annotations.DefaultValue;
import annotations.PrimaryKey;

import java.lang.reflect.Field;

// done first part for table creating

public class ColumnCreator {

    public String nameOfColumn;
    public TypeOfColumn typeOfColumn;
    public boolean isPrimaryKey;
    public boolean getDefaultValue;
    public String defaultValue;

    public ColumnCreator() {
    }

    public static ColumnCreator makeFromField(Field field) {
        return makeFromField(field, "#");

    }

    public static ColumnCreator makeFromField(Field field, String variants){
        TypeOfColumn typeOfColumn = checkFieldType(field);

        ColumnCreator columnCreator = new ColumnCreator();
        columnCreator.typeOfColumn = typeOfColumn;
        columnCreator.nameOfColumn = field.getName();
        columnCreator.isPrimaryKey = field.isAnnotationPresent(PrimaryKey.class);
        columnCreator.getDefaultValue = field.isAnnotationPresent(DefaultValue.class);
        //columnCreator = checkAnnotation (field, ColumnCreator);

        return columnCreator;
    }

    private static TypeOfColumn checkFieldType(Field field) {
        Class fieldType = field.getType();
        TypeOfColumn columnType;

        if (fieldType == Boolean.TYPE || (fieldType == Boolean.class)) {
            columnType = TypeOfColumn.BOOLEAN;
        } else if ((fieldType == Integer.TYPE) || (fieldType == Integer.class)) {
            columnType = TypeOfColumn.INTEGER;
        } else if (fieldType == Double.TYPE || fieldType == Double.class) {
            columnType = TypeOfColumn.DOUBLE;
        } else if (fieldType == Float.TYPE || fieldType == Float.class) {
            columnType = TypeOfColumn.FLOAT;
        } else if (fieldType == Character.TYPE || fieldType == Character.class) {
            columnType = TypeOfColumn.CHAR;
        } else if (fieldType == String.class) {
            columnType = TypeOfColumn.VARCHAR;
        } else {
            return null;
        }
        return columnType;


    }

}
