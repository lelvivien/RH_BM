import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.List;

public class MyTableDataModel extends AbstractTableModel {
   private  Connection cnx = null;
    private  Statement st = null;
    private  CallableStatement callableStatement;
    private  ResultSet rs = null;
    private  boolean connected = false;
    private  int nbrows;
    private  int nbcolumns;
    private ResultSetMetaData metadata = null;
    List<String>[] mylist;
    //database url
    static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/rhprovince?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";

    public  MyTableDataModel (){

    }

    public MyTableDataModel(String critere, int SelectedIndex) throws SQLException {

        try {
            cnx = DriverManager.getConnection(DATABASE_URL, "root", "");
            if( SelectedIndex == 0 ){
                callableStatement = cnx.prepareCall("{call selectbycnie (?)}",ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                callableStatement.setString("cin",critere);

            }else if( SelectedIndex == 1){
                //create callablestatement for recherceh par nom
                callableStatement = cnx.prepareCall("{call selectbyname (?)}",ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                callableStatement.setString("name_param",critere + "%");

            }else{
                callableStatement = cnx.prepareCall("{call selectbyservice (?)}",ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                callableStatement.setString("service_param",critere);
            }

           connected = true;
             }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
        setQuery();
    }

    @Override
    public String getColumnName(int column) throws IllegalStateException {
        if ( !connected )
            throw new IllegalStateException( "Not Connected to Database" );

        // determine number of columns
        try
        {
            return metadata.getColumnName(column+1);
        } // end try
        catch ( SQLException sqlException )
        {
            JOptionPane.showMessageDialog(null,sqlException.getMessage());
            sqlException.printStackTrace();
        } // end catch
        return "";
    }

    @Override
    public int getRowCount() {
        // ensure database connection is available
        if ( !connected )
        throw new IllegalStateException( "Not Connected to Database" );

        return nbrows;
        } // end method getRowCount

    @Override
    public int getColumnCount() throws IllegalStateException {
        if ( !connected )
             throw new IllegalStateException( "Not Connected to Database" );

         // determine number of columns
         try
         {
         return metadata.getColumnCount();
         } // end try
         catch ( SQLException sqlException )
         {
             JOptionPane.showMessageDialog(null,sqlException.getMessage());
         } // end catch
        return 0;
    }

    public Class getColumnClass(int columnIndex) throws IllegalStateException
    {
        if (!connected)
            throw new IllegalStateException("Not connected to the db");

        try{
            String classname = metadata.getColumnClassName(columnIndex+1);
            return  Class.forName(classname);
        }
        catch (Exception exception)
        {
            JOptionPane.showMessageDialog(null,exception.getMessage());
        }

        return Object.class;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) throws IllegalStateException {
        if (!connected)
            throw new IllegalStateException("Not connected to the db");

        try{
            rs.absolute( rowIndex + 1 );
            return rs.getObject( columnIndex + 1 );
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return "";
    }

    public void setQuery() throws SQLException, IllegalStateException{
        if (!connected)
            throw new IllegalStateException("Not connected to the db");
        else{
            rs = callableStatement.executeQuery();
            /* rs = st.executeQuery(query);*/
            metadata = rs.getMetaData();
            rs.last();
            nbrows =  rs.getRow();
            rs.first();
            fireTableStructureChanged();

        }

    }


    public  void disconnectFromDb() throws  SQLException,IllegalStateException{
        if (connected == true)
            try {
                rs.close();
                st.close();
                cnx.close();
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
            finally {
                connected = false;
            }
    }
}

