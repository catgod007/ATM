package ATM;

import java.util.Scanner;

public class TestBank {
    public static void main(String[] args) {
        Bank bank = Bank.getBank();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("***欢迎使用本ATM机***");
            System.out.println("***请选择要使用的功能：***");
            System.out.println("***1.  注册     ***");
            System.out.println("***2.  登录     ***");
            System.out.println("***3.  存款     ***");
            System.out.println("***4.  取款     ***");
            System.out.println("***5.  转账     ***");
            //获取用户输入的数字
            int i = sc.nextInt();
            switch (i) {
                case 1:
                    //注册账户
                    System.out.println("请输入密码：");
                    String password = sc.next();
                    System.out.println("请再次输入确认密码：");
                    String rePassword = sc.next();
                    System.out.println("请输入姓名：");
                    String name = sc.next();
                    System.out.println("请输入身份证号：");
                    String personId = sc.next();
                    System.out.println("请输入邮箱：");
                    String email = sc.next();
                    System.out.println("请输入账户类型：");
                    int type = sc.nextInt();
                    
                    Account acc =  bank.register(password,rePassword,name,personId,email,0.0,type);

                    if(acc != null){
                        System.out.println(acc);
                        System.out.println("注册成功");
                    }else{
                        System.out.println("注册失败");
                    }


                    break;
                case 2:
                    //登录账户

                    break;

                case 3:
                    //存款

                    break;
                case 4:
                    //取款
                    break;
                case 5:
                    //转账
                    break;
            }

        }
    }

}
