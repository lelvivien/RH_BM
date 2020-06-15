import javax.swing.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CbxBinding {

    private static PreparedStatement st = null;
    private static ResultSet rs = null;
    private static Statement statement = null;
    private  ResultSetMetaData metadata = null;
    static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/rhprovince?serverTimezone=UTC";
    private static Connection cnx=null;

    public CbxBinding() {

    }
    public static void populateChildCbx(JComboBox cbx, String query, int selectedIndex, String LabelName,
                                        String Labelid, Map<Integer,String> ListGradeEchellon) throws SQLException {

        try {
            cnx = DriverManager.getConnection(MyTableDataModel.DATABASE_URL, "root", "");
            st = cnx.prepareStatement(query);
            st.setInt(1, selectedIndex);
            rs = st.executeQuery();

            String data;
            int id;
            ComboItem cbxItem;
            cbx.addItem(new ComboItem( 0,"Selectionner"));
            ListGradeEchellon = new HashMap<>();
                while (rs.next()) {
                    data = rs.getString(LabelName);
                    id = rs.getInt(Labelid);
                    cbxItem = new ComboItem(id, data);
                    cbx.addItem(cbxItem);
                    ListGradeEchellon.put(cbxItem.getId(),cbxItem.getName());
                }
                cbx.setSelectedIndex(0);
        } catch (Exception ex) {
           ex.printStackTrace();
        }finally {
            rs.close();
            st.close();
            cnx.close();
        }
    }

    public static void bindigParentCbx(JComboBox jComboBox, String query,String tbid,String tbvalue) throws SQLException {

        int id;
        String name;
        try {
            cnx = DriverManager.getConnection(MyTableDataModel.DATABASE_URL, "root", "");
            statement = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(query);
        /*     resultset.last();

            int Nbofrows = resultset.getRow();
            resultset.beforeFirst();*/
            jComboBox.removeAllItems();
            jComboBox.addItem(new ComboItem( 0,"Selectionner"));
            while (rs.next()) {
                id = rs.getInt(tbid);
                name = rs.getString(tbvalue);
                ComboItem comboItem = new ComboItem(id, name);
                jComboBox.addItem(comboItem);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            rs.close();
            statement.close();
            cnx.close();
        }
    }

    public static class  ComboItem {
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private int id;
        private String name;

        public  ComboItem(){

        }
        public ComboItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return getName();
         }

        @Override
        public boolean equals(Object obj) {
           if (obj instanceof  ComboItem){
               return  ((ComboItem) obj).getId()==this.getId();

           }

            return super.equals(obj);
        }
    }



}