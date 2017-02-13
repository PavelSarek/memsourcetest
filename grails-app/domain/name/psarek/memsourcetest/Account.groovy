package name.psarek.memsourcetest

class Account {

    String userName
    String password

    static constraints = {
        userName(blank:false)
        password(blank:false)
    }

    boolean valueEquals(Account other) {
        return this.userName == other.userName && this.password == other.password
    }

}
