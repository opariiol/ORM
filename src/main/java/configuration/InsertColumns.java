package configuration;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

// for adding data to column, make with JDeek

public class InsertColumns {


    private Map<ColumnCreator,Object> valueMap = new HashMap<>();
    private Map<ColumnCreator,Integer> indexMap = new HashMap<>();

    public static int INSERT_DEFAULT = 0;
    public static int INSERT_REPLACE = 1;
    public static int INSERT_IGNORE = 2;
    private Connection connection;
    private Object object;
    private Class clss;
    private int initIndex = 0;

    private int insertType;
    private String table;

    private Map<String,String> functionValueMap = new HashMap<>();

    public InsertColumns(Connection connection, Object object){
        this.connection = connection;
        this.object = object;
        clss = object.getClass();
        this.insertType = INSERT_DEFAULT;
    }

    public InsertColumns setTableName(String name){
        this.table = name;
        return  this;
    }

    private String generateInsertQuery() throws Exception{
        String tableName = (table == null) || (table.isEmpty()) ? TableCreator.getTableName(clss) : table;

        List<String> setValueStringList = new ArrayList<>();
        Field[] fields = clss.getFields();

        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if ((Modifier.isPublic(modifiers)) && (!Modifier.isStatic(modifiers))) {

                ColumnCreator column = ColumnCreator.makeFromField(field);
                if (column != null) {
                    if (functionValueMap.containsKey(column.nameOfColumn)) {
                        String setValueString = "`" + column.nameOfColumn + "`" + "=" + functionValueMap.get(column.nameOfColumn);

                        setValueStringList.add(setValueString);
                    } else {
                        Object value = field.get(object);

                        if ((value != null) || (!column.getDefaultValue)) {

                            String setValueString = Constants.STRING_BEFORE_QUOTE + column.nameOfColumn + Constants.STRING_AFTER_QUOTE
                                    + Constants.STRING_ASSIGN + (value != null ? Constants.STRING_VAR : Constants.STRING_NULL);

                            setValueStringList.add(setValueString);

                            if (value != null) {
                                if ((value.getClass() == Character.TYPE) || (value.getClass() == Character.class)) {
                                    valueMap.put(column, String.valueOf(value));
                                } else {
                                    valueMap.put(column, value);
                                }
                                indexMap.put(column, ++initIndex);
                            }
                        }
                    }
                }
            }
        }
        boolean insertReplaceSet = (insertType == INSERT_REPLACE);

        boolean insertIgnoreSet = (insertType == INSERT_IGNORE);

        String header = (insertReplaceSet ? Constants.STRING_REPLACE : Constants.STRING_INSERT) +
                (insertIgnoreSet ? Constants.STRING_IGNORE : "") + Constants.STRING_INTO +
                Constants.STRING_BEFORE_QUOTE + tableName + Constants.STRING_AFTER_QUOTE + Constants.STRING_SET;



        String result = header + StringUtils.join(setValueStringList, Constants.STRING_GLUE) + Constants.STRING_QUERY_END;
        System.out.println(result);
        return result;
    }

    public PreparedStatement toPreparedStatement() throws Exception {
        try {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(generateInsertQuery());

            for (ColumnCreator column : valueMap.keySet()) {
                Object object = valueMap.get(column);
                preparedStatement.setObject(indexMap.get(column), object);
            }
            return preparedStatement;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    public String toSQLString() throws Exception{
        return toPreparedStatement().toString();
    }
}