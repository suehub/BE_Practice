package org.study;

public class name extends MotherName implements nameshouting , namePower{
    private String name;
    private int age;

    public name() {
        super();
        System.out.println("name constructor");
        pow();
    }
    @Override
    public void pow(){
        System.out.println("pow!!!!!!");
    }
    public name(String name) {
        System.out.println("name constructor2");

    }
    public name(String name, int age){
        System.out.println("name constructor3");

    }


    @Override
    public void printName() {
        System.out.println("name.printName");
    }

    @Override
    public void printAge() {
        System.out.println("name.printAge");

    }
}
