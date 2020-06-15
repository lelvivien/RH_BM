import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class CreateForm extends  JFrame {
    private boolean UpdateFired = false;
    private JPanel panel1;
    private JTextField txtNom;
    private JTextField txtPrenom;
    private JTextField txtCnie;
    private JTextField txtDateNaissance;
    private JComboBox cbxGrade;
    private JComboBox cbxService;
    private JTextField txtDateGrade;
    private JTextField txtDateRecru;
    private JTable tbCreation;
    private JButton btnSave;
    private JRadioButton rbH;
    private JRadioButton rbF;
    private JComboBox cbxEchellon;
    private JTextField txtDateEchellon;
    private JComboBox cbxBudget;
    private JComboBox cbxDiplom;
    private JComboBox cbxSpecialite;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnSearch;
    private JTextField txtSearch;
    private JComboBox cbxCategorieGrade;
    private JTabbedPane tabdPane;
    private JPanel AcceuilTab;
    private JTextField txtNumSomme;
    private JButton btnclear;
    private JButton btngenerer;
    private JComboBox cbxDocuments;
    private JScrollPane tbJsrollPan;
    private JPanel mainPanel;
    private JTextField txtnamear;
    private JTextField txtprenomar;
    private JTabbedPane tabPane;
    private JTable tbconge;
    private JButton btnconge;
    private JPanel PhotoPanel;
    private JLabel PhotoLabel;
    private JLabel lblcnie;
    private JComboBox cbxcritere;
    private JPanel main;
    private  String SelectedCnie = null;
    private Map<Integer,String> ListGrade;
    private Map<Integer,String> ListEchell  ;
    private  static Map <String,String> DataList;
    String Searchcnie ;
    private MyTableDataModel tableDataModel = null;
    private  ListSelectionListener listSelectionListener = null;
    private String CheckedCnie;
    private String Critere;
    public static Map<String,String> getDataList(){
        return DataList;
    }
    public static int pdfSelectedIndex ;
    private File photoFile = null;
    private ImageIcon photoFromDb;
    boolean ImageisSet = false;
    private static int selectedtab;

    public CreateForm() throws SQLException {

        DataList = new HashMap<String, String>();
        /*Combobox binding*/
        cbxGrade.setMaximumRowCount(5);
        CbxBinding.bindigParentCbx(cbxCategorieGrade,"Select * from tbcategoriegrade","idcategoriegrade","categoriegrade");
        CbxBinding.bindigParentCbx(cbxBudget,"select * from tbbudget","idbudget","budget");
        CbxBinding.bindigParentCbx(cbxDiplom,"select * from tbdiplom","iddiplom","diplom");
        CbxBinding.bindigParentCbx(cbxService,"select * from tbservice","idservice","service");
        CbxBinding.bindigParentCbx(cbxSpecialite,"select * from tbspecialite","idspecialite","specialite");
        CbxBinding.ComboItem[] items = new CbxBinding.ComboItem[8];
        CbxBinding.ComboItem selectionner = new CbxBinding.ComboItem(0,"SELECTIONNER");
        CbxBinding.ComboItem attesTravail = new CbxBinding.ComboItem(1,"ATTESTATION DE TRAVAIL");
        CbxBinding.ComboItem demande_attestation_de_travail = new CbxBinding.ComboItem(2,"DEMANDE ATTESTATION DE TRAVAIL");
        CbxBinding.ComboItem CONGE = new CbxBinding.ComboItem(3,"CONGE ADMINISTRATIF");
        CbxBinding.ComboItem demande_conge = new CbxBinding.ComboItem(4,"DEMANDE CONGE");
        CbxBinding.ComboItem Autorisation_quiter_teritoire= new CbxBinding.ComboItem(5,"AUTORISATION QUITTER LE TERITOIRE");
        CbxBinding.ComboItem  demande_pour_quitter_le_teritoire= new CbxBinding.ComboItem(6,"DEMANDE POUR QUITTER LE TERITOIRE");
        CbxBinding.ComboItem fiche_de_conge_administratif = new CbxBinding.ComboItem(7,"FICHE DE CONGE ADMINISTRATIF");
        CbxBinding.ComboItem [] criteres = new CbxBinding.ComboItem[3];
        criteres[0] = new CbxBinding.ComboItem(0,"cnie");
        criteres[1]= new CbxBinding.ComboItem(1,"nom");
        criteres[2] = new CbxBinding.ComboItem(2,"service");
        for (int i =0;i<criteres.length;i++){
            cbxcritere.addItem(criteres[i]);
        }

        //populate cbx pdf documents
        items[0] = selectionner;
        items[1]= attesTravail;
        items[2]=demande_attestation_de_travail;
        items[3]= CONGE;
        items[4]= demande_conge;
        items[5]= Autorisation_quiter_teritoire;
        items[6] = demande_pour_quitter_le_teritoire;
        items[7]= fiche_de_conge_administratif;

        cbxDocuments.setModel(new DefaultComboBoxModel(items));



        /*enter fake data*/
       enterdata();

        /* set panel borders*/
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
       add(mainPanel);



        /* Jframe Code */
        this.setTitle("GESTIONNAIRE RH DE LA PROVINCE DE BENI MELLAL");
        this.setSize(1020,700);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rbH.setSelected(true);

        /*--------------------------------------------Action listenner-----------------------------------*/
       cbxcritere.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
              if (((JComboBox) e.getSource()).getSelectedIndex() == 2) {
                    txtSearch.setEnabled(false);
               }else{
                  txtSearch.setEnabled(true);

              }
           }
       });
        MouseListener PhotoMouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
               // JOptionPane.showMessageDialog(null,"ok");
                if(e.getClickCount()==2 ){
                    try {
                        photoFile = EmployePhoto.openEmployeImageFile();
                        PhotoLabel.setText("");
                        ImageIcon PhotoIcon = new ImageIcon(photoFile.getPath());
                        Image photo = PhotoIcon.getImage();
                        photo =  photo.getScaledInstance(PhotoLabel.getWidth(),PhotoLabel.getHeight(),Image.SCALE_SMOOTH);
                        PhotoIcon = new ImageIcon(photo);
                         PhotoLabel.setIcon(PhotoIcon);
                        ImageisSet = true;

                     //   EmployePhoto.saveImageTodb(photoFile);
                    } catch (IllegalAccessException illegalAccessException) {
                        illegalAccessException.printStackTrace();
                    } catch (InstantiationException instantiationException) {
                        instantiationException.printStackTrace();
                    } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
                        unsupportedLookAndFeelException.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
            }
        };

        ActionListener congeActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conge conge = new conge(txtSearch.getText(),JOptionPane.showInputDialog("annee"));
                    tbconge.setModel(conge.getDefaultTableModel());
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        };

        ActionListener CbxGradeActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                        CbxBinding.ComboItem comboItem = (CbxBinding.ComboItem) cbxGrade.getSelectedItem();
                        cbxEchellon.removeAllItems();
                        CbxBinding.populateChildCbx(cbxEchellon, "select * from tbechellon where idgrade=?",
                                comboItem.getId(), "echellon", "idechellon",ListEchell);
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        };
        ActionListener cbxCategorieGradeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cbxGrade.removeActionListener(CbxGradeActionListener);
                cbxGrade.removeAllItems();
                cbxEchellon.removeAllItems();
                CbxBinding.ComboItem comboItem = (CbxBinding.ComboItem) cbxCategorieGrade.getSelectedItem();
                try {
                    CbxBinding.populateChildCbx(cbxGrade, "select * from tbgrade where idcategoriegrade= ? ",
                            comboItem.getId(), "grade", "idgrade", ListGrade);

                    cbxGrade.addActionListener(CbxGradeActionListener);
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        };
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();
                if(row > -1){
                    DataList  = new HashMap<String, String>();
                    for(int i = 0; i<target.getColumnCount();i++){
                        DataList.put(target.getColumnName(i), target.getValueAt(row,i).toString());
                    }
                }
                SelectedCnie = (String) target.getValueAt(row, 2);
                btnUpdate.setEnabled(true);
                btnDelete.setEnabled(true);
                btnSave.setEnabled(true);
            }};


        btngenerer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    generatePdf();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        //---------------------------Adding Action Listeners-----------------------------------------------

        cbxGrade.addActionListener(CbxGradeActionListener);
        cbxCategorieGrade.addActionListener(cbxCategorieGradeListener);
        tbCreation.addMouseListener(mouseListener);
     //   PhotoPanel.addMouseListener(PhotoMouseListener);

        /* Disconnect database code to check*/
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    tableDataModel.disconnectFromDb();
                    e.getWindow().dispose();
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });

        this.addWindowListener((new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                btnDelete.setEnabled(false);
                btnUpdate.setEnabled(false);
            }
        })  );




        /*Add buttons handlers*/
        ButtonHandlers btnHandler = new ButtonHandlers();
        btnSave.addActionListener(btnHandler);
        btnUpdate.addActionListener(btnHandler);
        btnclear.addActionListener(btnHandler);
        btnDelete.addActionListener(btnHandler);
        btnSearch.addActionListener(btnHandler);
        btngenerer.addActionListener(btnHandler);
        btnconge.addActionListener(congeActionListener);
        PhotoLabel.addMouseListener(PhotoMouseListener);
   //     tabdPane.addChangeListener(tabchangelistener);

       // JOptionPane.showMessageDialog(null,PhotoPanel.getWidth()+" "+PhotoPanel.getHeight());
    }
    /*-----------------------------------------------------end of action listener code--------------------------------*/
    public class ButtonHandlers implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            /*--------------Save code------------*/
            if(e.getSource() == btnSave ){
                try {
                    saveData();

                    btnSearch.setEnabled(true);
                    btnUpdate.setEnabled(false);
                    btnDelete.setEnabled(false);
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
                /*----------------Update------------*/
            }else if (e.getSource() == btnUpdate ){

                /*charge data from jtable*/
                try {
                    //  CbxBinding.ComboItem comboItem = new CbxBinding.ComboItem();
//                    Map<String,String> values = new HashMap<>();
                    bindSelectedEmployeDatatoFormControls();/*gets the value of the selected table record to form controls for update*/

                    btnSearch.setEnabled(false);
                    btnDelete.setEnabled(false);
                    btnUpdate.setEnabled(false);
                    btnSave.setEnabled(true);
                } catch (SQLException | IOException sqlException) {
                    sqlException.printStackTrace();
                }
            }else if(e.getSource()==btnclear){
                cleardata();

            }else if(e.getSource()==btnDelete){
                /*Delete selected table record*/
                if(SelectedCnie == null ){
                    JOptionPane.showMessageDialog(null,"Selectionner une ligne du tableau");
                    return;
                }
                int returnvalue;
                returnvalue =  JOptionPane.showConfirmDialog(null,"Voulez vous vraiment suprimer cet enregistrement",
                        "Comfirmer la supression",JOptionPane.YES_NO_OPTION);
                if(returnvalue == JOptionPane.YES_OPTION){
                    Connection cnx;
                    PreparedStatement statement;
                    try {
                        cnx = DriverManager.getConnection(MyTableDataModel.DATABASE_URL, "root", "");
                        statement = cnx.prepareStatement("Delete from tbemploye where cnie=?");
                        statement.setString(1,SelectedCnie);
                        statement.execute();
                        JOptionPane.showMessageDialog(null,"Operation reussie !","", JOptionPane.INFORMATION_MESSAGE);
                        cleardata();
                        refreshTable();
                        btnUpdate.setEnabled(false);
                        btnSave.setEnabled(true);
                        statement.close();
                        cnx.close();
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                }

            }else if(e.getSource()== btnSearch){

                try {



                 //check wich element was selected in cbxcreteria+validation
                    if(cbxcritere.getSelectedIndex() <2){
                        if(txtSearch.getText().equals("")){
                            JOptionPane.showMessageDialog(null,"inserer critere de recherche !","Recherche",JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }else{
                            if(cbxService.getSelectedIndex()==0){
                                JOptionPane.showMessageDialog(null,"Veuillez selectionner un service","Recherche",JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }else{
                                Critere = txtSearch.getText();
                            }
                        }


                    }else{
                       // txtSearch.setEnabled(false);
                        Critere = ((CbxBinding.ComboItem) cbxService.getSelectedItem()).getName();
                    }

                    tableDataModel = new MyTableDataModel(Critere,cbxcritere.getSelectedIndex());
                    if(tableDataModel.getRowCount() == 0){
                        JOptionPane.showMessageDialog(null,"Donnée introuvable !","Recherche",JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    tbCreation.setModel(tableDataModel);
                    hideColumns();
                    btnDelete.setEnabled(false);
                    btnUpdate.setEnabled(true);

                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
                /* Center Table Content */
                DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
                defaultTableCellRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
                for (int i = 0; i< (tbCreation.getColumnCount()); i++ )                    {
                    tbCreation.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
                }
            }else if (e.getSource() == btngenerer){
                try {
                    if(DataList.isEmpty()){
                        JOptionPane.showMessageDialog(null,"Veuilez selectionner un element du tableau!");
                        return;
                    }
                    generatePdf();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }
    private void saveData() throws SQLException {

        /* retrieve Comboboxes values*/
        CbxBinding.ComboItem cbxItemGrade =(CbxBinding.ComboItem) cbxGrade.getSelectedItem();
        CbxBinding.ComboItem cbxItemEchelon=(CbxBinding.ComboItem) cbxEchellon.getSelectedItem();
        CbxBinding.ComboItem cbxItemSpecialite=(CbxBinding.ComboItem) cbxSpecialite.getSelectedItem();
        CbxBinding.ComboItem cbxItemDiplom=(CbxBinding.ComboItem) cbxDiplom.getSelectedItem();
        CbxBinding.ComboItem cbxItemService=(CbxBinding.ComboItem) cbxService.getSelectedItem();
        CbxBinding.ComboItem cbxItemBudget=(CbxBinding.ComboItem) cbxBudget.getSelectedItem();
        CbxBinding.ComboItem cbxItemCategorieGrade= (CbxBinding.ComboItem) cbxCategorieGrade.getSelectedItem();
        if((cbxItemBudget == null) || cbxItemCategorieGrade == null || cbxItemDiplom == null || cbxItemEchelon == null || cbxItemGrade == null || cbxItemService == null   || cbxItemSpecialite == null)
        {
            JOptionPane.showMessageDialog(null,"Veuillez selectionner des valeurs sur toutes les listes de choix !,",
                    "Manque de donnee",JOptionPane.WARNING_MESSAGE);
            return;
        }


        PreparedStatement st = null;

        Employe employe = new Employe();

        /*check if cnie alrady exist before insert*/
        Connection  cnx = DriverManager.getConnection(MyTableDataModel.DATABASE_URL, "root", "");
        int nbRows = checkIfcnieExist(st,cnx);

        if(nbRows>0 && !UpdateFired)
        { JOptionPane.showMessageDialog(null,"Ce fonctionnaire existe deja !","Message",JOptionPane.ERROR_MESSAGE);
            return;
        }
        else if((nbRows>0 && UpdateFired) && (!SelectedCnie.equals(CheckedCnie.toString()))){

                JOptionPane.showMessageDialog(null,"Ce fonctionnaire existe deja !","Message",JOptionPane.ERROR_MESSAGE);
                return;
        }

        try {

            cnx = DriverManager.getConnection(MyTableDataModel.DATABASE_URL, "root", "");

            if(UpdateFired){

                st = cnx.prepareStatement("UPDATE tbemploye SET nom=?,prenom=?,cnie=?,numsomme=?,daterecru=?,dategrade=?,dateechellon=?,idgrade=?," +
                        "idechellon=?,idservice=?,iddiplom=?,idbudget=?,idspecialite=?,sex=?,datenaissance=?,idcategoriegrade=?,image=?,nomar=?,prenomar=?  WHERE cnie=? ");
                //total 18
                st.setString(18,employe.getCnie());
            } else
            {
                /*17 fields in the query*/
                st = cnx.prepareStatement(
                        "INSERT INTO tbemploye (nom,prenom,cnie,numsomme,daterecru,dategrade,dateechellon,idgrade," +
                                "idechellon,idservice,iddiplom,idbudget,idspecialite,sex,datenaissance,idcategoriegrade,image,nomar,prenomar) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            }




            /*binding inserted values to employe fields*/


            employe.setCnie(txtCnie.getText());
            employe.setNom(txtNom.getText());
            employe.setPrenom(txtPrenom.getText());

            if(rbH.isSelected()){
                employe.setSex("H");
            }else{employe.setSex("F");}

            employe.setIdcategorieGrade(cbxItemCategorieGrade.getId());
            employe.setIdGrade(cbxItemGrade.getId() );
            employe.setDateNaissance(txtDateNaissance.getText());
            employe.setEchellon(cbxItemEchelon.getId());
            employe.setDateGrade(txtDateGrade.getText());
            employe.setDateRecrutement( txtDateRecru.getText());
            employe.setDateEchellon(txtDateEchellon.getText());
            employe.setBudget(cbxItemBudget.getId());
            employe.setDiplome(cbxItemDiplom.getId());
            employe.setService(cbxItemService.getId());
            employe.setSpecialite(cbxItemSpecialite.getId());
            employe.setNumSomme(txtNumSomme.getText());

            /*----setting parametres values in prepered statement------*/
            st.setString(1,employe.getNom().trim());
            st.setString(2,employe.getPrenom().trim());
            st.setString(3,employe.getCnie().trim());
            st.setString(4,employe.getNumSomme());
            st.setString(5,employe.getDateRecrutement() );
            st.setString(6,employe.getDateGrade());
            st.setString(7,employe.getDateEchellon());
            st.setInt(8,employe.getIdGrade());
            st.setInt(9,employe.getEchellon());
            st.setInt(10,employe.getService());
            st.setInt(11,employe.getDiplome());
            st.setInt(12,employe.getBudget());
            st.setInt(13,employe.getSpecialite());
            st.setString(14,employe.getSex());
            st.setString(15,employe.getDateNaissance());
            st.setInt(16,employe.getIdcategorieGrade());


            String cano = new File(".").getCanonicalPath();
            if(ImageisSet){//initial  record save

                String NewPath = cano+ "\\src\\res\\photos\\"+employe.getNom() + "_" + employe.getPrenom()+".jpg";
                 photoFile.renameTo(new File(NewPath));
                st.setString(17,NewPath);

            }else if (!ImageisSet && !UpdateFired){
              //  photoFromDb = new ImageIcon("defaultimage.png");
                st.setString(17,cano+ "\\src\\res\\"+"defaultimage.png");
            }else{
                st.setString(17,DataList.get("image"));
            }

            st.setString(18, txtnamear.getText().trim());
            st.setString(19, txtprenomar.getText().trim());

            if (UpdateFired)
            {


                st.setString(20,employe.getCnie());
                st.executeUpdate();
                cleardata();
                JOptionPane.showMessageDialog(null,"Mise à jour réussie:");
                refreshTable();
                btnUpdate.setEnabled(false);
                btnDelete.setEnabled(false);
                btnSearch.setEnabled(true);
                SelectedCnie = null;
                CheckedCnie = null;
                hideColumns();
            }else
            {

                st.execute();
              //  cleardata();
                JOptionPane.showMessageDialog(null,"Enregisté!");
                btnUpdate.setEnabled(false);
                btnDelete.setEnabled(false);
                btnSearch.setEnabled(true);
                SelectedCnie = null;
                CheckedCnie = null;

            }

        } catch (IllegalArgumentException | IOException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Illegal Argument Exception",0);
            return;
        } finally {
            UpdateFired = false;
            ImageisSet = false;
            photoFile = null;
            st.close();
            cnx.close();
        }

    }
    public void bindSelectedEmployeDatatoFormControls() throws SQLException, IOException {
        ResultSet resultSet;
        DataList = new HashMap<>();
        Connection cnx = DriverManager.getConnection(MyTableDataModel.DATABASE_URL, "root", "");
        CallableStatement statement = cnx.prepareCall("{call selectbycnie (?)}",ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setString(1,SelectedCnie);
        resultSet =  statement.executeQuery();
        String columnLabel;
        String value;
        resultSet.next();
        for(int i=1; i<=resultSet.getMetaData().getColumnCount();i++){
            columnLabel = resultSet.getMetaData().getColumnName(i);
            value = resultSet.getString(i);
            DataList.put(columnLabel,value);
        }
        txtNom.setText(DataList.get("nom"));
        txtPrenom.setText(DataList.get("prenom"));
        txtnamear.setText(DataList.get("nomar"));
        txtprenomar.setText(DataList.get("prenomar"));
        txtDateRecru.setText(DataList.get("daterecru"));
        txtNumSomme.setText(DataList.get("numsomme"));
        txtCnie.setText(DataList.get("cnie"));
        txtDateNaissance.setText(DataList.get("datenaissance"));/*qjouter au requete d insertion*/
        txtDateEchellon.setText(DataList.get("dateechellon"));
        txtDateGrade.setText(DataList.get("dategrade"));
        String Sex = DataList.get("sex");

        if(Sex.equals("H") ){
            rbH.setSelected(true);
        }
        else{
            rbF.setSelected(true);
        }
        File file = new File(DataList.get("image"));
        photoFromDb = new ImageIcon(new ImageIcon(file.getPath()).getImage().getScaledInstance(PhotoPanel.getWidth(), PhotoPanel.getHeight(), Image.SCALE_DEFAULT));
        PhotoLabel.setIcon(photoFromDb);
        cbxBudget.setSelectedItem(new CbxBinding.ComboItem(Integer.parseInt(DataList.get("idbudget")) ,DataList.get("budget")));
        cbxCategorieGrade.setSelectedItem(new CbxBinding.ComboItem(Integer.parseInt(DataList.get("idcategoriegrade")) ,DataList.get("categoriegrade")));
        cbxGrade.setSelectedItem(new CbxBinding.ComboItem(Integer.parseInt(DataList.get("idgrade")) ,DataList.get("grade")));
        cbxEchellon.setSelectedItem(new CbxBinding.ComboItem(Integer.parseInt(DataList.get("idechellon")) ,DataList.get("echellon")));
        cbxDiplom.setSelectedItem(new CbxBinding.ComboItem(Integer.parseInt(DataList.get("iddiplom")),DataList.get("diplom")));
        cbxService.setSelectedItem(new CbxBinding.ComboItem(Integer.parseInt(DataList.get("idservice")),DataList.get("service")));
        cbxSpecialite.setSelectedItem(new CbxBinding.ComboItem(Integer.parseInt(DataList.get("idspecialite")),DataList.get("specialite")));
        resultSet.close();
        statement.close();
        cnx.close();
        photoFromDb = null;
        UpdateFired=true;
    }
    public int checkIfcnieExist(PreparedStatement st,Connection cnx) throws SQLException {
            ResultSet resultSet;
            int nbrows;
            st = cnx.prepareStatement("SELECT cnie FROM tbemploye WHERE cnie=?");
            st.setString(1,txtCnie.getText());
            resultSet =  st.executeQuery();
            resultSet.last();
            nbrows =resultSet.getRow();
            if(nbrows>0){
                CheckedCnie = resultSet.getString("cnie"); //this to check if the updated cnie is not duplicated *see update button code*---
            }
            resultSet.close();
            st.close();
            cnx.close();
            return nbrows;
        }
    public void cleardata(){
        for (Component c : AcceuilTab.getComponents()) {
            if (c instanceof JTextField) {
                ((JTextField)c).setText("");
            }
        }

        cbxCategorieGrade.setSelectedIndex(0);
        cbxSpecialite.setSelectedIndex(0);
        cbxService.setSelectedIndex(0);
        cbxDiplom.setSelectedIndex(0);
        cbxBudget.setSelectedIndex(0);

    }
    public void enterdata(){
        txtNom.setText("soufiane");
        txtPrenom.setText("zahiri");
        txtDateGrade.setText("15/05/2010");
        txtCnie.setText("sh748596");
        txtDateEchellon.setText("14/04/2005");
        txtDateNaissance.setText("15/02/1985");
        txtNumSomme.setText("1523652");
        txtDateRecru.setText("15/02/2019");

    }
    public void refreshTable() throws SQLException {
        tableDataModel = new MyTableDataModel(null,0);
        tbCreation.setModel(tableDataModel);
    }
    public  void hideColumns(){
         for (int i=0;i<tbCreation.getColumnCount();i++){
             if(tbCreation.getColumnName(i).toString().contains("id")){
                 tbCreation.getColumnModel().removeColumn(tbCreation.getColumnModel().getColumn(i));
                 i--;
             }
         }

    }
    //*************************generate PDF Doc****************************************************************************
    public void generatePdf() throws IOException {
       pdfSelectedIndex = cbxDocuments.getSelectedIndex();
        GenerateWord documents = new GenerateWord(pdfSelectedIndex);
        documents.ReplaceText();
    }

    private String returnSelectedCriteria(){
        String Criteria;
        CbxBinding.ComboItem criteriaItem =  ( CbxBinding.ComboItem) cbxcritere.getSelectedItem();
        Criteria = criteriaItem.getName();
        return Criteria;
    }
}
/*-------------------------------RESSOURCES-------------------------*/
/*https://docs.oracle.com/javase/7/docs/api/javax/swing/JTable.html*/