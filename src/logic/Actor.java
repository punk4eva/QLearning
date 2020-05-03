/*
 * Copyright (C) 2020 Adam Whittaker
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package logic;

import exceptions.UntrainedActorException;
import java.util.HashMap;

/**
 *
 * @author Adam Whittaker
 * @param <T>
 */
public abstract class Actor<T extends State<T>>{
    
    
    private final HashMap<T, T> strategy;
    
    
    public Actor(HashMap<T, T> strat){
        strategy = strat;
    }
    
    
    public void act(T current){
        if(strategy.containsKey(current)) transitionStates(current, strategy.get(current));
        else throw new UntrainedActorException(current);
    }
    
    public abstract void transitionStates(T current, T next);
    
    
}
