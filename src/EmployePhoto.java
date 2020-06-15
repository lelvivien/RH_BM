import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class EmployePhoto extends JFrame {
    private static Connection connection;
    private static File ImageFile;


    public EmployePhoto() throws  SQLException {
        this.connection =DriverManager.getConnection(MyTableDataModel.DATABASE_URL, "root", "");


    /*    Desktop desktop = Desktop.getDesktop();
        desktop.open(file1);*/

//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        frame.setSize(400,400);
//        JLabel label = new JLabel("hey",new ImageIcon(image.getScaledInstance(400,400, Image.SCALE_FAST)),SwingConstants.CENTER);
//        JPanel panel = new JPanel();
//       panel.add(label);
//       frame.add(panel);
//        frame.setVisible(true);

    }
    public static File openEmployeImageFile() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

           UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
           JFileChooser jFileChooser = new JFileChooser();
           jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);  //set file selection mode
           int result = jFileChooser.showOpenDialog(null);
           if(result == JFileChooser.CANCEL_OPTION){
               return null;
           }
           ImageFile  = jFileChooser.getSelectedFile();
           if( ImageFile==null || ImageFile.getName().equals("") || !checkExtension(ImageFile)){
               JOptionPane.showMessageDialog(null,"Invalid File");
               ImageFile= null;
           }

        return  ImageFile;
    }
    private static boolean checkExtension(File imageFile){
        boolean  isAnImage = false;
        String ext = FilenameUtils.getExtension(imageFile.getName());
    String [] Extensions = {"jpg","jpeg","png"};
    if (Arrays.asList(Extensions).contains(ext) && imageFile.length() < 450000)
    {
        isAnImage = true;
    }
    return  isAnImage;
}
public static byte[] convertImageFileToBytes(File imageFile) throws SQLException, IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
    byte[] imagetobyte ;
    BufferedImage bufferedImage =  ImageIO.read(imageFile);
    imagetobyte = ((DataBufferByte)( bufferedImage.getRaster().getDataBuffer())).getData();
   // ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
   // ImageIO.write(bufferedImage,"jpg",byteArrayOutputStream);
  //  imagetobyte = byteArrayOutputStream.toByteArray();
  //  byteArrayOutputStream.close();
//    ResultSet keysResultset = statement.getGeneratedKeys();//get the generated ID
    return  imagetobyte;
}

//public static ImageIcon readEmployeeImageFromResultset ( ) throws SQLException, IOException {
//
//
//return  imageIcon;
//
//}

    public static byte[] convertImageToBytes (Image image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageInByte;
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
        ImageIO.write(bufferedImage,"jpg",baos);
        imageInByte = baos.toByteArray();

        return  imageInByte;
    }



}
