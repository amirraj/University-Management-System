--which students from cse department have borrowed books written by knuth
select   student_id,Student.name
from Student 
join Book_borrower 
on student_id = borrower_id
where
         dept_id = (select dept_id from Department where name = 'CSE')
	 and book_id in (select book_id from Book where author = 'knuth')



--which books have been borrowed by the student and faculty of the same department

alter view BooksOfFaculty as

select faculty_id,Faculty.name,dept_id,book_id
from Faculty
join Book_borrower 
on faculty_id = borrower_id


alter view BooksOfStudent as

select student_id,name,dept_id,book_id
from Student
join Book_borrower 
on student_id = borrower_id


select  faculty_id,
        BooksOfFaculty.name as facultyName,
		student_id,
		BooksOfStudent.name as studentName, 
		(select name from Department where dept_id = BooksOfFaculty.dept_id) as deptName ,
		(select name from Book where book_id = BooksOfFaculty.book_id) as bookName
from BooksOfFaculty
join BooksOfStudent
on BooksOfFaculty.book_id = BooksOfStudent.book_id
where BooksOfFaculty.dept_id = BooksOfStudent.dept_id



--how many students have enrolled in a given course from a given dept 
use Project;
select  
       Course_id,
	   (select name from Course_faculty where course_id = 143) as [course name],
	   (select name from Department where dept_id = Student.dept_id) as department ,
	   COUNT(dept_id) as [number of student]
from Course_Student
join Student
on Course_Student.student_id = Student.student_id
where     course_id = 143 
      and dept_id = (select dept_id from Department where name = 'IPE')
group by course_id,dept_id



--amount of salary cse/all department pays to its staffs/electricians
use Project;
select (select name from Department where Staff.dept_id = dept_id) as [department]
       ,sum(salary) as salary
from Staff
where designation = 'electrician' 
group by dept_id



--which students who have cgpa above/less 3.5/3.0 attends classes taken by a professor/lecturer 
select Student.student_id,       
       Student.name as [student name],
	   cgpa,
	   Course_student.course_id, 
       Faculty.name as [faculty name]
	    
from Student
join Course_student
on Course_student.student_id = Student.student_id

join Course_faculty
on Course_student.course_id = Course_faculty.course_id

join Faculty
on Course_faculty.faculty_id = Faculty.faculty_id
where cgpa > 3.5 and designation = 'PROFESSOR'

 
 --list down the students from cse/eee/all departments who haven't borrowed any book
 select * 
 from Student 
 where student_id in 
 ( 
 select student_id
 from Student
 where dept_id = (select dept_id from Department where name = 'EEE')
 except 
 (select student_id from BooksOfStudent)
 )


 --which stduents have the same birth date 
 SELECT f.name, s.name  
 from Student  f, Student s
 where left(f.dob,6) = left(s.dob,6)
       and f.student_id < s.student_id

--find the students having given birth dates
select name
from Student 
where '10 NOV' = left(dob,6) 

use Project;

--show the name of the faculties in lowercase who have salary below/above average ,take atleast 2/3 course and have name length more than 13/10
select faculty_id,
       LOWER(name) as [name], 
	   salary  
from faculty f
where     salary < (select avg(salary) from faculty )
      and 2 <= ( select count(course_id)
	             from Course_faculty
				 join Faculty
				 on Course_faculty.faculty_id = Faculty.faculty_id
				 where Faculty.faculty_id = f.faculty_id 
	  
	            )
	  and 13< (select len(name) from Faculty where faculty_id = f.faculty_id)



--state the number of students, faculties and staffs of each department
select (select name from Department where dept_id = Student.dept_id) as [dept. name],
       count(distinct Student.name) as [Student no.], 
	   count(distinct Faculty.name) [Faculty no.], 
	   count(distinct Staff.name) as [Staff no.],
	   count(distinct Student.name) + count(distinct Faculty.name) + count(distinct Staff.name) as total
from Student
join Faculty
on Student.dept_id = Faculty.dept_id
join Staff
on Student.dept_id= Staff.dept_id

group by Student.dept_id


--which students haven't enrolled in any course

select Student.student_id, 
       name, 
       (select name from Department where dept_id = Student.dept_id) as [dept. name]
from Student
left join Course_student
on Student.student_id = Course_student.student_id
where Course_student.student_id is null 


--top 2/3 students of cse/eee department who have lowest/highest cgpa
select top 3 student_id,name,cgpa
from Student
where dept_id = (select dept_id from Department where name = 'CSE')
order by cgpa ASC 



--which book has been borrowed most from cse department
