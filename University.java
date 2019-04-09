import java.util.ArrayList;
import java.util.Arrays;

public class University
{
    private String name;
    private String state;
    private String control;
    private int numStudents;
    private double maleToFemale;
    private double studentToFaculty;
    private int satVerbal;
    private int satMath;
    private int exepenses;
    private double finAid;
    private int applicants;
    private int admittancePercent;
    private int enrolledPercent;
    private int academicScale;
    private int socialScale;
    private int qualityOfLife;
    private ArrayList<String> academics = new ArrayList<>();

    public University(String name)
    {
        this.name = name;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public void setControl(String control)
    {
        this.control = control;
    }

    public void setNumStudents(int numStudents)
    {
        this.numStudents = numStudents;
    }

    public void setMaleToFemale(double maleToFemale)
    {
        this.maleToFemale = maleToFemale;
    }

    public void setStudentToFaculty(double studentToFaculty)
    {
        this.studentToFaculty = studentToFaculty;
    }

    public void setSatVerbal(int satVerbal)
    {
        this.satVerbal = satVerbal;
    }

    public void setSatMath(int satMath)
    {
        this.satMath = satMath;
    }

    public void setExepenses(int exepenses)
    {
        this.exepenses = exepenses;
    }

    public void setFinAid(double finAid)
    {
        this.finAid = finAid;
    }

    public void setApplicants(int applicants)
    {
        this.applicants = applicants;
    }

    public void setAdmittancePercent(int admittancePercent)
    {
        this.admittancePercent = admittancePercent;
    }

    public void setEnrolledPercent(int enrolledPercent)
    {
        this.enrolledPercent = enrolledPercent;
    }

    public void setAcademicScale(int academicScale)
    {
        this.academicScale = academicScale;
    }

    public void setSocialScale(int socialScale)
    {
        this.socialScale = socialScale;
    }

    public void setQualityOfLife(int qualityOfLife)
    {
        this.qualityOfLife = qualityOfLife;
    }

    public void addAcademic(String academic)
    {
        this.academics.add(academic);
    }

    public String getName()
    {
        return name;
    }

    public String getState()
    {
        return state;
    }

    public String getControl()
    {
        return control;
    }

    public int getNumStudents()
    {
        return numStudents;
    }

    public double getMaleToFemale()
    {
        return maleToFemale;
    }

    public double getStudentToFaculty()
    {
        return studentToFaculty;
    }

    public int getSatVerbal()
    {
        return satVerbal;
    }

    public int getSatMath()
    {
        return satMath;
    }

    public int getExepenses()
    {
        return exepenses;
    }

    public double getFinAid()
    {
        return finAid;
    }

    public int getApplicants()
    {
        return applicants;
    }

    public int getAdmittancePercent()
    {
        return admittancePercent;
    }

    public int getEnrolledPercent()
    {
        return enrolledPercent;
    }

    public int getAcademicScale()
    {
        return academicScale;
    }

    public int getSocialScale()
    {
        return socialScale;
    }

    public int getQualityOfLife()
    {
        return qualityOfLife;
    }

    public ArrayList<String> getAcademics()
    {
        return academics;
    }

    @Override
    public String toString()
    {
        return "University{" +
                "name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", control='" + control + '\'' +
                ", numStudents=" + numStudents +
                ", maleToFemale=" + maleToFemale +
                ", studentToFaculty=" + studentToFaculty +
                ", satVerbal=" + satVerbal +
                ", satMath=" + satMath +
                ", exepenses=" + exepenses +
                ", finAid=" + finAid +
                ", applicants=" + applicants +
                ", admittancePercent=" + admittancePercent +
                ", enrolledPercent=" + enrolledPercent +
                ", academicScale=" + academicScale +
                ", socialScale=" + socialScale +
                ", qualityOfLife=" + qualityOfLife +
                ", academics=" + Arrays.toString(academics.toArray()) +
                '}';
    }
}
