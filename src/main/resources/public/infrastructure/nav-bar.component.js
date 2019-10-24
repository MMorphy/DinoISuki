tvzStore.component('navBar', {
    template:`
            <nav class="navbar navbar-default" role="navigation">
              <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-left">
                    <li><a class="nav-link" ui-sref="home">Home</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                 </ul>
              </div>
            </nav>
   
   `,
    controller:function () {

    },
    controllerAs:'c'
})