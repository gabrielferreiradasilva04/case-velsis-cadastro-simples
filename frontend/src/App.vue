<script setup>
import CustomFooter from './components/CustomFooter.vue';
import UserManager from './components/UserManager.vue';
</script>

<template>
  <div id="app">
    <div class="main-content">
      <UserManager />
    </div>
    <CustomFooter />
  </div>
</template>

<style scoped>
#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.main-content {
  flex: 1;
}
</style>

