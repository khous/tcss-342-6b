package function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kyle on 3/5/16.
 */
public class FunctionLibrary {
    private HashMap<String, Function> functions = new HashMap<>();

    public FunctionLibrary () {
        //Average function
        functions.put("AVG", new Function() {
            @Override
            public int compute(List<Integer> arguments) {
                if (arguments == null) return 0;
                int sum = 0;
                for (Integer i : arguments) {
                    sum += i.intValue();
                }

                return (sum /= arguments.size());
            }
        });

        //Median Function
        functions.put("MED", new Function() {
            @Override
            public int compute(List<Integer> arguments) {
                if (arguments == null) return 0;
                Collections.sort(arguments);
                return arguments.get(arguments.size() / 2);
            }
        });

        //Variable base logarithm Function
        functions.put("LOG", new Function() {
            @Override
            public int compute(List<Integer> arguments) {
                if (arguments == null) return 0;

                int result = 0;
                //Use log_2
                if (arguments.size() == 1) {
                    result = (int) (Math.log(arguments.get(0)) / Math.log(2));
                } else {//first argument is the base, second argument is the operand
                    result = (int) (Math.log(arguments.get(1)) / Math.log(arguments.get(0)));//Change of base formula
                }

                return result;
            }
        });

        functions.put("SQRT", new Function() {
            @Override
            public int compute(List<Integer> arguments) {
                if (arguments == null) return 0;

                return (int) Math.sqrt(arguments.get(0));
            }
        });

        //Invoked like this RTX(x, 2)
        functions.put("RTX", new Function() {
            @Override
            public int compute(List<Integer> arguments) {
                if (arguments == null) return 0;

                return (int) Math.pow(arguments.get(1), 1.0 / arguments.get(0));
            }
        });

        //Algorithm adapted from wikipedia
        functions.put("STDDEV", new Function() {
            @Override
            public int compute(List<Integer> arguments) {
                if (arguments == null) return 0;
                int sum = 0, n = 0, sumSq = 0;

                for (Integer x : arguments) {
                    n++;

                    sum += x;
                    sumSq = sumSq + (x * x);
                }

                return (sumSq - (sum * sum) / n) / (n - 1);
            }
        });
    }

    public Function getFunction (String key) {
        return functions.get(key);
    }
}
