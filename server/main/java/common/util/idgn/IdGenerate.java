package common.util.idgn;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * ID 생성기
 *
 * @author 서영석
 * @since 2022.11.07
 */
public class IdGenerate {

    //ID 생성을 위한 Table 정보 목록
    private ArrayList<IdGenerateTargetTable> idGenerateTargetTableList = null;

    //DB Connection 객체
    private DataSource dataSource = null;

    /**
     * ID 생성기 table 생성
     *
     * @author 서영석
     * @since 2022.11.07
     */
    public void idGenerateTableCreate () {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("" +
                    "CREATE TABLE IF NOT EXISTS id_generate_target_table (\n" +
                    "    table_name character varying(50) NOT NULL,\n" +
                    "    prefix character varying(50),\n" +
                    "    length smallint,\n" +
                    "    current_index int,\n" +
                    "    current_id character varying(100),\n" +
                    "    CONSTRAINT pk__id_generate_target_table PRIMARY KEY (table_name)\n" +
                    ")");
            preparedStatement.executeUpdate();
        } catch(Throwable e) {
            e.printStackTrace();
        } finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
                if(connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ID 생성기 Table 초기화
     *
     * @author 서영석
     * @since 2022.11.07
     */
    public void initIdGenerateTargetTableList () {
        if (idGenerateTargetTableList == null || idGenerateTargetTableList.size() == 0) {
            return;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();

            for (int i = 0; i < idGenerateTargetTableList.size(); i++) {

                String selectSql = "" +
                        "SELECT\n" +
                        "   COUNT(0) AS count\n" +
                        "FROM\n" +
                        "   id_generate_target_table\n" +
                        "WHERE\n" +
                        "   table_name = '" + idGenerateTargetTableList.get(i).getTableName() + "'";
                //System.out.println(selectSql);
                preparedStatement = connection.prepareStatement(selectSql);
                ResultSet rs = preparedStatement.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count == 0) {
                    String insertSql = "" +
                            "INSERT INTO id_generate_target_table (\n" +
                            "   table_name,\n" +
                            "   prefix,\n" +
                            "   length,\n" +
                            "   current_index,\n" +
                            "   current_id\n" +
                            ") VALUES (\n" +
                            "   '" + idGenerateTargetTableList.get(i).getTableName() + "',\n" +
                            "   '" + idGenerateTargetTableList.get(i).getPrefix() + "',\n" +
                            "   " + idGenerateTargetTableList.get(i).getLength() + ",\n" +
                            "   " + idGenerateTargetTableList.get(i).getCurrentIndex() + ",\n" +
                            "   '" + idGenerateTargetTableList.get(i).getCurrentId() + "'\n" +
                            ")";
                    //System.out.println(insertSql);
                    preparedStatement = connection.prepareStatement(insertSql);
                    preparedStatement.executeUpdate();
                } else {
                    continue;
                }
            }

        } catch(Throwable e) {
            e.printStackTrace();
        } finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
                if(connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Next ID 가지고 오기
     *
     * @author 서영석
     * @since 2022.11.07
     */
    public String getNextId (String tableName) {
        if (idGenerateTargetTableList == null || idGenerateTargetTableList.size() == 0) {
            return null;
        }

        String nextId = null;
        int nextIndex = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = connection.prepareStatement("" +
                    "SELECT\n" +
                    "   table_name,\n" +
                    "   prefix,\n" +
                    "   length,\n" +
                    "   current_index,\n" +
                    "   current_id\n" +
                    "FROM\n" +
                    "   id_generate_target_table\n" +
                    "WHERE\n" +
                    "   table_name = '" + tableName + "'");
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            String prefix = rs.getString(2);
            short length = rs.getShort(3);
            int currentIndex = rs.getInt(4);
            nextIndex = currentIndex + 1;
            nextId = prefix + "_" + String.format("%0" + length + "d", nextIndex);

            preparedStatement = connection.prepareStatement("" +
                    "UPDATE\n" +
                    "   id_generate_target_table\n" +
                    "SET\n" +
                    "   current_index = " + nextIndex + ",\n" +
                    "   current_id = '" + nextId + "'\n" +
                    "WHERE\n" +
                    "   table_name = '" + tableName + "'");
            preparedStatement.executeUpdate();

        } catch(Throwable e) {
            nextId = null;
            e.printStackTrace();
        } finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
                if(connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return nextId;
    }

    public ArrayList<IdGenerateTargetTable> getIdGenerateTargetTableList() {
        return idGenerateTargetTableList;
    }

    public void setIdGenerateTargetTableList(ArrayList<IdGenerateTargetTable> idGenerateTargetTableList) {
        this.idGenerateTargetTableList = idGenerateTargetTableList;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
