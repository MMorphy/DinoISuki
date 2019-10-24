/**
 * 
 */
vod.component('base',{

    template:`
    <div class="container">
    <h1> Test</h1>
    <nav-bar></nav-bar>
    <div class="col-lg-12" ui-view></div>
    
    </div>`,
    controller:function(){
    }
})