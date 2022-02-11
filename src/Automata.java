import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Automata<S, E>{

    private final HashSet<E> alphabetSet;
    private final HashSet<State<S,E>> qSet;
    private final State<S,E> initialState;

    private final HashMap<S, State<S,E>> stateMapper = new HashMap<>();

    public Automata(ArrayList<S> q, ArrayList<E> sigma, S qNot, ArrayList<S> f,
                    ArrayList<S> deltaInputs,
                    ArrayList<E> deltaElements,
                    ArrayList<S> deltaOutputs) {
        this.alphabetSet = new HashSet<>();
        this.qSet = new HashSet<>();

        State<S,E> newState;
        for (S state : q){
            newState = new State<>(state);
            qSet.add(newState);
            stateMapper.put(state, newState);
        }

        for (E element : sigma){
            alphabetSet.add(element);
        }
        // alphabetSet.addAll(sigma);

        //assert qNot.equals(stateMapper.get(qNot).getLabel());
        initialState = stateMapper.get(qNot);
        initialState.makeInitial();

        State<S,E> finalState;
        for (S state : f){
            //assert stateMapper.containsKey(qNot);
            finalState = stateMapper.get(state);
            finalState.makeFinal();
        }

        // assert deltaInputs.size() == deltaElements.size() && deltaInputs.size() == deltaOutputs.size();
        for (int d = 0; d < deltaInputs.size(); d++){
            State<S,E> inputState = stateMapper.get(deltaInputs.get(d));
            E element = deltaElements.get(d);
            // assert alphabetSet.contains(element);
            State<S,E> outputState = stateMapper.get(deltaOutputs.get(d));

            inputState.addTransition(element, outputState);
        }

    }

    public boolean solve(ArrayList<E> input){
        State<S,E> currentState = initialState;

        for (E element : input){
            currentState = currentState.getTransition(element);
        }

        return currentState.isFinal();
    }

    private State<S,E> findSet(int targetLabel){
        for (State<S,E> set : qSet){
            if (set.getCount() == targetLabel){
                return set;
            }
        }
        return null;
    }

    public static void main(String[] args) throws FileNotFoundException{
        Scanner scanner = new Scanner(new File(args[0]));
        String line;
        String[] lineSplit;

        ArrayList<String> q = new ArrayList<String>(List.of(scanner.nextLine().split(";")));
        ArrayList<String> sigma = new ArrayList<String>(List.of(scanner.nextLine().split(";")));
        String qNot = scanner.nextLine();
        ArrayList<String> f = new ArrayList<String>(List.of(scanner.nextLine().split(";")));

        ArrayList<String> deltaInputs = new ArrayList<>();
        ArrayList<String> deltaElements = new ArrayList<>();
        ArrayList<String> deltaOutputs = new ArrayList<>();

        line = scanner.nextLine();
        lineSplit = line.split(";");
        for (String delta : lineSplit){
            String[] deltaComponents = delta.split(",");
            deltaInputs.add(deltaComponents[0]);
            deltaElements.add(deltaComponents[1]);
            deltaOutputs.add(deltaComponents[2]);
        }

        Automata<String,String> dfa = new Automata<>(q, sigma, qNot, f, deltaInputs, deltaElements, deltaOutputs);
        System.out.println(dfa.solve(new ArrayList<>(List.of("a","b","b","a"))));
        System.out.println(dfa.solve(new ArrayList<>(List.of("a","b","b","a","a","a"))));

    }

}
