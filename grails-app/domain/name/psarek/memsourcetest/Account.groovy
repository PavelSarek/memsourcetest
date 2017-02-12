package name.psarek.memsourcetest

class Account {

    String userName
    String password

    static constraints = {
        userName(blank:false)
        password(blank:false)
    }

}
