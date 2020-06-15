import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenerateWord {

    private static XWPFDocument document;
    private Map<String, String> EmployeData;
    String[] responsables = {"Le Wali de la Région Beni Mellal-Khenifra et Gouverneur de la Province de Béni-Mellal",
            "Président du Conseil Provincial de Beni-Mellal"};

    public GenerateWord(int pdfSelectedIndex) throws IOException {
        List<String> docsPath = new ArrayList<String>();
        docsPath.add(0,"");
        docsPath.add(1,"c:/AT.docx");
        docsPath.add(2,"c:/demande_AT.docx");
        docsPath.add(3,"c:/conge.docx");
        docsPath.add(4,"c:/Auto_Terri.docx");
        docsPath.add(5,"c:/demande_AT.docx");
        docsPath.add(6,"c:/demande_Auto_Terri.docx");




        document = new XWPFDocument(new FileInputStream(docsPath.get(pdfSelectedIndex)));
        //  System.out.println(extractor.getText());
        //document.close();

    }
    public  void ReplaceText( ) throws IOException {
        String responsable;
      //  EmployeData = CreateForm.getDataList();
        String fullname = (CreateForm.getDataList().get("nom") + " " + CreateForm.getDataList().get("prenom")).toUpperCase() ;
        String grade = CreateForm.getDataList().get("grade").toUpperCase();
        String cnie = CreateForm.getDataList().get("cnie").toUpperCase();
        String ppr = CreateForm.getDataList().get("numsomme");
        String budget = CreateForm.getDataList().get("budget").toUpperCase();
        String service = CreateForm.getDataList().get("service").toUpperCase();
        if(budget.equals("BUDGET GENERAL") ){
             responsable = responsables[0];
        }else{
            responsable = responsables[1];
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy"); //to insert year in documents if needed

        for (XWPFParagraph p:this.document.getParagraphs()) {
            List<XWPFRun> runList = p.getRuns();
            if (runList != null) {
                for (XWPFRun run:runList) {
                    String text = run.getText(0);
                    if(text != null && text.contains("0")){
                        text = text.replace("0",responsable );
                        run.setText(text, 0);
                    }else if(text != null && text.contains("%") && budget.equals("Budget Provincial".toUpperCase()) ){
                        text = text.replace("%","S/C DE LA VOIE HIERARCHIQUE" );
                        run.setText(text, 0);


                    }else if(text != null && text.contains("%") && budget.equals("Budget General".toUpperCase()) ){
                        text = text.replace("%","" );
                        run.setText(text, 0);
                        
                    }else if (text != null && text.contains("1")) {
                        text = text.replace("1", fullname);
                        run.setText(text, 0);
                    }else if (text != null && text.contains("2")){
                        text = text.replace("2", grade);
                        run.setText(text, 0);
                    }else if(text != null && text.contains("3")){
                        text = text.replace("3", cnie);
                        run.setText(text, 0);
                    }else if(text != null && text.contains("4")){
                        text = text.replace("4", ppr);
                        run.setText(text, 0);
                    }else if(text != null && text.contains("5")){
                        text = text.replace("5", budget);
                        run.setText(text, 0);
                    }else if (text != null && text.contains("6")){
                        text = text.replace("6", service);
                        run.setText(text, 0);
                    }else if(text != null && text.contains("&")){
                        text = text.replace("&", dtf.format(LocalDate.now()) );
                        run.setText(text, 0);
                    }
                }//end  runlist if
            }

        }//end paragraph loop
        FileOutputStream output = new FileOutputStream("output.docx");
       document.write(output);
        output.close();
        document.close();
       File file = new File("output.docx");
       Desktop desktop = Desktop.getDesktop();
       desktop.open(file);


    }//end replace text methode

}