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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static utils.Utils.randItem;

/**
 *
 * @author Adam Whittaker
 * @param <T>
 */
public class ArrayStateSpace<T extends State<T>> implements StateSpace<T>{
    
    
    protected final T[] states;
    
    
    public ArrayStateSpace(T[] t){
        states = t;
    }
    

    @Override
    public List<T> listActionableStates(T current){
        return new LinkedList<>(Arrays.asList(states));
    }

    @Override
    public T randomInitialState(){
        return randItem(states);
    }
    
}
