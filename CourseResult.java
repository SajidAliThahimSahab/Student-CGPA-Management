
package driver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CourseResult {
    
    
    private String CourseID;
    private String CourseTitle;
    private int CreditHours;
    private int Semester;
    private int Marks;
   
    public CourseResult(){
        this.CourseID = "";
        this.CourseTitle = "";
        this.CreditHours = 0;
        this.Semester = 0;
    }

    public CourseResult(String id, String title, int CH, int sem, int marks) {
        if(this.isValidCourseID(id) &&
           this.isValidCourseTitle(title) &&
           this.isValidSemester(sem) &&
           this.areValidCreditHours(CH) &&
           this.areValidMarks(marks)) {
            this.CourseID = id;
            this.CourseTitle = title;
            this.CreditHours = CH;
            this.Semester = sem;
            this.Marks = marks;
        }
    }
    
 
    public CourseResult(CourseResult CourseRsult) {
        this.CourseID = CourseRsult.CourseID;
        this.CourseTitle = CourseRsult.CourseTitle;
        this.CreditHours = CourseRsult.CreditHours;
        this.Semester = CourseRsult.Semester;
        this.Marks = CourseRsult.Marks;
    }

    public boolean setCourseID(String id) {
        try{
            if (this.isValidCourseID(id)) {
                this.CourseID = id;
                return true;
            } else {
                throw  new Exception();
            }
        } catch (Exception e) {
            System.out.println("Invalid Course ID Entered");
            return false;
        }
    }
    
    public boolean setCourseTitle(String cTitle) {
        try{
            if(this.isValidCourseTitle(cTitle)) {
                this.CourseTitle = cTitle;
                return true;
            } else {
                throw  new Exception();
            }
        } catch (Exception e ) {
            System.out.println("Invalid Course Title Entered");
            return false;
        }
    }

    public boolean setCreditHours(int cHours) {
        try{
            if(this.areValidCreditHours(cHours)) {
                this.CreditHours = cHours;
                return true;
            } else {
                throw new Exception();
            }
        } catch(Exception e) {
            System.out.println("Invalid Credit Hours Entered");
            return false;
        }
        
    }
 
    public boolean setMarks(int Marks) {
        try{
            if(this.areValidMarks(Marks)) {
                this.Marks = Marks;
                return true;
            } else {
                throw new Exception();
            }
        } catch(Exception e) {
            System.out.println("Invalid Marks Entered");
            return false;
        }
            
    }
   
    public boolean setSemester(int Sem) {
        try{
            if(this.isValidSemester(Sem)) {
                this.Semester = Sem;
                return true;
            } else {
                throw new Exception();
                
            }
        } catch(Exception e) {
            System.out.println("Invalid Semseter Entered it be 1-8");
            return false;
        }
    }
  
    public String getCourseID() {
        return this.CourseID;
    }
  
    public String getCourseTitle() {
        return this.CourseTitle;
    }
  
    public int getCreditHours() {
        return this.CreditHours;
    }

    public int getMarks() {
        return this.Marks;
    }
   
    public int getSemester() {
        return this.Semester;
    }
    
 
    public double getGradePoints() {
       
        String grade = this.getGrade();
     
        switch(grade)
        {
            case "A":
            {
                return 4.0;
              
            }
            case "A-":
            {
                return 3.7;
            }
            case "B+":
            {
                return 3.3;
            }
            case "B-":
            {
                return 3.0;
            }
            case "C+":
            {
                return 2.7;
            }
            case "C":
            {
                return 2.3;
            }
            case "D":
            {
                return 1.0;
            }
            case "F":
            {
                return 0;
            }
        }
        return 0.0;
    }
    

    public String getGrade() {
        if(this.Marks < 40) {
            return "F";
        } else if (this.Marks < 50) {
            return "D";
        } else if (this.Marks < 55) {
            return "C";
        } else if (this.Marks < 60) {
            return "C+";
        } else if (this.Marks < 65) {
            return "B-";
        } else if (this.Marks < 70) {
            return "B+";
        } else if (this.Marks <= 80) {
            return "A-";
        } else if (this.Marks > 80) {
            return "A";
        }
        return "";
    }
 
    @Override
    public String toString() {
        return "CourseResult{" + "CourseID=" + CourseID + ", CourseTitle=" + CourseTitle + 
                ", CreditHours=" + CreditHours + ", Semester=" + Semester + ", Marks=" + Marks + '}';
    }


    private boolean isValidCourseID(String cID) {
        if(cID.length() >= 2 && cID.length() <=8) {
            // if first two or three charactes are alphabets 
            if(cID.substring(0, 2).chars().allMatch(Character::isLetter) ||
                    cID.substring(0, 3).chars().allMatch(Character::isLetter)) {
                if(cID.length() > 3){
                    if(cID.substring(2,5).chars().allMatch(Character::isDigit)||
                            cID.substring(3,6).chars().allMatch(Character::isDigit)) {
                        return true;
                    }        
                }
                else if(cID.length() ==7 && Pattern.matches("a-zA-Z", "" + cID.charAt(6))) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    

    private boolean isValidCourseTitle(String cTitle) {
        if(cTitle.length() >= 7 && cTitle.length() < 35) {
            Pattern p = Pattern.compile("[^A-Za-z ]");
            Matcher m = p.matcher(cTitle);
            boolean b = m.find();
            if (b == true)
                return false;
            else
             return true;
        }
        return false;
    }

    private boolean areValidCreditHours(int CH) {
        if(CH >= 1 && CH <= 3) {
            return true;
        }
        return false;
    }
    
 
    
    private boolean isValidSemester( int sem) {
        if(sem >= 1 && sem <= 8){
            return true;
        }
        return false;
    }
    
 
    private boolean areValidMarks(int marks) {
        
        if(marks >= 0 && marks <= 100) {
            return true;
        }
        return false;
    }
    
}
