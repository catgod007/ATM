public class SavingAccount extends Account{
    public SavingAccount() {
    }

    public SavingAccount(Long id, String password, String name, String personId, String email, double balance,int type) {
        super(id, password, name, personId, email, balance,type);
//        this.setType(0);
    }

}
