package configuration;

import annotations.Column;
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
        TypeOfColumn typeOfColumn = checkTypeOfField(field);

        ColumnCreator columnCreator = new ColumnCreator();
        columnCreator.typeOfColumn = typeOfColumn;
        columnCreator.nameOfColumn = field.getName();
        columnCreator.isPrimaryKey = field.isAnnotationPresent(PrimaryKey.class);
        columnCreator.getDefaultValue = field.isAnnotationPresent(DefaultValue.class);
        columnCreator = verifyAnnot (field, columnCreator);

        return columnCreator;
    }

    private static TypeOfColumn checkTypeOfField(Field field) {
        Class fieldType = field.getType();
        TypeOfColumn typeOfColumn;

        if (fieldType == Boolean.TYPE || (fieldType == Boolean.class)) {
            typeOfColumn = TypeOfColumn.BOOLEAN;
        } else if ((fieldType == Integer.TYPE) || (fieldType == Integer.class)) {
            typeOfColumn = TypeOfColumn.INTEGER;
        } else if (fieldType == Double.TYPE || fieldType == Double.class) {
            typeOfColumn = TypeOfColumn.DOUBLE;
        } else if (fieldType == Float.TYPE || fieldType == Float.class) {
            typeOfColumn = TypeOfColumn.FLOAT;
        } else if (fieldType == Character.TYPE || fieldType == Character.class) {
                typeOfColumn = TypeOfColumn.CHAR;
        }
        else if (fieldType == String.class){
                typeOfColumn = TypeOfColumn.VARCHAR;
        } else {
            return  null;
        }
        return typeOfColumn;
    }

    private static ColumnCreator verifyAnnot (Field field, ColumnCreator columnCreator){
        TypeOfColumn typeOfColumn = checkTypeOfField(field);
        if ( columnCreator.getDefaultValue){
            DefaultValue defaultValueForCheck = field.getAnnotation(DefaultValue.class);
            columnCreator.defaultValue = defaultValueForCheck.value();
        }
        if (typeOfColumn == TypeOfColumn.VARCHAR){
            columnCreator.typeOfColumn = TypeOfColumn.TEXT;
        }
        if (field.isAnnotationPresent(annotations.Column.class)){
            Column nameOfColumn = field.getAnnotation(Column.class);
            String name = nameOfColumn.value();
            if (!name.trim().isEmpty()){
                columnCreator.nameOfColumn = name;
            }
        }
        return columnCreator;
    }

    public String toString (){
        StringBuilder resultString = new StringBuilder();
        resultString.append("`");
        resultString.append(nameOfColumn);
        resultString.append("`");
        resultString.append(typeOfColumn.toString());
        if (typeOfColumn == TypeOfColumn.CHAR){
            resultString.append("(1)");
        } else if (typeOfColumn == TypeOfColumn.VARCHAR){
            resultString.append("()");
        }
        return resultString.toString();
    }

}
