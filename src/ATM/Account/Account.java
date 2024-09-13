package ATM.Account;

import java.io.Serializable;

//账户类（ATM.Account.Account）,属性并且完全封装（注意:要辨别每个属性的set/get方法是否需要公开）
//id:账户号码 长整数（Long）
//password:账户密码 字符串类型（String）
//name:真实姓名 字符串类型（String）
//personId:身份证号码 字符串类型（String）
//email:客户的电子邮箱 字符串类型（String）
//balance:账户余额 双精度（double）
//type :账户类型 整形（int）
//说明：规定账户类型：0 – 储蓄账户  1 – 信用账户 2 – 可贷款储蓄账户 3– 可贷款信用账户
//方法:
//        deposit:  存款方法,参数类型：double, 返回类型：ATM.Account.Account
//        withdraw:取款方法,参数类型：double, 返回类型：ATM.Account.Account
//        构造方法:
//        有参和无参,有参构造方法用于设置必要的属性
public abstract class Account implements Serializable {
    private Long id;
    private String password;
    private String name;
    private String personId;
    private String email;
    private double balance;
    //type:规定账户类型：0 – 储蓄账户  1 – 信用账户 2 – 可贷款储蓄账户 3– 可贷款信用账户
    private int type;
    public Account(){}
    public Account(Long id, String password, String name, String personId, String email, double balance,int type) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.personId = personId;
        this.email = email;
        this.balance = balance;
        this.type = type;
    }

    //存款
    public final Account deposit(double money){
        if(money>0){
            this.balance +=money;
        }else{
            System.out.printf("请重新存入金额");
        }
        return this;
    }
    //取款
    public abstract Account withdraw(double money);

    public abstract String toString();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
