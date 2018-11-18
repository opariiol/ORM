package configuration;

import annotations.*;

// done first part for table creating

public class TableCreator{

    private Class classe;
    private String  nameOfTable;

    public TableCreator(Class classe) {
        this.classe = classe;
    }

    public TableCreator(Class classe, String nameOfTable) {
        this.classe = classe;
        this.nameOfTable = nameOfTable;
    }

    public Class getClasse() {
        return classe;
    }

    public String getNameOfTable() {
        return nameOfTable;
    }

    public static String getTableName(Class classe){
        String tableName = "";

        if (classe.isAnnotationPresent(annotations.Table.class)){
            annotations.Table tableNameAnn = (annotations.Table) classe.getAnnotation(annotations.Table.class);
            tableName = tableNameAnn.value();
        }

        if (tableName.isEmpty()){
            tableName = classe.getSimpleName();
        }
        return tableName;
    }

}
