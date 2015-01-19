//package com.company;

import java.util.Scanner;
import java.util.ArrayList;

public class Buckets {
    
    int bucket1, bucket2, bucket1cap, bucket2cap, result;
    ArrayList<BucketsState> al1 = new ArrayList<BucketsState>();
    ArrayList<BucketsState> al2 = new ArrayList<BucketsState>();

    
    void run(){

        Scanner sc = new Scanner(System.in);            // ======= input data
        System.out.println("Enter volume of two buckets divided by space, and required volume on another line: ");
        try {
                String [] buckets = sc.nextLine().split(" ");
                if (buckets.length!=2) {
                    throw new NumberFormatException();
                }
                bucket1cap = Integer.parseInt(buckets[0]);
                bucket2cap = Integer.parseInt(buckets[1]);
                result = sc.nextInt();
            } 
        catch (NumberFormatException e) {
            System.err.println("Wrong format");
            System.exit(0);
        }                                             

        if (result%gcd(bucket1cap, bucket2cap)!=0 || (bucket1cap < 0 || bucket2cap < 0)) {    // ======= check if it can be solved
            System.out.println("Sorry, trouble can't be solved with this params.");
            System.exit(0);
        }

        solve(bucket1cap, bucket2cap, result, al1, 1, 2);
        solve(bucket2cap, bucket1cap, result, al2, 2, 1);

        if (al1.size() < al2.size())  output(al1);      // ======= tryig to solve two ways and output shorter
        else output(al2);
 }
 
    int gcd(int a, int b) {     // ======= counting greatest common divisor
            while (b!=0) {
                int temp = a%b;
                a = b;
                b = temp;
                
            }
            return a;
        }

    void solve (int bc1, int bc2, int result, ArrayList<BucketsState> al, int bn1, int bn2) {
        int b1 = 0;
        int b2 = 0;
        while (b1!=result && b2!=result) {
            b1 = bc1; 
            al.add(new BucketsState(b1, b2, "fill #" + bn1 + ": "));
            while (bc2-b2 < b1) {
                b1 = b1 - (bc2-b2);
                b2 = bc2;
                al.add(new BucketsState(b1, b2, "transfer #" + bn1 + " -> #" + bn2 + ": "));
                if (b1==result || b2 == result) return;
                b2 = 0;
                al.add(new BucketsState(b1, b2, "empty #" + bn1 + ": "));
            }
            b2 = b2 + b1;
            b1 = 0;
            al.add(new BucketsState(b1, b2, "transfer #" + bn1 + " -> #" + bn2 + ": "));
        }
    }

    void output(ArrayList <BucketsState> al) {
        for (BucketsState bs : al) {
            System.out.println (bs.action + bs.bucket1 + " " + bs.bucket2);
        }
    }

    public static void main(String[] args) {
        new Main().run();

    }
}

class BucketsState {
    BucketsState(int bucket1, int bucket2, String action) {
        this.bucket1 = bucket1;
        this.bucket2 = bucket2;
        this.action = action;
    }
    String action;
    int bucket1, bucket2;
}
