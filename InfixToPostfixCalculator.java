import java.util.Scanner;
import java.util.Stack;

public class InfixToPostfixCalculator {

    // 오퍼레이터 저장 스택
    private Stack<Character> opStack = new Stack<>();
    public static void main(String[] args) {
        new InfixToPostfixCalculator();
    }

    public InfixToPostfixCalculator(){
        Scanner scanner = new Scanner(System.in);

        while(true) {
            String infixStr = scanner.nextLine();
            String postfix = infixToPostfix(infixStr);
            System.out.println("중위 계산식 : " + infixStr);
            System.out.println("후위 계산식 : " + postfix);
            System.out.println("계산 결과 : " + calculate(postfix));
        }
    }

    private int calculate(String postfix){
        // 사용할 정수를 저장할 스택을 선언한다.
        final Stack<Integer> intStack = new Stack<>();
        // 문자열의 길이만큼 반복시킨다.
        for(int i = 0 ; i < postfix.length() ; i++){
            char ch = postfix.charAt(i);
            int value = 0;
            if(isDigit(ch)){
                // 만약, ch가 숫자면, 해당 숫자를 찾아서 value값에 저장한 후에, 스택에 push한다.
                while(isDigit(ch)) {
                    value = value + ch - '0';
                    if(++i == postfix.length())
                        break;
                    ch= postfix.charAt(i);
                }
                i--;
                intStack.push(value);
            }else if (isOperator(ch)){
                // 만약, ch가 연산자면, 스택의 맨 위 두개의 숫자를 빼서, 계산한 후에, 결과값을 다시 스택에 집어넣는다.
                int secValue = intStack.pop();
                int firValue = intStack.pop();
                int tempResult = 0;
                switch(ch){
                    case '*':
                        tempResult = firValue * secValue;
                        break;
                    case '/':
                        tempResult = firValue / secValue;
                        break;
                    case '+':
                        tempResult = firValue + secValue;
                        break;
                    case '-':
                        tempResult = firValue - secValue;
                        break;
                    default:
                        break;
                }
                intStack.push(tempResult);
            }
        }
        // 스택에 마지막에 남은 숫자가 결과값이므로 해당값을 pop 하여 반환한다.
        return intStack.pop();
    }

    private String infixToPostfix(String infixStr){
        // postfix 문자열을 저장할 StringBuilder를 선언해준다.
        StringBuilder postfixStringBuilder = new StringBuilder();

        // 문자열의 길이만큼 반복한다.
        for(int i = 0 ; i < infixStr.length() ; i++){
            // ch 에 중위 계산식 문자열의 i번째 character를 반환하여 넣어준다.
            char ch = infixStr.charAt(i);
            // 만약 , ch가 숫자라면
            if(isDigit(ch)){
                int value = 0;
                // 해당 숫자를 추출해낸다.
                while( isDigit(ch)){
                    value = value*10 + ch - '0';
                    i++;
                    if(infixStr.length() == i)
                        break;
                    ch = infixStr.charAt(i);
                }
                i--;
                // 추출해낸 숫자를 postfixStringBuilder에 추가한다.
                postfixStringBuilder.append(value);
                postfixStringBuilder.append(' ');
            }else if (isOperator(ch)){
                // opStack이 비었으면, push한다.
                if(opStack.isEmpty()) {
                    opStack.push(ch);
                }else{
                    // opStack에 이미 연산자가 있다면, 해당 연산자와, ch에 있는 연산자의 우선순위를 비교하여,
                    // opStack의 제일 위에 있는 연산자의 우선순위가 더 크거나 같다면,
                    // opStack을 pop하여 postfixStringBuilder에 추가하고, ch를 push한다.
                    if(getOpProperty(opStack.peek())>= getOpProperty(ch)){
                        postfixStringBuilder.append(opStack.pop());
                        postfixStringBuilder.append(' ');
                    }
                    opStack.push(ch);
                }
            }else if (isBracket(ch)){
                // 만약, 괄호가 나왔을 때, 여는 괄호면, opStack에 push한다.
                if(ch == '('){
                    opStack.push(ch);
                }else{
                    // 만약, 닫는 괄호라면, opStack의 제일 위에 쌓여있는 연산자가 ( 가 나올 때까지
                    // pop하여 postfixStringBuilder에 append한다.
                    // 만약, (가 나온다면, pop해서 없애버린다.
                    while(opStack.peek()!='('){
                        postfixStringBuilder.append(opStack.pop());
                        postfixStringBuilder.append(' ');
                    }
                    // remove '('
                    opStack.pop();
                }
            }else if(ch == ' '){
                continue;
            }else {
                // 만약, 빈 공백문자나, 숫자, 연산자, 괄호가 아닐경우, null을 반환하고 종료한다.
                return null;
            }
        }
        // 마지막에 남아있는 operator 들을 후위 계산식 문자열에 돌려준다.
        while(!opStack.isEmpty()){
            postfixStringBuilder.append(opStack.pop());
            postfixStringBuilder.append(' ');
        }

        return postfixStringBuilder.toString();
    }

    // 숫자라면 참을 반환함
    private boolean isDigit(char ch){
        return ch>='0' && ch<='9';
    }

    // 연산자인지 여부를 확인
    private boolean isOperator(char ch){
        return ch == '*' || ch=='-'||ch=='+'||ch=='/';
    }

    // 괄호인지 여부를 확인
    private boolean isBracket(char ch){
        return ch=='(' || ch==')';
    }

    // 오퍼레이터 우선순위를 확인
    private int getOpProperty(char ch){
        if(ch == '*' || ch == '/')
            return 2;
        else if (ch == '+' || ch == '-')
            return 1;
        else
            // 만약, 그외의 괄호같은 값이 들어오면 제일 낮은 우선순위를 주어서 반환할 일 없게만듬.
            return 0;
    }
}
