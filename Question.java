
import java.util.*;

public class Question{
    private int correct;
    private int incorrect;
    private int level;
    private int var1;
    private int var2;
    private int var3;
    private int var4;
    private char op1;
    private char op2;
    private char op3;

    //four question constructors, one for each level
    public Question(){
    }
    public Question(int a){
        level = a;
    }
    public Question(int x, int y, int z, int q, char a, char b, char c){
        var1=x;
        var2=y;
        var3=z;
        var4=q;
        op1=a;
        op2=b;
        op3=c;
        level=3;
    }
    public Question(int x, int y, int z, char a, char b){
        var1=x;
        var2=y;
        var3=z;
        op1=a;
        op2=b;
        level=2;
    }
    public Question(int x, int y, char a){
        var1=x;
        var2=y;
        op1=a;
        level=1;
    }
    //holds and generates 100 questions in each level and stores into an arraylist
    public static ArrayList<Question> Bank(int level){
        int [] nums = new int [10];
        for(int i = 0; i < nums.length; i++){
            nums[i]=i;
        }
        char [] operations = new char [3];
        operations[0]='+';
        operations[1]='-';
        operations[2]='*';

        ArrayList<Question> one =new ArrayList<Question>();
        ArrayList<Question> two =new ArrayList<Question>();
        ArrayList<Question> three =new ArrayList<Question>();

        if(level == 1){
            int bababoo = 0;
            while(bababoo < 10){
                int randNum1 = (int)(Math.random()*10);
                
                char randOp1 = operations[(int)(Math.random()*3)];
                int randNum2 = (int)(Math.random()*10);
                Question levelques1 = new Question(randNum1, randNum2, randOp1);
                one.add(levelques1);
                bababoo++;
            }
            return one;
        }
        else if(level == 2){
            int bababoo = 0;
            while(bababoo < 100){
                int randNum1 = (int)(Math.random()*10);
                char randOp1 = operations[(int)(Math.random()*3)];
                int randNum2 = (int)(Math.random()*10);
                char randOp2 = operations[(int)(Math.random()*3)];
                int randNum3 = (int)(Math.random()*10);
                Question levelques2 = new Question(randNum1, randNum2, randNum3, randOp1, randOp2);
                two.add(levelques2);
                bababoo++;
            }
            return two;
        }
        else if(level == 3){
            int bababoo = 0;
            while(bababoo < 100){
                int randNum1 = (int)(Math.random()*10);
                char randOp1 = operations[(int)(Math.random()*3)];
                int randNum2 = (int)(Math.random()*10);
                char randOp2 = operations[(int)(Math.random()*3)];
                int randNum3 = (int)(Math.random()*10);
                char randOp3 = operations[(int)(Math.random()*3)];
                int randNum4 = (int)(Math.random()*10);
                Question levelques3 = new Question(randNum1, randNum2, randNum3, randNum4, randOp1, randOp2, randOp3);
                three.add(levelques3);
                bababoo++;
            }
            return three;
        }
        return three;
    }
        //print out question in a readable way
    public String toString(){
        if(level==1){
            return ""+var1+op1+var2+"=";
        }
        else if(level==2){
            return ""+var1+op1+var2+op2+var3+"=";
        }
        else if(level==3){
            return ""+var1+op1+var2+op2+var3+op3+var4+"=";
        }
        else{
            return "";
        }
    }
        //checks if answer is correct
    public boolean isCorrect(int answer){
        //int answer=Bird.getAnswer();
        int correctAnswer=0;
        if(level==1){
            if(op1=='+'){
                correctAnswer=var1+var2;
            }
            else if(op1=='-'){
                correctAnswer=var1-var2;
            }
            else if(op1=='*'){
                correctAnswer=var1*var2;
            }
        }
        else if(level==2){
            if(op1=='+'){
                if(op2=='+'){
                    correctAnswer=var1+var2+var3;
                }
                else if(op2=='-'){
                    correctAnswer=var1+var2-var3;
                }
                else if(op2=='*'){
                    correctAnswer=var1+var2*var3;
                }

            }
            else if(op1=='-'){
                if(op2=='+'){
                    correctAnswer=var1-var2+var3;
                }
                else if(op2=='-'){
                    correctAnswer=var1-var2-var3;
                }
                else if(op2=='*'){
                    correctAnswer=var1-var2*var3;
                }

            }
            else if(op1=='*'){
                if(op2=='+'){
                    correctAnswer=var1*var2+var3;
                }
                else if(op2=='-'){
                    correctAnswer=var1*var2-var3;
                }
                else if(op2=='*'){
                    correctAnswer=var1*var2*var3;
                }
                
            }

        }
        else if(level==3){
            if(op1=='+'){
                if(op2=='+'){
                    if(op3=='+'){
                        correctAnswer=var1+var2+var3+var4;
                    }
                    else if(op3=='-'){
                        correctAnswer=var1+var2+var3-var4;
                    }
                    else if(op3=='*'){
                        correctAnswer=var1+var2+var3*var4;
                    }
                }
                else if(op2=='-'){
                    if(op3=='+'){
                        correctAnswer=var1+var2-var3+var4;
                    }
                    else if(op3=='-'){
                        correctAnswer=var1+var2-var3-var4;
                    }
                    else if(op3=='*'){
                        correctAnswer=var1+var2-var3*var4;
                    }                
                }
                else if(op2=='*'){
                    if(op3=='+'){
                        correctAnswer=var1+var2*var3+var4;
                    }
                    else if(op3=='-'){
                        correctAnswer=var1+var2*var3-var4;
                    }
                    else if(op3=='*'){
                        correctAnswer=var1+var2*var3*var4;
                    }                
                }
            }
            else if(op1=='-'){
                if(op2=='+'){
                    if(op3=='+'){
                        correctAnswer=var1-var2+var3+var4;
                    }
                    else if(op3=='-'){
                        correctAnswer=var1-var2+var3-var4;
                    }
                    else if(op3=='*'){
                        correctAnswer=var1-var2+var3*var4;
                    }                
                }
                else if(op2=='-'){
                    if(op3=='+'){
                        correctAnswer=var1-var2-var3+var4;
                    }
                    else if(op3=='-'){
                        correctAnswer=var1-var2-var3-var4;
                    }
                    else if(op3=='*'){
                        correctAnswer=var1-var2-var3*var4;
                    }                
                }
                else if(op2=='*'){
                    if(op3=='+'){
                        correctAnswer=var1-var2*var3+var4;
                    }
                    else if(op3=='-'){
                        correctAnswer=var1-var2*var3-var4;
                    }
                    else if(op3=='*'){
                        correctAnswer=var1-var2*var3*var4;
                    }                
                }
            }
            else if(op1=='*'){
                if(op2=='+'){
                    if(op3=='+'){
                        correctAnswer=var1*var2+var3+var4;
                    }
                    else if(op3=='-'){
                        correctAnswer=var1*var2+var3-var4;
                    }
                    else if(op3=='*'){
                        correctAnswer=var1*var2+var3*var4;
                    }                
                }
                else if(op2=='-'){
                    if(op3=='+'){
                        correctAnswer=var1*var2-var3+var4;
                    }
                    else if(op3=='-'){
                        correctAnswer=var1*var2-var3-var4;
                    }
                    else if(op3=='*'){
                        correctAnswer=var1*var2-var3*var4;
                    }                
                }
                else if(op2=='*'){
                    if(op3=='+'){
                        correctAnswer=var1*var2*+var4;
                    }
                    else if(op3=='-'){
                        correctAnswer=var1*var2*var3-var4;
                    }
                    else if(op3=='*'){
                        correctAnswer=var1*var2*var3*var4;
                    }                
                }
            }

        }
        if(answer==correctAnswer){
            return true;
        }
        return false;
    }

    /*public static void main(String args[]){
        Question a = new Question(3);
        ArrayList<Question> questions = new ArrayList<Question>();
        questions = a.Bank(3);
        for(Question x: questions){
            System.out.println(x.toString());
        }
    }*/
}