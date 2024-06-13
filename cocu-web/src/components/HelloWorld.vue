<template>
  <div>Club Management</div>
  <el-form-item>
    <el-button type="primary" @click="addBtn" native-type="submit">submit</el-button>
  </el-form-item>
  <el-icon :size="20">
    <Edit />
  </el-icon>
  <div>
      {{ count }}
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { testStore } from '@/store/test';
// get store
const store = testStore()
// get store value
const count = computed(() =>{
  return store.getCount
})

const addBtn = ()=>{
  //1
  // store.setCount(++store.count)
  //2
  //++store.count
  //3
  //store.$patch({
  //  count:++store.count
  //})
  store.$patch((state)=>{
    state.count = ++state.count
  })
}
</script>

<style scoped>
.read-the-docs {
  color: #888;
}
</style>
