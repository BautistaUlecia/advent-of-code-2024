package com.bautistaulecia.DayTwo;

import com.bautistaulecia.Util.FileParser;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DayTwo {
    public static void solve(){
        List<String> lines = FileParser.toLines("src/main/resources/DayTwo/input.txt");
        long counter = lines.stream().filter(DayTwo::isSafe).count();
        System.out.println("Part one: ");
        System.out.println(counter);
    }
    public static Boolean isSafe(String line){
        List<Integer> numbers = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
        return isSequence(numbers) && valuesDifferByOneToThree(numbers);
    }
    private static Boolean isSequence(List<Integer> line){
        // Should be sorted (ascending or descending) if sequence
        return line.equals(line.stream().sorted().toList()) || line.equals(line.stream().sorted(Comparator.reverseOrder()).toList());
    }
    private static Boolean valuesDifferByOneToThree(List<Integer> line){
        // Every value should differ by no more than 3 and no less than 1 from its pair
        for (int i = 1; i < line.size(); i++){
            if ((Math.abs(line.get(i) - line.get(i - 1)) < 1) || Math.abs(line.get(i) - line.get(i - 1)) > 3){
                return false;
            }
        }
        return true;
    }
}
