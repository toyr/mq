package demo.msa.rabbitmq.hello.server;

import java.io.Serializable;

/**
 * @author unisk1123
 * @Description
 * @create 2019/4/25
 */
public class Foo implements Serializable {

    private String name;
    private int age;

    @Override
    public String toString() {
        return "Foo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
