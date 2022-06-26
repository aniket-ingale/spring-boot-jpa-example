package com.example.demo.student;

import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
@Log4j2
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    @Qualifier("thirdPartyAPICall")
    RestTemplate restTemplate;

    @Value("${customproperty.baseUrl}")
    public String baseUrl;

    public List<Student> getStudents(){
//        List<Student> studentList = List.of(
//                new Student(
//                        1L, "Anix", "a@a.a", LocalDate.of(1982, Month.JUNE, 4), 40
//                )
//        );
//        return studentList;
        String apiCallResponse = apiCall();
        //JSONObject json = new JSONObject(apiCallResponse);
        log.info("apiCall response is:"+ apiCallResponse);
        return studentRepository.findAll();
    }

    private String apiCall(){
        ResponseEntity<String> response
                = restTemplate.getForEntity(baseUrl, String.class);
        return response.getBody();
    }

    public void addNewStudent(Student student) {
        List<Student> students = studentRepository.findStudentByEmail(student.getEmail());
        Boolean isEmailPresent = students.size() > 0 ?  true:false;
        log.info("isEmailPresent:"+isEmailPresent);

        if(!isEmailPresent)
            studentRepository.save(student);
        else
            throw new IllegalStateException("Email already exists");
    }

    public void deleteStudent(Long studentId) {
        List<Student> students = studentRepository.findStudentById(studentId);
        Boolean isStudentPresent = students.size() > 0 ?  true:false;
        log.info("isStudentPresent:"+isStudentPresent);

        if(isStudentPresent)
            studentRepository.deleteById(studentId);
        else
            throw new IllegalStateException("Student doesn't exist");
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    new IllegalStateException("Student not found for given id:"+studentId);
                    return null;
                });

        log.info("Found student to update:"+student.getName());
        log.info("name arg:"+name);
        log.info("email arg:"+email);

        if (name!=null && name.length()>0)
            student.setName(name);

        if (email!=null && email.length()>0)
            student.setEmail(email);
    }
}
