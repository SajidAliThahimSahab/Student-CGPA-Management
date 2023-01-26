
package driver;
import java.awt.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.table.*;

public class Grading { 
  
    private String StudentName;
    private String RegistrationNumber;
    private String Degree;
    private ArrayList<CourseResult> Courses;
  
    public void setCourses(ArrayList<CourseResult> Courses) {
        this.Courses = Courses;
    }
    

    public Grading() {
        this.StudentName = "";
        this.RegistrationNumber = "";
        this.Degree = "";
        this.Courses = new ArrayList<>();
    }
 
    public boolean setStudentName(String sName) {
        try {
            if (this.isValidName(sName)) {
                this.StudentName = sName;
                return true;
            } else {
                throw new Exception();
            }
            
        } catch(Exception e) {
            System.out.println("Invalid Student Name Entered , it can't contain numbers or Special characters");
            return false;
        }
    }
 
    public boolean setRegNumber(String RegNo) {
        try
        {
            if(this.isValidRegNo(RegNo))
            {
                this.RegistrationNumber = RegNo.toUpperCase();
                return true;
            } else {
                throw new Exception();
            }
        } catch(Exception e) {
            System.out.println("Invalid Registration Number");
            return false;
        }
        
    }

    public boolean setDegree(String degree) {
        try{
            if(degree.toUpperCase().equals("MS") ||
               degree.toUpperCase().equals("BS") ||
               degree.toUpperCase().equals("BE")) {
            this.Degree = degree.toUpperCase();
            return true;
            } else {
                throw new Exception();

            }
        } catch(Exception e ) {
            System.out.println("Degree Name is Invalid , it must be MS , BS or BE");
            return false;
        }
    }
  
    public boolean setCourse(CourseResult course) {
      
        this.Courses.add(course);
        return true;
    }
    
 
    public String getStudentName(){
        return this.StudentName;
    }
  
    public String getRegNumber(){
        return this.RegistrationNumber;
    }
 
    public String getDegree(){
        return this.Degree;
    }
   
    public ArrayList<CourseResult> getCourses(){
        return this.Courses;
    }
 
    public CourseResult getCoursebyID(String crsID){
         CourseResult crs;
         for(int i = 0; i < Courses.size(); i++){
            if(Courses.get(i).getCourseID().equals(crsID)){
                crs = Courses.get(i);
                return crs;
            }
         }
         return null;
     }
  
    public boolean removeCoursebyID(String crsID){
        for(int i = 0; i < Courses.size(); i++){
            if(Courses.get(i).getCourseID().equals(crsID)){
                Courses.remove(Courses.get(i));
                return true;
            }
        }
        return false;
    }
  
    private boolean isValidName(String name){
        if(name.length()>0){
        Pattern p = Pattern.compile("[^A-Za-z ]");
        Matcher m = p.matcher(name);
        boolean b = m.find();
        if (b == true)
            return false;
        else
         return true;
        }
        return false;
    }
    
 
    private boolean isValidRegNo(String RegNo){
        if(RegNo.length() >= 8){
            String year = "";// = ""+ RegNo.charAt(0) + RegNo.charAt(1) + RegNo.charAt(2) + RegNo.charAt(3);
            String dept = "";
            String regN = "";
            boolean dashDetect = false;

            for(int i =0; i< RegNo.length(); i++){
                if(RegNo.charAt(i) == '-')
                {
                    while(RegNo.charAt(i+1)!= '-' ){
                        dept += RegNo.charAt(i+1);
                        i++;
                    }
                    i += 2;
                    dashDetect = true;
                }
                if(dashDetect == false){
                    year += RegNo.charAt(i);
                }
                if(dashDetect == true){
                    regN += RegNo.charAt(i);
                }
            }
         if(Pattern.matches("\\d+", year) && 
            Pattern.matches("\\d+", regN) && 
            isValidName(dept) && 
            year.length()==4 && 
            dept.length() >1 && 
            regN.length()>0)
         {
                    return true;
         }
        }
        
        return false;
    }
    
   
    public int getSemesters(){
        int numberOfSemesters = 0;
       
        HashSet semesters = new HashSet();
        for (Iterator<CourseResult> i = Courses.iterator(); i.hasNext();) {
            semesters.add(i.next().getSemester());
        }
        
        numberOfSemesters = semesters.size();
        return numberOfSemesters;
    }
  
    public double getSemesterGPA(int semester){
       
        DecimalFormat abc = new DecimalFormat("#.####");
        return Double.valueOf(abc.format(getSemesterGradePoints(semester)/getSemesterCreditHours(semester)));
        
    }
    
    public double getCGPA(){
        double courseGradePoints = 0;
        double totalCreditHours = this.getTotalCreditHours();
        HashSet semesters = new HashSet();
        CourseResult course;
        for (Iterator<CourseResult> i = Courses.iterator(); i.hasNext();) {
            course = i.next();
            courseGradePoints += (course.getGradePoints()* course.getCreditHours());
        }
       
        DecimalFormat abc = new DecimalFormat("#.####");
        return Double.valueOf(abc.format(courseGradePoints/totalCreditHours));
       
    }
 
    private double getSemesterGradePoints(int semester){
        double semesterGradePoints = 0;    
        HashSet semesters = new HashSet();
        for (Iterator<CourseResult> i = Courses.iterator(); i.hasNext();) {
            CourseResult course = i.next();
            if(course.getSemester() == semester){
                semesterGradePoints += (course.getGradePoints() * course.getCreditHours());
            }
        }
        return semesterGradePoints;
    }
 
    public int getSemesterCreditHours(int semester){
        if(this.Courses.size() > 0){
            int CH = 0;
            HashSet semesters = new HashSet();
            for (Iterator<CourseResult> i = Courses.iterator(); i.hasNext();) {
                CourseResult course = i.next();
                if(course.getSemester() == semester){
                    CH += course.getCreditHours();
                }
            }
            return CH;
        }
        return 0;
    }
  
    public int getTotalCreditHours(){
        if(this.Courses.size() > 0){
            int CH = 0;
            HashSet semesters = new HashSet();
            for (Iterator < CourseResult> i = Courses.iterator(); i.hasNext();) {
                CourseResult course = i.next();
                    CH += course.getCreditHours();
                }
            return CH;
        }
        return 0;
    }
  
    public String getDescipline(){
        String[] arr = this.RegistrationNumber.split("-");
        if( arr.length ==  3) {
            return arr[1];
        }
        return "";
    }
    
    public int getSession(){
        String[] arr = this.RegistrationNumber.split("-");
        if( arr.length ==  3) {
            return Integer.parseInt(arr[0]);
        }
        return 0;
    }
    
    @Override
    public String toString() {
        Object[][] rows = new Object[Courses.size()][5];
        for(int i = 0; i < Courses.size(); i++){
                    rows[i][0] = Courses.get(i).getCourseID();
                    rows[i][1] = Courses.get(i).getCourseTitle();
                    rows[i][2] = Courses.get(i).getCreditHours();
                    rows[i][3] = Courses.get(i).getMarks();
                    rows[i][4] = Courses.get(i).getGrade();
        }
    Object[] Col_Names = {"ID","Name","CH","Marks", "Grade"};
    JTable table = new JTable(rows, Col_Names);
    table.setShowGrid(false);
    final TableColumnModel columnModel = table.getColumnModel();
    for (int column = 0; column < table.getColumnCount(); column++) {
        int width = 15; // Min width
        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            final Component comp = table.prepareRenderer(renderer, row, column);
            width = Math.max(comp.getPreferredSize().width +1 , width);
        }
        if(width > 300)
            width=300;
        columnModel.getColumn(column).setPreferredWidth(width);
    }
    JOptionPane.showMessageDialog(null, new JScrollPane(table),"Courses Details",0);
    return "";
}
   
    public void showDMC(){
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        ArrayList<String> semesters = new ArrayList<String>();
        // label for Name
        JLabel name = new JLabel("Name: " + this.getStudentName() + 
                                            "    Degree: " + this.getDegree() +
                                            " "+ this.getDescipline()+"\n");
        name.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        JLabel regNo = new JLabel("Registration Number: " + this.getRegNumber());
        regNo.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        JLabel session = new JLabel("Session: " + this.getSession()+"\n\n");
        session.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        panel.add(name);
        panel.add(regNo);
        panel.add(session);
        CourseResult temp;
        
       
        for(int i=0;i < Courses.size(); i++){
            temp = Courses.get(i);
            if(!(semesters.contains(""+temp.getSemester()))){
                semesters.add(""+temp.getSemester());
            }
        }
       
        Collections.sort(semesters);
        
      
        Object[] column_names = {"ID", "Name", "CH", "Marks", "Grade"};
        int row_index = 0;
        for(int i = 0; i < semesters.size(); i++){
            panel.add(new JLabel(" "));
            JLabel lbl = new JLabel("Semester " + semesters.get(i)+": ");
            lbl.setAlignmentX(JLabel.LEFT_ALIGNMENT);
            panel.add(lbl);
          Object[][] rows = new Object[this.getNumberOfSemsterSubjects(Integer.parseInt(semesters.get(i)))][5];
          for(int j=0; j<Courses.size(); j++){
              CourseResult temp1 =Courses.get(j);
              if(temp1.getSemester() == Integer.parseInt(semesters.get(i))){
                  rows[row_index][0] = temp1.getCourseID();
                  rows[row_index][1] = temp1.getCourseTitle();
                  rows[row_index][2] = temp1.getCreditHours();
                  rows[row_index][3] = temp1.getMarks();
                  rows[row_index][4] = temp1.getGrade();
                  row_index ++;
              }
          }
          row_index = 0;
           JTable table = new JTable(rows, column_names);
            // Making table Appearance Better
            table.setShowGrid(false);
            //<editor-fold defaultstate="collapsed" desc="Adjust the Widths of the Columns">
            final TableColumnModel columnModel = table.getColumnModel();
            for (int column = 0; column < table.getColumnCount(); column++) {
                int width = 15; // Min width
                for (int row = 0; row < table.getRowCount(); row++) {
                    TableCellRenderer renderer = table.getCellRenderer(row, column);
                    final Component comp = table.prepareRenderer(renderer, row, column);
                    width = Math.max(comp.getPreferredSize().width +1 , width);
                }
                if(width > 300)
                    width=300;
                columnModel.getColumn(column).setPreferredWidth(width);
            }
        
        JScrollPane scrlPane = new JScrollPane(table);
        scrlPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        panel.add(scrlPane);
        panel.add(new JLabel(" "));
        JLabel semGPA = new JLabel("SGPA: "+getSemesterGPA(Integer.parseInt(semesters.get(i))));
        panel.add(semGPA);
       
        }
        panel.add(new JLabel(" "));
        if(Courses.size()>0){
            JLabel cgpa = new JLabel("CGPA: "+this.getCGPA());
            
            panel.add(cgpa);
        }
        
       
        JFrame dmc = new JFrame();
        dmc.setTitle("Student DMC");
        dmc.setSize(800,600);
        dmc.add(panel);
       
        dmc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dmc.setVisible(true);
        dmc.show();
    }
    
  
    private int getNumberOfSemsterSubjects(int Semester){
        int semNo = 0;
        for(int i = 0; i < Courses.size(); i++){
            if(Courses.get(i).getSemester() == Semester){
                semNo += 1;
            }
        }
        return semNo;
    }
}
