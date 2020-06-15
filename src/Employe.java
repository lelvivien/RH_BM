public class Employe {
    private String Nom;
    private String Prenom;
    private String DateNaissance;
    private String Sex;
    private String DateRecrutement;
    private int Grade;
    private String DateGrade;
    private int Echellon;
    private String DateEchellon;
    private int Budget;
    private String NumSomme;
    private int Service;
    private int Diplome;
    private int Specialite;
    private int IdcategorieGrade;



    public int getIdcategorieGrade() {
        return IdcategorieGrade;
    }

    public void setIdcategorieGrade(int idcategorieGrade) {
     dataValidation(idcategorieGrade,"idcategoriegrade");
        IdcategorieGrade = idcategorieGrade;
    }

    public Employe() {

    }

    public String getCnie() {

        return cnie;
    }

    public void setCnie(String cnie){
            dataValidation(cnie, "cnie");
            this.cnie = cnie.trim();


    }

    private String cnie;
    private int CongeRestant;
    private int[] DemandesConge;

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
      dataValidation(nom,"nom");
        Nom = nom.trim();
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        dataValidation(prenom, "prenom");
        Prenom = prenom.trim();
    }

    public String getDateNaissance() {
        return DateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        dataValidation(dateNaissance, "date Naissance");
        DateNaissance = dateNaissance;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        dataValidation(sex, "sex");
        Sex = sex;
    }

    public String getDateRecrutement() {
        return DateRecrutement;
    }

    public void setDateRecrutement(String dateRecrutement) {
        dataValidation(dateRecrutement, "date recrutement");
        DateRecrutement = dateRecrutement;
    }

    public int getIdGrade() {
        return Grade;
    }

    public void setIdGrade(int grade) {
        dataValidation(grade, "grade");
        Grade = grade;
    }

    public String getDateGrade() {
        return DateGrade;
    }

    public void setDateGrade(String dateGrade) {
      dataValidation(dateGrade,"date grade");
        DateGrade = dateGrade;
    }

    public int getEchellon() {
        return Echellon;
    }

    public void setEchellon(int echellon) {
        dataValidation(echellon, "echellon");
        Echellon = echellon;
    }

    public String getDateEchellon() {
        return DateEchellon;
    }

    public void setDateEchellon(String dateEchellon) {
        dataValidation(dateEchellon,"date echellon");
        DateEchellon = dateEchellon;
    }

    public int getBudget() {
        return Budget;
    }

    public void setBudget(int budget) {
        dataValidation(budget, "Budget");
        Budget = budget;
    }

    public String getNumSomme() {

        return NumSomme;
    }

    public void setNumSomme(String numSomme) {
        dataValidation(numSomme, "Numero de somme");
        NumSomme = numSomme;
    }

    public int getService() {
        return Service;
    }

    public void setService(int service) {
        dataValidation(service, "service");
        Service = service;
    }

    public int getDiplome() {
        return Diplome;
    }

    public void setDiplome(int diplome) {
        dataValidation(diplome, "diplome");
        Diplome = diplome;
    }

    public int getSpecialite() {
        return Specialite;
    }


    public void setSpecialite(int specialite) {
        dataValidation(specialite, "specialite");
        Specialite = specialite;
    }


    private  void dataValidation(Object variable, String variableName) {


        if( variable  instanceof Integer  ) {
            int var2 = (Integer)variable;
            int result;
            result = Integer.compare(var2,0);
            if (result == 0) {
                throw new IllegalArgumentException("la valeur de '" + variableName + "' ne doit pas être 'null'");
            }
        }
        else if (variable.equals("") || variable.equals(null)) {

            throw new  IllegalArgumentException("la valeur de '" + variableName + "' ne doit pas être 'null'");
            /*.showMessageDialog(null, "la valeur de '" + variable + "' ne doit pas être 'null'");*/

        }
    }

    @Override
    public String toString() {
        String Information = "Fonctionnaire: "+getNom()+" "+getPrenom()+" cnie: " + getCnie();
        return Information;
    }
}



