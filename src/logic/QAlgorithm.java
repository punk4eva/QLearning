
package logic;

import java.util.HashMap;
import java.util.List;

import static utils.Utils.randItem;

/**
 *
 * @author Adam Whittaker
 * @param <S>
 * @param <T>
 */
public abstract class QAlgorithm<T extends State<T>, S extends StateSpace<T>>{
    
    
    public final static double IMPOSSIBLE_THRESHOLD = -Double.MAX_VALUE;
    
    
    protected final double alpha;
    protected final double gamma;
    protected final int trainingCycles;
    protected final S space;
    
    
    public QAlgorithm(double a, double g, int iter, S space){
        alpha = a;
        gamma = g;
        trainingCycles = iter;
        this.space = space;
    }
    
    
    protected T getRandomNextState(T current){
        List<T> possible = space.listActionableStates(current);
        possible.removeIf(st -> current.getRewardTo(st) <= IMPOSSIBLE_THRESHOLD);
        return randItem(possible);
    }
    
    
    protected abstract double maxQ(T st);
    
    public abstract void computeQMatrix(double epsilon);
    
    protected abstract Iterable<T> getUsedStates();
    
    
    public HashMap<T, T> generateStrategy(){
        HashMap<T, T> map = new HashMap<>();
        for(T state : getUsedStates()) if(!state.isGoalState()) map.put(state, getMaxNextState(state, 1));
        return map;
    }
    
    public abstract T getMaxNextState(T c, double epsilon);
    
}
