<template>
    <div id="app">
        <!--      <el-button type="primary">This is a button!</el-button>-->
        <!--      <IndexContainer></IndexContainer>-->
        <router-view></router-view>
    </div>
</template>

<script>
// import HelloWorld from './components/HelloWorld.vue'

export default {
    name: 'App',
    components: {
        // HelloWorld
    },
    data(){
        return {
            user: JSON.parse(sessionStorage.getItem('curUser')),
        }
    },
    watch: {
        // watch menu in store
        '$store.state.menuList': {
            handler(val, old){
                console.log(val, old)
                // if old menu empty, and user exists
                if(!old && this.user && this.user.email){
                    this.$store.commit("setMenuAfterRefresh", val)
                }
            },
            immediate: true
        }
    }
}
</script>

<style>
#app {
    height: 100%;
}
</style>
