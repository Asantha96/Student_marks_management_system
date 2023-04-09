import java.util.*;
import java.io.*;
class Student{
    private String id;
    private String name;
    private int dbmsMarks;
    private int pfMarks;

    Student(String id, String name){
        this.id = id;
        this.name = name;
        this.dbmsMarks = -1;
        this.pfMarks = -1;
    }

    Student(String id,String name,int dbmsMarks, int pfMarks){
        //Student(id,name);
        //this.id = id;
        //this.name = name;
        this(id,name);
        this.dbmsMarks = dbmsMarks;
        this.pfMarks = pfMarks;
    }

    public String getName(){
        return name;
    }
    public void setStdName(String name){
        this.name = name;
    }

    public String getId(){
        return id;
    }

    public int getDbmsMarks(){
        return dbmsMarks;
    }
    public void setDbmsMarks(int dbmsMark){
        this.dbmsMarks = dbmsMark;
    }

    public int getPfMarks(){
        return pfMarks;
    }
    public void setPfMarks(int pfMark){
        this.pfMarks = pfMark;
    }
}
class Demo {
///////////////////////////// Main /////////////////////////////////////////
    public static void main(String[] args) throws IOException {
        do {
            clearConsole();
            String option1 = "[1] Add New Student", option2 = "[2] Add New Student With Marks",
                    option3 = "[3] Add Marks",
                    option4 = "[4] Update Student Details", option5 = "[5] Update Marks",
                    option6 = "[6] Delete Student",
                    option7 = "[7] Print Student Details", option8 = "[8] Print Student Ranks",
                    option9 = "[9] Best in Programming Fundamentals",
                    option10 = "[10] Best In DataBase Management System", option11 = "[11] Exit";
            Scanner input = new Scanner(System.in);

            System.out.printf("%-18s%-60s", "", "WELCOME TO GDSE MARKS MANAGEMENT SYSTEM");
            System.out.println();
            System.out.println();
            System.out.printf("%-50s%-50s\n%-50s%-50s\n%-50s%-50s\n%-50s%-50s\n%-50s%-50s\n%-50s", option1, option2, option3,
                    option4, option5, option6, option7, option8, option9, option10, option11);
            System.out.println();
            System.out.print("\nEnter an option: ");
            int mainOption = input.nextInt();
            input.nextLine();

            switch (mainOption) {
                case 1:
                    addNewStudent();
                    break;
                case 2:
                    addNewStudentWithMarks();
                    break;
                case 3:
                    addMarks();
                    break;
                case 4:
                    updateStudentDetails();
                    break;
                case 5:
                    updateMarks();
                    break;
                case 6:
                    deleteStudent();
                    break;
                case 7:
                    printStudentDetails();
                    break;
                case 8:
                    printStudentRanks();
                    break;
                case 9:
                    bestInProgrammingFundamentals();
                    break;
                case 10:
                    bestInDatabaseManagment();
                    break;
                case 11:
                    System.exit(0);

            }
            //clearConsole();
            System.out.print("Do you want to exit the program(Y/N):");
            String exitOption = input.nextLine();
            if (exitOption.equalsIgnoreCase("y")) {
                break;
            }

        } while (true);

    }
    
    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            e.printStackTrace();
            // Handle any exceptions.
        }
    }
    
    public static Scanner input = new Scanner(System.in);
    public static Student[] stdObjArray = new Student[0];
    public static Student[] rankObjArray = new Student[0];
    public static int countForStdNoMarks = 0;

    public static boolean isIdAvailable(String id){
        for (Student student : stdObjArray) {
            if (id.equalsIgnoreCase(student.getId())) {
                return true;
            }
        }
        return false;
    }

    public static void extendStdObjArray(){
        Student[] tempStdObjArray = new Student[stdObjArray.length+1];
        for (int i = 0; i < stdObjArray.length; i++) {
            tempStdObjArray[i] = stdObjArray[i];
        }
        stdObjArray = tempStdObjArray;
    }

    public static void shrinkArray(int index){
    Student[] tempStdObjArray = new Student[stdObjArray.length-1];
    for (int i = 0; i < index; i++) {
        tempStdObjArray[i] = stdObjArray[i];
    }
    for (int i = index; i < tempStdObjArray.length; i++) {
        tempStdObjArray[i] = stdObjArray[i+1];
    }
    stdObjArray = tempStdObjArray;
}

    public static boolean isValidityMark(int mark){
        if (mark < 0 || mark > 100) {
            return false;
        }
        return true;
    }

    public static int searchStudent(String id){
        int index = -1;
        for (int i = 0; i < stdObjArray.length; i++) {
            if (id.equalsIgnoreCase(stdObjArray[i].getId())) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static void prepareRankArray(){
        for (int i = 0; i < stdObjArray.length; i++) {
            if (stdObjArray[i].getDbmsMarks() != -1) {
                Student tempStudent = stdObjArray[i];
                Student[] tempStdObjArray = new Student[rankObjArray.length+1];
                for (int j = 0; j < rankObjArray.length; j++) {
                    tempStdObjArray[j] = rankObjArray[j];
                }
                tempStdObjArray[tempStdObjArray.length-1] = tempStudent;
                rankObjArray = tempStdObjArray;
            }
        }
        for (int i = rankObjArray.length-1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if ((rankObjArray[j].getDbmsMarks() + rankObjArray[j].getPfMarks()) < (rankObjArray[j+1].getDbmsMarks() + rankObjArray[j+1].getPfMarks())) {
                    Student tempStd = rankObjArray[j];
                    rankObjArray[j] = rankObjArray[j+1];
                    rankObjArray[j+1] = tempStd;
                }
            }
        }
    }

    public static void preparePfRankArray(){

        for (int i = rankObjArray.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (rankObjArray[j].getPfMarks() < rankObjArray[j + 1].getPfMarks()) {
                    Student tempStd = rankObjArray[j];
                    rankObjArray[j] = rankObjArray[j + 1];
                    rankObjArray[j + 1] = tempStd;
                }
            }
        }
    }

    public static void prepareDbmsRankArray(){
        for (int i = rankObjArray.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (rankObjArray[j].getDbmsMarks() < rankObjArray[j + 1].getDbmsMarks()) {
                    Student tempStd = rankObjArray[j];
                    rankObjArray[j] = rankObjArray[j + 1];
                    rankObjArray[j + 1] = tempStd;
                }
            }
        }
    }

    public static void addNewStudent(){
        clearConsole();
        System.out.printf("%-50s%-50s", "", "Add New Student");
        System.out.println();
        String subOption;
        do {
            boolean status;
            do {
                System.out.println();
                System.out.print("Enter student ID: ");
                String tempId = input.nextLine();
                status = isIdAvailable(tempId);
               if (!status) {
                System.out.println();
                System.out.print("Enter student name: ");
                String tempName = input.nextLine();
                Student newStudent = new Student(tempId, tempName);
                extendStdObjArray();
                stdObjArray[stdObjArray.length-1] = newStudent;
                countForStdNoMarks++;
               }
               else{
                System.out.println("Student ID already exist!");
               }
                
            } while (status);

            System.out.println();
            System.out.print("Student has been added successfully. DO you want to add a new student(Y/N): ");
            subOption = input.nextLine();
            System.out.println();

        } while (subOption.equalsIgnoreCase("y"));
    }

    public static void addNewStudentWithMarks() {
        
        String subOption;
        do {
            clearConsole();
            System.out.printf("%-50s%-50s", "", "Add New Student with Marks ");
            System.out.println();
            boolean status;
            do {
                System.out.println();
                System.out.print("Enter student ID: ");
                String tempId = input.nextLine();
                status = isIdAvailable(tempId);
                if (!status) {
                    System.out.println();
                    System.out.print("Enter student name: ");
                    String tempName = input.nextLine();
                    boolean markValidity;
                    int tempDbmsMark = -1;
                    do {
                        System.out.println();
                        System.out.print("Enter DBMS Marks: ");
                        int dbmsMark = input.nextInt();
                        input.nextLine();
                        markValidity = isValidityMark(dbmsMark);
                        if (markValidity) {
                            tempDbmsMark = dbmsMark;
                        }
                        else{
                            System.out.println("Invalid Mark!");
                        }
                    } while (!markValidity);

                    int tempPfMark = -1;
                    do {
                        System.out.println();
                        System.out.print("Enter Programming Fundamental Marks: ");
                        int pfMark = input.nextInt();
                        input.nextLine();
                        markValidity = isValidityMark(pfMark);
                        if (markValidity) {
                            tempPfMark = pfMark;
                        } else {
                            System.out.println("Invalid Mark!");
                        }
                    } while (!markValidity);

                    Student newStudent = new Student(tempId, tempName, tempDbmsMark, tempPfMark);
                    extendStdObjArray();
                    stdObjArray[stdObjArray.length - 1] = newStudent;
                } else {
                    System.out.println("Student ID already exist!");
                }

            } while (status);

            System.out.println();
            System.out.print("Student has been added successfully. DO you want to add a new student(Y/N): ");
            subOption = input.nextLine();

        } while (subOption.equalsIgnoreCase("y"));
    }
  
    public static void addMarks(){
        clearConsole();
        System.out.printf("%-50s%-50s", "", "Add Marks");
        System.out.println();
        if (countForStdNoMarks >= 1) {
            String subOption = "n";
            do {
                clearConsole();
                System.out.printf("%-50s%-50s", "", "Add Marks");
                System.out.println();

                boolean status;
                String choice = "n";
                do {
                    System.out.print("Enter student ID: ");
                    String tempId = input.nextLine();
                    status = isIdAvailable(tempId);
                    if (status) {
                        int index = searchStudent(tempId);
                        System.out.println();
                        System.out.print("Student name: "+stdObjArray[index].getName());

                        int tempDbmsMark = -1;
                        boolean markValidity;
                        do {
                            System.out.println();
                            System.out.print("Enter DBMS Marks: ");
                            int dbmsMark = input.nextInt();
                            input.nextLine();
                            markValidity = isValidityMark(dbmsMark);
                            if (markValidity) {
                                tempDbmsMark = dbmsMark;
                            } else {
                                System.out.println("Invalid Mark!");
                            }
                        } while (!markValidity);

                        int tempPfMark = -1;
                        do {
                            System.out.println();
                            System.out.print("Enter Programming Fundamental Marks: ");
                            int pfMark = input.nextInt();
                            input.nextLine();
                            markValidity = isValidityMark(pfMark);
                            if (markValidity) {
                                tempPfMark = pfMark;
                            } else {
                                System.out.println("Invalid Mark!");
                            }
                        } while (!markValidity);
                        stdObjArray[index].setDbmsMarks(tempDbmsMark);
                        stdObjArray[index].setPfMarks(tempPfMark);
                        countForStdNoMarks--;
                        System.out.println();
                        System.out.print("Marks have been added. Do you want update marks for another student(y/n): ");
                        subOption = input.nextLine(); 
                    }
                    else{
                        System.out.println();
                        System.out.print("Invalid student ID!. Do you want to search again(y/n): ");
                        choice = input.nextLine();
                    }
                    
                } while (choice.equalsIgnoreCase("y") && (countForStdNoMarks >= 1));

            } while (subOption.equalsIgnoreCase("y") && (countForStdNoMarks >= 1));
        }
        else{
            System.out.println("No record to update! ");
        }
    }

    public static void updateStudentDetails(){
        
        String subOption = "n";
        do {
            clearConsole();
            System.out.printf("%-50s%-50s", "", "Update Student Details");
            System.out.println();
            boolean status;
            String choice = "n";
            do {
                System.out.print("Enter student ID: ");
                String tempId = input.nextLine();
                status = isIdAvailable(tempId);
                if (status) {
                    int index = searchStudent(tempId);
                    System.out.print("Student name: "+stdObjArray[index].getName());
                    System.out.println();
                    System.out.print("Enter new Student Name: ");
                    String tempName = input.nextLine();
                    stdObjArray[index].setStdName(tempName);
                    System.out.println();
                    System.out.print( "Student details have been updated successfully. Do you want to update details for another student(y/n): ");
                    subOption = input.nextLine();
                }
                else{
                    System.out.print("Invalid student ID. Do you want to search ID again(y/n): ");
                    choice  = input.nextLine();
                }
                
            } while (choice.equalsIgnoreCase("y"));
           
        } while (subOption.equalsIgnoreCase("y"));
    }

    public static void updateMarks(){
        String subOption = "n";
        do {
            clearConsole();
            System.out.printf("%-50s%-50s", "", "Update Marks");
            System.out.println();
            String choice = "n";
            do {
                System.out.print("Enter Student ID: ");
                String tempId = input.nextLine();
                boolean status = isIdAvailable(tempId);
                if (status) {
                    int index = searchStudent(tempId);
                    if (stdObjArray[index].getDbmsMarks() == -1) {
                        System.out.print("Student name: "+stdObjArray[index].getName()+"\n");
                        System.out.print("This students' marks yet to be added.");
                        System.out.print("Invalid ID. Do you want to search again(y/n): ");
                        choice = input.nextLine();
                    }
                    else{
                        System.out.print("Student name: " + stdObjArray[index].getName() + "\n");
                        System.out.print("DBMS marks: "+stdObjArray[index].getDbmsMarks()+"\n");
                        System.out.print("PF marks: "+stdObjArray[index].getPfMarks()+"\n");
                        int tempDbmsMark = stdObjArray[index].getDbmsMarks();
                        boolean markValidity;
                        do {
                            System.out.println();
                            System.out.print("Enter new DBMS Marks: ");
                            int dbmsMark = input.nextInt();
                            input.nextLine();
                            markValidity = isValidityMark(dbmsMark);
                            if (markValidity) {
                                tempDbmsMark = dbmsMark;
                            } else {
                                System.out.println("Invalid Mark!");
                            }
                        } while (!markValidity);
                        int tempPfMark = stdObjArray[index].getPfMarks();
                        do {
                            System.out.println();
                            System.out.print("Enter new PF Marks: ");
                            int pfMark = input.nextInt();
                            input.nextLine();
                            markValidity = isValidityMark(pfMark);
                            if (markValidity) {
                                tempPfMark = pfMark;
                            } else {
                                System.out.println("Invalid Mark!");
                            }
                        } while (!markValidity);
                        stdObjArray[index].setDbmsMarks(tempDbmsMark);
                        stdObjArray[index].setPfMarks(tempPfMark);
                        System.out.print("Marks have been updated successfully. Do you want to update marks for another student(y/n): ");
                        subOption = input.nextLine();                 
                    }
                }
                else{
                    System.out.print("Invalid ID. Do you want to search again(y/n): ");
                    choice = input.nextLine();
                }
            } while (choice.equalsIgnoreCase("y"));

        } while (subOption.equalsIgnoreCase("y"));
    }

    public static void deleteStudent(){
        if (stdObjArray.length >= 1) {
            String subOption = "n";
            do {
                clearConsole();
                System.out.printf("%-50s%-50s", "", "Delete Student");
                System.out.println();
                String choice = "n";
                do {
                    System.out.print("Enter student ID: ");
                    String tempId = input.nextLine();
                    boolean status = isIdAvailable(tempId);
                    if (status) {
                        int index = searchStudent(tempId);
                        if ((stdObjArray[index].getDbmsMarks()) == 0) {
                            countForStdNoMarks--;
                        }
                        shrinkArray(index);
                        System.out.print(
                                "Student has been deleted successfully. Do you want to delete another student(y/n): ");
                        subOption = input.nextLine();

                    } else {
                        System.out.print("Invalid ID. Do you want search again(y/n): ");
                        choice = input.nextLine();
                    }

                } while (choice.equalsIgnoreCase("y"));

            } while (subOption.equalsIgnoreCase("y") && (stdObjArray.length != 0));
        }
        else{
            System.out.println("No record to delete! ");
        }
        
        
    }
    
    public static void printStudentDetails(){
        prepareRankArray();
        String subOption = "n";
        do {
            String choice = "n";
            do {
                boolean status;
                clearConsole();
                System.out.printf("%-50s%-50s", "", "Print Student Details");
                System.out.println();
                System.out.print("Enter student ID: ");
                String tempId = input.nextLine();
                status = isIdAvailable(tempId);
                if(status){
                    int index = searchStudent(tempId);
                    if (stdObjArray[index].getDbmsMarks() == -1) {
                        System.out.print("Student name: "+stdObjArray[index].getName()+"\n"+"Marks yet to be added"+"\n");
                    }
                    else{
                        int rankIndex = -1;
                        L1:for (int i = 0; i < rankObjArray.length; i++) {
                            if (tempId.equalsIgnoreCase(rankObjArray[i].getId())) {
                                rankIndex = i;
                                break L1;
                            }
                        }
                        System.out.print("Student name: "+rankObjArray[rankIndex].getName()+"\n");
                        System.out.print("Programming fundamental Marks: "+rankObjArray[rankIndex].getPfMarks()+"\n");
                        System.out.print("Database management system Marks: "+rankObjArray[rankIndex].getDbmsMarks()+"\n");
                        System.out.print("Total marks: "+( rankObjArray[rankIndex].getDbmsMarks()+ rankObjArray[rankIndex].getPfMarks() ) +"\n");
                        System.out.print("Average marks: "+ (double) (rankObjArray[rankIndex].getDbmsMarks()
                                + rankObjArray[rankIndex].getPfMarks())/2 +"\n");
                        System.out.print("Rank: "+(rankIndex+1) +"\n");
                    }
                }
                else{
                    System.out.print("Invalid ID. Do you want to searcg again(y/n): ");
                    choice = input.nextLine();

                }
            } while (choice.equalsIgnoreCase("y"));

            System.out.print("Do you want to search another student details(y/n):? ");
            subOption = input.nextLine();
        } while (subOption.equalsIgnoreCase("y"));
        Student[] resetArray = new Student[0];
        rankObjArray = resetArray;
    }
   
    public static void printStudentRanks(){

        prepareRankArray();
        String subOption = "y";
        do {
            clearConsole();
            System.out.printf("%-50s%-50s", "", "Print Student Ranks");
            System.out.println();
            System.out.printf("%-10s%-10s%-15s%-20s%-20s", "Rank", "ID", "Name", "Total Marks", "Avg. Marks");
            System.out.println();
            for (int i = 0; i < rankObjArray.length; i++) {
                System.out.printf("%-10d%-10s%-15s%-20d%-20.2f", (i + 1), rankObjArray[i].getId(),rankObjArray[i].getName(),(rankObjArray[i].getDbmsMarks() + rankObjArray[i].getPfMarks()),(double)(rankObjArray[i].getDbmsMarks() + rankObjArray[i].getPfMarks())/2);
                System.out.println();
            }
            System.out.print("Do you want to go back to main menu(y/n)?: ");
            subOption = input.nextLine();
        } while (subOption.equalsIgnoreCase("n"));
        Student[] resetArray = new Student[0];
        rankObjArray = resetArray;

    }
   
    public static void bestInProgrammingFundamentals(){
        prepareRankArray();
        preparePfRankArray();
        String subOption = "y";
        do {
            clearConsole();
            System.out.printf("%-50s%-50s", "", "BEST IN PROGRAMMING FUNDAMENTALS");
            System.out.println();
            System.out.printf("%-10s%-20s%-15s%-15s", "ID", "Name", "PF Marks", "DBMS Marks");
            System.out.println();
            for (int i = 0; i < rankObjArray.length; i++) {
                System.out.printf("%-10s%-20s%-15d%-15d",rankObjArray[i].getId(),rankObjArray[i].getName(),rankObjArray[i].getPfMarks(),rankObjArray[i].getDbmsMarks());
                System.out.println();
            }
            System.out.print("Do you want to go back to main menu?(Y/N): ");
            subOption = input.nextLine();
        } while (subOption.equalsIgnoreCase("n"));
        Student[] resetArray = new Student[0];
        rankObjArray = resetArray;
    }
   
    public static void bestInDatabaseManagment(){
        prepareRankArray();
        prepareDbmsRankArray();
        String subOption = "y";
        do {
            clearConsole();
            System.out.printf("%-50s%-50s", "", "BEST IN DATABASE MANAGEMENT SYSTEM");
            System.out.println();
            System.out.printf("%-10s%-20s%-15s%-15s", "ID", "Name", "DBMS Marks", "PF Marks");
            System.out.println();
            for (int i = 0; i < rankObjArray.length; i++) {
                System.out.printf("%-10s%-20s%-15d%-15d", rankObjArray[i].getId(), rankObjArray[i].getName(),
                        rankObjArray[i].getDbmsMarks(), rankObjArray[i].getPfMarks());
                System.out.println();
            }
            System.out.print("Do you want to go back to main menu?(Y/N): ");
            subOption = input.nextLine();
        } while (subOption.equalsIgnoreCase("n"));
        Student[] resetArray = new Student[0];
        rankObjArray = resetArray;
    }
}