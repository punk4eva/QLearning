/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logic;

import java.io.Serializable;

/**
 *
 * @author Adam Whittaker
 * @param <E>
 */
public interface State<E extends State<E>> extends Serializable{    
    
    public abstract boolean isGoalState();
    
    public abstract double getRewardTo(E st);
    
}
