import javax.xml.soap.Text;

public class Test {
    private static int x = 100;

    public static void main(String[] args) {
        Test t1 = new Test();
        t1.x++;
        Test t2 = new Test();
        t2.x++;
        t1 = new Test();
        t1.x++;
        Test.x--;
        System.out.println("x="+x);

    }
}

