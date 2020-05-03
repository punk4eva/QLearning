
package logic;

import java.util.Arrays;

import static utils.Utils.R;
import static utils.Utils.shuffle;

/**
 *
 * @author Adam Whittaker
 * @param <T>
 * @param <S>
 */
public class MatrixQAlgorithm<T extends IndexedState<T>, S extends ArrayStateSpace<T>> extends QAlgorithm<T, S>{
    
    
    private final double[][] rMat, qMat;
    private final int[] indices;
    
    
    public MatrixQAlgorithm(double a, double g, int iter, S sp){
        super(a, g, iter, sp);
        indices = new int[space.states.length];
        for(int n=0;n<indices.length;n++) indices[n] = n;
        rMat = initRMatrix(space);
        qMat = initQMatrix(rMat);
    }
    
    
    /**
     * -Double.MAX_VALUE is reserved to mark impossible actions.
     */
    private double[][] initRMatrix(ArrayStateSpace<T> st){
        double[][] r = new double[indices.length][indices.length];
        for(int y : indices){
            st.states[y].setStateIndex(y);
            for(int x : indices){
                st.states[x].setStateIndex(x);
                r[y][x] = st.states[y].getRewardTo(st.states[x]);
            }
        }
        return r;
    }
    
    private double[][] initQMatrix(double[][] rmat){
        return new double[rmat.length][rmat.length];
    }
    
    @Override
    protected double maxQ(T st){
        double max = IMPOSSIBLE_THRESHOLD;
        for(double val : qMat[st.getStateIndex()]){
            if(max<val) max = val;
        }
        return max;
    }
    
    
    @Override
    public void computeQMatrix(double epsilon){
        T cS, nS;
        for(int trainingNum=0;trainingNum<trainingCycles;trainingNum++){
            
            cS = space.randomInitialState();
            
            while(!cS.isGoalState()){
                
                if(R.nextDouble()<epsilon) nS = getRandomNextState(cS);
                else nS = getMaxNextState(cS, epsilon);
                
                qMat[cS.getStateIndex()][nS.getStateIndex()] += alpha * 
                        (rMat[cS.getStateIndex()][nS.getStateIndex()] - qMat[cS.getStateIndex()][nS.getStateIndex()]
                            + gamma * maxQ(nS));
                
                cS = nS;
            }
        }
    }
    
    @Override
    public T getMaxNextState(T c, double epsilon){
        double max = IMPOSSIBLE_THRESHOLD;
        int maxN = -1;
        if(R.nextDouble()>epsilon) shuffle(indices);
        for(int n : indices){
            if(qMat[c.getStateIndex()][n]>max && rMat[c.getStateIndex()][n]>IMPOSSIBLE_THRESHOLD){
                max = qMat[c.getStateIndex()][n];
                maxN = n;
            }
        }
        if(maxN==-1) return null;
        else return space.states[maxN];
    }

    @Override
    protected Iterable<T> getUsedStates(){
        return Arrays.asList(space.states);
    }
    
}
