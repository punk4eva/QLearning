/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import examples.BoardState;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import logic.ArrayStateSpace;
import logic.MapQAlgorithm;

/**
 *
 * @author Adam Whittaker
 */
public final class Utils{
    
    
    public static final Random R = new Random();
    
    
    private Utils(){}
    
    
    public @interface Unfinished{
        String value() default "";
    }
    
    public @interface Tested{
        String value() default "";
    }
    
    
    public static <T> T randItem(T[] ary){
        return ary[R.nextInt(ary.length)];
    }
    
    public static <T> T randItem(List<T> lst){
        return lst.get(R.nextInt(lst.size()));
    }
    
    public static <T> void shuffle(T[] ary){
        int j;
        T temp;
        for(int i=ary.length-1;i>=0;i--){
            j = R.nextInt(i+1);
            temp = ary[j];
            ary[j] = ary[i];
            ary[i] = temp;
        }
    }
    
    public static <T> void shuffle(int[] ary){
        int j, temp;
        for(int i=ary.length-1;i>=0;i--){
            j = R.nextInt(i+1);
            temp = ary[j];
            ary[j] = ary[i];
            ary[i] = temp;
        }
    }
    
    
    public static void main(String... args){
        System.out.println("Running...");
        
        BoardState states[] = new BoardState[9];
        for(int n=0;n<states.length;n++) states[n] = new BoardState(n);
        
        MapQAlgorithm<BoardState, ArrayStateSpace<BoardState>> qLearn = new MapQAlgorithm<>(0.2, 0.9, 100000, new ArrayStateSpace<>(states));
        //MatrixQAlgorithm<BoardState, ArrayStateSpace<BoardState>> qLearn = new MatrixQAlgorithm<>(0.2, 0.9, 1000, new ArrayStateSpace<>(states));
        
        qLearn.computeQMatrix(0.6);
        HashMap<BoardState, BoardState> strat = qLearn.generateStrategy();
        
        strat.entrySet().forEach((entry) -> {
            System.out.println("From state " + entry.getKey().getStateIndex() + 
                    " go to state " + entry.getValue().getStateIndex());
        });
        
    }
    
}
