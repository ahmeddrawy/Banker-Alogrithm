package banker;

import java.util.Scanner;
import java.util.Vector;

/*

5 3

0 1 0
2 0 0
3 0 2
2 1 1
0 0 2

7 5 3
3 2 2
9 0 2
2 2 2
4 3 3

3 3 2

7 4 3
1 2 2
6 0 0
0 1 1
4 3 1



 */
public class bankerStructure {
    int []available;
    int [][]maximum;
    int [][]allocation;
    int [][]need;
    int n , m ;

    bankerStructure(){
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        available = new int[m];
        maximum = new int[n][m];
        allocation = new int[n][m];
        need = new int[n][m];
        System.out.println("input the allocation n*m matrix ");
        input(allocation , sc);
        System.out.println("input the maximum n*m matrix ");
        input(maximum , sc);
        System.out.println("input the available 1*m matrix ");
        input(available , sc);
        calcualateNeed();
        PrintSnapShot();
    }
    private void PrintSnapShot(){
        System.out.println("allocation");
        output(allocation , n , m);
        System.out.println("maximum");
        output(maximum , n , m);
        System.out.println("Available");
        output(available , m);
        System.out.println("need");
        output(need , n , m);
    }

    bankerStructure(int n , int m ,int []_available , int [][] _maximum , int [][]_allocation , int[][]_need){
        available = _available;
        maximum = _maximum;
        allocation = _allocation;
        need = _need;
        this.n = n ;
        this.m = m;
    }
    boolean safeState(){
        boolean[] finished = new boolean[n];
        int []tmpavailable = available;
        boolean yes=  false;
        Vector<Integer> seq = new Vector<>();
        while(!yes){
             yes = true ;
            for (int i = 0 ; i < n ; i++){
                if(!finished[i]){
                    boolean can = true;
                    /// making yes = true and anding with the condition if it fails once we can't take it
                    for (int j = 0 ; j < m ;++j){
                        can &=(need[i][j]<=tmpavailable[j]);

                    }
                    if(can) {
                        finished[i] = can;
                        seq.add(i+1);
                        for (int j = 0 ; j < m ;++j){
                            tmpavailable[j] +=allocation[i][j];

                        }
                    }
                    yes&=can;
                }
            }
        }
        yes = true;
        for(int i = 0 ; i < n ; ++i){
           yes&=finished[i];
        }
        if(yes){
            for (int i :seq) {
                System.out.print(i +" ");
            }
            System.out.println("");
        }

        return yes;
    }


    void GrantRequest(int ProcessNumber , int [] request){
        boolean exceededNeed = true ;
        for (int i = 0 ; i < m ; ++i){
           exceededNeed&=  (request[i]<= need[ProcessNumber][i]);
        }
        if(!exceededNeed){
            System.out.println("can't exceed the max need");
            return ;
        }
        boolean exceededAvailable = true;
        for (int i = 0 ; i < m ; ++i){
            exceededAvailable&=(request[i] <=available[i]);
        }
        if(!exceededAvailable){

            System.out.println("process must wait , available resourse are not enough");
            return ;
        }
        int[]tmpAvailable = available;
        int[][]tmpAllocat = allocation;
        int[]tmpNeed = need[ProcessNumber];
        for (int i = 0 ; i < m ; ++i){
            available[i]-=request[i];
            allocation[ProcessNumber][i]+=request[i];
            need[ProcessNumber][i]-=request[i];
        }
        boolean safe = safeState();
        if(safe){
            ///todo check if user want to proceed , default will be discarded
            System.out.println("safe state");

        }
        else {
            System.out.println("unsafe state can't proceed");
            available = tmpAvailable;
            allocation=  tmpAllocat;
            need[ProcessNumber] = tmpNeed;
        }


    }

    private void output(int arr[][] , int r , int c  ){
        for (int i = 0 ; i <r ; ++i) {
            for (int j = 0; j < c; ++j){
                System.out.print(arr[i][j] + " ");
            }
            System.out.println("");
        }
    }
    private void output(int arr[] , int c  ){
        for (int j = 0; j < c; ++j){
            System.out.print(arr[j] + " ");
        }
        System.out.println("");
    }

    private  void calcualateNeed(){
        for (int i = 0 ; i < n ; ++i){
            for (int j = 0 ; j <  m ; ++j){
                need[i][j] = maximum[i][j] - allocation[i][j];

            }
        }

    }
    private void input(int arr[][] , Scanner sc){
        for (int i = 0 ; i < n ; ++i) {
            for (int j = 0; j < m; ++j){
                arr[i][j] = sc.nextInt();
            }
        }
    }
    private void input(int arr[] , Scanner sc){
        for (int i = 0 ; i < m ; ++i) {
            arr[i] = sc.nextInt();
        }
    }

    public void setAllocation(int[][] allocation) {
        this.allocation = allocation;
    }

    public void setAvailable(int[] available) {
        this.available = available;
    }

    public void setMaximum(int[][] maximum) {
        this.maximum = maximum;
    }

    public void setNeed(int[][] need) {
        this.need = need;
    }

    public int[] getAvailable() {
        return available;
    }

    public int[][] getAllocation() {
        return allocation;
    }

    public int[][] getMaximum() {
        return maximum;
    }

    public int[][] getNeed() {
        return need;
    }
}
