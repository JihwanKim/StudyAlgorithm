import java.math.BigInteger;
import java.util.*;

/*
 solve date : 170401
 jihwan Kim
 github - https://github.com/JihwanKim/StudyAlgorithm.git
 problem url - http://tryhelloworld.co.kr/challenge_codes/73
*/

class CorrectParenthesis {
    public BigInteger parenthesisCase(int n) {
        long startTime = System.currentTimeMillis();
        BigInteger answer = new BigInteger("0");
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("()");
        ArrayList<String> tempArr = new ArrayList<String>();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < arr.size(); j++) {
                tempArr.add("(" + arr.get(j) + ")");
                tempArr.add("()" + arr.get(j));
                tempArr.add(arr.get(j) + "()");
            }
            // check same value
            for (int j = 0; j < tempArr.size(); j++) {
                for (int k = j + 1; j < tempArr.size(); k++) {
                    if (j >= tempArr.size() || k >= tempArr.size())
                        break;
                    if (tempArr.get(j).equals(tempArr.get(k))) {
                        tempArr.remove(k);
                    }
                }
            }
            arr = (ArrayList<String>) tempArr.clone();
            tempArr.clear();
            //System.out.println("i=" + i);
            //for (String str : arr) {
            //    System.out.println(str);
            //}
        }

        System.out.println("running time =" + (System.currentTimeMillis() - startTime) + "ms");
//        System.out.println("check same value");
//        for (int i = 0; i < arr.size(); i++) {
//            for (int j = i + 1; j < arr.size(); j++) {
//                if (arr.get(i).equals(arr.get(j)))
//                    System.out.println("sameValue!" + arr.get(i) + "," + arr.get(j));
//            }
//        }
        answer = new BigInteger(String.valueOf(arr.size()));
        return answer;
    }

    public BigInteger parenthesisCase2(int n) {
        long startTime = System.currentTimeMillis();
        BigInteger answer = new BigInteger("0");
        LinkedHashSet<String> hash = new LinkedHashSet<>();
        LinkedHashSet<String> hashSec = new LinkedHashSet<>();
        hash.add("()");
        for(int i = 0 ; i < n-1 ; i ++) {
            Iterator<String> stringIterator = hash.iterator();
            while (stringIterator.hasNext()) {
                String tempStr = stringIterator.next();
                for (int j = 0; j < hash.size(); j++) {
                    hashSec.add("(" + tempStr + ")");
                    hashSec.add("()" + tempStr);
                    hashSec.add(tempStr + "()");
                    //check sameValue
                }
            }
            hash = (LinkedHashSet<String>) hashSec.clone();
            hashSec.clear();
        }

        //Object tempArr[] = hash.toArray();
        //for (Object str : tempArr) {
        //    System.out.println(str);
        //}
        System.out.println("running time =" + (System.currentTimeMillis() - startTime) + "ms");
        //System.out.println("check same value");
        answer = new BigInteger(String.valueOf(hash.size()));
        return answer;
    }

    // 실행을 위한 main입니다.
    public static void main(String[] args) {
        CorrectParenthesis cp = new CorrectParenthesis();
        //18546ms = 12 case 2   7-15
        //7466ms = 11 case 1 7-6
        if (cp.parenthesisCase(7).equals(new BigInteger("5"))) {
            System.out.println("parenthesisCase(3)이 정상 동작합니다. 제출을 눌러서 다른 경우에도 정답인지 확인해 보세요.");
        } else {
            System.out.println("parenthesisCase(3)이 정상 동작하지 않습니다.");
        }
        // 비교 코드
        for(int i = 0 ; i < 12 ; i ++){
            System.out.print(i + " = ");
            cp.parenthesisCase(i);
            System.out.print("\t second = ");
            cp.parenthesisCase2(i);
        }
    }
}
