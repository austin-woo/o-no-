//package tetris;

/**
 * Calculus class
 */
public class Calculus {
    int num1;
    int operator;
    String integral;
    String derivative;
    static String[] operators = {"", "x", "x^2", "cosx", "sinx"};

    /**
     * constructor of calculus class
     */
    public Calculus(){
        num1 = 0;
        operator = 0;
    }

    /**
     * 
     * @return a random integral problem
     */
    public String intGenerator(){
        num1 = (int) (Math.random() * 100) + 1;
        operator = (int) (Math.random() * 5);
        integral = intAnswer(num1, operator);
        return "∫" + num1 + operators[operator] + "dx";
    }

    /**
     * 
     * @return a random derivative problem
     */
    public String derivGenerator()
    {
        num1 = (int) (Math.random() * 80) + 1;
        operator = (int) (Math.random() * 5);
        derivative = derivAnswer(num1, operator);
        return "d/dx " + num1 + operators[operator];

    }

    /**
     * 
     * @param num number of answer
     * @param oper operator of answer
     * @return answer to a given integral
     */
    public String intAnswer(int num, int oper)
    {
        
        String answer = "";
        if(oper == 0)
        {
            answer = num + "x" + "+C";
        }
        if(oper == 1)
        {
            answer = (double)(num)/2 + "x^2" + "+C";
        }
        if(oper == 2)
        {
            answer = num + "/3x^3" + "+C";
        }
        if(oper == 3)
        {
            answer = num + "sinx" + "+C";
        }
        if(oper == 4)
        {
            answer = "-" + num + "cosx" + "+C";
        }
        System.out.println(answer);
        return answer;
    }

    /**
     * 
     * @return answer to the integral
     */
    public String getIntAns()
    {
        return integral;
    }

    /**
     * 
     * @param num number of answer
     * @param oper operator of answer
     * @return the answer to a given derivative
     */
    public String derivAnswer(int num, int oper)
    {
        String answer = "";
        if(oper == 0)
        {
            answer = "0";
        }
        if(oper == 1)
        {
            answer = num + "";
        }
        if(oper == 2)
        {
            answer = num*2 + "x";
        }
        if(oper == 3)
        {
            answer = "-" + num + "sinx";
        }
        if(oper == 4)
        {
            answer = num + "cosx";
        }
        System.out.println(answer);
        return answer;

    }
    /**
     * 
     * @return answer to a derivative 
     */
    public String getDerivAns()
    {
        return derivative;
    }
}