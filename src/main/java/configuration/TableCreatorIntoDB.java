package configuration;

import org.apache.commons.lang3.StringUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class TableCreatorIntoDB {

    private List<Object> defaults = new ArrayList<>();
    private String tableIntoDB;
    private Connection connection;
    private Class clase;

    public TableCreatorIntoDB(Connection connection, Class classe) {
        this.connection = connection;
        this.clase = classe;
        tableIntoDB = TableCreator.getTableName(classe);
    }

    public TableCreatorIntoDB setTableIntoDB(String name) {
        tableIntoDB = name;
        return this;
    }

    private String generateQueryOfCreateTable() throws Exception {
        String nameOfTable = tableIntoDB == null || tableIntoDB.isEmpty() ? TableCreator.getTableName(clase) : tableIntoDB;

        List<ColumnCreator> columnCreators = new ArrayList<>();
        Field[] fields = clase.getFields();
        for (Field field : fields) {
            int modificators = field.getModifiers();
            if (Modifier.isPublic(modificators) && !Modifier.isStatic(modificators)) {
                ColumnCreator columnCreator = ColumnCreator.makeFromField(field);
                if (columnCreator != null) {
                    columnCreators.add(columnCreator);
                    if (columnCreator.getDefaultValue) {
                        defaults.add(columnCreator.getDefaultValue);
                    }
                }
            }
        }
        String headline = Constants.STRING_CREATE_TABLE +"`" +nameOfTable+"`"+"(\n";
        int primaryKeys = 0;
        String primaryKey = "";
        List<String> columnStringList = new ArrayList<>();
        for (ColumnCreator columnCreator : columnCreators) {
            if (columnCreator.isPrimaryKey) {
                primaryKeys++;
                primaryKey = columnCreator.nameOfColumn;
            }
            columnStringList.add(columnCreator.toString());
        }
        if (primaryKeys > 1) throw new Exception();
        String footer;
        if (primaryKeys == 1) {
            footer = Constants.STRING_PRIMARY_KEY + "`" + primaryKey + "`))";
        } else {
            footer = ")";
        }

        return headline + StringUtils.join(columnStringList,",\n") + ", " + footer + ";";
    }

    public PreparedStatement toPrepare () throws Exception {
        try {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(generateQueryOfCreateTable());
            int index = 0;

            for (Object defaultValue: defaults){
                preparedStatement.setObject(++index, defaultValue);
            }
            return preparedStatement;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    public String toSQLString () throws Exception{
        return toPrepare().toString();
    }
}