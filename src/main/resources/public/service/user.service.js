class UserService {

    constructor($http, $rootScope, $state) {
        this.user = null;
        this.http = $http;
        this.rootScope = $rootScope;
        this.state = $state;

        //URLs
        this.loginUrl = '';
        this.registerUrl = '';
    }

    //TODO Formatiraj podatke za body kad se REST završi
    login(user){
        this.http.post(this.loginUrl, {data:user}).then(r => {
            if (r.data.status==100){
                this.user = r.data.user;
                //TODO Settaj role od usera
                sessionStorage.setItem('auth', "ROLE_ADMIN");
                sessionStorage.setItem('loggedUser', JSON.stringify(this.user));
                this.rootScope.$broadcast('validLogin');
            }
            else {
                //TODO provjeri što se vrati
                this.rootScope.$broadcast('invalidLogin', r.data.message);
            }
        });
    }

    logout(){
        sessionStorage.clear();
        this.rootScope.$broadcast('logout');
        this.state.go('home');
    }

    //TODO Formatiraj podatke za body kad se REST završi
    register(user){
        this.http.post(this.registerUrl, {data:user}).then(r=>{
            if(r.data.status == 200){
                this.rootScope.$broadcast('validRegister');
                this.state.go('login');
            } else {
                this.rootScope.$broadcast('invalidRegister', r.data.message);
            }
        });
    }
    ifLoggedIn(){
        if (JSON.parse(sessionStorage.getItem('loggedUser'))!= null){
            return true;
        }
        else{
            return false;
        }
    }
}
vod.service('UserService', UserService);