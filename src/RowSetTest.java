
import com.sun.rowset.JdbcRowSetImpl;
import javax.sql.rowset.JdbcRowSet;
import java.sql.*;

public class RowSetTest {
    public RowSetTest (){
        JdbcRowSet jdbcRowSet =  new JdbcRowSetImpl();

        try {
            jdbcRowSet.setUrl("jdbc:mysql://localhost:3306/rhprovince");
            jdbcRowSet.setUsername("root");
            jdbcRowSet.setPassword("");
            jdbcRowSet.setCommand("Select * from tbcategoriegrade");
            jdbcRowSet.execute();

            ResultSetMetaData resultSetMetaData = jdbcRowSet.getMetaData();
           for (int i =1;i<resultSetMetaData.getColumnCount();i++)
           {
                System.out.printf("%-8s\t",resultSetMetaData.getColumnName(i));
           }

           while (jdbcRowSet.next()){
               for (int i =1;i<resultSetMetaData.getColumnCount();i++)
               {
                   System.out.printf("%-8s\t",jdbcRowSet.getObject(i));
                   System.out.println();
               }
           }

            jdbcRowSet.close();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }


    }
}
