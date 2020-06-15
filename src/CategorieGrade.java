public class CategorieGrade {


    public CategorieGrade() {
    }

    public int getIdcategoriegrade() {
        return idcategoriegrade;
    }

    public void setIdcategoriegrade(int idcategoriegrade) {
        this.idcategoriegrade = idcategoriegrade;
    }

    public String getCategoriegrade() {
        return categoriegrade;
    }

    public void setCategoriegrade(String categoriegrade) {
        this.categoriegrade = categoriegrade;
    }

    private int idcategoriegrade;
    private String categoriegrade;

    public CategorieGrade(int idcategoriegrade, String categoriegrade){
        this.idcategoriegrade = idcategoriegrade;
        this.categoriegrade = categoriegrade;

    }
}
