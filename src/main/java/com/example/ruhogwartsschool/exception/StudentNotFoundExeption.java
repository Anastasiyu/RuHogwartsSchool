package com.example.ruhogwartsschool.exception;

public class StudentNotFoundExeption extends  RuntimeException{

    private  final  long id;
    public StudentNotFoundExeption(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }
}
