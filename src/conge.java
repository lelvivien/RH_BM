import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class conge {
    public DefaultTableModel getDefaultTableModel() {
        return defaultTableModel;
    }

  private   DefaultTableModel defaultTableModel = new DefaultTableModel();
    public conge(String cnie,String annee) throws SQLException {
        String congequery = "SELECT nom,prenom,annee,dure,date FROM tbemploye INNER JOIN tbconge ON tbemploye.idemploye=tbconge.idemploye " +
                "where cnie=? AND annee = ?";
            Connection connection = DriverManager.getConnection(MyTableDataModel.DATABASE_URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(congequery,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, cnie);
            preparedStatement.setString(2, annee);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();


            int nbcol = resultSet.getMetaData().getColumnCount();

        defaultTableModel.setColumnCount(nbcol);
            int i = 1;
            String colname = null;
            for (i = 1; i < nbcol; i++) {
                colname = resultSet.getMetaData().getColumnName(i);
                defaultTableModel.addColumn(colname);

            }
            resultSet.last();


            int nbrow = resultSet.getRow();
        defaultTableModel.setRowCount(nbrow);
        resultSet.first();
        int j = 1;
           while (resultSet.next()){
               j=1;
               for (i=1;i<nbcol;i++){
                   defaultTableModel.setValueAt(resultSet.getObject(i),i,j);
                   j++;
               }
        }


        }

    }