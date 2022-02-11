import java.util.HashMap;
import java.util.Objects;

public class State<S, E> {

    private static int COUNTER = 0;

    private final S label;
    private final int count;
    private boolean isInitial;
    private boolean isFinal;
    private final HashMap<E, State<S,E>> transitions;

    public State(S label){
        this.label = label;
        this.count = COUNTER++;
        this.transitions = new HashMap<>();
        this.isInitial = false;
        this.isFinal = false;
    }

    public boolean isInitial(){
        return this.isInitial;
    }

    public boolean isFinal(){
        return this.isFinal;
    }

    // implement for DFAs only now
    public State<S,E> getTransition(E element){
        return transitions.get(element);
    }

    public void addTransition(E element, State<S,E> outputState){
        transitions.put(element, outputState);
    }

    public void makeInitial(){
        this.isInitial = true;
    }

    public void makeFinal(){
        this.isFinal = true;
    }

    public S getLabel(){
        return this.label;
    }

    public int getCount(){
        return this.count;
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof State<?,?> otherState){
            return this.count == otherState.count;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, count, isInitial, isFinal);
    }
}
