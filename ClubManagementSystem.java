import java.io.*;
import java.util.*;

abstract class User implements Serializable {
    protected String id;
    protected String name;
    protected String password;
    protected String email;
    protected String phone;

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; } 
}

class Admin extends User implements Serializable {
    public Admin(String id, String name, String password) {
        super(id, name, password);
    }
}

class Student extends User implements Serializable {
    private String department;
    private String batch;
    private String gender;

    public Student(String id, String name, String password, String department, String batch, String gender) {
        super(id, name, password);
        this.department = department;
        this.batch = batch;
        this.gender = gender;
    }
    
    public void setDepartment(String department) { this.department = department; } 
}

class President extends User implements Serializable {
    private String clubId;

    public President(String id, String name, String password, String clubId) {
        super(id, name, password);
        this.clubId = clubId;
    }
    public String getClubId() { return clubId; }
    public void setClubId(String clubId) { this.clubId = clubId; }
}

class Club implements Serializable {
    private String clubId;
    private String clubName;
    private double membershipFee;
    private String presidentId;

    public Club(String clubId, String clubName, double membershipFee) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.membershipFee = membershipFee;
        this.presidentId = "None";
    }

    public String getClubId() { return clubId; }
    public String getClubName() { return clubName; }
    public double getMembershipFee() { return membershipFee; }
    public String getPresidentId() { return presidentId; }
    
    public void setClubId(String clubId) { this.clubId = clubId; }
    public void setClubName(String clubName) { this.clubName = clubName; }
    public void setMembershipFee(double membershipFee) { this.membershipFee = membershipFee; }
    public void setPresidentId(String presidentId) { this.presidentId = presidentId; }
}

class Membership implements Serializable {
    private String membershipId;
    private String studentId;
    private String clubId;
    private double membershipFee;
    private String paymentStatus;
    private String membershipStatus;

    public Membership(String studentId, String clubId, double membershipFee) {
        this.membershipId = studentId; 
        this.studentId = studentId;
        this.clubId = clubId;
        this.membershipFee = membershipFee;
        this.paymentStatus = "Unpaid";
        this.membershipStatus = "Pending";
    }

    public String getMembershipId() { return membershipId; }
    public String getStudentId() { return studentId; }
    public String getClubId() { return clubId; }
    public String getMembershipStatus() { return membershipStatus; }
    public void setClubId(String clubId) { this.clubId = clubId; }
    public void setPaymentStatus(String status) { this.paymentStatus = status; }
    public void setMembershipStatus(String status) { this.membershipStatus = status; }
    
    @Override
    public String toString() {
        return "Student ID: " + studentId + " | Club: " + clubId + 
               " | Fee: " + membershipFee + " | Payment: " + paymentStatus + " | Status: " + membershipStatus;
    }
}

class Event implements Serializable {
    private String eventId;
    private String eventName;
    private String description;
    private String date;
    private String time;
    private String venue;
    private String clubId;

    public Event(String eventId, String eventName, String description, String date, String time, String venue, String clubId) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.description = description;
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.clubId = clubId;
    }

    public String getEventId() { return eventId; }
    public String getEventName() { return eventName; }
    public String getClubId() { return clubId; }
    
    public void setEventName(String eventName) { this.eventName = eventName; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setVenue(String venue) { this.venue = venue; }

    @Override
    public String toString() {
        return "ID: " + eventId + " | Name: " + eventName + " | Desc: " + description + 
               " | Date: " + date + " | Time: " + time + " | Venue: " + venue;
    }
}

class Notice implements Serializable {
    private String noticeId;
    String text, clubId;
    public Notice(String noticeId, String text, String clubId) {
        this.noticeId = noticeId;
        this.text = text; 
        this.clubId = clubId;
    }
    public String getNoticeId() { return noticeId; }
}

public class ClubManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    
    private static List<User> users = new ArrayList<>();
    private static List<Club> clubs = new ArrayList<>();
    private static List<Membership> memberships = new ArrayList<>();
    private static List<Event> events = new ArrayList<>();
    private static List<Notice> notices = new ArrayList<>();

    private static final String DATA_FILE = "system_data.ser";

    public static void main(String[] args) {
        loadData();
        
        while (true) {
            clearScreen();
            System.out.println("=================================");
            System.out.println(" University Club Management System ");
            System.out.println("=================================");
            System.out.println("1. Admin Login");
            System.out.println("2. President Login");
            System.out.println("3. Student Section (Login/Register)");
            System.out.println("4. Exit");
            System.out.print("Select Option: ");
            
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": adminFlow(); break;
                case "2": presidentFlow(); break;
                case "3": studentFlow(); break;
                case "4": 
                    saveData();
                    System.out.println("Data saved successfully. Exiting System..."); 
                    System.exit(0);
                    break;
                default: 
                    System.out.println("Invalid choice!"); 
                    pause(); 
                    break;
            }
        }
    }

    private static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(users);
            oos.writeObject(clubs);
            oos.writeObject(memberships);
            oos.writeObject(events);
            oos.writeObject(notices);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            initializeData();
            saveData();
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            users = (List<User>) ois.readObject();
            clubs = (List<Club>) ois.readObject();
            memberships = (List<Membership>) ois.readObject();
            events = (List<Event>) ois.readObject();
            notices = (List<Notice>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Error loading data, initializing default data.");
            initializeData();
        }
    }

    private static void initializeData() {
        users.add(new Admin("admin", "Super Admin", "admin123"));
        Club c1 = new Club("C01", "Computer Club", 500.0);
        clubs.add(c1);
        users.add(new President("p1", "Alice", "p123", "C01"));
        c1.setPresidentId("p1");
        users.add(new Student("s1", "Bob", "s123", "CSE", "24th", "Male"));
        users.add(new Student("s2", "Charlie", "s123", "EEE", "23rd", "Male"));
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void pause() {
        System.out.print("\nPress [Enter] to continue... ");
        scanner.nextLine();
    }

    private static User getUserById(String id) {
        for (User u : users) {
            if (u.getId().equals(id)) return u;
        }
        return null;
    }

    private static Club getClubById(String id) {
        for (Club c : clubs) {
            if (c.getClubId().equals(id)) return c;
        }
        return null;
    }

    // ==========================================
    // ADMIN SECTION
    // ==========================================
    private static void adminFlow() {
        clearScreen();
        System.out.println("--- Admin Login ---");
        System.out.print("Admin ID: "); String id = scanner.nextLine();
        System.out.print("Password: "); String pass = scanner.nextLine();
        
        User user = authenticate(id, pass, Admin.class);
        if (user == null) { System.out.println("Login Failed!"); pause(); return; }
        
        System.out.println("Login Successful!"); pause();

        while (true) {
            clearScreen();
            System.out.println("--- Admin Dashboard ---");
            System.out.println("1. Add Club");
            System.out.println("2. View All Clubs");
            System.out.println("3. Update Club");
            System.out.println("4. Delete Club");
            System.out.println("5. Assign Club President");
            System.out.println("6. Change Club President");
            System.out.println("7. Remove Club President");
            System.out.println("8. View Students of Each Club");
            System.out.println("9. Change Password"); 
            System.out.println("10. Logout");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();
            
            if (choice.equals("1")) {
                System.out.print("Club ID: "); String cId = scanner.nextLine();
                System.out.print("Club Name: "); String cName = scanner.nextLine();
                System.out.print("Membership Fee: "); double fee = Double.parseDouble(scanner.nextLine());
                clubs.add(new Club(cId, cName, fee));
                saveData();
                System.out.println("Club added and saved successfully!");
                pause();
            } else if (choice.equals("2")) {
                System.out.println("\n--- All Registered Clubs ---");
                if(clubs.isEmpty()){
                    System.out.println("No clubs available.");
                } else {
                    for (Club c : clubs) {
                        String presidentName = "None";
                        if(!c.getPresidentId().equals("None")){
                            User p = getUserById(c.getPresidentId());
                            if(p != null) presidentName = p.getName();
                        }
                        System.out.println("Club ID: " + c.getClubId() + 
                                           " | Name: " + c.getClubName() + 
                                           " | Fee: " + c.getMembershipFee() + 
                                           " | President: " + presidentName + " (" + c.getPresidentId() + ")");
                    }
                }
                pause();
            } else if (choice.equals("3")) {
                System.out.print("Enter Club ID to Update: "); String cId = scanner.nextLine();
                Club c = getClubById(cId);
                if (c != null) {
                    System.out.print("New Club ID (Leave empty to keep current): ");
                    String nId = scanner.nextLine();
                    if (!nId.isEmpty()) {
                        String oldId = c.getClubId();
                        c.setClubId(nId);
                        for(User u : users) {
                            if(u instanceof President && ((President)u).getClubId().equals(oldId)) {
                                ((President)u).setClubId(nId);
                            }
                        }
                        for(Membership m : memberships) {
                            if(m.getClubId().equals(oldId)) m.setClubId(nId);
                        }
                        for(Notice n : notices) {
                            if(n.clubId.equals(oldId)) n.clubId = nId;
                        }
                    }

                    System.out.print("New Club Name (Leave empty to keep current): ");
                    String nName = scanner.nextLine();
                    if (!nName.isEmpty()) c.setClubName(nName);
                    
                    System.out.print("New Membership Fee (Enter -1 to keep current): ");
                    double nFee = Double.parseDouble(scanner.nextLine());
                    if (nFee != -1) c.setMembershipFee(nFee);

                    if (!c.getPresidentId().equals("None")) {
                        System.out.print("New President Name (Leave empty to keep current): ");
                        String pName = scanner.nextLine();
                        if (!pName.isEmpty()) {
                            User p = getUserById(c.getPresidentId());
                            if (p != null) p.setName(pName);
                        }
                    }
                    saveData();
                    System.out.println("Club Details Updated and Saved!");
                } else {
                    System.out.println("Club not found!");
                }
                pause();
            } else if (choice.equals("4")) {
                System.out.print("Enter Club ID to Delete: "); String cId = scanner.nextLine();
                Club c = getClubById(cId);
                if (c != null) {
                    clubs.remove(c);
                    saveData();
                    System.out.println("Club Deleted and Saved!");
                } else {
                    System.out.println("Club not found!");
                }
                pause();
            } else if (choice.equals("5") || choice.equals("6")) {
                System.out.print("Enter Club ID: "); String cId = scanner.nextLine();
                Club c = getClubById(cId);
                if (c != null) {
                    System.out.print("Enter New President ID: "); String pId = scanner.nextLine();
                    System.out.print("Enter New President Name: "); String pName = scanner.nextLine();
                    System.out.print("Enter Initial Password (Leave empty for '1234'): "); 
                    String pPass = scanner.nextLine();
                    if(pPass.isEmpty()) pPass = "1234";
                    
                    users.removeIf(u -> u instanceof President && ((President)u).getClubId().equals(cId));
                    c.setPresidentId(pId);
                    users.add(new President(pId, pName, pPass, cId));
                    saveData();
                    System.out.println("President Assigned/Changed and Saved Successfully!");
                } else {
                    System.out.println("Club not found!");
                }
                pause();
            } else if (choice.equals("7")) {
                System.out.print("Enter Club ID: "); String cId = scanner.nextLine();
                Club c = getClubById(cId);
                if (c != null) {
                    c.setPresidentId("None");
                    users.removeIf(u -> u instanceof President && ((President)u).getClubId().equals(cId));
                    saveData();
                    System.out.println("President Removed and Saved!");
                } else {
                    System.out.println("Club not found!");
                }
                pause();
            } else if (choice.equals("8")) {
                for (Club c : clubs) {
                    System.out.println("\n--- Club: " + c.getClubName() + " ---");
                    boolean hasMembers = false;
                    for (Membership m : memberships) {
                        if (m.getClubId().equals(c.getClubId()) && m.getMembershipStatus().equalsIgnoreCase("Approved")) {
                            User student = getUserById(m.getStudentId());
                            String studentName = (student != null) ? student.getName() : "Unknown";
                            System.out.println("Student ID: " + m.getStudentId() + " | Name: " + studentName);
                            hasMembers = true;
                        }
                    }
                    if (!hasMembers) System.out.println("No approved members yet.");
                }
                pause();
            } else if (choice.equals("9")) {
                System.out.print("Enter New Password: ");
                String newPass = scanner.nextLine();
                user.setPassword(newPass);
                saveData();
                System.out.println("Password Changed Successfully!");
                pause();
            } else if (choice.equals("10")) {
                return;
            } else {
                System.out.println("Invalid Choice!");
                pause();
            }
        }
    }

    // ==========================================
    // PRESIDENT SECTION
    // ==========================================
    private static void presidentFlow() {
        clearScreen();
        System.out.println("--- President Login ---");
        System.out.print("President ID: "); String id = scanner.nextLine();
        System.out.print("Password: "); String pass = scanner.nextLine();
        
        President pres = (President) authenticate(id, pass, President.class);
        if (pres == null) { System.out.println("Login Failed!"); pause(); return; }
        
        System.out.println("Login Successful!"); pause();

        while (true) {
            clearScreen();
            System.out.println("--- President Dashboard (" + pres.getClubId() + ") ---");
            System.out.println("1. View Club Information");
            System.out.println("2. Update Club Information");
            System.out.println("3. View Club Members");
            System.out.println("4. Membership Request");
            System.out.println("5. Remove Member");
            System.out.println("6. Create Event");
            System.out.println("7. Update Event");
            System.out.println("8. Delete Event");
            System.out.println("9. Publish Club Notice");
            System.out.println("10. View All Events");   
            System.out.println("11. View All Notices");  
            System.out.println("12. Delete Notice");     
            System.out.println("13. Change Password");   
            System.out.println("14. Logout");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                Club c = getClubById(pres.getClubId());
                if (c != null) {
                    System.out.println("\n--- Club Information ---");
                    System.out.println("Club ID: " + c.getClubId());
                    System.out.println("Club Name: " + c.getClubName());
                    System.out.println("Membership Fee: " + c.getMembershipFee());
                    System.out.println("President ID: " + c.getPresidentId());
                }
                pause();
            } else if (choice.equals("2")) {
                Club c = getClubById(pres.getClubId());
                if (c != null) {
                    System.out.print("New Club Name (Leave empty to keep current): ");
                    String nName = scanner.nextLine();
                    if (!nName.isEmpty()) c.setClubName(nName);

                    System.out.print("New Membership Fee (Enter -1 to keep current): ");
                    double nFee = Double.parseDouble(scanner.nextLine());
                    if (nFee != -1) c.setMembershipFee(nFee);

                    saveData();
                    System.out.println("Club Information Updated and Saved Successfully!");
                }
                pause();
            } else if (choice.equals("3")) {
                System.out.println("\n--- Club Members ---");
                boolean found = false;
                for (Membership m : memberships) {
                    if (m.getClubId().equals(pres.getClubId()) && m.getMembershipStatus().equalsIgnoreCase("Approved")) {
                        User u = getUserById(m.getStudentId());
                        System.out.println("Student ID: " + m.getStudentId() + " | Name: " + (u != null ? u.getName() : "Unknown"));
                        found = true;
                    }
                }
                if (!found) System.out.println("No members found.");
                pause();
            } else if (choice.equals("4")) {
                System.out.println("\n--- Membership Requests ---");
                List<Membership> pendingRequests = new ArrayList<>();
                for (Membership m : memberships) {
                    if (m.getClubId().equals(pres.getClubId()) && m.getMembershipStatus().equals("Pending")) {
                        pendingRequests.add(m);
                        System.out.println(m);
                    }
                }

                if (pendingRequests.isEmpty()) {
                    System.out.println("No pending membership requests.");
                } else {
                    System.out.print("\nEnter Student ID to take action (or press Enter to go back): ");
                    String sId = scanner.nextLine();
                    if (!sId.isEmpty()) {
                        Membership targetReq = null;
                        for (Membership m : pendingRequests) {
                            if (m.getStudentId().equals(sId)) {
                                targetReq = m;
                                break;
                            }
                        }

                        if (targetReq != null) {
                            System.out.print("Do you want to (1) Accept or (2) Reject this request? Enter choice: ");
                            String action = scanner.nextLine();
                            if (action.equals("1")) {
                                targetReq.setMembershipStatus("Approved");
                                saveData();
                                System.out.println("Request Accepted Successfully!");
                            } else if (action.equals("2")) {
                                targetReq.setMembershipStatus("Rejected");
                                saveData();
                                System.out.println("Request Rejected Successfully!");
                            } else {
                                System.out.println("Invalid action choice.");
                            }
                        } else {
                            System.out.println("Invalid Student ID.");
                        }
                    }
                }
                pause();
            } else if (choice.equals("5")) {
                System.out.print("Enter Student ID to Remove from Club: "); String sId = scanner.nextLine();
                boolean removed = memberships.removeIf(m -> m.getClubId().equals(pres.getClubId()) && m.getStudentId().equals(sId));
                if (removed) {
                    saveData();
                    System.out.println("Member Removed and Saved Successfully!");
                } else {
                    System.out.println("Member not found in this club.");
                }
                pause();
            } else if (choice.equals("6")) {
                System.out.print("Event ID: "); String eId = scanner.nextLine();
                System.out.print("Event Name: "); String name = scanner.nextLine();
                System.out.print("Description: "); String desc = scanner.nextLine();
                System.out.print("Date: "); String date = scanner.nextLine();
                System.out.print("Time: "); String time = scanner.nextLine();
                System.out.print("Venue: "); String venue = scanner.nextLine();
                
                events.add(new Event(eId, name, desc, date, time, venue, pres.getClubId()));
                saveData();
                System.out.println("Event Created and Saved Successfully!");
                pause();
            } else if (choice.equals("7")) {
                System.out.print("Enter Event ID to Update: "); String eId = scanner.nextLine();
                Event ev = events.stream().filter(e -> e.getEventId().equals(eId) && e.getClubId().equals(pres.getClubId())).findFirst().orElse(null);
                if (ev != null) {
                    System.out.print("New Event Name (Leave empty to keep current): ");
                    String name = scanner.nextLine();
                    if (!name.isEmpty()) ev.setEventName(name);

                    System.out.print("New Date (Leave empty to keep current): ");
                    String date = scanner.nextLine();
                    if (!date.isEmpty()) ev.setDate(date);

                    saveData();
                    System.out.println("Event Updated and Saved Successfully!");
                } else {
                    System.out.println("Event not found.");
                }
                pause();
            } else if (choice.equals("8")) {
                System.out.print("Enter Event ID to Delete: "); String eId = scanner.nextLine();
                boolean deleted = events.removeIf(e -> e.getEventId().equals(eId) && e.getClubId().equals(pres.getClubId()));
                if (deleted) {
                    saveData();
                    System.out.println("Event Deleted and Saved Successfully!");
                } else {
                    System.out.println("Event not found.");
                }
                pause();
            } else if (choice.equals("9")) {
                System.out.print("Notice ID: "); String nId = scanner.nextLine();
                System.out.print("Notice Text: "); String text = scanner.nextLine();
                notices.add(new Notice(nId, text, pres.getClubId()));
                saveData();
                System.out.println("Notice Published and Saved Successfully!");
                pause();
            } else if (choice.equals("10")) { 
                System.out.println("\n--- All Events for Club: " + pres.getClubId() + " ---");
                boolean found = false;
                for (Event e : events) {
                    if (e.getClubId().equals(pres.getClubId())) {
                        System.out.println(e);
                        found = true;
                    }
                }
                if (!found) System.out.println("No events available.");
                pause();
            } else if (choice.equals("11")) { 
                System.out.println("\n--- All Notices for Club: " + pres.getClubId() + " ---");
                boolean found = false;
                for (Notice n : notices) {
                    if (n.clubId.equals(pres.getClubId())) {
                        System.out.println("Notice ID: " + n.getNoticeId() + " | Text: " + n.text);
                        found = true;
                    }
                }
                if (!found) System.out.println("No notices available.");
                pause();
            } else if (choice.equals("12")) {
                System.out.print("Enter Notice ID to Delete: "); String nId = scanner.nextLine();
                boolean deleted = notices.removeIf(n -> n.getNoticeId().equals(nId) && n.clubId.equals(pres.getClubId()));
                if (deleted) {
                    saveData();
                    System.out.println("Notice Deleted and Saved Successfully!");
                } else {
                    System.out.println("Notice not found.");
                }
                pause();
            } else if (choice.equals("13")) {
                System.out.print("Enter New Password: ");
                String newPass = scanner.nextLine();
                pres.setPassword(newPass);
                saveData();
                System.out.println("Password Changed Successfully!");
                pause();
            } else if (choice.equals("14")) {
                return;
            } else {
                System.out.println("Invalid Choice!");
                pause();
            }
        }
    }

    // ==========================================
    // STUDENT SECTION
    // ==========================================
    private static void studentFlow() {
        clearScreen();
        System.out.println("--- Student Section ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Choice: ");
        String authChoice = scanner.nextLine();
        
        if (authChoice.equals("2")) {
            System.out.print("Enter New Student ID: "); String newId = scanner.nextLine();
            System.out.print("Enter Name: "); String newName = scanner.nextLine();
            System.out.print("Enter Password: "); String newPass = scanner.nextLine();
            System.out.print("Enter Department: "); String newDept = scanner.nextLine();
            System.out.print("Enter Batch: "); String newBatch = scanner.nextLine();
            System.out.print("Enter Gender: "); String newGender = scanner.nextLine();
            
            users.add(new Student(newId, newName, newPass, newDept, newBatch, newGender));
            saveData();
            System.out.println("Registration Successful! Please login to continue.");
            pause();
            clearScreen();
        } else if (!authChoice.equals("1")) {
            System.out.println("Invalid Choice!");
            pause();
            return;
        }

        System.out.println("--- Student Login ---");
        System.out.print("Student ID: "); String id = scanner.nextLine();
        System.out.print("Password: "); String pass = scanner.nextLine();
        
        Student std = (Student) authenticate(id, pass, Student.class);
        if (std == null) { System.out.println("Login Failed!"); pause(); return; }
        
        System.out.println("Login Successful!"); pause();

        while (true) {
            clearScreen();
            System.out.println("--- Student Dashboard (" + std.getName() + ") ---");
            System.out.println("1. View All Clubs");
            System.out.println("2. Apply for Membership");
            System.out.println("3. Pay Fee");
            System.out.println("4. View Joined Clubs");
            System.out.println("5. View All Notices");
            System.out.println("6. View All Events");
            System.out.println("7. Change Password"); 
            System.out.println("8. Logout");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                for (Club c : clubs) System.out.println(c.getClubId() + " - " + c.getClubName() + " | Fee: " + c.getMembershipFee());
                pause();
            } else if (choice.equals("2")) { 
                System.out.print("Enter Club ID to Apply: "); String cId = scanner.nextLine();
                Club target = getClubById(cId);
                if (target != null) {
                    System.out.print("Enter your Department: ");
                    String dept = scanner.nextLine();
                    System.out.print("Enter your Phone Number: ");
                    String phone = scanner.nextLine();
                    
                    std.setDepartment(dept);
                    std.setPhone(phone);
                    
                    memberships.add(new Membership(std.getId(), cId, target.getMembershipFee()));
                    saveData();
                    System.out.println("Applied! Status: Pending (Saved)");
                } else {
                    System.out.println("Club not found.");
                }
                pause();
            } else if (choice.equals("3")) {
                System.out.print("Enter Student ID to Pay: "); String mId = scanner.nextLine();
                for (Membership m : memberships) {
                    if (m.getStudentId().equals(mId) && m.getStudentId().equals(std.getId())) {
                        m.setPaymentStatus("Paid");
                        saveData();
                        System.out.println("Payment Successful and Saved!");
                    }
                }
                pause();
            } else if (choice.equals("4")) {
                for (Membership m : memberships) {
                    if (m.getStudentId().equals(std.getId())) {
                        Club joinedClub = getClubById(m.getClubId());
                        String cName = (joinedClub != null) ? joinedClub.getClubName() : "Unknown";
                        System.out.println(m.toString() + " | Club Name: " + cName);
                    }
                }
                pause();
            } else if (choice.equals("5")) {
                System.out.println("--- All Notices (Approved Clubs Only) ---");
                boolean foundNotice = false;
                for (Notice n : notices) {
                    boolean isApprovedMember = false;
                    for (Membership m : memberships) {
                        if (m.getStudentId().equals(std.getId()) && m.getClubId().equals(n.clubId) && m.getMembershipStatus().equalsIgnoreCase("Approved")) {
                            isApprovedMember = true;
                            break;
                        }
                    }
                    if (isApprovedMember) {
                        System.out.println("Notice ID: " + n.getNoticeId() + " | Club ID: " + n.clubId + " | Text: " + n.text);
                        foundNotice = true;
                    }
                }
                if (!foundNotice) {
                    System.out.println("No notices available for your approved clubs.");
                }
                pause();
            } else if (choice.equals("6")) {
                System.out.println("--- All Events (Approved Clubs Only) ---");
                boolean foundEvent = false;
                for (Event e : events) {
                    boolean isApprovedMember = false;
                    for (Membership m : memberships) {
                        if (m.getStudentId().equals(std.getId()) && m.getClubId().equals(e.getClubId()) && m.getMembershipStatus().equalsIgnoreCase("Approved")) {
                            isApprovedMember = true;
                            break;
                        }
                    }
                    if (isApprovedMember) {
                        System.out.println(e + " | Club ID: " + e.getClubId());
                        foundEvent = true;
                    }
                }
                if (!foundEvent) {
                    System.out.println("No events available for your approved clubs.");
                }
                pause();
            } else if (choice.equals("7")) {
                System.out.print("Enter New Password: ");
                String newPass = scanner.nextLine();
                std.setPassword(newPass);
                saveData();
                System.out.println("Password Changed Successfully!");
                pause();
            } else if (choice.equals("8")) {
                return;
            } else {
                System.out.println("Invalid Choice!");
                pause();
            }
        }
    }

    private static User authenticate(String id, String pass, Class<?> role) {
        for (User u : users) {
            if (u.getId().equals(id) && u.getPassword().equals(pass) && role.isInstance(u)) {
                return u;
            }
        }
        return null;
    }
}