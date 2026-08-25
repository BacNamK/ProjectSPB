package com.example.projectTLearn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectTLearn.model.StudentModel;
import com.example.projectTLearn.repository.AuthRepository;
import com.example.projectTLearn.repository.StudentRepository;

@Service
public class StudentService {
    
    @Autowired
    public StudentRepository studentRepository;
    @Autowired
    public AuthRepository authRepository;

    public StudentModel findById(int id){

        return studentRepository.findById(id);
    }

    public StudentModel findByName(String name){
        StudentModel student =  authRepository.findByName(name);

        if (student == null) return null;

        return student;
    }

    public List<StudentModel> returnAll(){

        try{
            List<StudentModel> listStudent = studentRepository.findAll();

            if (listStudent == null) return null;
            
            return listStudent;
        }
        catch(Exception e){
            System.err.println(e);
        return null;
        }
    }

}
