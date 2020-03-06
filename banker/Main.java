package banker;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        bankerStructure b = new bankerStructure();
        System.out.println(b.safeState());
        int []req = {1,0,2};
        b.GrantRequest(1 ,req );
    }
}
