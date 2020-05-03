/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package examples;

import logic.IndexedState;

import static logic.QAlgorithm.IMPOSSIBLE_THRESHOLD;

/**
 *
 * @author Adam Whittaker
 */
public class BoardState extends IndexedState<BoardState>{


    private static final long serialVersionUID = 2321792387L;
    
    
    public BoardState(int n){
        setStateIndex(n);
    }
    

    @Override
    public boolean isGoalState(){
        return getStateIndex()==8;
    }

    @Override
    public double getRewardTo(BoardState st){
        int i = getStateIndex(), j = st.getStateIndex();
        if(i==j) return IMPOSSIBLE_THRESHOLD;
        if(j==i+3 || j==i-3 || (j==i+1 && i%3!=2) || (j==i-1 && i%3!=0)){
            switch(j){
                case 3:
                    return -10;
                case 8:
                    return 100;
                default:
                    return 0;
            }
        }else return IMPOSSIBLE_THRESHOLD;
    }
    
}
