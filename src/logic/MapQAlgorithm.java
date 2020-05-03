
package logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Optional;

import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 * @param <T>
 * @param <S>
 */
public class MapQAlgorithm<T extends State<T>, S extends StateSpace<T>> extends QAlgorithm<T, S>{
    
    
    private final HashMap<T, StateFrontier> map = new HashMap<>();


    public MapQAlgorithm(double a, double g, int iter, S sp){
        super(a, g, iter, sp);
    }
    

    @Override
    protected double maxQ(T st){
        return map.get(st).maxQ();
    }

    @Override
    public void computeQMatrix(double epsilon){
        T cS, nS;
        double[] rq;
        for(int trainingNum=0;trainingNum<trainingCycles;trainingNum++){
            
            cS = space.randomInitialState();
            
            while(!cS.isGoalState()){
                
                if(R.nextDouble()<epsilon) nS = getRandomNextState(cS);
                else{
                    if(!map.containsKey(cS)) nS = getRandomNextState(cS);
                    else nS = getMaxNextState(cS, epsilon);
                }
                
                if(!map.containsKey(cS)) map.put(cS, new StateFrontier(cS, nS));
                
                rq = map.get(cS).nextToCost.get(nS);
                if(rq == null){
                    map.get(cS).registerNewState(cS, nS);
                    rq = map.get(cS).nextToCost.get(nS);
                }
                
                if(!map.containsKey(nS)) map.put(nS, new StateFrontier(nS, cS));
                        
                rq[1] += alpha * (rq[0] - rq[1] + gamma * maxQ(nS));
                
                cS = nS;
            }
        }
    }

    @Override
    public T getMaxNextState(T c, double epsilon){
        return map.get(c).getMaxNextState(epsilon);
    }
    
    @Override
    public HashMap<T, T> generateStrategy(){
        HashMap<T, T> ret = new HashMap<>();
        map.keySet().stream().filter(state -> !state.isGoalState()).forEach(state -> ret.put(state, getMaxNextState(state, 1)));
        return ret;
    }

    @Override
    protected Iterable<T> getUsedStates(){
        return map.keySet();
    }
    
    
    private final class StateFrontier{
        
        
        /**
         * key: next state
         * value: {R, Q}
         */
        private final HashMap<T, double[]> nextToCost = new HashMap<>();
        
        
        private StateFrontier(T current, T next){
            registerNewState(current, next);
        }
        
        
        public void registerNewState(T current, T next){
            nextToCost.put(next, new double[]{current.getRewardTo(next), 0});
        }
        
        double maxQ(){
            return nextToCost.values().stream().mapToDouble(ary -> ary[1])
                    .reduce(IMPOSSIBLE_THRESHOLD, (a,b) -> a>b?a:b);
        }
        
        T getMaxNextState(double epsilon){
            LinkedList<Entry<T, double[]>> entries = new LinkedList<>(nextToCost.entrySet());
            if(R.nextDouble()>epsilon) Collections.shuffle(entries);
            Optional<Entry<T, double[]>> max = entries.stream()
                    .reduce((a, b) -> a.getValue()[1]>b.getValue()[1]?a:b);
            if(max.isPresent()) return max.get().getKey();
            else return null;
        }
        
        @Override
        public String toString(){
            T max = getMaxNextState(0);
            if(max == null) return "null";
            else return max.toString();
        }
        
    }
    
}
