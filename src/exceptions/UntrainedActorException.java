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

package exceptions;

import logic.State;

/**
 *
 * @author Adam Whittaker
 */
public class UntrainedActorException extends IllegalStateException{
    

    private static final long serialVersionUID = 46237832111L;
    
    
    public UntrainedActorException(State<? extends State<?>> e){
        super("The actor wasn't trained to deal with state: " + e);
    }
    
}