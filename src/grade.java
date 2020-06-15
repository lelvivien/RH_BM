public class grade extends CategorieGrade {
    public int getIdgrade() {
        return idgrade;
    }

    public void setIdgrade(int idgrade) {
        this.idgrade = idgrade;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    private int idgrade;
    private  String grade;
    private int idcategoriegrade;
    public  grade(){

    }

    public grade(int idgrade, String grade,int idcategoriegrade){
        this.idgrade = idgrade;
        this.grade = grade;
        this.idcategoriegrade = idcategoriegrade;
    }

}
