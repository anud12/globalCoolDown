package ro.anud.globalcooldown;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ro.anud.globalcooldown.geometry.Point;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GlobalCooldownApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Test
    public void pointTest() {
        Point point = new Point(50000, 50000);

        Point secondPoint = new Point(50000, 50000);

        System.out.println(point.equals(secondPoint));
    }
}
