package org.study;

public class MotherFatherName implements namePower{
    public MotherFatherName(){
        System.out.println("MotherFatherName.MotherFatherName");
        pow();
    }
    @Override
    public void pow(){
        System.out.println("MotherFatherName.pow");
    }
    /**
     *  animal(wow)
     *  cat(wow3) dog(wow2)
     *  persian cat() sham cat
     */
}
