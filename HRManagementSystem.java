
package com.mycompany.hrmanagementsystem;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// --- 1. Lớp Department ---
class Department {
    private String departmentId;
    private String departmentName;

    public Department(String id, String name) {
        this.departmentId = id;
        this.departmentName = name;
    }
    public String getDepartmentName() { return departmentName; }
}

// --- 2. Lớp Employee (Abstract) ---
abstract class Employee {
    protected String employeeId;
    protected String fullName;
    protected LocalDate startDate;
    protected String status;
    protected double basicSalary;
    protected Department department;
    protected String jobTitle;

    public Employee(String id, String name, double salary, Department dept, String title) {
        this.employeeId = id;
        this.fullName = name;
        this.basicSalary = salary;
        this.department = dept;
        this.jobTitle = title;
        this.startDate = LocalDate.now();
        this.status = "Active";
    }

    public abstract double calculateSalary(int absentDays, int overtimeHours);
    public String getFullName() { return fullName; }
}

// --- 3. Các lớp con kế thừa ---
class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String id, String name, double salary, Department dept, String title) {
        super(id, name, salary, dept, title);
    }

    @Override
    public double calculateSalary(int absentDays, int overtimeHours) {
        double OVERTIME_RATE = 80000;
        double ABSENT_DEDUCTION = 100000;
        return basicSalary + (overtimeHours * OVERTIME_RATE) - (absentDays * ABSENT_DEDUCTION);
    }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String id, String name, double salary, Department dept, String title) {
        super(id, name, salary, dept, title);
    }

    @Override
    public double calculateSalary(int absentDays, int overtimeHours) {
        double OVERTIME_RATE = 50000;
        double ABSENT_DEDUCTION = 100000;
        return basicSalary + (overtimeHours * OVERTIME_RATE) - (absentDays * ABSENT_DEDUCTION);
    }
}

// --- 4. Lớp Salary ---
class Salary {
    private double totalSalary;
    private int absentDays;
    private int overtimeHours;

    public void setAttendanceSummary(int absentDays, int overtimeHours) {
        this.absentDays = absentDays;
        this.overtimeHours = overtimeHours;
    }

    public void calculate(Employee employee) {
        this.totalSalary = employee.calculateSalary(this.absentDays, this.overtimeHours);
    }

    public double getTotalSalary() { return totalSalary; }
}

// --- 5. Lớp chính để chạy chương trình ---
public class HRManagementSystem {
    public static void main(String[] args) {
        try {
            Department itDept = new Department("IT01", "Phong Cong Nghe");
            
            // Thêm nhân viên mới
            Employee emp1 = new FullTimeEmployee("FT01", "Nguyen Van A", 10000000, itDept, "Dev");
            
            Salary salaryCalc = new Salary();
            salaryCalc.setAttendanceSummary(1, 10); // Nghỉ 1 ngày, làm thêm 10h
            salaryCalc.calculate(emp1);
            System.out.println("=== KET QUA ===");
            System.out.println("Nhan vien: " + emp1.getFullName());
            System.out.println("Luong thuc nhan: " + (long)salaryCalc.getTotalSalary() + " VND");
            
        } catch (Exception e) {
            System.out.println("Co loi xay ra: " + e.getMessage());
        }
    }
}