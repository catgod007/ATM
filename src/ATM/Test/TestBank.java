package ATM.Test;

import ATM.Account.Account;
import ATM.Bank;

import java.io.IOException;
import java.util.Scanner;

public class TestBank {
    public static void main(String[] args) throws IOException {
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
            System.out.println("***6.  银行账户余额     ***");
            System.out.println("***7.  银行账户透支额度总数     ***");
            System.out.println("***8.  余额排行榜 ***");
            System.out.print("");

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

                    Account acc = bank.register(password, rePassword, name, personId, email, 0.0, type);
                    if (acc != null) {
                        System.out.println(acc);
                        System.out.println("注册成功");
                    } else {
                        System.out.println("注册失败");
                    }
                    break;
                case 2:
                    //登录账户
                    System.out.println("请输入id：");
                    Long id = sc.nextLong();
                    System.out.println("请再次输入密码：");
                    String Password = sc.next();

                    Account loginAccount = bank.login(id,Password);
                    if(loginAccount != null){
                        System.out.println("登录成功");
                    }else {
                        System.out.println("登录失败");
                    }
                    break;
                case 3:
                    //存款
                    System.out.println("输入你的账号: ");
                    Long depositId = sc.nextLong();
                    System.out.println("请输入存款金额： ");
                    double deposit = sc.nextDouble();
                    Account depositAccount = bank.deposit(depositId,deposit);
                    if (depositAccount !=null){
                        System.out.println("账户:"+depositId+" 存入:" +deposit +"元");
                    }else {
                        System.out.println("存款失败");
                    }
                    break;
                case 4:
                    //取款
                    System.out.println("输入你的账号: ");
                    Long withdrawId = sc.nextLong();
                    System.out.println("请输入你的密码： ");
                    String withdrawPassword = sc.next();
                    System.out.println("请输入取款： ");
                    double withdraw = sc.nextDouble();
                    Account withdrawAccount = bank.withdraw(withdrawId,withdrawPassword,withdraw);
                    if(withdrawAccount != null){
                        System.out.println("取款成功");
                    }else {
                        System.out.println("取款失败");
                    }
                    break;
                case 5:
                    //转账
                    System.out.println("输入转出账号: ");
                    Long transferFrom = sc.nextLong();
                    System.out.println("请输入转出账号密码： ");
                    String transferPassword = sc.next();
                    System.out.println("请输入转入账户账号： ");
                    Long transferTo = sc.nextLong();
                    System.out.println("请输入转账金额： ");
                    double money = sc.nextDouble();
                    Boolean transfer = bank.transfer(transferFrom,transferPassword,transferTo,money);
                    if(transfer == true){
                        System.out.println("转账成功");
                    }else {
                        System.out.println("转账失败");
                    }
                    break;
                case 6:
                    System.out.println("银行账户总余额: "+bank.balanceSum());
                    break;
                case 7:
                    System.out.println("银行账户透支额度总数: "+bank.ceilingSum());
                    break;
                case 8:
                    System.out.println("***** 现金排行榜 *****");
                    bank.assetRankingSort();
            }
        }
    }
}
