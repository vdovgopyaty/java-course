import interfaces.AfterSuite;
import interfaces.BeforeSuite;
import interfaces.Test;

public class SomethingTest {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("BeforeSuite");
    }

    @Test(priority = 3)
    public void test3() {
        System.out.println("Test3");
    }

    @Test(priority = 1)
    public void test1() {
        System.out.println("Test1");
    }

    @Test(priority = 2)
    public void test2() {
        System.out.println("Test2");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("AfterSuite");
    }
}
