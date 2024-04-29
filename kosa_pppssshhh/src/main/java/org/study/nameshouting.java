package org.study;
public interface nameshouting {
    void printName();
    void printAge();

     default void wow(){
         System.out.println("bow");
     }
}
