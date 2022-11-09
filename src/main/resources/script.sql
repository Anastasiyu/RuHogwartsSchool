select *
from student;
select *
from student;
select *
from student
where name LIKE '%с%';
select *
from faculty
where color LIKE '%red%';
select *
from student,
     faculty
where faculty.color = student.name;
select name
FROM student;
select *
from student
where age < 20
  and student.age < 18;
select *
from student
where age < student.id;
select *
from student
ORDER BY age;